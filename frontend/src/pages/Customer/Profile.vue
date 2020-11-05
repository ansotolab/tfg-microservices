<template>
  <v-container>
      <v-card v-if="customer.id">
        <v-card-text>
            <p class="display-1">{{ customer.name }} <v-btn class="ml-2" v-role="'ROLE_ADMIN'" x-small :to="{ name: 'settings', params: { id: customer.id } }"><v-icon class="mr-1" x-small>mdi-pencil</v-icon> Ajustes</v-btn></p>
            <p><b>CIF:</b> {{ customer.cif }}</p>
            <p><b>Dirección:</b> {{ customer.address }}</p>
            <p><b>Código postal:</b> {{ customer.postalCode }}</p>
            <p><b>Ciudad:</b> {{ customer.city }}</p>
            <p><b>País:</b> {{ customer.country }}</p>
            <p><b>Email:</b> {{ customer.email }}</p>
            <p><b>Contactos:</b> {{ customer.people }}</p>
            <p><b>Observaciones:</b> {{ customer.observations }}</p>
        </v-card-text>
      </v-card>
      <v-card v-else>
          <v-card-text>
              Cliente no encontrado
          </v-card-text>
      </v-card>


    <p class="display-1 mt-5">
      Detalles
      <v-btn v-role="'ROLE_ADMIN'" class="ml-2" x-small :to="{ name: 'details', params: { id: customer.id }}"><v-icon class="mr-1" x-small>mdi-pencil</v-icon> Editar</v-btn>
    </p>
    <v-card class="my-5 pa-6">
        <editor-content class="editor__content" :editor="editor" />
    </v-card>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
import { GET_CUSTOMER } from "@/store/actions.type";
import { Editor, EditorContent } from 'tiptap'
import {
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
  Image,
} from 'tiptap-extensions'

export default {
  name: "Profile",
  components: {
    EditorContent
  },
  data: () => ({
    customer: {},
    detail: {},
    editor: new Editor({
      extensions: [
          new Blockquote(),
          new BulletList(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new HorizontalRule(),
          new ListItem(),
          new OrderedList(),
          new TodoItem(),
          new TodoList(),
          new Link(),
          new Bold(),
          new Code(),
          new Italic(),
          new Strike(),
          new Underline(),
          new History(),
          new Image(),
        ],
        content: `<h1>Texto de prueba desde Vue</h1>`,
        editable: false
      })
  }),
  mounted() {
    this.getOne();
  },
  methods: {
    getOne() {
      this.$store
        .dispatch(GET_CUSTOMER, this.$route.params.id)
        .then((response) => {
          this.customer = response.data;
          this.detail = this.customer.detail
          this.editor.setContent(this.customer.detail.text);
          });
    }
  },
  computed: {
    ...mapGetters(["user"])
  },
};
</script>