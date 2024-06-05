package com.kartik.noteapp.dtos;

import java.time.LocalDateTime;

public record ErrorDTO(String type, int code, String error, LocalDateTime timestamp) {
}
