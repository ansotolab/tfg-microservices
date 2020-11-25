import Vue from 'vue'
import Vuex from 'vuex'

import auth from "./auth.module"
import customers from "./customers.module"
import details from "./details.module"
import images from "./images.module"
import alerts from "./alerts.module"
import users from "./users.module"
import materials from "./materials.module"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    auth,
    customers,
    details,
    images,
    alerts,
    users,
    materials
  }
})
  