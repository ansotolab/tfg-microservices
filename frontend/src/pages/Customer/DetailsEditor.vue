<style>
img {
  width: 100% !important;
  max-width: 500px !important;
}
</style>

<template>
<v-container>
  <p class="display-1">

    <router-link :to="'/customers/'+this.$route.params.id" class="btn btn-md show">
    <v-icon
        medium
        class="mr-2"
    >
        mdi-arrow-left
    </v-icon>
    </router-link>
    {{ customer.name }}
  </p>
  <div class="editor">
    <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
      <v-row align="center"
      justify="center">

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.bold() }"
          @click="commands.bold"
        >
          <v-icon small>mdi-format-bold</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.italic() }"
          @click="commands.italic"
        >
          <v-icon small>mdi-format-italic</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.strike() }"
          @click="commands.strike"
        >
         <v-icon small>mdi-format-strikethrough</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.underline() }"
          @click="commands.underline"
        >
          <v-icon small>mdi-format-underline</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.code() }"
          @click="commands.code"
        >
          <v-icon small>mdi-code-tags</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.paragraph() }"
          @click="commands.paragraph"
        >
          <v-icon small>mdi-format-paragraph</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 1 }) }"
          @click="commands.heading({ level: 1 })"
        >
          <v-icon small>mdi-format-header-1</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 2 }) }"
          @click="commands.heading({ level: 2 })"
        >
          <v-icon small>mdi-format-header-2</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 3 }) }"
          @click="commands.heading({ level: 3 })"
        >
          <v-icon small>mdi-format-header-3</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.bullet_list() }"
          @click="commands.bullet_list"
        >
          <v-icon small>mdi-format-list-bulleted</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.ordered_list() }"
          @click="commands.ordered_list"
        >
          <v-icon small>mdi-format-list-numbered</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.blockquote() }"
          @click="commands.blockquote"
        >
          <v-icon small>mdi-format-quote-close</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          :class="{ 'is-active': isActive.code_block() }"
          @click="commands.code_block"
        >
          <v-icon small>mdi-code-tags</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          @click="commands.horizontal_rule"
        >
          <v-icon small>mdi-minus</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          @click="commands.undo"
        >
          <v-icon small>mdi-undo</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          @click="commands.redo"
        >
          <v-icon small>mdi-redo</v-icon>
        </v-btn>

        <v-btn x-small
          class="menubar__button"
          @click="dialog = true"
        >
          <v-icon small>mdi-image</v-icon>
        </v-btn>
<v-dialog
      v-model="dialog"
      persistent
      max-width="400"
    >
      <v-card>
        <v-card-title class="headline">
          Subir una imagen
        </v-card-title>
        <v-card-text>
          <v-file-input
            accept="image/*"
            label="Archivo adjunto"
            v-model="chosenFile"
          ></v-file-input>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="grey darken-1"
            text
            small
            @click="dialog = false"
          >
            Cancelar
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            x-large
            @click="uploadFile(commands.image)"
          >
            Aceptar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
      </v-row>
    </editor-menu-bar>

    

    <v-card class="my-5 pa-6" style="max-height: 1000px; min-height: 250px; overflow-y: scroll;">
        <editor-content class="editor__content" :editor="editor" />
    </v-card>
    <v-row align="center"
      justify="center">

        <v-btn
          class="menubar__button"
          :disabled="!saved"
          @click="saveDetails"
        >
          <v-icon small class="mr-2">mdi-content-save</v-icon>
          Guardar
        </v-btn>
    </v-row>
  </div>
</v-container>
</template>

<script>
import { GET_CUSTOMER, EDIT_DETAILS, FETCH_IMAGE } from "@/store/actions.type";
import { Editor, EditorContent, EditorMenuBar } from 'tiptap'
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
  components: {
    EditorContent,
    EditorMenuBar,
  },
  data() {
    return {
      dialog: false,
      saved: true,
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
        content: ``,
      }),
    }
  },
  mounted() {
    this.getOne();
    this.interval = setInterval(() => this.checkDifference(), 1000);
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
    },
    checkDifference() {
      if (this.detail.text == this.editor.getHTML()) {
        this.saved = false;
      } else {
        this.saved = true;
      }
    },
    saveDetails() {
      this.$store
        .dispatch(EDIT_DETAILS, { 
          slug: this.detail.id,
          details: {
            id: this.detail.id,
            text: this.editor.getHTML(),
            customer: {
              id: this.customer.id
            }
          }
        })
    },
    uploadFile(command) {
      this.dialog = false;
      if (this.chosenFile) {
        let formData = new FormData();
        //axios post
        formData.append("file", this.chosenFile, this.chosenFile.name)
        this.$store
          .dispatch(FETCH_IMAGE,formData)
          .then(response => {
            const src = process.env.VUE_APP_BACKEND_API + "api/images/" + response.data.id
            command({ src })
          });
        /*axios.post("http://localhost:8762/api/images/", formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          },
        }).then(response => {
            this.$store.commit('setMsg', { type: 'success', text: 'La imagen creada tiene id' + response.data.id + '.' });
            console.log(response)
            const src = "http://localhost:8762/api/images/" + response.data.id
            command({ src })
        }).catch(e => {
            this.$store.commit('setMsg', { type: 'error', text: 'Ha habido un error en la operación.' });
            this.errorResponse = e;
        });*/
      }
    }
  },
  beforeDestroy() {
    this.editor.destroy()
  },
}
</script>

<style>
    .is-active {
        background-color: yellow !important;
    }
</style>