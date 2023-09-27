import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class RentalClient extends BaseClass {

    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getRental', 'createRental', 'getAllVehicles',
            'createReservation', 'updateReservation', 'deleteReservation','getAllReservations'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getRental(id, errorCallback) {
        try {
            const response = await this.client.get(`/rental/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getRental", error, errorCallback)
        }
    }

    async createRental(name, description, retailPrice, mileage, vehicleType, make, images, errorCallback) {
        try {
            const response = await this.client.post(`rental`, {
                "name": name,
                "description": description,
                "retailPrice": retailPrice,
                "mileage": mileage,
                "vehicleType": vehicleType,
                "make": make,
                "images": images
            });
            return response.data;
        } catch (error) {
            this.handleError("createRental", error, errorCallback);
        }
    }

    async getAllVehicles(errorCallback) {
        try {
            const response = await this.client.get(`rental/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllVehicles", error, errorCallback)
        }
    }

    async createReservation(customerId, payed, vehicleId, startData, endData, errorCallback) {
        try {
            const response = await this.client.post(`rental/reservation`, {
                "customerId": customerId,
                "payed": payed,
                "vehicleId": vehicleId,
                "startData": startData,
                "endData": endData,

            });
            return response.data;
        } catch (error) {
            this.handleError("createReservation", error, errorCallback);
        }
    }

    async updateReservation(id, customerId, payed, vehicleId, startData, endData, errorCallback) {
        try {
            const response = await this.client.put(`rental/reservation/${id}`, {
                "customerId": customerId,
                "payed": payed,
                "vehicleId": vehicleId,
                "startData": startData,
                "endData": endData,

            });
            return response.data;
        } catch (error) {
            this.handleError("updateReservation", error, errorCallback);
        }
    }

    async deleteReservation(id, errorCallback) {
        try {
            const response = await this.client.delete(`rental/reservation/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("deleteReservation", error, errorCallback);
        }
    }

    async getAllReservations(errorCallback) {
        try {
            const response = await this.client.get(`rental/reservation/all`);
            return response.data;
        } catch (error) {
            this.handleError("getAllReservations", error, errorCallback)
        }
    }
    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
