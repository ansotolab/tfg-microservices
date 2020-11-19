<template>
<v-container>
    <v-alert
      class="text-center"
      :type="type"
      dismissible
      border="left"
      @input="closeAlert"
      v-if="msg != ''"
      >{{ msg }}</v-alert
    >
</v-container>
</template>

<script>
import { mapState } from "vuex";
import { SEND_ALERT } from "@/store/actions.type"

  export default {
    name: 'Alert',

    methods: {
      closeAlert() {
        this.$store.dispatch(SEND_ALERT, { msg: '', type: '' } );
      }
    },

    computed: {
    ...mapState({
      msg: state => state.alerts.msg,
      type: state => state.alerts.type
    })
  }
  };
</script>