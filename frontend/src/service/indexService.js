import axios from "axios";

class IndexService {
    indexImage(formData) {
        return axios.post(`${process.env.VUE_APP_BASE_PATH}/index`, formData);
    }
}

export const indexService = new IndexService();
