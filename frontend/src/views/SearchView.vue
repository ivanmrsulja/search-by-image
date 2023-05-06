<template>
    <v-container style="margin-top: 50px">
        <v-row justify="center">
            <v-col cols="6" md="6" class="header-col">
                <h1>Search by image</h1>
            </v-col>
        </v-row>
        <search-bar></search-bar>
        <v-row v-if="loading">
            <v-col cols="12" style="text-align: center">
                <v-progress-circular :size="50" color="primary" indeterminate />
            </v-col>
        </v-row>
        <search-results :results="results"></search-results>
    </v-container>
</template>

<script>
    import { ref } from "vue";
    import SearchBar from "@/components/search/SearchBar.vue";
    import SearchResults from "@/components/search/SearchResults.vue";
    import { provide } from "vue";

    export default {
        name: "search-view",
        components: { SearchBar, SearchResults },
        setup() {
            const results = ref([]);
            const loading = ref(false);

            const resultsCallback = (searchResults) => {
                results.value = searchResults;
                loading.value = false;
            };

            const startSearchCallback = () => {
                loading.value = true;
            };

            provide("resultsCallback", resultsCallback);
            provide("startSearchCallback", startSearchCallback);

            return {
                results,
                loading,
            };
        },
    };
</script>

<style scoped>
    .header-col {
        text-align: center;
        margin-bottom: 2%;
    }
</style>
