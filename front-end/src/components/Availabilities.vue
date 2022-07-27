<template>
  <div>
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

export default {
  name: "Availabilities",
  data(){
    return {
      content: [],
    };
  },
  methods: {
      formatDate: function(value) {
       if (value) {
         return moment(String(value)).format('MM/DD/YYYY hh:mm');
       }
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