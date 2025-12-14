package org.example.listeners;

import lombok.RequiredArgsConstructor;
import org.example.entities.Meme;
import org.example.services.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemeModelListener extends AbstractMongoEventListener<Meme> {
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Meme> event) {
        Meme meme = event.getSource();

        if (meme.getId() == null) {
            meme.setId(sequenceGeneratorService.generateSequence("meme_sequence"));
        }
    }
}
