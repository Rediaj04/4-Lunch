package com.notasapp.repository;

import com.notasapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repositorio que maneja las operaciones de persistencia para los usuarios.
 * Extiende MongoRepository para heredar operaciones CRUD básicas.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario
     * @return El usuario con ese nombre de usuario, o null si no existe
     */
    User findByUsername(String username);
};
