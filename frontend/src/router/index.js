import { createRouter, createWebHistory } from "vue-router";
import SearchView from "../views/SearchView.vue";

const routes = [
    {
        path: "/",
        name: "search",
        component: SearchView,
    },
    {
        path: "/index",
        name: "index",
        component: () => import("../views/IndexView.vue"),
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router;
