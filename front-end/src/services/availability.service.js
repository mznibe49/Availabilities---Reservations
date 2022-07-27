import axios from 'axios';

const API_URL = 'http://localhost:8080/api/availabilities/';

class availabilityService {

    getAvailabilities() {
        return axios
            .get(API_URL, {})
            .then(response => {
                return response.data;
            });
    }

    createAvailability(start_date, end_date) {
        return axios
            .post(API_URL, {
                start: start_date,
                end:end_date
            })
            .then(response => {
                return response.data;
            });
    }

    deleteAvailability(id) {
        return axios
            .delete(API_URL + id)
            .then(response => {
                return response.data;
            });
    }
}

export default new availabilityService();
