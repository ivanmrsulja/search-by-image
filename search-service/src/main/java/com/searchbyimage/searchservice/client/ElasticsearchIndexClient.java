package com.searchbyimage.searchservice.client;

import com.searchbyimage.searchservice.dto.AcknowledgementResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "elasticsearchIndexClient", url = "http://localhost:9200")
public interface ElasticsearchIndexClient {

    @PostMapping("/applications/_close")
    AcknowledgementResponseDTO closeIndex();

    @PostMapping("/applications/_open")
    AcknowledgementResponseDTO openIndex();

    @DeleteMapping(value = "/images")
    AcknowledgementResponseDTO dropImageIndex();
}
