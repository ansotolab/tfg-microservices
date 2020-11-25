import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import JwtService from "@/common/jwt.service";
//import { API_URL } from "@/common/config";
import { SEND_ALERT } from "@/store/actions.type";
import store from "@/store";

const ApiService = {
  init() {
    Vue.use(VueAxios, axios);
    Vue.axios.defaults.baseURL = process.env.VUE_APP_BACKEND_API;
  },

  setHeader() {
    Vue.axios.defaults.headers.common[
      "Authorization"
    ] = `Bearer ${JwtService.getToken()}`;
  },

  query(resource, params) {
    return Vue.axios.get(resource, params);
  },

  get(resource, slug = "") {
    return Vue.axios.get(`${resource}/${slug}`);
  },

  post(resource, params) {
    return Vue.axios.post(`${resource}`, params);
  },

  postFile(resource, params) {
    return Vue.axios.post(`${resource}`, 
      params, 
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      });
  },

  update(resource, slug, params) {
    return Vue.axios.put(`${resource}/${slug}`, params);
  },

  put(resource, params) {
    return Vue.axios.put(`${resource}`, params);
  },

  delete(resource) {
    return Vue.axios.delete(resource);
  }
};

export default ApiService;

export const CustomersService = {
  get(slug) {
    return ApiService.get("api/customers", slug);
  },
  create(params) {
    return ApiService.post("api/customers", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha creado correctamente el cliente.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  },
  update(slug, params) {
    return ApiService.update("api/customers", slug, params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha actualizado correctamente el cliente.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  },
  destroy(slug) {
    return ApiService.delete(`api/customers/${slug}`)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha eliminado correctamente el cliente.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  }
};

export const DetailsService = {
  update(slug, params) {
    return ApiService.put("api/details/" + slug, params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se han actualizado correctamente los detalles.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  }
};

export const ImagesService = {
  create(params) {
    return ApiService.postFile("api/images/", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se han actualizado correctamente los detalles.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  }
};

export const UsersService = {
  getAuthorities(slug) {
    return ApiService.get("auth/authorities", slug);
  },
  getUsers(slug) {
    return ApiService.get("auth/users", slug);
  },
  registerUser(params) {
    return ApiService.post("auth/register", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha creado correctamente el usuario.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  },
  updateUser(params) {
    return ApiService.post("auth/update", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha actualizado correctamente el usuario.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  }
};

export const MaterialsService = {
  get(slug) {
    return ApiService.get("api/materials", slug);
  },
  getUnits() {
    return ApiService.get("api/units");
  },
  create(params) {
    return ApiService.post("api/materials", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha creado correctamente el material.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  },
};

export const ProductsService = {
  get(slug) {
    return ApiService.get("api/products", slug);
  },
  create(params) {
    return ApiService.post("api/products", params)
      .then((data) => {
        store.dispatch(SEND_ALERT, { msg: 'Se ha creado correctamente el producto.', type: 'success' } );
        return data;
      })
      .catch(() => store.dispatch(SEND_ALERT, { msg: 'Ha habido un error en la operación.', type: 'error' } ) );
  },
};