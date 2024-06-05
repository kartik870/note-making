package com.kartik.noteapp.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kartik.noteapp.dtos.CreateNoteDTO;
import com.kartik.noteapp.dtos.NoteDTO;
import com.kartik.noteapp.entities.Note;
import com.kartik.noteapp.entities.User;
import com.kartik.noteapp.exception.EntityNotFoundException;
import com.kartik.noteapp.repositories.NoteRepository;
import com.kartik.noteapp.services.NoteService;
import com.kartik.noteapp.utils.UserUtil;

import static com.kartik.noteapp.constants.ExceptionConstants.NOTE_WITH_ID_NOT_FOUND;
import static com.kartik.noteapp.constants.ExceptionConstants.NOTE_WITH_THEMSELVES;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;
    private final UserUtil userUtil;

    public NoteServiceImpl(NoteRepository noteRepository, UserUtil userUtil) {
        this.noteRepository = noteRepository;
        this.userUtil = userUtil;
    }

    @Override
    public List<NoteDTO> getAllNotes(String userName) {
        userUtil.checkIfUserNotExistElseGet(userName);
        return noteRepository.findByAuthor(userName).stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO getNoteById(String noteId, String userName) {
        userUtil.checkIfUserNotExistElseGet(userName);
        Optional<Note> optionalNote = noteRepository.findByNoteIdAndAuthor(noteId, userName);

        if (optionalNote.isEmpty()) {
            throw new EntityNotFoundException(NOTE_WITH_ID_NOT_FOUND);
        }

        return new NoteDTO(optionalNote.get());
    }


    @Override
    public NoteDTO createNote(CreateNoteDTO noteDTO, String userName) {
        User user = userUtil.checkIfUserNotExistElseGet(userName);
        //TODO:check if shared note user is present in DB
        Note note = noteRepository.save(
                Note.builder()
                        .author(user)
                        .content(noteDTO.getContent())
                        .title(noteDTO.getTitle())
                        .sharedWith(noteDTO.getSharedWith())
                        .build()
        );

        return new NoteDTO(note);
    }

    @Override
    public NoteDTO updateNote(String noteId, NoteDTO noteDTO, String userName) {
        userUtil.checkIfUserNotExistElseGet(userName);
        Optional<Note> optionalNote = noteRepository.findByNoteIdAndAuthor(noteId, userName);
        if (optionalNote.isEmpty()) {
            throw new EntityNotFoundException(NOTE_WITH_ID_NOT_FOUND);
        }
        Note existingNote = optionalNote.get();
        existingNote.setTitle(noteDTO.getTitle());
        existingNote.setContent(noteDTO.getContent());
        existingNote.setSharedWith(noteDTO.getSharedWith());

        return new NoteDTO(noteRepository.save(existingNote));
    }

    @Override
    public void deleteNote(String noteId, String userName) {
        userUtil.checkIfUserNotExistElseGet(userName);
        Optional<Note> optionalNote = noteRepository.findByNoteIdAndAuthor(noteId, userName);
        if (optionalNote.isEmpty()) {
            throw new EntityNotFoundException(NOTE_WITH_ID_NOT_FOUND);
        }
        noteRepository.delete(optionalNote.get());
    }

    @Override
    public NoteDTO shareNote(String noteId, String shareWithUser, String userName) {
        if (userName.equals(shareWithUser)) {
            throw new IllegalArgumentException(NOTE_WITH_THEMSELVES);
        }

        userUtil.checkIfUsersExists(List.of(userName, shareWithUser));

        Optional<Note> optionalNote = noteRepository.findByNoteIdAndAuthor(noteId, userName);
        if (optionalNote.isEmpty()) {
            throw new EntityNotFoundException(NOTE_WITH_ID_NOT_FOUND);
        }
        Note note = optionalNote.get();
        Set<String> sharedWithSet = note.getSharedWith();
        sharedWithSet.add(shareWithUser);
        note.setSharedWith(sharedWithSet);
        return new NoteDTO(noteRepository.save(note));
    }

    @Override
    public List<NoteDTO> searchByText(String searchText, String userName) {
        User user = userUtil.checkIfUserNotExistElseGet(userName);
        List<Note> res = noteRepository.findByAuthorAndTextSearch(user.getUserId(), searchText);
        return res.stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
    }
}
