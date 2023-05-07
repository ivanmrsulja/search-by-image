package com.searchbyimage.searchservice.redisrepository;

import com.searchbyimage.searchservice.model.ImageProcessingCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProcessingCacheRepository extends
    CrudRepository<ImageProcessingCache, String>, QueryByExampleExecutor<ImageProcessingCache> {
}
