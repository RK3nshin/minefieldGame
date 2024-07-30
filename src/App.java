import Model.Board;
import Visao.BoardTerminal;
public class App {
    public static void main(String[] args) throws Exception {
        Board board = new Board(6,6,2);
        
        BoardTerminal game = new BoardTerminal(board);
    }
}
