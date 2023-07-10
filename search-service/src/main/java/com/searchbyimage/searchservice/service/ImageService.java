package com.searchbyimage.searchservice.service;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveImage(MultipartFile image);

    File getImageFileForDownload(String filename);

    void deleteSavedImage(String fileName);
}
