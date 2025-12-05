package org.example.listeners;

import lombok.RequiredArgsConstructor;
import org.example.entities.User;
import org.example.services.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelListener extends AbstractMongoEventListener<User> {
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User user = event.getSource();

        if(user.getId() == null)  {
            user.setId(sequenceGeneratorService.generateSequence("users_sequence"));
        }
    }
}
