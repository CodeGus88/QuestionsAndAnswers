package com.questionsandanswers.questionsandanswers.services;
import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.services.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.repository.JpaUserInterface;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de User, peticiones al servidor
 */
@Service
public class UserService {

    @Autowired
    private JpaUserInterface jpaUserInterface;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Retorna la lista de usuarios
     * @return userList
     */
    public ResponseEntity<List<UserDto>> getUserList() {
        ResponseEntity<List<UserDto>> responseEntity;
        try{
            List<User> userList = jpaUserInterface.findAll();
            List<UserDto> userDtoList = new ArrayList<>();
            if(userList!=null){
                for(User user : userList){
                    UserDto userDto = new UserDto(user);
                    userDtoList.add(userDto);
                }
            }
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(userDtoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
         return responseEntity;
    }

    /**
     * Retorna el DTO del usuario id
     * @param id
     * @return userDto
     */
    public ResponseEntity<UserDto> getUser(Long id) {
        ResponseEntity<UserDto> responseEntity;
        Optional<User> optional = jpaUserInterface.findById(id);
        Validation.notFound(id, optional.isEmpty());
        UserDto userDto = new UserDto(optional.get());
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(userDto);
        return responseEntity;
    }

    /**
     * Crea un nuevo usuario
     * @param user
     * return userDto
     */
    public ResponseEntity<UserDto> saveUser(User user) {
        ResponseEntity<UserDto> responseEntity;
        user.setId(0L);
        Validation.validateWhriteUserData(user, jpaUserInterface.findByEmail(user.getEmail()), true);
        try{
            UserDto userDto = new UserDto(jpaUserInterface.save(user));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Actualiza un usuario
     * @param user
     */
    public ResponseEntity<UserDto> updateUser(User user) {
        ResponseEntity<UserDto> responseEntity;
        Validation.notFound(user.getId(), jpaUserInterface.findById(user.getId()).isEmpty());
        Validation.validateWhriteUserData(user, jpaUserInterface.findByEmail(user.getEmail()), false);
        try{
            UserDto userDto = new UserDto(jpaUserInterface.save(user));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        return responseEntity;
    }

    /**
     * Elimina un usuario con id
     * @param id
     */
    public void deleteUser(Long id) {
        Validation.notFound(id, jpaUserInterface.findById(id).isEmpty());
        jpaUserInterface.deleteById(id);
    }
}
