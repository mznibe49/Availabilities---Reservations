<template>
  <div>
    <h1>Create Reservation</h1>

    <Form @submit="handleCreation" :validation-schema="creationSchema">

      <div class="form-group">
        <label for="av_id">Availability ID</label>
        <Field name="av_id" type="text" class="form-control" />
        <ErrorMessage name="av_id" class="error-feedback" />
      </div>

      <div class="form-group">
        <label for="start_date">Start date</label>
        <Field name="start_date" type="datetime-local" class="form-control" />
        <ErrorMessage name="start_date" class="error-feedback" />
      </div>

      <div class="form-group">
        <label for="end_date">End date</label>
        <Field name="end_date" type="datetime-local" class="form-control" />
        <ErrorMessage name="end_date" class="error-feedback" />
      </div>

      <div class="form-group">
        <label for="creation_email">Email</label>
        <Field name="creation_email" type="email" class="form-control" />
        <ErrorMessage name="creation_email" class="error-feedback" />
      </div>

      <div class="form-group">
        <label for="title">Event title</label>
        <Field name="title" type="text" class="form-control" />
        <ErrorMessage name="title" class="error-feedback" />
      </div>

      <div class="form-group">
        <button class="btn btn-primary btn-block" type="submit" :disabled="loading">
            <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
            ></span>
          <span>create</span>
        </button>
      </div>

      <div class="form-group">
        <div v-if="creationMessage" class="alert alert-danger" role="alert">
          {{ creationMessage }}
        </div>
      </div>
    </Form>

    <h1>Delete reservation</h1>

    <Form @submit="handleDeletion" :validation-schema="deletionSchema">

      <div class="form-group">
        <label for="res_id">ID</label>
        <Field name="res_id" type="text" class="form-control" />
        <ErrorMessage name="res_id" class="error-feedback" />

        <label for="deletion_email">Email</label>
        <Field name="deletion_email" type="email" class="form-control" />
        <ErrorMessage name="deletion_email" class="error-feedback" />
      </div>

      <div class="form-group">
        <button class="btn btn-primary btn-block" type="submit" :disabled="loading">
            <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
            ></span>
          <span>delete</span>
        </button>
      </div>

      <div class="form-group">
        <div v-if="deletionMessage" class="alert alert-danger" role="alert">
          {{ deletionMessage }}
        </div>
      </div>
    </Form>

    <h1>Reservations</h1>

    <ul id="reservations">

      <li v-for="(item) in content"  :key="item.start">
        <div>ID : {{ item.id }} - {{ formatDate(item.start) }} - {{ formatDate(item.end) }} - email : {{ (item.email) }} - title : {{ (item.eventTitle) }}</div>
      </li>

    </ul>
  </div>
</template>

<script>

import reservationService from "../services/reservation.service";
import moment from 'moment';
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "ReservationManager",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data(){
    const creationSchema = yup.object().shape({
      av_id: yup.string().required("Availability ID  is required!"),
      start_date: yup.string().required("Start date is required!"),
      end_date:   yup.string().required("End date is required!"),
      creation_email:   yup.string().required("Email is required!"),
      title:   yup.string().required("Title is required!"),
    });
    const deletionSchema = yup.object().shape({
      res_id: yup.string().required("ID is required"),
      deletion_email: yup.string().required("Email is required"),
    });

    return {
      loading: false,
      creationMessage: "",
      deletionMessage: "",
      creationSchema,
      deletionSchema,
      content: []
    };
  },
  methods: {
    formatDate: function(value) {
      if (value) {
        return moment(String(value)).format('yyyy-MM-DD HH:mm:ss');
      }
    },

    handleCreation(reservation){
      console.log("b4 : ");
      this.loading = true;

      let id = reservation.av_id;
      let start_date = this.formatDate(reservation.start_date);
      let end_date = this.formatDate(reservation.end_date);
      let email = reservation.creation_email;
      let title = reservation.title;

      console.log("id : ",id);
      reservationService.createReservation(id, start_date, end_date, email, title).then(
          (response) => {
            this.content = response;
            location.reload();
          },
          (error) => {
            console.log("err : ");
            this.loading = false;
            this.creationMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          }
      );
    },
    handleDeletion(reservationDeletion){
      this.loading = true;
      reservationService.deleteReservation(reservationDeletion.res_id, reservationDeletion.deletion_email).then(
          (response) => {
            this.content = response;
          },
          (error) => {
            this.loading = false;
            this.deletionMessage =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          }
      );
    }
  },
  mounted() {
    reservationService.getReservations().then(
        (response) => {
          this.content = response;
        },
        (error) => {
          this.content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
        }
    );
  },
}
</script>

<style scoped>

</style>