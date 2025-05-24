package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.ColunaDto;
import com.reury.kabanquadro.model.Coluna;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ColunaMapper {
    @Mapping(source = "board.id", target = "boardId")
    ColunaDto toDto(Coluna coluna);

    @Mapping(source = "boardId", target = "board.id")
    Coluna toEntity(ColunaDto dto);
}
