package com.kartik.noteapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.kartik.noteapp.entities.Note;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteDTO {
    private String title;
    private String content;
    private Set<String> sharedWith;

    public CreateNoteDTO(Note note) {
        this.title = note.getTitle();
        this.content = note.getContent();
        this.sharedWith = note.getSharedWith();
    }
}
