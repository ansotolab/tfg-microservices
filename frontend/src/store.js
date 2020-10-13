import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    msg: { type: '', text: '' }
  },
  mutations: {
    setMsg(state, msg) {
      state.msg = msg
    }
  },
  getters: {
    msg: state => state.msg
  }
})
  