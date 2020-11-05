<template>
<v-container>
    <v-alert type="error" v-if="errorResponse.count">
      Ha habido un error al crear el cliente.
    </v-alert>
    <p class="display-1">Nuevo Cliente</p>    
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
      label="Name"
      required
    ></v-text-field>

    <v-text-field
      v-model="address"
      :counter="100"
      :rules="addressRules"
      label="Address"
      required
    ></v-text-field>

    <v-text-field
      v-model="city"
      :counter="100"
      :rules="cityRules"
      label="City"
      required
    ></v-text-field>

    <v-text-field
      v-model="postalCode"
      :rules="postalCodeRules"
      label="Postal Code"
      required
    ></v-text-field>

    <v-select
      v-model="country"
      :items="countries"
      :rules="[v => !!v || 'Country is required']"
      label="Country"
      required
    ></v-select>

    <v-text-field
      v-model="email"
      :rules="emailRules"
      label="E-mail"
      required
    ></v-text-field>

    <v-text-field
      v-model="phone"
      :rules="phoneRules"
      label="Phone"
      required
    ></v-text-field>

    <v-text-field
      v-model="people"
      :rules="peopleRules"
      label="People"
      required
    ></v-text-field>
    
    <v-text-field
      v-model="observations"
      :rules="observationsRules"
      label="Observations"
      required
    ></v-text-field>

    <v-btn
      color="info"
      :disabled="!valid"
      @click="send"
    >
      Submit
    </v-btn>
  </v-form>
</v-container>
</template>

<script>
import { CREATE_CUSTOMER } from "@/store/actions.type";
// import axios from "axios"; 
  export default {
    name: 'Form',
    data: () => ({
      errorResponse: {},
      valid: true,
      cif: '',
      cifRules: [
        v => !!v || 'CIF is required',
        v => (v && v.length <= 12) || 'CIF must be less than 12 characters',
      ],
      name: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 100) || 'Name must be less than 100 characters',
      ],
      address: '',
      addressRules: [
        v => !!v || 'Address is required',
        v => (v && v.length <= 100) || 'Address must be less than 100 characters',
      ],
      city: '',
      cityRules: [
        v => !!v || 'City is required',
        v => (v && v.length <= 100) || 'City must be less than 100 characters',
      ],
      postalCode: '',
      postalCodeRules: [
        v => (v && v.length <= 10) || 'Address must be less than 10 characters',
      ],
      country: null,
      countries: [
        'Spain',
        'Germany',
        'Italy',
        'France',
      ],
      email: '',
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      phone: '',
      phoneRules: [
        v => (v && v.length <= 10) || 'Phone must be less than 10 characters',
      ],
      people: '',
      peopleRules: [
        v => (v && v.length <= 1000) || 'People must be less than 1000 characters',
      ],
      observations: '',
      observationsRules: [
        v => (v && v.length <= 1000) || 'Observations must be less than 1000 characters',
      ],
    }),
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
        .dispatch(CREATE_CUSTOMER, this.customer)
        .then(response => {
            this.$router.push('/customers/' + response.data.id)
        });
      }
    }
  }
</script>