<template>
    <v-row justify="center" v-if="results.length > 0">
        <v-col cols="6" md="6" class="header-col">
            <h1>Results for given sample image</h1>
        </v-col>
    </v-row>
    <v-row cols="12">
        <v-card
            class="mx-auto"
            width="400"
            style="margin-top: 10px"
            v-for="result in results">
            <v-img
                class="align-end text-white"
                height="200"
                width="100%"
                :src="'http://127.0.0.1:8081/api/download/' + result.fileName"
                cover>
            </v-img>

            <v-card-text>
                <div>{{ result.fileName }}</div>
            </v-card-text>

            <v-card-actions>
                <v-btn color="orange"> Download </v-btn>
            </v-card-actions>
        </v-card>
    </v-row>
    <v-row style="margin-top: 50px" v-if="results.length > 0">
        <v-col cols="12">
            <v-pagination
                :length="pages"
                v-model="page"
                @click="changePage"></v-pagination>
        </v-col>
    </v-row>
</template>

<script>
    import { ref } from "vue";
    import { inject } from "vue";

    export default {
        name: "search-results",
        props: ["results", "pages"],
        setup() {
            const page = ref(1);
            const searchCallback = inject("searchCallback");

            const changePage = () => {
                searchCallback(null, page.value - 1);
            };

            return {
                page,
                changePage,
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
