package com.searchbyimage.searchservice.elasticsearchrepository;

import com.searchbyimage.searchservice.model.Image;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends ElasticsearchRepository<Image, String> {
}
