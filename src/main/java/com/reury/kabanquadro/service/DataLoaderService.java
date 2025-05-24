package com.reury.kabanquadro.service;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.Bloqueio;
import com.reury.kabanquadro.repository.BoardRepository;
import com.reury.kabanquadro.repository.CardRepository;
import com.reury.kabanquadro.repository.ColunaRepository;
import com.reury.kabanquadro.repository.BloqueioRepository;
import com.reury.kabanquadro.model.TipoColuna;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataLoaderService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColunaRepository colunaRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BloqueioRepository bloqueioRepository;

    public void loadSampleData() {
        // Board 1
        Board board1 = new Board();
        board1.setNome("Projeto Kanban 1");
        board1.setDataCriacao(LocalDateTime.now());
        board1 = boardRepository.save(board1);

        Coluna colunaToDo = new Coluna();
        colunaToDo.setNome("A Fazer");
        colunaToDo.setOrdem(1);
        colunaToDo.setTipo(TipoColuna.INICIAL);
        colunaToDo.setBoard(board1);
        colunaToDo.setArquivado(false);
        colunaToDo = colunaRepository.save(colunaToDo);

        Coluna colunaDoing = new Coluna();
        colunaDoing.setNome("Em Progresso");
        colunaDoing.setOrdem(2);
        colunaDoing.setTipo(TipoColuna.PENDENTE);
        colunaDoing.setBoard(board1);
        colunaDoing.setArquivado(false);
        colunaDoing = colunaRepository.save(colunaDoing);

        Coluna colunaDone = new Coluna();
        colunaDone.setNome("Concluído");
        colunaDone.setOrdem(3);
        colunaDone.setTipo(TipoColuna.FINAL);
        colunaDone.setBoard(board1);
        colunaDone.setArquivado(false);
        colunaDone = colunaRepository.save(colunaDone);

        // Card em cada coluna
        Card cardToDo = new Card();
        cardToDo.setTitulo("Card em A Fazer");
        cardToDo.setDescricao("Primeira tarefa do board 1");
        cardToDo.setDataCriacao(LocalDateTime.now());
        cardToDo.setBloqueado(false);
        cardToDo.setArquivado(false);
        cardToDo.setColuna(colunaToDo);
        cardToDo.setDataEntradaColuna(LocalDateTime.now());
        cardRepository.save(cardToDo);

        Card cardDoing = new Card();
        cardDoing.setTitulo("Card em Progresso");
        cardDoing.setDescricao("Tarefa em andamento no board 1");
        cardDoing.setDataCriacao(LocalDateTime.now());
        cardDoing.setBloqueado(false);
        cardDoing.setArquivado(false);
        cardDoing.setColuna(colunaDoing);
        cardDoing.setDataEntradaColuna(LocalDateTime.now());
        cardRepository.save(cardDoing);

        Card cardDone = new Card();
        cardDone.setTitulo("Card Concluído");
        cardDone.setDescricao("Tarefa finalizada no board 1");
        cardDone.setDataCriacao(LocalDateTime.now());
        cardDone.setBloqueado(false);
        cardDone.setArquivado(false);
        cardDone.setColuna(colunaDone);
        cardDone.setDataEntradaColuna(LocalDateTime.now());
        cardRepository.save(cardDone);

        // Card bloqueado em "Em Progresso"
        Card cardBloqueado = new Card();
        cardBloqueado.setTitulo("Card Bloqueado");
        cardBloqueado.setDescricao("Tarefa bloqueada no board 1");
        cardBloqueado.setDataCriacao(LocalDateTime.now());
        cardBloqueado.setBloqueado(true);
        cardBloqueado.setArquivado(false);
        cardBloqueado.setColuna(colunaDoing);
        cardBloqueado.setDataEntradaColuna(LocalDateTime.now());
        cardBloqueado = cardRepository.save(cardBloqueado);

        Bloqueio bloqueio = new Bloqueio();
        bloqueio.setCard(cardBloqueado);
        bloqueio.setDataHoraBloqueio(LocalDateTime.now());
        bloqueio.setMotivoBloqueio("Aguardando aprovação");
        bloqueioRepository.save(bloqueio);

        // Board 2
        Board board2 = new Board();
        board2.setNome("Projeto Kanban 2");
        board2.setDataCriacao(LocalDateTime.now());
        board2 = boardRepository.save(board2);

        Coluna coluna2ToDo = new Coluna();
        coluna2ToDo.setNome("A Fazer");
        coluna2ToDo.setOrdem(1);
        coluna2ToDo.setTipo(TipoColuna.INICIAL);
        coluna2ToDo.setBoard(board2);
        coluna2ToDo.setArquivado(false);
        coluna2ToDo = colunaRepository.save(coluna2ToDo);

        Coluna coluna2Done = new Coluna();
        coluna2Done.setNome("Concluído");
        coluna2Done.setOrdem(2);
        coluna2Done.setTipo(TipoColuna.FINAL);
        coluna2Done.setBoard(board2);
        coluna2Done.setArquivado(false);
        coluna2Done = colunaRepository.save(coluna2Done);

        // Dois cards no board 2
        Card card2a = new Card();
        card2a.setTitulo("Primeiro Card Board 2");
        card2a.setDescricao("Tarefa 1 do board 2");
        card2a.setDataCriacao(LocalDateTime.now());
        card2a.setBloqueado(false);
        card2a.setArquivado(false);
        card2a.setColuna(coluna2ToDo);
        card2a.setDataEntradaColuna(LocalDateTime.now());
        cardRepository.save(card2a);

        Card card2b = new Card();
        card2b.setTitulo("Segundo Card Board 2");
        card2b.setDescricao("Tarefa 2 do board 2");
        card2b.setDataCriacao(LocalDateTime.now());
        card2b.setBloqueado(false);
        card2b.setArquivado(false);
        card2b.setColuna(coluna2Done);
        card2b.setDataEntradaColuna(LocalDateTime.now());
        cardRepository.save(card2b);
    }

    @PostConstruct
    public void loadSampleDataIfEmpty() {
        if (boardRepository.count() == 0) {
            loadSampleData();
            System.out.println("Dados de exemplo carregados automaticamente.");
        }
    }
}
