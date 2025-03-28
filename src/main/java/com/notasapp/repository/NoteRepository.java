package com.notasapp.repository;

import com.notasapp.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByUserId(String userId);
    List<Note> findByUserIdAndStatus(String userId, String status);
}