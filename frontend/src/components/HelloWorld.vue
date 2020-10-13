<template>
<v-container>
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
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="validate"
    >
      Validate
    </v-btn>

    <v-btn
      color="error"
      class="mr-4"
      @click="reset"
    >
      Reset Form
    </v-btn>

    <v-btn
      color="warning"
      @click="resetValidation"
    >
      Reset Validation
    </v-btn>

    <v-btn
      color="info"
      @click="getAll"
    >
      Get Customers
    </v-btn>

    <v-btn
      color="info"
      :disabled="!valid"
      @click="send"
    >
      Submit
    </v-btn>
  </v-form>

  <!-- LIST -->
  <v-card
    class="mx-auto"
    max-width="400"
    tile
  >
    <v-list-item
      v-for="(customer, index) in wholeResponse"
      :key="index"
    >
      <v-list-item-content>
        <v-list-item-title>{{ customer.cif }} - {{ customer.name }}</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-card>
</v-container>
</template>

<script>

import axios from "axios"; 
  export default {
    name: 'HelloWorld',
    data: () => ({
      wholeResponse: [],
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
      validate () {
        this.$refs.form.validate()
      },
      reset () {
        this.$refs.form.reset()
      },
      resetValidation () {
        this.$refs.form.resetValidation()
      },
      getAll() {
        axios
        .get('http://localhost:8762/customers/')
        .then(response => {
          this.wholeResponse = response.data
        })
        .catch(error => {
          console.log(error)
        })
      },
      send() {
        axios.post('http://localhost:8762/customers/', {
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
        }).then(response => {
            console.log(response)
        }).catch(e => {
            console.log(e);
        });
      }
    },
  }
</script>