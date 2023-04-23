import axios from "axios";

class ImageService {
    downloadImage(fileName) {
        return axios.get(`http://127.0.0.1:8081/api/download/${fileName}`);
    }
}

export const imageService = new ImageService();
