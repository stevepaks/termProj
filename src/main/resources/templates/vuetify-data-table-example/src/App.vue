<template>
  <v-app>
    <v-app-bar app dark>

      <v-btn to="/leaves" text>
        Leaves
      </v-btn>

      <v-btn to="/add" text>
        Add
      </v-btn>

      <v-btn to="/leave-statistics" text>
        Leave Statistics
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
  export default {
    name: "app",
  };
</script>