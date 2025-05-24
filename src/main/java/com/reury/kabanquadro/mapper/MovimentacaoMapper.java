package com.reury.kabanquadro.mapper;

import com.reury.kabanquadro.dto.MovimentacaoDto;
import com.reury.kabanquadro.model.Movimentacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimentacaoMapper {
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "colunaOrigem.id", target = "colunaOrigemId")
    @Mapping(source = "colunaDestino.id", target = "colunaDestinoId")
    MovimentacaoDto toDto(Movimentacao movimentacao);

    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "colunaOrigemId", target = "colunaOrigem.id")
    @Mapping(source = "colunaDestinoId", target = "colunaDestino.id")
    Movimentacao toEntity(MovimentacaoDto dto);
}