import Vue from 'vue'

Vue.directive('role', {
    inserted: function (el, binding, vnode) {
      if (binding.value) {
        let hasPermission = vnode.context.$store.getters.hasRole(binding.value)
        if (!hasPermission) {
          el.parentNode.removeChild(el)
        }
      } else {
        console.error('You must specify a permission ID')
      }
    }
})