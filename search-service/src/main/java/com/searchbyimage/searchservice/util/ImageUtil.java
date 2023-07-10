package com.searchbyimage.searchservice.util;

import java.io.IOException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtil {

    public String multipartImageToBase64(MultipartFile image) throws IOException {
        var imageBytes = Base64.encodeBase64(image.getBytes());
        return new String(imageBytes);
    }
}
