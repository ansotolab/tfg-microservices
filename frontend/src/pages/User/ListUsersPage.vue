<template>
  <v-container>
    <p class="display-1">
      Usuarios
      <v-btn
        small
        color="normal"
        dark
        right
        :to="{ name: 'users-new' }"
        v-role="'ROLE_ADMIN'"
      >
        <v-icon  class="mr-1" small>mdi-plus-box</v-icon>
        Crear 
      </v-btn>
      </p>
    
    <v-card>
      <v-card-title>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="users"
        :items-per-page="10"
        class="elevation-1"
        :search="search"
      >
        <template v-slot:[`item.actions`]="{ item }">
            <router-link :to="'/user/'+item.id+'/edit'" class="btn btn-sm show">
            <v-icon
                small
                class="mr-2"
            >
                mdi-grease-pencil
            </v-icon>
            </router-link>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import { FETCH_USERS } from "@/store/actions.type";

export default {
  name: "ListUsersPage",
  data: () => ({
    users: [],
    search: "",
    headers: [
      {
        text: "Usuario",
        align: "start",
        sortable: false,
        value: "username",
      },
      { text: "Nombre", value: "firstname" },
      { text: "Apellidos", value: "lastname" },
      { text: "Email", value: "email" },
      { 
        text: "", 
        sortable: false,
        value: "actions" 
      },
    ],
  }),
  mounted() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      this.$store
        .dispatch(FETCH_USERS)
        .then((response) => {
          this.users = response.data
          });
    },
  },
};
</script>