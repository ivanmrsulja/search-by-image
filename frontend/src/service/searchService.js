import axios from "axios";

class SearchService {
    searchImages(formData, page, size) {
        return axios.post(
            `${process.env.VUE_APP_BASE_PATH}/search?page=${page}&size=${size}`,
            formData
        );
    }
}

export const searchService = new SearchService();
