import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RentalClient from "../api/rentalClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ReservationPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderReservation', 'onUpdate'], this);
        this.dataStore = new DataStore();
        this.client = new RentalClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        const urlParams = new URLSearchParams(window.location.search);
        const reservationId = urlParams.get('reservationId');
        const reservation = await this.client.getRental(reservationId, this.errorHandler);
        this.dataStore.set("reservation", reservation);
        await this.renderReservation();
        document.getElementById('update-form').addEventListener('submit', this.onUpdate);
    }

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let reservation = this.dataStore.get("reservation");
        let reservationId = reservation.id;
        let customerId = document.getElementById("customerId").value;
        let paid = document.getElementById("paid").value;
        let vehicleId = document.getElementById("vehicleId").value;
        let startDate = document.getElementById("startDate").value;
        let endDate = document.getElementById("endDate").value;


        const reservedReservation = await this.client.updateReservation(reservationId, customerId, paid, vehicleId, startDate, endDate, this.errorHandler);
        this.dataStore.set("reservation", reservedReservation);

        if (reservedReservation) {
            this.showMessage(`Updating ${reservedReservation.id}!`)
        } else {
            this.errorHandler("Error updating!  Try again...");
        }
    }


    async renderReservation(){
        let resultArea = document.getElementById("reservation");
        const reservation = this.dataStore.get("reservation");
        resultArea.innerHTML =
            `
                        <div class = "card">
                        <form class="card-content" id="update-form">
                        <p class="form-field">
                        <label>Customer Id</label>
                        <input type="text" required class="validated-field" id="customerId" value="${reservation.customerId}">
                        <label>Paid</label>
                        <input type="checkbox" required class="validated-field" id="paid" value="${reservation.payed}">
                        <label>Vehicle Id</label>
                        <input type="text" required class="validated-field" id="vehicleId" value="${reservation.vehicleId}">
                        <label>Start Date</label>
                        <input type="date" required class="validated-field" id="startDate" value="${reservation.startData}">
                        <label>End Date</label>
                        <input type="date" required class="validated-field" id="endDate" value="${reservation.endData}">
                        </p>
                        <button type="submit">Update</button>
                        </form>
                        </div>
                `;

    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const reservationPage = new ReservationPage();
    await reservationPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
