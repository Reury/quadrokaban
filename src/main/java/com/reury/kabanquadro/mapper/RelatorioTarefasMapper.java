package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.RelatorioTarefasDto;
import com.reury.kabanquadro.model.RelatorioTarefas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RelatorioTarefasMapper {
    @Mapping(source = "board.id", target = "boardId")
    @Mapping(source = "card.id", target = "cardId")
    RelatorioTarefasDto toDto(RelatorioTarefas relatorio);

    @Mapping(source = "boardId", target = "board.id")
    @Mapping(source = "cardId", target = "card.id")
    RelatorioTarefas toEntity(RelatorioTarefasDto dto);
}