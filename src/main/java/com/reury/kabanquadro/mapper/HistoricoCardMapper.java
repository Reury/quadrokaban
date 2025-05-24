package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.HistoricoCardDto;
import com.reury.kabanquadro.model.HistoricoCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoricoCardMapper {
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "board.id", target = "boardId")
    HistoricoCardDto toDto(HistoricoCard historico);

    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "boardId", target = "board.id")
    HistoricoCard toEntity(HistoricoCardDto dto);
}