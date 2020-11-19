<template>
    <span>
    {{ countries.getName(code, "es", {select: "official"}) }}
    </span>
</template>

<script>
import listCountries from "i18n-iso-countries";

export default {
  name: 'Country',

  data: () => ({
    countries: listCountries
  }),

  props: ['code'],

  computed() {
    listCountries.registerLocale(require("i18n-iso-countries/langs/es.json"));
  },
};
</script>