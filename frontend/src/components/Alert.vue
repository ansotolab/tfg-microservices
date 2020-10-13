<template>
<v-container>
    <v-alert
      class="text-center"
      :type="$store.getters.msg.type"
      dismissible
      border="left"
      @input="closeAlert"
      v-if="$store.getters.msg.text != ''"
      >{{ $store.getters.msg.text }}</v-alert
    >
</v-container>
</template>

<script>
  export default {
    name: 'Alert',
    methods: {
      closeAlert() {
        this.$store.commit('setMsg', { type: '', text: '' } );
      }
    }
  };
</script>