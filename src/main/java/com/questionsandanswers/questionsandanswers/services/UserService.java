package com.questionsandanswers.questionsandanswers.services;
import com.questionsandanswers.questionsandanswers.services.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.repository.JpaUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JpaUserInterface jpaUserInterface;

    /**
     * Retorna la lista de usuarios
     * @return userList
     */
    public List<UserDto> getUserList() {
        List<User> userList = jpaUserInterface.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        if(userList!=null){
            for(User user : userList){
                UserDto userDto = new UserDto();
                userDto.writeFromModel(user);
                userDtoList.add(userDto);
            }
            return userDtoList;
        } return null;
    }

    /**
     * Retorna el DTO del usuario id
     * @param id
     * @return userDto
     */
    public UserDto getUser(Long id) {
        try{
            Optional<User> optional = jpaUserInterface.findById(id);
            UserDto userDto = new UserDto();
            userDto.writeFromModel(optional.get());
            return userDto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Crea un nuevo usuario
     * @param user
     * return userDto
     */
    public UserDto saveUser(User user) {
        try{
            user.setId(0L);
            UserDto userDto = new UserDto();
            userDto.writeFromModel(jpaUserInterface.save(user));
            return userDto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Actualiza un usuario
     * @param user
     */
    public UserDto updateUser(User user) {
        try{
            Optional<User> optional = jpaUserInterface.findById(user.getId());
            if(!optional.isEmpty()){
                UserDto userDto = new UserDto();
                userDto.writeFromModel(jpaUserInterface.save(user));
                return userDto;
            }else
                return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina un usuario con id
     * @param id
     */
    public void deleteUser(Long id) {
        jpaUserInterface.deleteById(id);
    }
}
