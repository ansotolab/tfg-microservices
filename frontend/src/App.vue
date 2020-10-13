<template>
  <v-app>
    <v-app-bar
      app
      color="blue"
      dense
    >
      <router-link 
      :to="{ name: 'customers' }"
          style="text-decoration: none">
        <v-toolbar-title
          class="white--text"
        >
          Clientes
        </v-toolbar-title>
      </router-link>
    </v-app-bar>

    <v-main>      
      <Alert />
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import Alert from '@/components/Alert'

export default {
  name: 'App',

  components: {
    Alert
  },

  data: () => ({
    //
  }),
};
</script>
