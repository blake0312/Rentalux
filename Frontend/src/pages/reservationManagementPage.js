import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RentalClient from "../api/rentalClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ReservationManagementPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderReservation'], this);
        this.dataStore = new DataStore();
        this.client = new RentalClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        const reservation = await this.client.getAllReservations();
        this.dataStore.set("reservations", reservation);
        await this.renderReservation();
    }
    async renderReservation(){
        let resultArea = document.getElementById("Reservation-Management");

        const reservations = this.dataStore.get("reservations");

        if (reservations && reservations.length > 0){
            resultArea.innerHTML = reservations
                .map(
                    (reservation) => `
                        <div class = "card">
                        <a href="reservation.html?reservationId=${reservation.id}" id="id"> Id: ${reservation.id}</a>
                        <div>Customer Id: ${reservation.customerId}</div>
                        <div>Paid: ${reservation.payed} </div>
                        <div>Vehicle Id: ${reservation.vehicleId}</div>
                        <div>Start Date: ${reservation.startData}</div>
                        <div>End Date: ${reservation.endData}</div>
                        <button class ="remove-button" id = "${reservation.id}">Delete</button>
                        </div>
                `
                )
                .join("");
            const removeButtons = resultArea.querySelectorAll('.remove-button');
            removeButtons.forEach((button) => {
                button.addEventListener('click', async () => { const reservationId = button.id;
                    try {
                        await this.client.deleteReservation(reservationId, this.errorHandler);
                        await this.mount();
                    } catch (error) {
                        console.log("delete has failed!", error)
                    }
                })
            })
        }
    }
}



const main = async () => {
    const reservationManagementPage = new ReservationManagementPage();
    await reservationManagementPage.mount();
};

window.addEventListener('DOMContentLoaded', main);