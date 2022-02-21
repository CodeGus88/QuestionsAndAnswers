package com.questionsandanswers.questionsandanswers.dao;

import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDaoImplementation implements UserDao{

    @PersistenceContext
    EntityManager entityManager; // Sirve para hacer la conexión con la base de datos

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public User user(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void create(User user) {
        entityManager.merge(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

}
