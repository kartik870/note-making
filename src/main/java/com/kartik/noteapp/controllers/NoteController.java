package com.kartik.noteapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.kartik.noteapp.dtos.CreateNoteDTO;
import com.kartik.noteapp.dtos.NoteDTO;
import com.kartik.noteapp.services.AuthenticationService;
import com.kartik.noteapp.services.impl.NoteServiceImpl;

import static com.kartik.noteapp.constants.OpenApiConstants.BEARER_AUTH;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteServiceImpl noteService;
    private final AuthenticationService authService;

    public NoteController(NoteServiceImpl noteService, AuthenticationService authService) {
        this.noteService = noteService;
        this.authService = authService;
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes(Authentication authentication) {
        List<NoteDTO> notes = noteService.getAllNotes(authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable String id, Authentication authentication) {
        NoteDTO note = noteService.getNoteById(id, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO noteDTO, Authentication authentication) {
        NoteDTO createdNote = noteService.createNote(noteDTO, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable String id, @RequestBody NoteDTO noteDTO, Authentication authentication) {
        NoteDTO updatedNote = noteService.updateNote(id, noteDTO, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id, Authentication authentication) {
        noteService.deleteNote(id, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @PostMapping("/{id}/share")
    public ResponseEntity<NoteDTO> shareNote(@PathVariable String id, @RequestParam String shareWith, Authentication authentication) {
        NoteDTO sharedNote = noteService.shareNote(id, shareWith, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(sharedNote, HttpStatus.OK);
    }

    @Operation(
            security = @SecurityRequirement(name = BEARER_AUTH)
    )
    @GetMapping("/search")
    public ResponseEntity<List<NoteDTO>> searchByText(@RequestParam String q, Authentication authentication) {
        List<NoteDTO> searchResults = noteService.searchByText(q, authService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}
