package com.searchbyimage.searchservice.controller;

import com.searchbyimage.searchservice.dto.ImageDisplayDTO;
import com.searchbyimage.searchservice.dto.ImageUploadDTO;
import com.searchbyimage.searchservice.service.RetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final RetrievalService retrievalService;

    @PostMapping
    public Page<ImageDisplayDTO> indexImage(ImageUploadDTO imageUpload, Pageable pageable) throws IOException {
        return retrievalService.searchForImage(imageUpload, pageable);
    }
}
