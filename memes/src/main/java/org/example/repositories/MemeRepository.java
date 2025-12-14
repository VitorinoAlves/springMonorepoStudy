package org.example.repositories;

import org.example.entities.Meme;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<Meme, Long> {
}
