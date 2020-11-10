import Vue from "vue";
import VueRouter from 'vue-router'
import store from '../store'

import Index from "@/pages/Customer/index.vue";
import Profile from "@/pages/Customer/Profile.vue";
import Edit from "@/pages/Customer/Edit.vue";
import Settings from "@/pages/Customer/Settings.vue";
import Form from "@/pages/Customer/Form.vue";
import DetailsEditor from "@/pages/Customer/DetailsEditor.vue";
import LoginPage from "@/pages/Login/LoginPage.vue";
import NotFoundPage from "@/pages/Error/404.vue";
import ForbiddenPage from "@/pages/Error/403.vue";
import ListUsersPage from "@/pages/User/ListUsersPage.vue";
import RegisterUserPage from "@/pages/User/RegisterUserPage.vue";
import EditUserPage from "@/pages/User/EditUserPage.vue";

import { CHECK_AUTH } from "@/store/actions.type"

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: { name: 'customers' }},
  {
    path: "/customers/",
    name: "customers",
    component: Index,
    meta: {
      authorities: 'VIEW_CUSTOMER'
    }
  },
  {
    path: "/customers/new",
    name: "form",
    component: Form,
    meta: {
      authorities: 'EDIT_CUSTOMER'
    }
  },
  {
    path: "/customers/:id",
    name: "profile",
    component: Profile,
    meta: {
      authorities: 'VIEW_CUSTOMER'
    }
  },
  {
    path: "/customers/:id/edit",
    name: "edit",
    component: Edit,
    meta: {
      authorities: 'EDIT_CUSTOMER'
    }
  },
  {
    path: "/customers/:id/settings",
    name: "settings",
    component: Settings,
    meta: {
      authorities: 'EDIT_CUSTOMER'
    }
  },
  {
    path: "/customers/:id/details",
    name: "details",
    component: DetailsEditor,
    meta: {
      authorities: 'EDIT_CUSTOMER'
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginPage,
  },
  {
    path: "/not-found",
    name: "not-found",
    component: NotFoundPage,
  },
  {
    path: "/forbidden",
    name: "forbidden",
    component: ForbiddenPage,
  },
  {
    path: "/users",
    name: "users",
    component: ListUsersPage,
    meta: {
      authorities: 'EDIT_USER'
    }
  },
  {
    path: "/users/new",
    name: "users-new",
    component: RegisterUserPage,
    meta: {
      authorities: 'EDIT_USER'
    },
  },
  {
    path: "/users/:id/edit",
    name: "users-edit",
    component: EditUserPage,
    meta: {
      authorities: 'EDIT_USER'
    }
  }
];

const router = new VueRouter({
  mode: "history",
  routes
});

// Ensure we checked auth before each page load.
/*router.beforeEach((to, from, next) => {
  Promise.all([store.dispatch(CHECK_AUTH)]).then(next)
});*/

router.beforeEach((to, from, next) => {
  
  if (to.name == "login" && store.getters.isAuthenticated) {
    next('/customers')
  }
  
  if (to.name != "login" && !store.getters.isAuthenticated) {
    next('/login')
  }

  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    store.dispatch(CHECK_AUTH, to.meta.authorities).then(value => {
      if (!value) {
        next('/forbidden');
      } else {
        next();
      }
    });
  } else {
    // no authorities, so just proceed
    next();
  }
});


export default router;