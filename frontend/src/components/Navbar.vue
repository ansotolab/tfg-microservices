<template>
  <v-app-bar
      app
      dense
      dark
      v-if="isAuthenticated"
    >
        <v-btn
          text
          small
          v-for="item in menuItems"
          :key="item.title"
          :to="item.path">
          {{ item.title }}
        </v-btn>
      
      <v-spacer></v-spacer>

      <v-menu
        left
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
          <v-list-item><v-icon class="mr-3">mdi-account</v-icon> <span class="font-weight-bold">{{ user.firstname }}</span></v-list-item>
          <v-divider></v-divider>
          <v-list-item @click="logout">
            <v-list-item-title>Cerrar sesión</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
</template>

<script>
import { mapState } from "vuex";
import { mapGetters } from 'vuex';
import { LOGOUT } from '@/store/actions.type'

  export default {
    name: 'Navbar',

    data: () => ({
      menu: [
            { title: 'Clientes', path: '/customers', icon: 'home', role: 'VIEW_CUSTOMER' },
            { title: 'Usuarios', path: '/users', icon: 'face', role: 'EDIT_USER' },
      ]
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
        isAuthenticated: state => state.auth.isAuthenticated,
        user: state => state.auth.user
      }),
      ...mapGetters({
        hasRole: 'hasRole'
      }),
      menuItems(){
        return this.menu.filter(e => this.hasRole(e.role))
      }
    }
  
  };
</script>