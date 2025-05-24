package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.RelatorioBloqueiosDto;
import com.reury.kabanquadro.model.RelatorioBloqueios;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RelatorioBloqueiosMapper {
    @Mapping(source = "board.id", target = "boardId")
    @Mapping(source = "card.id", target = "cardId")
    RelatorioBloqueiosDto toDto(RelatorioBloqueios relatorio);

    @Mapping(source = "boardId", target = "board.id")
    @Mapping(source = "cardId", target = "card.id")
    RelatorioBloqueios toEntity(RelatorioBloqueiosDto dto);
}
