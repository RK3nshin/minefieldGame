package Vision;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import Model.Board;

public class PanelBoard extends JPanel {
    PanelBoard(Board board) {
        setLayout(new GridLayout(
                board.getAmountRows(), board.getAmountCols()));

        board.toExecute(c -> add(new CellButton(c)));
        board.registerObservers(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e == true) {
                    JOptionPane.showMessageDialog(this, "Vitoria !!!");
                } else
                    JOptionPane.showMessageDialog(this, "Fim !!!");
                board.restart();
            }

            );

        });

    }
}
