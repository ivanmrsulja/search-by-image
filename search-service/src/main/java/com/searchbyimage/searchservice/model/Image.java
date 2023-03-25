package com.searchbyimage.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "images")
public class Image {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "tags")
    private String tags;

    @Field(type = FieldType.Double, store = true, name = "hue")
    private Double hue;

    @Field(type = FieldType.Double, store = true, name = "saturation")
    private Double saturation;

    @Field(type = FieldType.Text, store = true, name = "file_name")
    private String fileName;
}
