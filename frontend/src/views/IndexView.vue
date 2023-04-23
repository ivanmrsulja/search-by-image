<template>
    <v-container style="margin-top: 50px">
        <v-row justify="center">
            <v-col cols="6" md="6" class="header-col">
                <h1>Upload an image</h1>
            </v-col>
        </v-row>
        <v-row>
            <v-col sm="11" cols="9">
                <v-file-input
                    label="File input"
                    filled
                    show-size
                    prepend-icon="mdi-camera"
                    v-model="files"></v-file-input>
            </v-col>
            <v-col sm="1" cols="3">
                <v-btn
                    style="margin-top: 10px"
                    variant="tonal"
                    @click="indexImage()">
                    Upload
                </v-btn>
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
    import { indexService } from "../service/indexService";

    export default {
        name: "navbar",
        setup() {
            const files = ref([]);
            const success = ref(false);
            const error = ref(false);

            const indexImage = () => {
                if (files.value) {
                    let formData = new FormData();

                    formData.append("image", files.value[0]);

                    indexService
                        .indexImage(formData)
                        .then((response) => {
                            success.value = true;
                            files.value = [];
                        })
                        .catch((error) => {
                            error.value = true;
                        });
                } else {
                    console.log("There are no files.");
                }
            };

            return { files, success, error, indexImage };
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
