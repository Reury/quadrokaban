package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.BoardDto;
import com.reury.kabanquadro.model.Board;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ColunaMapper.class})
public interface BoardMapper {
    BoardDto toDto(Board board);
    Board toEntity(BoardDto dto);
}
