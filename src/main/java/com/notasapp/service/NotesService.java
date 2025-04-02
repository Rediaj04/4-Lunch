package com.notasapp.service;

import com.notasapp.model.Note;
import com.notasapp.repository.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

/**
 * Servicio que maneja la lógica de negocio relacionada con las notas.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
@Service
public class NotesService {

    // Repositorio para acceder a la base de datos de notas
    private final NoteRepository noteRepository;
    private final UserService userService;

    /**
     * Constructor que inyecta el repositorio de notas.
     * @param noteRepository El repositorio de notas a utilizar
     * @param userService El servicio de usuarios
     */
    public NotesService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
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

    /**
     * Crea una nueva nota para un usuario verificando que el estado sea válido.
     * @param userId El ID del usuario
     * @param title El título de la nota
     * @param content El contenido de la nota
     * @param status El estado de la nota
     * @return La nota creada, o null si el estado no es válido
     */
    public Note createNote(String userId, String title, String content, String status) {
        // Verificar que el estado sea válido para este usuario usando directamente el User
        if (!userService.isStatusAvailable(userId, status)) {
            return null;
        }

        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(title);
        note.setContent(content);
        note.setStatus(status);
        note.setCreatedAt(new Date());
        note.setUpdatedAt(new Date());

        return noteRepository.save(note);
    }

    /**
     * Actualiza el estado de una nota existente.
     * @param noteId El ID de la nota
     * @param userId El ID del usuario propietario
     * @param newStatus El nuevo estado
     * @return La nota actualizada, o null si no se encuentra o el estado no es válido
     */
    public Note updateNoteStatus(String noteId, String userId, String newStatus) {
        // Verificar que el estado sea válido para este usuario
        if (!userService.isStatusAvailable(userId, newStatus)) {
            return null;
        };

        Note note = noteRepository.findById(noteId).orElse(null);

        if (note != null && note.getUserId().equals(userId)) {
            note.setStatus(newStatus);
            note.setUpdatedAt(new Date());
            return noteRepository.save(note);
        };

        return null;
    };

    /**
     * Actualiza una nota existente.
     * @param note La nota a actualizar
     * @return La nota actualizada, o null si no se encuentra
     */
    public Note updateNote(Note note) {
        Note existingNote = noteRepository.findById(note.getId()).orElse(null);
        if (existingNote != null && existingNote.getUserId().equals(note.getUserId())) {
            return noteRepository.save(note);
        }
        return null;
    }

    /**
     * Elimina una nota por su ID y el ID del usuario propietario.
     * @param noteId El ID de la nota a eliminar
     * @param userId El ID del usuario propietario
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteNote(String noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElse(null);
        if (note != null && note.getUserId().equals(userId)) {
            noteRepository.delete(note);
            return true;
        }
        return false;
    }

    /**
     * Elimina una nota por su título y el ID del usuario propietario.
     * @param title El título de la nota a eliminar
     * @param userId El ID del usuario propietario
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteNoteByTitle(String title, String userId) {
        List<Note> notes = noteRepository.findByUserId(userId);
        for (Note note : notes) {
            if (note.getTitle().equals(title)) {
                noteRepository.delete(note);
                return true;
            }
        }
        return false;
    }
};