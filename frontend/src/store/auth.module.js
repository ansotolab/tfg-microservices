import ApiService from "@/common/api.service";
import JwtService from "@/common/jwt.service";
import {
  LOGIN,
  LOGOUT,
  CHECK_AUTH
} from "./actions.type";
import { SET_AUTH, PURGE_AUTH, SET_ERROR } from "./mutations.type";

const state = {
  errors: null,
  user: {},
  isAuthenticated: !!JwtService.getToken()
};

const getters = {
  currentUser(state) {
    return state.user;
  },
  isAuthenticated(state) {
    return state.isAuthenticated;
  },
  hasRole: state => role => {
    var found = false;
    if (state.user.authorities !== undefined) {
      
      state.user.authorities.forEach( authority => {
          console.log(authority.name)
          if (authority.name == role) {
              found = true;
          }
      })
    }
    console.log("Has role " + role + ": " + found)
    return found
  }
};

const actions = {
  [LOGIN](context, credentials) {
    return new Promise(resolve => {
      ApiService.post("auth/login", credentials)
        .then(({ data }) => {
          context.commit(SET_AUTH, { user: {}, token: data.id_token } );
          resolve(data);
        })
        .catch(({ response }) => {
          context.commit(SET_ERROR, response.data.errors);
        });
    });
  },
  [LOGOUT](context) {
    context.commit(PURGE_AUTH);
  },
  [CHECK_AUTH](context, permission) {
    if (JwtService.getToken()) {
      ApiService.setHeader();
      return new Promise(resolve => {
        ApiService.get("auth/user")
        .then(({ data }) => {
          if (context.state.isAuthenticated) {
            context.commit(SET_AUTH, { user: data, token: JwtService.getToken() });

            var found = false;
            if (context.state.user.authorities !== undefined) {
              
              context.state.user.authorities.forEach( authority => {
                  console.log(authority.name)
                  if (authority.name == permission) {
                      found = true;
                  }
              })
              resolve(found)
            }
          } else {
            resolve(false);
          }
        })
      });
    } else {
      context.commit(PURGE_AUTH);
    }
  }
};

const mutations = {
  [SET_ERROR](state, error) {
    state.errors = error;
  },
  [SET_AUTH](state, { user, token }) {
    state.isAuthenticated = true;
    state.user = user;
    state.errors = {};
    JwtService.saveToken(token);
  },
  [PURGE_AUTH](state) {
    state.isAuthenticated = false;
    state.user = {};
    state.errors = {};
    JwtService.destroyToken();
  }
};

export default {
  state,
  actions,
  mutations,
  getters
};