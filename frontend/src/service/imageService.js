import axios from "axios";

class ImageService {
    downloadImage(fileName) {
        return axios.get(
            `${process.env.VUE_APP_BASE_PATH}/download/${fileName}`
        );
    }
}

export const imageService = new ImageService();
