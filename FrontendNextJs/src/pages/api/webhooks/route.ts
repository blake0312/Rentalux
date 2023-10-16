import { Reservation } from '@/custom components/reservations/tablecustomer';
import { NextApiRequest, NextApiResponse } from 'next';
import { buffer } from 'stream/consumers';
import Stripe from 'stripe';

const stripe = new Stripe(process.env.STRIPE_SECRET_KEY!, {
  apiVersion: '2023-08-16',
});

const handleSuccessfulPayment = async (reservation : Reservation) => {
  try {
    const requestData = {
      "customerId": reservation.customerId,
      "payed": true,
      "vehicleId": reservation.vehicleId,
      "startData": reservation.startData,
      "endData": reservation.endData,
    };

    const url = `http://localhost:5001/rental/reservation/${reservation.id}`;
    const response = await fetch(url, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestData),
    });

    if (!response.ok) {
      console.error('Failed to update reservation:', response.statusText);
    }
  } catch (error) {
    console.error('Error updating reservation:' , error);
    // Handle error as needed
  }
};

export const config = {
  api: {
    bodyParser: false,
  },
}

const webhookHandler = async (req: NextApiRequest, res: NextApiResponse) => {
  const body = await buffer(req);
  const signature = req.headers['stripe-signature']!;


  let event: Stripe.Event

  try {
    event = stripe.webhooks.constructEvent(
      body.toString(),
      signature,
      process.env.STRIPE_WEBHOOK_SECRET!
    )

    
  } catch (err : any) {
    res.status(400).send(`Webhook Error: ${err.message}`)
    return
  }
  
  if (event.type === "checkout.session.completed") {
    // Retrieve the subscription details from Stripe.
    const reservationString = (event.data.object as { metadata?: { reservation?: string } })?.metadata?.reservation;
    const reservation = JSON.parse(reservationString!);

   await handleSuccessfulPayment(reservation);
   res.status(200).json({ message: `${reservation.id}` })
  }
  else{
    res.status(200).json({ message: `${event.type}` })
  }
}
export default webhookHandler;