package com.questionsandanswers.questionsandanswers.repository;


import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para User
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su ID
     * @param id
     * @return user
     */
    List<User> findByEmail(String id);


    /**
     * Verifica si un usuario existe
     */
    boolean existsById(long id);

    //auth
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
