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
        const reservationId = urlParams.get('id');
        const customerId = urlParams.get('customerId');
        const paid = urlParams.get('paid');
        const vehicleId = urlParams.get('vehicleId');
        const startDate = urlParams.get('startDate');
        const endDate = urlParams.get('endDate');

        this.dataStore.set("id", reservationId);
        this.dataStore.set("customerId", customerId);
        this.dataStore.set("paid", paid);
        this.dataStore.set("vehicleId", vehicleId);
        this.dataStore.set("startDate", startDate);
        this.dataStore.set("endDate", endDate);
        await this.renderReservation();
        document.getElementById('update-form').addEventListener('submit', this.onUpdate);
    }

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let reservationId = this.dataStore.get("id");
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
        let customerId = this.dataStore.get("customerId");
        let paid = this.dataStore.get("paid");
        let vehicleId = this.dataStore.get("vehicleId");
        let startDate = this.dataStore.get("startDate");
        let endDate = this.dataStore.get("endDate");

        resultArea.innerHTML =
            `
                        <div class = "card">
                        <form class="card-content" id="update-form">
                        <p class="form-field">
                        <label>Customer Id</label>
                        <input type="text" required class="validated-field" id="customerId" value="${customerId}">
                        <label>Paid</label>
                        <input type="checkbox" required class="validated-field" id="paid" value="${paid}">
                        <label>Vehicle Id</label>
                        <input type="text" required class="validated-field" id="vehicleId" value="${vehicleId}">
                        <label>Start Date</label>
                        <input type="date" required class="validated-field" id="startDate" value="${startDate}">
                        <label>End Date</label>
                        <input type="date" required class="validated-field" id="endDate" value="${endDate}">
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
