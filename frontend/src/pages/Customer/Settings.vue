<template>
  <v-container>
    <v-btn color="primary" large :to="{ name: 'edit', id:  this.$route.params.id }"><v-icon class="mr-1" small>mdi-pencil</v-icon> Editar</v-btn>
    <v-btn  @click="dialog = true"><v-icon class="mr-1" small>mdi-delete</v-icon> Eliminar</v-btn>

    <v-dialog
      v-model="dialog"
      persistent
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">
          ¿Eliminar el cliente?
        </v-card-title>
        <v-card-text class="mt-3">Esta operación no es reversible. Se eliminará toda la información personal del cliente, así como pedidos e información de productos.</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            x-large
            @click="dialog=false"
          >
            Cancelar
          </v-btn>
          <v-btn
            color="grey darken-1"
            text
            small
            @click="deleteCustomer"
          >
            Eliminar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { DELETE_CUSTOMER } from "@/store/actions.type";
export default {
  name: "Settings",
  data: () => ({
    dialog: false,
  }),
  methods: {
    deleteCustomer() {
      this.dialog = false;
      this.$store
        .dispatch(DELETE_CUSTOMER, this.$route.params.id)
        .then(() => {
            this.$router.push('/customers/')
        });
    }
  }
};
</script>