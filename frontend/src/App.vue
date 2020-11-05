<template>
  <v-app>
    <v-app-bar
      app
      color="blue"
      dense
      v-if="isAuthenticated"
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
      <div class="text-center">
      <v-menu
        right
        bottom
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item @click="logout">
            <v-list-item-title>Cerrar sesión</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
  </div>
    </v-app-bar>

    <v-main>      
      <Alert />
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import { mapState } from "vuex";
import Alert from '@/components/Alert'
import { LOGOUT } from "@/store/actions.type";

export default {
  name: 'App',

  components: {
    Alert
  },

  data: () => ({
    //
  }),

  methods: {
    logout() {
      this.$store.dispatch(LOGOUT).then(() => {        
        this.$router.push({ name: 'login' })
      })
    }
  },

  computed: {
    ...mapState({
      isAuthenticated: state => state.auth.isAuthenticated
    })
  }
};
</script>
