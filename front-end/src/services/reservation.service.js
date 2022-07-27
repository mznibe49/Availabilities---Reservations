import axios from 'axios';

const API_URL = 'http://localhost:8080/api/reservations/';

class reservationService {

    getReservations() {
        return axios
            .get(API_URL, {})
            .then(response => {
                return response.data;
            });
    }

    createReservation(id, start, end, email, title) {
        return axios
            .post(API_URL, {
                availabilityId: id,
                start: start,
                end: end,
                email: email,
                title: title,
            })
            .then(response => {
                return response.data;
            });
    }

    deleteReservation(id, email) {
        return axios
            .delete(API_URL + id, {
                data: {
                    email: email
                }
            })
            .then(response => {
                return response.data;
            });
    }
}

export default new reservationService();
