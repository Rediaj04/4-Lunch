package com.notasapp.service;

import com.notasapp.model.Note;
import com.notasapp.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService {

    private final NoteRepository noteRepository;

    public NotesService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotesByUser(String userId) {
        return noteRepository.findByUserId(userId);
    }

    public List<Note> getNotesByStatus(String userId, String status) {
        return noteRepository.findByUserIdAndStatus(userId, status);
    }
}