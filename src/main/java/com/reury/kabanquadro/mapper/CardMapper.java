package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.CardDto;
import com.reury.kabanquadro.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(source = "coluna.id", target = "colunaId")
    CardDto toDto(Card card);

    Card toEntity(CardDto dto);
}
