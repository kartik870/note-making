package com.kartik.noteapp.services;

import java.util.List;

import com.kartik.noteapp.dtos.CreateNoteDTO;
import com.kartik.noteapp.dtos.NoteDTO;

public interface NoteService {
    List<NoteDTO> getAllNotes(String userName);

    NoteDTO getNoteById(String noteId, String userName);

    NoteDTO createNote(CreateNoteDTO noteDTO, String userName);

    NoteDTO updateNote(String noteId, NoteDTO noteDTO, String userName);

    void deleteNote(String noteId, String userName);

    NoteDTO shareNote(String noteId, String shareWith, String userName);

    List<NoteDTO> searchByText(String searchText, String userName);
}
