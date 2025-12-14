package org.example.listeners;

import ch.qos.logback.core.spi.SequenceNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.example.entities.Categoria;
import org.example.services.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaModelListener extends AbstractMongoEventListener<Categoria> {
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Categoria> event) {
        Categoria categoria = event.getSource();

        if (categoria.getId() == null) {
            categoria.setId(sequenceGeneratorService.generateSequence("categoria_sequence"));
        }
    }
}
