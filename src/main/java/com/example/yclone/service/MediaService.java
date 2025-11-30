package com.example.yclone.service;

import com.example.yclone.models.Image;
import com.example.yclone.models.User;
import com.example.yclone.models.Video;
import com.example.yclone.repository.ImageRepository;
import com.example.yclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MediaService {
    private final MinioService minioService;
    private final VideoRepository videoRepository;
    private final ImageRepository imageRepository;

    public Video uploadVideo(MultipartFile file, User user) throws Exception {

        String fileName = minioService.uploadFile(file);

        String url = "/minio/" + fileName;

        Video video = new Video();
        video.setFileName(fileName);
        video.setContentType(file.getContentType());
        video.setSize(file.getSize());
        video.setUploadedAt(LocalDateTime.now());
        video.setUrl(url);
        video.setUser(user);

        video.setDuration(120.0);
        video.setResolution("1920x1080");
        video.setCodec("H.264");

        return videoRepository.save(video);
    }

    public Image uploadImage(MultipartFile file, User user) throws Exception {

        String fileName = minioService.uploadFile(file);
        String url = "/minio/" + fileName;

        Image image = new Image();
        image.setFileName(fileName);
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setUploadedAt(LocalDateTime.now());
        image.setUrl(url);
        image.setUser(user);

        image.setWidth(1920L);
        image.setHeight(1080L);
        image.setFormat("jpg");

        return imageRepository.save(image);
    }
}
