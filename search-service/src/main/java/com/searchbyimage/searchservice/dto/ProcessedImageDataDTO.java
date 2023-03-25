package com.searchbyimage.searchservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
