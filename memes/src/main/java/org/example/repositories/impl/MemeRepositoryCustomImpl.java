package org.example.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.example.entities.Meme;
import org.example.repositories.MemeRepository;
import org.example.repositories.MemeRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemeRepositoryCustomImpl implements MemeRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Meme findRandomMeme() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.sample(1));
        AggregationResults<Meme> results = mongoTemplate.aggregate(aggregation, "meme", Meme.class);
        return results.getUniqueMappedResult();
    }
}
