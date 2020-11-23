<template>
<v-container>
    <p class="display-1">Nuevo Material</p>    
  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="name"
      label="Nombre"
      required
    ></v-text-field>

    <v-select
      v-model="unit"
      :rules="[v => !!v || 'La unidad es obligatoria']"
      label="Unidad"
      :items="units"
      item-text="name"
      item-value="id"
      required
    >
    </v-select>

    <v-btn
      color="info"
      :disabled="!valid"
      @click="send"
    >
      Enviar
    </v-btn>
  </v-form>
</v-container>
</template>

<script>
import { CREATE_MATERIAL, FETCH_UNITS } from "@/store/actions.type";

export default {
  name: 'FormMaterialPage',
  data: () => ({
    valid: true,
    name: '',
    nameRules: [
      v => !!v || 'El nombre es obligatorio',
    ],
    unit: null,
    units: null,
  }),
  mounted() {
    this.getUnits();
  },
  methods: {
    send() {
      console.log(this.unit)
      var material= {
          name: this.name,
          unit: {
            id: this.unit
          }
        }
      this.$store
      .dispatch(CREATE_MATERIAL, material)
      .then(() => {
          this.$router.push('/materials')
      });
    },
    remove () {
      this.unit = ''
    },
    getUnits() {
      this.$store
      .dispatch(FETCH_UNITS)
      .then(response => {
        this.units = response.data
      });
    }
  }
}
</script>