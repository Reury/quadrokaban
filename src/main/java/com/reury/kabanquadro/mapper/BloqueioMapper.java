package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.BloqueioDto;
import com.reury.kabanquadro.model.Bloqueio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BloqueioMapper {
    @Mapping(source = "card.id", target = "cardId")
    BloqueioDto toDto(Bloqueio bloqueio);

    @Mapping(source = "cardId", target = "card.id")
    Bloqueio toEntity(BloqueioDto dto);
}
