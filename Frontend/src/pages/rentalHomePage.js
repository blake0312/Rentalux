import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import RentalClient from "../api/rentalClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class RentalHomePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate'], this);
        this.dataStore = new DataStore();
        this.client = new RentalClient();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new RentalClient();
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("rental", null);

        let result = await this.client.getRental(id, this.errorHandler);
        this.dataStore.set("rental", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("rental", null);

        let name = document.getElementById("create-name-field").value;
        let description = document.getElementById("create-description-field").value;
        let retailPrice = document.getElementById("create-retailPrice-field").value;
        let mileage = document.getElementById("create-mileage-field").value;
        let vehicleType = document.getElementById("create-vehicleType-field").value;
        let make = document.getElementById("create-make-field").value;
        let imagesString = document.getElementById("create-images-field").value;
        let images = imagesString.split('\n');
        images = images.map(value => value.trim());

        const createdRental = await this.client.createRental(name, description, retailPrice, mileage, vehicleType, make, images, this.errorHandler);
        this.dataStore.set("rental", createdRental);

        if (createdRental) {
            this.showMessage(`Created ${createdRental.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }


}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const rentalHomePage = new RentalHomePage();
    await rentalHomePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
