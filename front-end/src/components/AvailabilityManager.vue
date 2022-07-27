<template>
  <div>
    <h1>Create availability</h1>

    <Form @submit="handleCreation" :validation-schema="creationSchema">

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

    <h1>Delete availability</h1>

    <Form @submit="handleDeletion" :validation-schema="deletionSchema">

      <div class="form-group">
        <label for="av_id">ID</label>
        <Field name="av_id" type="text" class="form-control" />
        <ErrorMessage name="av_id" class="error-feedback" />
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

    <h1>Availabilities</h1>

    <ul id="availabilities">
      <li v-for="(item) in content"  :key="item.start">
        <div>ID : {{ item.id }} - {{ formatDate(item.start) }} - {{ formatDate(item.end) }}</div>
        <div v-if="item.reservations.length !== 0">
          <ul id="reservations">
            <li v-for="(item2, index) in item.reservations"  :key="item2.start">
              Reservation {{ index+1 }} : {{ formatDate(item2.start) }} - {{ formatDate(item2.end) }} - email : {{ item2.email }} - title : {{ item2.eventTitle }}
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>

import availabilityService from "../services/availability.service";
import moment from 'moment';
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "AvailabilityManager",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data(){
    const creationSchema = yup.object().shape({
      start_date: yup.string().required("Start date is required!"),
      end_date:   yup.string().required("End date is required!"),
    });
    const deletionSchema = yup.object().shape({
      av_id: yup.string().required("ID is required"),
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

    handleCreation(availability){
      this.loading = true;

      let start_date = this.formatDate(availability.start_date);
      let end_date = this.formatDate(availability.end_date);

      availabilityService.createAvailability(start_date, end_date).then(
          (response) => {
            this.content = response;
            location.reload();
          },
          (error) => {
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
    handleDeletion(id){
      this.loading = true;
      availabilityService.deleteAvailability(id.av_id).then(
          (response) => {
            this.content = response;
            location.reload();
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
    availabilityService.getAvailabilities().then(
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