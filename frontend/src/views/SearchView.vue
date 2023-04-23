<template>
    <v-container style="margin-top: 50px">
        <v-row justify="center">
            <v-col cols="6" md="6" class="header-col">
                <h1>Search by image</h1>
            </v-col>
        </v-row>
        <v-row>
            <v-col sm="10" cols="6">
                <v-file-input
                    label="File input"
                    filled
                    show-size
                    prepend-icon="mdi-camera"
                    v-model="files"></v-file-input>
            </v-col>
            <v-col sm="1" cols="4">
                <v-checkbox
                    label="Sort by color space"
                    v-model="useColorSpace"></v-checkbox>
            </v-col>
            <v-col sm="1" cols="2">
                <v-btn
                    style="margin-top: 10px"
                    variant="tonal"
                    @click="searchImages()">
                    Search
                </v-btn>
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
                    :src="
                        'http://127.0.0.1:8081/api/download/' + result.fileName
                    "
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
    </v-container>
</template>

<script>
    import { ref } from "vue";
    import { searchService } from "../service/searchService";
    import { imageService } from "../service/imageService";

    export default {
        name: "navbar",
        setup() {
            const results = ref([]);
            const files = ref([]);
            const useColorSpace = ref(false);

            const searchImages = () => {
                if (files.value) {
                    let formData = new FormData();

                    formData.append("image", files.value[0]);
                    formData.append("hsvSort", useColorSpace.value);

                    searchService
                        .searchImages(formData, 0, 10)
                        .then((response) => {
                            results.value = response.data.content;
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                } else {
                    console.log("There are no files.");
                }
            };

            return { results, files, useColorSpace, searchImages };
        },
    };
</script>

<style scoped>
    .header-col {
        text-align: center;
        margin-bottom: 2%;
    }
</style>
