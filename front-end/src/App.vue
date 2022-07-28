<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <div class="navbar-nav mr-auto">

        <li class="nav-item">
          <router-link to="/availabilities" class="nav-link">Availabilities</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/availabilityManager" class="nav-link">Availability-Manager </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/reservationManager" class="nav-link">Reservation-Manager</router-link>
        </li>

      </div>
    </nav>

    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  computed: {

  },
  methods: {

  }
};
</script>
