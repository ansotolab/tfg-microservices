import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from "./router"
import store from './store'
import './directives'

import ApiService from "./common/api.service"
import ErrorFilter from "./common/error.filter"


Vue.filter("error", ErrorFilter);

ApiService.init();

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
