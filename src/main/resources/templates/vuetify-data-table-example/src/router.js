import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/",
            alias: "/leaves",
            name: "leaves",
            component: () => import("./components/GetLeaves")
        },
        {
            path: "/add",
            name: "add",
            component: () => import("./components/AddLeave")
        },
        {
            path: "/leave-statistics",
            name: "leave-statistics",
            component: () => import("./components/GetLeaveStatistics")
        }
    ]
});