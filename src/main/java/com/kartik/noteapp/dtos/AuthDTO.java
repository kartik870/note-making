package com.kartik.noteapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthDTO {
    //TODO:Add @JsonView here
    private String userId;
    private String userName;
    private String accessToken;
    private LocalDateTime createdAt;
}
