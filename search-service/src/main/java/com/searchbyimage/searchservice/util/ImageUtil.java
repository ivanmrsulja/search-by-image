package com.searchbyimage.searchservice.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtil {

    private final String DATA_DIR_PATH = "src/main/resources/data";

    public String multipartImageToBase64(MultipartFile image) throws IOException {
        var imageBytes = Base64.encodeBase64(image.getBytes());
        return new String(imageBytes);
    }

    public String saveImageLocally(MultipartFile image) throws IllegalStateException, IOException {
        var fileName = "";
        if (!image.isEmpty()) {
            fileName = UUID.randomUUID() + ".jpg";
            var bytes = image.getBytes();
            var path = Paths.get(DATA_DIR_PATH + File.separator + fileName);

            Files.write(path, bytes);
        }
        return fileName;
    }

    public File getImageFileForDownload(String filename) {
        return new File(Paths.get(DATA_DIR_PATH + File.separator + filename).toString());
    }

    public void deleteLocallySavedImage(String fileName) throws IllegalStateException, IOException {
        var path = Paths.get(DATA_DIR_PATH + File.separator + fileName);
        Files.delete(path);
    }
}
