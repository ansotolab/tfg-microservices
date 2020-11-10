<template>
<v-container>
  <p class="display-1">Editar Cliente</p>    
  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="cif"
      :counter="12"
      :rules="cifRules"
      label="CIF"
      required
    ></v-text-field>

    <v-text-field
      v-model="name"
      :counter="100"
      :rules="nameRules"
      label="Nombre"
      required
    ></v-text-field>

    <v-text-field
      v-model="address"
      :counter="100"
      :rules="addressRules"
      label="Dirección"
      required
    ></v-text-field>

    <v-text-field
      v-model="city"
      :counter="100"
      :rules="cityRules"
      label="Ciudad"
      required
    ></v-text-field>

    <v-text-field
      v-model="postalCode"
      :rules="postalCodeRules"
      label="Código postal"
      required
    ></v-text-field>

    <v-autocomplete
      v-model="country"
      :rules="[v => !!v || 'El pais es obligatorio']"
      label="País"
      :items="countries"
      item-text="country"
      item-value="code"
      required
      auto-select-first
      chips
      clearable
    >
      <template v-slot:selection="data">
        <v-chip
          v-bind="data.attrs"
          :input-value="data.selected"
          close
          @click="data.select"
          @click:close="remove(data.item)"
        >
          <v-avatar left>
            <span :class="'flag-icon flag-icon-' + data.item.code.toLowerCase()"></span>
          </v-avatar>
          {{ data.item.country }}
        </v-chip>
      </template>
      <template v-slot:item="data">
        <v-list-item-content>
          <v-list-item-title><span :class="'flag-icon flag-icon-' + data.item.code.toLowerCase()"></span> {{ data.item.country }}</v-list-item-title>
        </v-list-item-content>
      </template>
    </v-autocomplete>

    <v-text-field
      v-model="email"
      :rules="emailRules"
      label="E-mail"
      required
    ></v-text-field>

    <v-text-field
      v-model="phone"
      :rules="phoneRules"
      label="Teléfono"
      required
    ></v-text-field>

    <v-text-field
      v-model="people"
      :rules="peopleRules"
      label="Personas de contacto"
      required
    ></v-text-field>
    
    <v-text-field
      v-model="observations"
      :rules="observationsRules"
      label="Observaciones"
      required
    ></v-text-field>

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

import { GET_CUSTOMER, EDIT_CUSTOMER } from "@/store/actions.type";
import listCountries from "i18n-iso-countries";
import 'flag-icon-css/css/flag-icon.css'
listCountries.registerLocale(require("i18n-iso-countries/langs/es.json"));

  export default {
    name: 'Edit',
    data: () => ({
      customer: {},
      valid: true,
      cif: '',
      cifRules: [
        v => !!v || 'El CIF es obligatorio',
        v => (v && v.length <= 12) || 'El CIF debe tener menos de 12 caracteres',
      ],
      name: '',
      nameRules: [
        v => !!v || 'El nombre es obligatorio',
        v => (v && v.length <= 100) || 'El nombre debe tener menos de 100 caracteres',
      ],
      address: '',
      addressRules: [
        v => !!v || 'La dirección es obligatoria',
        v => (v && v.length <= 100) || 'La dirección debe tener menos de 100 caracteres',
      ],
      city: '',
      cityRules: [
        v => !!v || 'La ciudad es obligatoria',
        v => (v && v.length <= 100) || 'La ciudad debe tener menos de 100 caracteres',
      ],
      postalCode: '',
      postalCodeRules: [
        v => (v && v.length <= 10) || 'El código postal debe tener menos de 10 caracteres',
      ],
      country: null,
      countries: Object.entries(listCountries.getNames("es", {select: "official"})).map((v) => { return { code: v[0], country: v[1]}}),
      email: '',
      emailRules: [
        v => !!v || 'El E-mail es obligatorio',
        v => /.+@.+\..+/.test(v) || 'El E-mail tiene que ser válido',
      ],
      phone: '',
      phoneRules: [
        v => (v && v.length <= 10) || 'El teléfono debe tener menos de 10 caracteres',
      ],
      people: '',
      peopleRules: [
        v => (v && v.length <= 1000) || 'Las personas de contacto deben tener menos de 1000 caracteres',
      ],
      observations: '',
      observationsRules: [
        v => (v && v.length <= 1000) || 'Las observaciones deben tener menos de 1000 caracteres',
      ],
    }),
    mounted() {
      this.getOne();
    },
    methods: {
      send() {
        this.customer= {
            cif: this.cif,
            name: this.name,
            address: this.address,
            city: this.city,
            postalCode: this.postalCode,
            country: this.country,
            email: this.email,
            phone: this.phone,
            people: this.people,
            observations: this.observations,
          }
        this.$store
        .dispatch(EDIT_CUSTOMER, {
          id: this.$route.params.id,
          customer: this.customer
        })
        .then(response => {
            this.$router.push('/customers/' + response.data.id)
        });
      },
      getOne() {
        this.$store
        .dispatch(GET_CUSTOMER, this.$route.params.id)
        .then((response) => {
            this.customer = response.data
            this.cif = this.customer.cif
            this.name = this.customer.name
            this.address = this.customer.address
            this.city = this.customer.city
            this.postalCode = this.customer.postalCode
            this.country = this.customer.country
            this.email = this.customer.email
            this.phone = this.customer.phone
            this.people = this.customer.people
            this.observations = this.customer.observations
          });
      },
      remove () {
        this.country = ''
      },
    }
  }
</script>