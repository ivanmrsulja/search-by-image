package com.searchbyimage.searchservice.service;

import com.searchbyimage.searchservice.client.ImageProcessingClient;
import com.searchbyimage.searchservice.dto.ImageDisplayDTO;
import com.searchbyimage.searchservice.dto.ProcessedImageDataDTO;
import com.searchbyimage.searchservice.dto.RawImageDTO;
import com.searchbyimage.searchservice.dto.SearchRequestDTO;
import com.searchbyimage.searchservice.exception.ImageProcessingFailedException;
import com.searchbyimage.searchservice.model.Image;
import com.searchbyimage.searchservice.util.ImageUtil;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final ImageUtil imageUtil;

    private final ElasticsearchOperations template;

    private final ImageProcessingClient imageProcessingClient;

    public Page<ImageDisplayDTO> searchForImage(SearchRequestDTO sampleImageUpload,
                                                Pageable pageable)
        throws IOException {
        var base64Image = imageUtil.multipartImageToBase64(sampleImageUpload.getImage());

        ProcessedImageDataDTO processedImageData = new ProcessedImageDataDTO();
        try {
            processedImageData = imageProcessingClient.processImage(new RawImageDTO(base64Image));
        } catch (Exception e) {
            throw new ImageProcessingFailedException(
                "Something went wrong when trying to process image.");
        }

        var queryBuilder = buildQuery(processedImageData.getClasses());

        var searchQueryBuilder = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable);

        if (sampleImageUpload.getHsvSort()) {
            var script = new Script(
                "Math.sqrt(Math.pow(doc.hue.value - " + processedImageData.getHsvColorSpace()[0] +
                    ", 2) + Math.pow(doc.saturation.value - " +
                    processedImageData.getHsvColorSpace()[1] + ", 2))");
            var sort = new ScriptSortBuilder(script, ScriptSortBuilder.ScriptSortType.NUMBER);
            searchQueryBuilder.withSorts(sort);
        }

        var searchQuery = searchQueryBuilder.build();

        var searchHits = template
            .search(searchQuery, Image.class, IndexCoordinates.of("images"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        var page = (Page<Image>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);

        return page.map(image -> new ImageDisplayDTO(image.getFileName()));
    }

    private QueryBuilder buildQuery(List<String> tags) {
        var queryBuilder = QueryBuilders.boolQuery();

        for (var tag : tags) {
            queryBuilder.should(QueryBuilders.matchQuery("tags", tag));
        }

        return QueryBuilders.boolQuery().must(queryBuilder);
    }
}
