import axios from "axios";

class IndexService {
    indexImage(formData) {
        return axios.post("http://127.0.0.1:8081/api/index", formData);
    }
}

export const indexService = new IndexService();
