package com.searchbyimage.searchservice.service.impl;

import com.searchbyimage.searchservice.exception.ImageStorageException;
import com.searchbyimage.searchservice.service.ImageService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceFileSystemImpl implements ImageService {

    private final String DATA_DIR_PATH = "src/main/resources/data";


    public String saveImage(MultipartFile image) {
        var fileName = "";
        if (!image.isEmpty()) {
            var filenameTokens = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
            var extension = filenameTokens[filenameTokens.length - 1];
            fileName = UUID.randomUUID() + extension;

            byte[] bytes;
            try {
                bytes = image.getBytes();
            } catch (IOException e) {
                throw new ImageStorageException("Cannot extract byte representation from image.");
            }

            var path = Paths.get(DATA_DIR_PATH + File.separator + fileName);
            if (!path.normalize().startsWith(Paths.get(DATA_DIR_PATH).normalize())) {
                throw new IllegalArgumentException(
                    "File saving outside desired directory is not allowed.");
            }

            try {
                Files.write(path, bytes);
            } catch (IOException e) {
                throw new ImageStorageException("Error saving image.");
            }
        }
        return fileName;
    }

    public File getImageFileForDownload(String filename) {
        return new File(Paths.get(DATA_DIR_PATH + File.separator + filename).toString());
    }

    public void deleteSavedImage(String fileName) {
        var path = Paths.get(DATA_DIR_PATH + File.separator + fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new ImageStorageException("Error deleting image.");
        }
    }
}
