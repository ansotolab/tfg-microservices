<template>
<v-container>
  <p class="display-1">Editar usuario</p>    
  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="username"
      :counter="12"
      :rules="usernameRules"
      label="Usuario"
      required
    ></v-text-field>

    <v-text-field
      v-model="firstname"
      :counter="100"
      :rules="firstnameRules"
      label="Nombre"
      required
    ></v-text-field>

    <v-text-field
      v-model="lastname"
      :counter="100"
      :rules="lastnameRules"
      label="Apellidos"
      required
    ></v-text-field>

    <v-text-field
      v-model="email"
      :rules="emailRules"
      label="E-mail"
      required
    ></v-text-field>

    <v-text-field
      v-model="password"
      :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
      :type="'password'"
      name="input-10-1"
      label="Nueva contraseña (opcional)"
    ></v-text-field>

    <v-text-field
      v-model="passwordCheck"
      :type="'password'"
      :rules="[v => password === v|| 'Las contraseñas deben coincidir']"
      name="input-10-1"
      label="Repite la contraseña"
    ></v-text-field>

    <v-checkbox
      v-model="activated"
      label="Activado"
      required
    ></v-checkbox>

    <v-select
      v-model="authorities"
      :items="authoritiesList"
      item-text="name"
      :rules="[v => !!v || 'Los roles son obligatorios']"
      label="Permisos"
      attach
      chips
      required
      multiple
    ></v-select>

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
import { EDIT_USER, GET_USER, FETCH_AUTHORITIES } from "@/store/actions.type";


  export default {
    name: 'Form',
    data: () => ({
      valid: false,
      user: {},
      authoritiesList: [],
      showPass: false,
      id: null,
      username: '',
      usernameRules: [
        v => !!v || 'El nombre de usuario es obligatorio',
        v => (v && v.length <= 12) || 'El nombre de usuario debe tener menos de 12 caracteres.',
      ],
      firstname: '',
      firstnameRules: [
        v => !!v || 'El nombre es obligatorio',
        v => (v && v.length <= 100) || 'El nombre debe tener menos de 100 caracteres.',
      ],
      lastname: '',
      lastnameRules: [
        v => !!v || 'Los apellidos son obligatorios',
        v => (v && v.length <= 100) || 'Los apellidos deben tener menos de 100 caracteres.',
      ],
      email: '',
      emailRules: [
        v => !!v || 'E-mail es obligatorio',
        v => /.+@.+\..+/.test(v) || 'E-mail debe ser válido',
      ],
      password: '',
      passwordCheck: '',
      activated: true,
      authorities: []
    }),
    methods: {
      send() {
        const user = {
            id: this.id,
            username: this.username,
            password: this.password,
            firstname: this.firstname,
            lastname: this.lastname,
            email: this.email,
            activated: this.activated,
            authorities: this.authorities.map((e) => { return { "name": e }; })
          }
        this.$store
        .dispatch(EDIT_USER, user)
        .then(() => {
            this.$router.push('/users/')
        })
        .catch((e) => {
          console.log(e)
        });
      }
    },
    mounted() {
      this.$store.dispatch(FETCH_AUTHORITIES).then(response => { this.authoritiesList = response.data });
      this.$store.dispatch(GET_USER, this.$route.params.id).then(response => { 
        this.user = response.data,
        this.id = response.data.id,
        this.username = response.data.username,
        this.firstname = response.data.firstname,
        this.lastname = response.data.lastname,
        this.email = response.data.email,
        this.activated = response.data.activated,
        this.authorities = response.data.authorities
     });
    }
  }
</script>