import axios from "axios";

class ImageService {
    downloadImage(fileName) {
        axios
            .get(`${process.env.VUE_APP_BASE_PATH}/download/${fileName}`, {
                responseType: "blob",
            })
            .then((response) => {
                this.initialteDownload(response, "jpg");
            })
            .catch(console.error);
    }

    initialteDownload(response, extension) {
        const blob = new Blob([response.data], {
            type: "application/" + extension,
        });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        if (extension === "html") {
            extension = "x" + extension;
        }
        link.download = "download." + extension;
        link.click();
        URL.revokeObjectURL(link.href);
    }
}

export const imageService = new ImageService();
