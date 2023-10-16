import { NextApiRequest, NextApiResponse } from 'next';
import Stripe from 'stripe';


const calculatePaymentAmount = (startDate: string, endDate: string): number => {
  const start = new Date(startDate);
  const end = new Date(endDate);

  const timeDifference = end.getTime() - start.getTime();
  const daysDifference = timeDifference / (1000 * 3600 * 24);

  const paymentAmount = daysDifference;

  return paymentAmount;
};



export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    return res.status(405).end();
  }

  const { reservation } = req.body;

  const startDate = reservation.startData;
  const endDate = reservation.endData;

  const paymentAmount = calculatePaymentAmount(startDate, endDate);

  
  const stripe = new Stripe(process.env.STRIPE_SECRET_KEY!, {
    apiVersion: '2023-08-16',
  });

  const currentUrl = req.headers.referer || `${req.headers.origin}/`;

  const params = {
    mode: 'payment' as const,
    line_items: [
      {
        price: "price_1O1LYcKDcIN9Z8gS8agNdWbd",
        quantity: paymentAmount,
      },
    ],
    metadata: {
        reservation: JSON.stringify(reservation),
    },
    success_url: currentUrl,
    cancel_url: currentUrl,
  };

  try {
    const checkoutSession = await stripe.checkout.sessions.create(params!);
    res.json({ sessionId: checkoutSession.id });
    
  } catch (error) {
    console.error('Error creating Checkout Session:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
}
