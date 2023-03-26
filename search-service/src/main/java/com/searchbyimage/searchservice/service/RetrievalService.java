package com.searchbyimage.searchservice.service;

import com.searchbyimage.searchservice.client.ImageProcessingClient;
import com.searchbyimage.searchservice.dto.ImageDisplayDTO;
import com.searchbyimage.searchservice.dto.ImageUploadDTO;
import com.searchbyimage.searchservice.dto.ProcessedImageDataDTO;
import com.searchbyimage.searchservice.dto.RawImageDTO;
import com.searchbyimage.searchservice.exception.ImageProcessingFailedException;
import com.searchbyimage.searchservice.model.Image;
import com.searchbyimage.searchservice.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final ImageUtil imageUtil;

    private final ElasticsearchOperations template;

    private final ImageProcessingClient imageProcessingClient;

    public Page<ImageDisplayDTO> searchForImage(ImageUploadDTO sampleImageUpload, Pageable pageable) throws IOException {
        var base64Image = imageUtil.multipartImageToBase64(sampleImageUpload.getImage());

        ProcessedImageDataDTO processedImageData = new ProcessedImageDataDTO();
        try {
            processedImageData = imageProcessingClient.processImage(new RawImageDTO(base64Image));
        } catch (Exception e) {
            throw new ImageProcessingFailedException("Something went wrong when trying to process image.");
        }

        var queryBuilder = buildQuery(processedImageData.getClasses());

        var searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        var searchHits = template
                .search(searchQuery, Image.class, IndexCoordinates.of("images"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        var page = (Page<Image>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);

        return page.map(image -> new ImageDisplayDTO(image.getFileName()));
    }

    private QueryBuilder buildQuery(List<String> tags) {
        var queryBuilder = QueryBuilders.boolQuery();

        for(var tag: tags) {
            queryBuilder.should(QueryBuilders.matchQuery("tags", tag));
        }

        return QueryBuilders.boolQuery().must(queryBuilder);
    }
}
