package com.searchbyimage.searchservice.client;

import com.searchbyimage.searchservice.dto.ProcessedImageDataDTO;
import com.searchbyimage.searchservice.dto.RawImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "image-processing-client", url = "http://127.0.0.1:8000")
public interface ImageProcessingClient {

    @PostMapping("/preprocess")
    ProcessedImageDataDTO processImage(@RequestBody RawImageDTO rawImageDTO);
}
