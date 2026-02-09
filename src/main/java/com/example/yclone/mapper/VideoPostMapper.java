package com.example.yclone.mapper;

import com.example.yclone.dto.image.ImageDTO;
import com.example.yclone.dto.video.VideoDTO;
import com.example.yclone.dto.videoPost.VideoPostDTO;
import com.example.yclone.dto.videoPost.VideoPostDetailDTO;
import com.example.yclone.models.Image;
import com.example.yclone.models.Video;
import com.example.yclone.models.VideoPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.time.Duration;

@Mapper(componentModel = "spring")
public abstract class VideoPostMapper {
    @Autowired
    protected S3Presigner s3Presigner;

    public abstract VideoPostDTO toDTO(VideoPost entity);

    public abstract VideoPostDetailDTO toDetailDTO(VideoPost entity);

    @Mapping(target = "url", expression = "java(generatePresignedUrl(\"yclone\", video.getFileName()))")
    public abstract VideoDTO videoToVideoDTO(Video video);

    @Mapping(target = "url", expression = "java(generatePresignedUrl(\"yclone\", image.getFileName()))")
    public abstract ImageDTO imageToImageDTO(Image image);

    protected String generatePresignedUrl(String bucket, String key) {
        if (key == null) return null;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
