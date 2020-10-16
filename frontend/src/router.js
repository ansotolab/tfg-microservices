import Vue from "vue";
import VueRouter from 'vue-router'

import Index from "@/pages/Customer/index.vue";
import Profile from "@/pages/Customer/Profile.vue";
import Edit from "@/pages/Customer/Edit.vue";
import Settings from "@/pages/Customer/Settings.vue";
import Form from "@/pages/Customer/Form.vue";
import DetailsEditor from "@/pages/Customer/DetailsEditor.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: "/customers/",
    name: "customers",
    component: Index
  },
  {
    path: "/customers/new",
    name: "form",
    component: Form
  },
  {
    path: "/customers/:id",
    name: "profile",
    component: Profile
  },
  {
    path: "/customers/:id/edit",
    name: "edit",
    component: Edit
  },
  {
    path: "/customers/:id/settings",
    name: "settings",
    component: Settings
  },
  {
    path: "/customers/:id/details",
    name: "details",
    component: DetailsEditor,
  }
];

const router = new VueRouter({
  mode: "history",
  routes
});

export default router;