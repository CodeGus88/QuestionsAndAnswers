package com.questionsandanswers.questionsandanswers.services;
import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.services.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.repository.JpaUserInterface;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.services.tools.ListConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de User, peticiones al servidor
 */
@Service
public class UserService {

    @Autowired
    private JpaUserInterface jpaUserInterface;;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Retorna la lista de usuarios
     * @return userList
     */
    public List<UserDto> getUserList() {
         return ListConvert.userToUserDto(jpaUserInterface.findAll());
    }

    /**
     * Retorna el DTO del usuario id
     * @param id
     * @return userDto
     */
    public UserDto getUser(Long id) {
        Optional<User> optional = jpaUserInterface.findById(id);
        Validation.notFound(id, optional.isEmpty());
        return new UserDto(optional.get());
    }

    /**
     * Crea un nuevo usuario
     * @param user
     * return userDto
     */
    public UserDto saveUser(User user) {
        user.setId(0L);
        Validation.validateWhriteUserData(user, jpaUserInterface.findByEmail(user.getEmail()), true);
        try{
            return new UserDto(jpaUserInterface.save(user));
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza un usuario
     * @param user
     */
    public UserDto updateUser(User user) {

        Validation.notFound(user.getId(), jpaUserInterface.existsById(user.getId()));
        Validation.validateWhriteUserData(user, jpaUserInterface.findByEmail(user.getEmail()), false);
        try{
            return new UserDto(jpaUserInterface.save(user));
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Elimina un usuario con id
     * @param id
     */
    public void deleteUser(Long id) {
        Validation.notFound(id, jpaUserInterface.existsById(id));
        jpaUserInterface.deleteById(id);
    }
}
