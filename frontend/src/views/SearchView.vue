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
        <search-results :results="results" :pages="pages"></search-results>
    </v-container>
</template>

<script>
    import { ref } from "vue";
    import SearchBar from "@/components/search/SearchBar.vue";
    import SearchResults from "@/components/search/SearchResults.vue";
    import { searchService } from "../service/searchService";
    import { provide } from "vue";

    export default {
        name: "search-view",
        components: { SearchBar, SearchResults },
        setup() {
            const results = ref([]);
            const loading = ref(false);
            const cachedFormData = ref(null);
            const pages = ref(0);
            const RESULTS_PER_PAGE = 5;

            const clearResultsCallback = (searchResults) => {
                results.value = searchResults;
            };

            const startSearchCallback = () => {
                loading.value = true;
            };

            const searchCallback = (formData, page) => {
                if (formData === null) {
                    formData = cachedFormData.value;
                }
                searchService
                    .searchImages(formData, page, RESULTS_PER_PAGE)
                    .then((response) => {
                        cachedFormData.value = formData;
                        results.value = response.data.content;
                        pages.value = response.data.totalPages;
                        loading.value = false;
                        resultsCallback();
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            };

            provide("searchCallback", searchCallback);
            provide("clearResultsCallback", clearResultsCallback);
            provide("startSearchCallback", startSearchCallback);

            return {
                results,
                loading,
                pages,
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
