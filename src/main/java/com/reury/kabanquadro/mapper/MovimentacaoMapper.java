package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.MovimentacaoDto;
import com.reury.kabanquadro.model.Movimentacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimentacaoMapper {
    @Mapping(source = "card.id", target = "cardId")
    MovimentacaoDto toDto(Movimentacao movimentacao);

    @Mapping(source = "cardId", target = "card.id")
    Movimentacao toEntity(MovimentacaoDto dto);
}