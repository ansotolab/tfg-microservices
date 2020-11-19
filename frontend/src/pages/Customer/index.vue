<template>
  <v-container>
    <p class="display-1">
      Clientes
      <v-btn
        small
        color="normal"
        dark
        right
        :to="{ name: 'form' }"
        v-role="'EDIT_CUSTOMER'"
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
        :items="customers"
        :items-per-page="10"
        class="elevation-1"
        :search="search"
      >

        <template v-slot:[`item.country`]="{ item }">
          <Flag v-bind:code="item.code" /> {{ item.country }}
        </template>
        <template v-slot:[`item.actions`]="{ item }">
            <router-link :to="'/customers/'+item.id" class="btn btn-sm show">
            <v-icon
                small
                class="mr-2"
            >
                mdi-launch
            </v-icon>
            </router-link>
        </template>
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import { FETCH_CUSTOMERS } from "@/store/actions.type";
import Flag from "@/components/Flag";
import listCountries from "i18n-iso-countries";

export default {
  name: "Index",
  components: {
    Flag,
  }, 

  data: () => ({
    countries: listCountries,
    customers: [],
    search: "",
    headers: [
      {
        text: "Nombre",
        align: "start",
        sortable: false,
        value: "name",
      },
      { text: "CIF", value: "cif" },
      { text: "País", value: "country" },
      { 
        text: "", 
        sortable: false,
        value: "actions" 
      },
    ],
  }),
  mounted() {
    listCountries.registerLocale(require("i18n-iso-countries/langs/es.json"));
    this.fetchCustomers();
  },
  methods: {
    fetchCustomers() {
      this.$store
        .dispatch(FETCH_CUSTOMERS)
        .then((response) => {
              this.customers = response.data.map((e) => { return {
                id: e.id,
                name: e.name,
                cif: e.cif,
                code: e.country,
                country: this.countries.getName(e.country, "es", {select: "official"})
              } })
          });
    },
  },
  computed() {
    listCountries.registerLocale(require("i18n-iso-countries/langs/es.json"));
  },
};
</script>