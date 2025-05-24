package com.reury.kabanquadro.cli;

import com.reury.kabanquadro.model.Board;
import com.reury.kabanquadro.model.Card;
import com.reury.kabanquadro.model.Coluna;
import com.reury.kabanquadro.model.TipoColuna;
import com.reury.kabanquadro.service.BoardService;
import com.reury.kabanquadro.service.CardService;
import com.reury.kabanquadro.service.ColunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class KanbanCliMenu implements CommandLineRunner {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CardService cardService;
    @Autowired
    private ColunaService colunaService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Criar novo Board");
            System.out.println("2. Listar e selecionar Board existente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida ou fim do input. Encerrando.");
                break;
            }
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print("Nome do novo Board: ");
                String nome = scanner.nextLine();
                try {
                    Board boardCriado = boardService.criarBoard(nome);
                    System.out.println("Board criado: " + boardCriado.getNome());
                } catch (Exception e) {
                    System.out.println("Erro ao criar board: " + e.getMessage());
                }
            } else if (opcao == 2) {
                List<Board> boards = boardService.listarBoardsAtivos();
                if (boards.isEmpty()) {
                    System.out.println("Nenhum board encontrado.");
                    continue;
                }
                System.out.println("Boards disponíveis:");
                for (int i = 0; i < boards.size(); i++) {
                    System.out.println((i + 1) + ". " + boards.get(i).getNome());
                }
                System.out.print("Selecione o número do board: ");
                int idx = scanner.nextInt() - 1;
                scanner.nextLine();
                if (idx < 0 || idx >= boards.size()) {
                    System.out.println("Opção inválida.");
                    continue;
                }
                Board boardSelecionado = boardService.buscarBoardComColunas(boards.get(idx).getId());
                menuCards(scanner, boardSelecionado);
            } else if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private void menuCards(Scanner scanner, Board board) {
        while (true) {
            System.out.println("\n--- Menu de Cards do Board: " + board.getNome() + " ---");
            System.out.println("1. Criar Card");
            System.out.println("2. Listar Cards");
            System.out.println("3. Bloquear Card");
            System.out.println("4. Desbloquear Card");
            System.out.println("5. Mover Card para próxima coluna");
            System.out.println("6. Cancelar Card");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {
                if (opcao == 1) {
                    Coluna inicial = board.getColunas().stream()
                            .filter(c -> c.getTipo() == TipoColuna.INICIAL)
                            .findFirst().orElseThrow(() -> new RuntimeException("Coluna INICIAL não encontrada!"));
                    System.out.print("Título do Card: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição do Card: ");
                    String descricao = scanner.nextLine();
                    Card card = cardService.criarCard(titulo, descricao, inicial);
                    System.out.println("Card criado! ID: " + card.getId());
                } else if (opcao == 2) {
                    // Listar todos os cards do board (em todas as colunas)
                    for (Coluna coluna : board.getColunas()) {
                        Set<Card> cards = coluna.getCards();
                        if (cards != null && !cards.isEmpty()) {
                            for (Card card : cards) {
                                System.out.println("ID: " + card.getId() + " | Título: " + card.getTitulo() +
                                        " | Coluna: " + coluna.getNome() +
                                        " | Bloqueado: " + card.isBloqueado());
                            }
                        }
                    }
                } else if (opcao == 3) {
                    System.out.print("ID do Card: ");
                    Long cardId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Motivo do bloqueio: ");
                    String motivo = scanner.nextLine();
                    Card card = cardService.buscarPorId(cardId);
                    cardService.bloquearCard(card, motivo);
                    System.out.println("Card bloqueado!");
                } else if (opcao == 4) {
                    System.out.print("ID do Card: ");
                    Long cardId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Motivo do desbloqueio: ");
                    String motivo = scanner.nextLine();
                    Card card = cardService.buscarPorId(cardId);
                    cardService.desbloquearCard(card, motivo);
                    System.out.println("Card desbloqueado!");
                } else if (opcao == 5) {
                    System.out.print("ID do Card: ");
                    Long cardId = scanner.nextLong();
                    scanner.nextLine();
                    Card card = cardService.buscarPorId(cardId);
                    cardService.moverParaProximaColuna(card);
                    System.out.println("Card movido para próxima coluna!");
                } else if (opcao == 6) {
                    System.out.print("ID do Card: ");
                    Long cardId = scanner.nextLong();
                    scanner.nextLine();
                    Card card = cardService.buscarPorId(cardId);
                    cardService.cancelarCard(card);
                    System.out.println("Card cancelado!");
                } else if (opcao == 0) {
                    break;
                } else {
                    System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}
