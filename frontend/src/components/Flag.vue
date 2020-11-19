<template>
  <span :class="'flag-icon flag-icon-' + code.toLowerCase()"></span>
</template>

<script>
import 'flag-icon-css/css/flag-icon.css'

export default {
  name: 'Flag',

  data: () => ({
  }),

  props: ['code'],

  computed() {
  },
};
</script>