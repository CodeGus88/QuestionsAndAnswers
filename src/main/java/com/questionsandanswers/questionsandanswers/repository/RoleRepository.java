package com.questionsandanswers.questionsandanswers.repository;
import java.util.Optional;
import com.questionsandanswers.questionsandanswers.models.Role;
import com.questionsandanswers.questionsandanswers.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
