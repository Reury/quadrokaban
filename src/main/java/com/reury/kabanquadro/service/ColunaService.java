package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.repository.ColunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColunaService {

    @Autowired
    private ColunaRepository colunaRepository;

    public Coluna buscarPorId(Long id) {
        return colunaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Coluna não encontrada!"));
    }

    // Outros métodos: editar, arquivar, excluir, etc.
}
