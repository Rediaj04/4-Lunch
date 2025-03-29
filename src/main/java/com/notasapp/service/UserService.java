package com.notasapp.service;

import com.notasapp.model.User;
import com.notasapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor que inyecta el repositorio de usuarios.
     * @param userRepository El repositorio de usuarios
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    };

    /**
     * Obtiene o crea un usuario por su nombre de usuario.
     * @param username El nombre de usuario
     * @return El usuario existente o uno nuevo
     */
    public User getOrCreateUser(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            user = new User(username);
            userRepository.save(user);
        };

        return user;
    };

    /**
     * Añade un nuevo estado a la lista de estados disponibles del usuario.
     * @param username El nombre de usuario
     * @param status El nuevo estado a añadir
     * @return true si se añadió correctamente, false si ya existía
     */
    public boolean addStatusToUser(String username, String status) {
        User user = getOrCreateUser(username);
        boolean added = user.addStatus(status);

        if (added) {
            userRepository.save(user);
        };

        return added;
    };

    /**
     * Elimina un estado de la lista de estados disponibles del usuario.
     * @param username El nombre de usuario
     * @param status El estado a eliminar
     * @return true si se eliminó correctamente, false si no se pudo eliminar
     */
    public boolean removeStatusFromUser(String username, String status) {
        User user = getOrCreateUser(username);
        boolean removed = user.removeStatus(status);

        if (removed) {
            userRepository.save(user);
        };

        return removed;
    };

    /**
     * Obtiene la lista de estados disponibles para un usuario.
     * @param username El nombre de usuario
     * @return Lista de estados disponibles
     */
    public List<String> getAvailableStatuses(String username) {
        return getOrCreateUser(username).getAvailableStatuses();
    };

    /**
     * Verifica si un estado está disponible para un usuario.
     * @param username El nombre de usuario
     * @param status El estado a verificar
     * @return true si el estado está disponible, false en caso contrario
     */
    public boolean isStatusAvailable(String username, String status) {
        return getOrCreateUser(username).isStatusAvailable(status);
    };
};
