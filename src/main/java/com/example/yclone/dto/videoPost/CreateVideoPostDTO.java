package com.example.yclone.dto.videoPost;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateVideoPostDTO {
    private String title;
    @JsonProperty("video_id")
    private UUID videoId;

    @JsonProperty("thumbnail_id")
    private UUID thumbnailId;

    @JsonProperty("channel_id")
    private UUID channelId;
}
