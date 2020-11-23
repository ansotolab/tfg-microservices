<template>
  <v-container>
    <p class="display-1">
      Materiales
      <v-btn
        small
        color="normal"
        dark
        right
        :to="{ name: 'materials-new' }"
        v-role="'EDIT_MATERIAL'"
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
        :items="materials"
        :items-per-page="10"
        class="elevation-1"
        :search="search"
      >
      </v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import { FETCH_MATERIALS } from "@/store/actions.type";

export default {
  name: "ListMaterialPage",

  data: () => ({
    materials: [],
    search: "",
    headers: [
      {
        text: "Material",
        align: "start",
        sortable: false,
        value: "name",
      },
      { 
        text: "", 
        sortable: false,
        value: "actions" 
      },
    ],
  }),
  mounted() {
    this.fetchMaterials();
  },
  methods: {
    fetchMaterials() {
      this.$store
        .dispatch(FETCH_MATERIALS)
        .then((response) => {
              this.materials = response.data
          });
    },
  }
};
</script>