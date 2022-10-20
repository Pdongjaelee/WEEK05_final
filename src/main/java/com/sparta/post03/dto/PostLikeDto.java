package com.sparta.post03.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeDto {
    private String postId;
    private String memberId;
}