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

    async buildURL(reservation) {
        let id = reservation.id;
        let customerId = reservation.customerId;
        let paid = reservation.payed;
        let vehicleId = reservation.vehicleId;
        let startDate = reservation.startData;
        let endDate = reservation.endData;

        return "reservation.html?id=" + id + "&customerId=" + customerId + "&paid=" + paid + "&vehicleId=" + vehicleId +
            "&startDate=" + startDate + "&endDate=" + endDate;

    }
    async renderReservation(){
        let resultArea = document.getElementById("Reservation-Management");

        const reservations = this.dataStore.get("reservations");

        if (reservations && reservations.length > 0){
            const urls = reservations.map(
                async (reservation) => {
                    return await this.buildURL(reservation);
                }
            )
            const urlPromises = await Promise.all(urls);
            resultArea.innerHTML = reservations
                .map(
                    (reservation, index) => {
                        return`
                        <div class = "card">
                        <a href="${urlPromises[index]}" id="id"> Id: ${reservation.id}</a>
                        <div>Customer Id: ${reservation.customerId}</div>
                        <div>Paid: ${reservation.payed} </div>
                        <div>Vehicle Id: ${reservation.vehicleId}</div>
                        <div>Start Date: ${reservation.startData}</div>
                        <div>End Date: ${reservation.endData}</div>
                        <button class ="remove-button" id = "${reservation.id}">Delete</button>
                        </div>
                `;
                    })
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