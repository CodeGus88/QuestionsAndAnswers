package com.questionsandanswers.questionsandanswers.services.dto;

import com.questionsandanswers.questionsandanswers.models.User;

/**
 * La representación del modelo usuario en DTO
 */
public class UserDto {

    private long id;
    private String fullName;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    /**
//     * Convierte un modelo User al DTO
//     * @param user
//     */
//    public void writeFromModel(User user){
//        this.id = user.getId();
//        this.fullName = user.getFullName();
//        this.email = user.getEmail();
//    }

}
