import { SEND_ALERT } from "./actions.type";
import { SET_MSG } from "./mutations.type";

const state = {
    msg: '',
    type: '',
};
  
const getters = {
    msg(state) {
        return state.msg;
    },
    type(state) {
        return state.type;
    }
};

const actions = {
    [SEND_ALERT](context, alert) {
        context.commit( SET_MSG, alert );
    },
};

const mutations = {
    [SET_MSG](state, alert) {
      state.msg = alert.msg;
      state.type = alert.type;
    }
};

export default {
    state,
    actions,
    mutations,
    getters
};