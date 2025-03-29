package com.notasapp.service;

import com.notasapp.model.Note;
import com.notasapp.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que maneja la lógica de negocio relacionada con las notas.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
@Service
public class NotesService {

    // Repositorio para acceder a la base de datos de notas
    private final NoteRepository noteRepository;

    /**
     * Constructor que inyecta el repositorio de notas.
     * @param noteRepository El repositorio de notas a utilizar
     */
    public NotesService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Obtiene todas las notas de un usuario específico.
     * @param userId El ID del usuario cuyas notas se quieren recuperar
     * @return Lista de notas pertenecientes al usuario
     */
    public List<Note> getAllNotesByUser(String userId) {
        return noteRepository.findByUserId(userId);
    }

    /**
     * Obtiene las notas de un usuario filtradas por estado.
     * @param userId El ID del usuario
     * @param status El estado por el que filtrar las notas
     * @return Lista de notas del usuario que tienen el estado especificado
     */
    public List<Note> getNotesByStatus(String userId, String status) {
        return noteRepository.findByUserIdAndStatus(userId, status);
    }
}