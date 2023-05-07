package com.searchbyimage.searchservice.service;

import com.searchbyimage.searchservice.dto.ProcessedImageDataDTO;
import com.searchbyimage.searchservice.model.ImageProcessingCache;
import com.searchbyimage.searchservice.redisrepository.ImageProcessingCacheRepository;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final ImageProcessingCacheRepository imageProcessingCacheRepository;

    public ProcessedImageDataDTO retrieveCached(String imageHash) {
        var dataSample = new ImageProcessingCache();
        dataSample.setImageHash(imageHash);

        var imageDataHits = StreamSupport.stream(
                imageProcessingCacheRepository.findAll(Example.of(dataSample)).spliterator(), false)
            .collect(Collectors.toList());

        if (imageDataHits.size() == 0) {
            return null;
        }

        var imageData = imageDataHits.get(0);
        return new ProcessedImageDataDTO(imageData.getTags(), imageData.getHsvColorSpace());
    }

    public void cache(ProcessedImageDataDTO imageData, String imageHash) {
        var cacheData = new ImageProcessingCache();
        cacheData.setTags(imageData.getClasses());
        cacheData.setHsvColorSpace(imageData.getHsvColorSpace());
        cacheData.setImageHash(imageHash);
        imageProcessingCacheRepository.save(cacheData);
    }

    @Scheduled(cron = "0 */5 * ? * *")
    public void clearCache() {
        imageProcessingCacheRepository.deleteAll();
    }
}
