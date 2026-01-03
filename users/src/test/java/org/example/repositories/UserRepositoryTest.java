package org.example.repositories;

import org.example.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.util.Date;
import java.util.Optional;

@DataMongoTest
@AutoConfigureDataMongo
public class UserRepositoryTest {

    /*@Autowired
    private UserRepository userRepository;

    // 3. Ferramenta do Spring para lidar com entidades no teste (salvar, buscar, etc.)
    @Autowired
    private MongoTemplate mongoTemplate;*/

    @Test
    void deveEncontrarUsuarioPorId() {
        /*Long userId = 1L;
        User userToPersist = new User(userId, "Test Mongo User", "mongo@email.com", new Date());

        User savedUser =  mongoTemplate.save(userToPersist);

        Optional<User> foundUser = userRepository.findById(userId);

        Assertions.assertTrue(foundUser.isPresent(), "O usuário deve ser encontrado.");
        Assertions.assertEquals(userId, foundUser.get().getId());
        Assertions.assertEquals("Test Mongo User", foundUser.get().getNome());

        userRepository.deleteById(userId);*/
    }

}
