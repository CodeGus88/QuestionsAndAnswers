package com.questionsandanswers.questionsandanswers.services;
import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.repository.UserRepository;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.services.tools.ListConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de User, peticiones al servidor
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Retorna la lista de usuarios
     * @return userList
     */
    public List<UserDto> getUserList() {
         return ListConvert.userToUserDto(userRepository.findAll());
    }

    /**
     * Retorna el DTO del usuario id
     * @param id
     * @return userDto
     */
    public UserDto getUser(Long id) {
        Optional<User> optional = userRepository.findById(id);
        Validation.notFound(id, optional.isEmpty());
        return new UserDto(optional.get());
    }

    /**
     * Crea un nuevo usuario
     * @param user
     * return userDto
     */
    public UserDto saveUser(User user) {
        Validation.validateWhriteUserData(user, userRepository.findByEmail(user.getEmail()), true);
        try{
            return new UserDto(userRepository.save(user));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un usuario
     * @param user
     */
    public UserDto updateUser(User user) {

        Validation.notFound(user.getId(), userRepository.existsById(user.getId()));
        Validation.validateWhriteUserData(user, userRepository.findByEmail(user.getEmail()), false);
        try{
            return new UserDto(userRepository.save(user));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un usuario con id
     * @param id
     */
    public void deleteUser(Long id) {
        Validation.notFound(id, userRepository.existsById(id));
        userRepository.deleteById(id);
    }
}
