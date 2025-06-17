package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.ColunaDto;
import com.reury.kabanquadro.model.Coluna;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ColunaMapper {
    @Mapping(source = "board.id", target = "boardId")
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "bloqueado", ignore = true) 
    ColunaDto toDto(Coluna coluna);

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "arquivado", ignore = true) 
    Coluna toEntity(ColunaDto dto);
}