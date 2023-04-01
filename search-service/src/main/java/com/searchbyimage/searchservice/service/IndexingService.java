package com.searchbyimage.searchservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.searchbyimage.searchservice.client.ImageProcessingClient;
import com.searchbyimage.searchservice.dto.ImageUploadDTO;
import com.searchbyimage.searchservice.dto.ProcessedImageDataDTO;
import com.searchbyimage.searchservice.dto.RawImageDTO;
import com.searchbyimage.searchservice.exception.ImageProcessingFailedException;
import com.searchbyimage.searchservice.model.Image;
import com.searchbyimage.searchservice.util.ImageUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexingService {

    private final ImageProcessingClient imageProcessingClient;

    private final ImageUtil imageUtil;

    private final ElasticsearchClient elasticsearchClient;

    public void indexImage(ImageUploadDTO imageUpload) throws IOException {
        var fileName = imageUtil.saveImageLocally(imageUpload.getImage());

        var base64Image = imageUtil.multipartImageToBase64(imageUpload.getImage());

        ProcessedImageDataDTO processedImageData = new ProcessedImageDataDTO();
        try {
            processedImageData = imageProcessingClient.processImage(new RawImageDTO(base64Image));
        } catch (Exception e) {
            imageUtil.deleteLocallySavedImage(fileName);
            throw new ImageProcessingFailedException(
                "Something went wrong when trying to process image.");
        }

        var image = new Image();

        StringBuilder tags = new StringBuilder();
        for (var tag : processedImageData.getClasses()) {
            tags.append(tag).append(" ");
        }

        image.setFileName(fileName);
        image.setTags(tags.toString());
        image.setHue(processedImageData.getHsvColorSpace()[0]);
        image.setSaturation(processedImageData.getHsvColorSpace()[1]);

        elasticsearchClient.index(i -> i
            .index("images")
            .id(image.getId())
            .document(image)
        );
    }
}
