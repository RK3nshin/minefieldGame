package Visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import Exception.ExitException;
import Exception.ExploitationException;
import Model.Board;

public class BoardTerminal {
    private Board board;
    private Scanner keyboard = new Scanner(System.in);
    public BoardTerminal(Board board){
        this.board= board;
        initgame();
    }
    private void initgame() {
       try {
        boolean  Continue  = true;
        while (Continue) {
            System.out.println("New Game? (S/n)");
            String resp = keyboard.nextLine();
            if("n".equalsIgnoreCase(resp)){
                Continue =false;
            }else{
                board.restart();
                loopGame();
            }
        }
    
       } catch (ExitException e) {
        System.out.println("Fim");
       }
    }
    private String  message(String Text){
        System.out.print(Text);
        String key = keyboard.nextLine();
        if("sair".equalsIgnoreCase(key)){
            throw new ExitException();
        }
        return key;
    }
    private void loopGame(){
        try {
            while (!(board.endGame())) {
                System.out.println(board);
                String key = message("Digite (x,y): ");
                Iterator<Integer> rowsCols = Arrays.stream(key.split(","))
                                            .map(e -> Integer.parseInt((e.trim()))).iterator();
                
                key = message("1- open    2 -Mekead/mark off");
                if("1".equals(key)){
                    board.open(rowsCols.next(), rowsCols.next());
                }else  if("2".equals(key)){
                    board.marked(rowsCols.next(), rowsCols.next());
                }
        
            }
            System.out.println(board);
            System.out.println("You won");
        } catch (ExploitationException e) {
            System.out.println(board);
            System.out.println("You lost");
        }
    }
}
