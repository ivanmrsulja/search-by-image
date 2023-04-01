package com.searchbyimage.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedImageDataDTO {

    @JsonProperty("classes")
    private List<String> classes;

    @JsonProperty("hsv_color_space")
    private Double[] hsvColorSpace;
}
