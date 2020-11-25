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

    <!-- Material table -->

    <!-- Is pack? -->
    <!-- Pack table -->

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
import { CREATE_PRODUCT, FETCH_MATERIALS, FETCH_PRODUCTS } from "@/store/actions.type";

export default {
  name: 'FormProductPage',
  data: () => ({
    valid: true,
    name: '',
    nameRules: [
      v => !!v || 'El nombre es obligatorio',
    ],
    materials: [],
    materialList: null,
  }),
  mounted() {
    this.getMaterials();
  },
  methods: {
    send() {
      console.log(this.unit)
      var product= {
          // name: this.name,
          // unit: {
          //   id: this.unit
          // }
        }
      this.$store
      .dispatch(CREATE_PRODUCT, product)
      .then(() => {
          this.$router.push('/products')
      });
    },
    remove () {
      this.materials = []
    },
    getMaterials() {
      this.$store
      .dispatch(FETCH_MATERIALS)
      .then(response => {
        this.materials = response.data
      });
    }
  }
}
</script>