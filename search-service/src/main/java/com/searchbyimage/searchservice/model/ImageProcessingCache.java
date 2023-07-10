package com.searchbyimage.searchservice.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "image", timeToLive = 300)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProcessingCache implements Serializable {

    @Id
    private String id;

    @Indexed
    private String imageHash;

    private List<String> tags;

    private Double[] hsvColorSpace;
}
