package Vision;

import javax.swing.JFrame;

import Model.Board;

public class ScreenMain  extends JFrame{
    public ScreenMain(){
        Board board = new Board(16, 30, 55);
        add(new PanelBoard(board));
        setTitle("Campo Minado");
        setSize(690,438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }
    public static void main(String[] args) {
        new ScreenMain();
    }
}
