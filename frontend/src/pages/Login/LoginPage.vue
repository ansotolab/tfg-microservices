<template>
  <v-card
    class="mx-auto pa-4"
    max-width="500"
    >
    <v-card-title
      class="justify-center">
      Inicio sesión
    </v-card-title>

  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="username"
      :rules="usernameRules"
      label="Usuario"
      required
    ></v-text-field>

    <v-text-field
      v-model="password"
      :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
      :rules="passwordRules"
      :type="showPass ? 'text' : 'password'"
      name="input-10-1"
      label="Contraseña"
      @click:append="showPass = !showPass"
    ></v-text-field>

    <v-btn
      :disabled="!valid"
      color="success"
      class="mt-4 mx-auto"
      @click="login"
    >
      Entrar
    </v-btn>
  </v-form>
</v-card>
</template>

<script>
import { LOGIN } from "@/store/actions.type";

export default {
  name: "LoginPage",
  data: () => ({
    showPass: false,
    password: '',
    passwordRules: [
      v => !!v || 'La contraseña es obligatoria'
    ],
    username: '',
    usernameRules: [
      v => !!v || 'El nombre de usuario es obligatorio'
    ],
  }),
  methods: {
    login() {
      this.$store.dispatch(LOGIN, { username: this.username, password: this.password })
      .then(() => this.$router.push("customers"));
    }
  },
};
</script>