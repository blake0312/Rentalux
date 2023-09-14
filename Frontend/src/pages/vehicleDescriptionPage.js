import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RentalClient from "../api/rentalClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class VehicleDescriptionPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderVehicle'], this);
        this.dataStore = new DataStore();
        this.client = new RentalClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        const urlParams = new URLSearchParams(window.location.search);
        const vehicleId = urlParams.get('id');
        const vehicle = await this.client.getRental(vehicleId, this.errorHandler);
        this.dataStore.setVehicle(vehicle);
        await this.renderVehicle();
    }
    async renderVehicle(){
        let resultArea = document.getElementById("Vehicle-Description");
        const vehicle = this.dataStore.getVehicle();
        console.log(vehicle);
            resultArea.innerHTML =
                `
                        <div class = "card">
                        <div id = "id"> Id: ${vehicle.id}</div>
                        <div>Name: ${vehicle.name} </div>
                        <div>Description: ${vehicle.description}</div>
                        <div>RetailPrice: ${vehicle.retailPrice} </div>
                        <div>Mileage: ${vehicle.mileage}</div>
                        <div>Type: ${vehicle.vehicleType}</div>
                        <div>Make: ${vehicle.make}</div
                        <div>Images: ${vehicle.images}</div>
                        </div>
                `;

    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const vehicleDescriptionPage = new VehicleDescriptionPage();
    await vehicleDescriptionPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
