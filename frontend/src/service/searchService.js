import axios from "axios";

class SearchService {
    searchImages(formData, page, size) {
        return axios.post(
            `http://127.0.0.1:8081/api/search?page=${page}&size=${size}`,
            formData
        );
    }
}

export const searchService = new SearchService();
