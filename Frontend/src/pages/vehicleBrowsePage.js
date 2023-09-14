import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RentalClient from "../api/rentalClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class VehicleBrowsePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['renderVehicles'], this);
        this.dataStore = new DataStore();
        this.client = new RentalClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        const vehicles = await this.client.getAllVehicles();
        this.dataStore.setVehicles(vehicles);
        await this.renderVehicles();
    }
    async renderVehicles(){
        let resultArea = document.getElementById("Vehicle-List");

        const vehicles = this.dataStore.getVehicles();

        if (vehicles && vehicles.length > 0){
            resultArea.innerHTML = vehicles
                .map(
                    (vehicle) => `
                        <div class = "card">
                        <div id = "id"> Id: ${vehicle.id}</div>
                        <a href="vehicle-details.html?id=${vehicle.id}">Name: ${vehicle.name} </a>
                        <div>Description: ${vehicle.description}</div>
                        <div>RetailPrice: ${vehicle.retailPrice} </div>
                        <div>Mileage: ${vehicle.mileage}</div>
                        <div>Type: ${vehicle.vehicleType}</div>
                        <div>Make: ${vehicle.make}</div
                        <div>Images: ${vehicle.images}</div>
                        </div>
                `
                )
                .join("");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const vehicleBrowsePage = new VehicleBrowsePage();
    await vehicleBrowsePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
