<template>
    <v-container style="margin-top: 50px">
        <v-row justify="center">
            <v-col cols="6" md="6" class="header-col">
                <h1>Upload an image</h1>
            </v-col>
        </v-row>
        <index-bar></index-bar>
        <v-row v-if="loading">
            <v-col cols="12" style="text-align: center">
                <v-progress-circular :size="50" color="primary" indeterminate />
            </v-col>
        </v-row>
        <v-row v-if="success">
            <v-col cols="12" class="success-msg">
                <h1>Indexed Successfully!</h1>
            </v-col>
        </v-row>
        <v-row v-if="error">
            <v-col cols="12" class="error-msg">
                <h1>Something went wrong, reload the page and try again!</h1>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import { ref } from "vue";
    import IndexBar from "@/components/index/IndexBar.vue";
    import { provide } from "vue";

    export default {
        name: "index-view",
        components: { IndexBar },
        setup() {
            const success = ref(false);
            const error = ref(false);
            const loading = ref(false);

            const successCallback = (ok) => {
                if (ok) {
                    success.value = true;
                    error.value = false;
                } else {
                    success.value = false;
                    error.value = true;
                }

                loading.value = false;
            };

            const startIndexCallback = () => {
                loading.value = true;
            };

            provide("successCallback", successCallback);
            provide("startIndexCallback", startIndexCallback);

            return { success, error, loading };
        },
    };
</script>

<style scoped>
    .header-col {
        text-align: center;
        margin-bottom: 2%;
    }

    .success-msg {
        text-align: center;
        color: green;
    }

    .error-msg {
        text-align: center;
        color: red;
    }
</style>
