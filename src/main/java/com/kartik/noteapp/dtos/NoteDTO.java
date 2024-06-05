package com.kartik.noteapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.kartik.noteapp.entities.Note;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private String title;
    private String content;
    private String authorId;
    private Set<String> sharedWith;

    public NoteDTO(Note note) {
        this.title = note.getTitle();
        this.content = note.getContent();
        this.authorId = note.getAuthor().getUserId();
        this.sharedWith = note.getSharedWith();
    }
}
