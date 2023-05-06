<template>
    <v-row>
        <v-col sm="11" cols="9">
            <v-file-input
                label="File input"
                filled
                show-size
                multiple
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
</template>

<script>
    import { ref, inject } from "vue";
    import { indexService } from "../../service/indexService";

    export default {
        name: "index-bar",
        setup() {
            const files = ref([]);
            const successCallback = inject("successCallback");
            const startIndexCallback = inject("startIndexCallback");

            const indexImage = () => {
                if (files.value) {
                    startIndexCallback();
                    let formData = new FormData();

                    for (let i = 0; i < files.value.length; i++) {
                        formData.append("images[]", files.value[i]);
                    }

                    indexService
                        .indexImage(formData)
                        .then((response) => {
                            successCallback(true);
                            files.value = [];
                        })
                        .catch((err) => {
                            successCallback(false);
                        });
                }
            };

            return { files, indexImage };
        },
    };
</script>
