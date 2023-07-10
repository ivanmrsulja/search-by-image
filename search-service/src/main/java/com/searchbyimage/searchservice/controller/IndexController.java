package com.searchbyimage.searchservice.controller;

import com.searchbyimage.searchservice.dto.ImageUploadDTO;
import com.searchbyimage.searchservice.service.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void indexImage(ImageUploadDTO imagesUpload) throws Exception {
        for (var image : imagesUpload.getImages()) {
            indexingService.indexImage(image);
        }
    }
}
