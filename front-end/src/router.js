import { createWebHistory, createRouter } from "vue-router";

import Availabilities from "./components/Availabilities";
import AvailabilityManager from "./components/AvailabilityManager";
import ReservationManager from "@/components/ReservationManager";

const routes = [
    {
        path: "/",
        name: "availabilities",
        component: Availabilities,
    },
    {
        path: "/availabilities",
        name: "availabilities",
        component: Availabilities,
    },
    {
        path: "/availabilityManager",
        name: "availabilityManager",
        component: AvailabilityManager,
    },
    {
        path: "/reservationManager",
        name: "reservationManager",
        component: ReservationManager,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});


export default router;
