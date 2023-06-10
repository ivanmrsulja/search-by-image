<template>
    <v-row>
        <v-col sm="11" cols="10">
            <v-file-input
                label="File input"
                filled
                show-size
                prepend-icon="mdi-camera"
                v-model="files"
                @change="onFileSelected"></v-file-input>
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
    <v-row style="margin-top: -50px; margin-left: -21px">
        <v-col cols="12">
            <v-checkbox
                label="Sort by color space"
                v-model="useColorSpace"></v-checkbox>
        </v-col>
    </v-row>
    <v-row justify="center">
        <div v-if="imageUrl" class="upload-preview">
            <img :src="imageUrl" alt="Uploaded Image" />
        </div>
    </v-row>
</template>

<script>
    import { ref } from "vue";

    import { inject } from "vue";

    export default {
        name: "search-bar",
        setup() {
            const searchCallback = inject("searchCallback");
            const clearResultsCallback = inject("clearResultsCallback");
            const startSearchCallback = inject("startSearchCallback");
            const files = ref([]);
            const useColorSpace = ref(false);

            const imageUrl = ref(null);
            const selectedFile = ref(null);

            const onFileSelected = (event) => {
                if (event.target.files.length === 0) {
                    return;
                }
                selectedFile.value = event.target.files[0];
                imageUrl.value = URL.createObjectURL(selectedFile.value);
                clearResultsCallback([]);
            };

            const searchImages = () => {
                if (!files.value) {
                    return;
                }

                startSearchCallback();

                let formData = new FormData();
                formData.append("image", files.value[0]);
                formData.append("hsvSort", useColorSpace.value);

                searchCallback(formData, 0);
            };

            return {
                files,
                useColorSpace,
                imageUrl,
                searchImages,
                onFileSelected,
            };
        },
        beforeUnmount() {
            URL.revokeObjectURL(this.imageUrl);
        },
    };
</script>

<style scoped>
    .upload-preview {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 20px;
    }

    .upload-preview img {
        max-width: 100%;
        max-height: 300px;
        border-radius: 5px;
        box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
    }
</style>
