package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * Existe el usuario con el nombre excluyendo el usuario id
     */
    @Query(value = "SELECT CASE WHEN EXISTS ( " +
            "SELECT * FROM users WHERE id <> ?1 AND username = ?2 " +
            ") " +
            "THEN TRUE ELSE FALSE " +
            "END", nativeQuery = true)
    boolean existsByUsernameNotIncludingId(long id, String username);

    /**
     * Existe el usuario con el email excluyendo el usuario id
     */
    @Query(value = "SELECT CASE WHEN EXISTS ( " +
            "SELECT * FROM users WHERE id <> ?1 AND email = ?2 " +
            ") " +
            "THEN TRUE ELSE FALSE " +
            "END", nativeQuery = true)
    boolean existsByEmailNotIncludingById(long id, String email);

    //auth
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
