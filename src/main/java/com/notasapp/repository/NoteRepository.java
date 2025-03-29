package com.notasapp.repository;

import com.notasapp.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repositorio que maneja las operaciones de persistencia para las notas.
 * Extiende MongoRepository para heredar operaciones CRUD básicas.
 */
public interface NoteRepository extends MongoRepository<Note, String> {
    
    /**
     * Busca todas las notas que pertenecen a un usuario específico.
     * @param userId El ID del usuario
     * @return Lista de notas del usuario
     */
    List<Note> findByUserId(String userId);
    
    /**
     * Busca notas de un usuario que tienen un estado específico.
     * @param userId El ID del usuario
     * @param status El estado de las notas a buscar
     * @return Lista de notas filtradas por usuario y estado
     */
    List<Note> findByUserIdAndStatus(String userId, String status);
}