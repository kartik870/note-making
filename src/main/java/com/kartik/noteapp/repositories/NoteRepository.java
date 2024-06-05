package com.kartik.noteapp.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kartik.noteapp.entities.Note;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByAuthor(String userName);

    Optional<Note> findByNoteIdAndAuthor(String noteId, String userName);

    @Aggregation(pipeline = {
            "{$match: {'author': ?0, $text: { $search: ?1 } }}",
            "{$sort: {score: { $meta: 'textScore' }}}",
    })
    List<Note> findByAuthorAndTextSearch(String userName, String searchText);

}
