package org.example.repositories;

import org.example.entities.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriaRepository extends MongoRepository<Categoria, Long> {
}
