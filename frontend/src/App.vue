<template>
  <v-app>
    <Navbar />
    <v-main>      
      <Alert />
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Alert from '@/components/Alert'
import Navbar from '@/components/Navbar'

export default {
  name: 'App',

  components: {
    Alert,
    Navbar
  },

  data: () => ({
  }),

  methods: {
  },

  computed: {
  }
};
</script>
