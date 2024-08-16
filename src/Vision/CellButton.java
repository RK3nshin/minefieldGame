package Vision;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Model.Cell;
import Model.CellEvent;
import Model.CellObserver;

public class CellButton extends JButton implements CellObserver , MouseListener {
    private Cell cell; 
    private final Color BG_default = new Color(184,184,184);
    private final Color BG_Mark = new Color(8,179,247);
    private final Color BG_Explode = new Color(189,66,68);
    private final Color BG_Green = new Color(0,100,0);

    

    CellButton(Cell cell){
        this.cell = cell;
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_default);
        setOpaque(true);
        cell.registerObservers(this);
        addMouseListener(this);
    }

    @Override
    public void eventRun(Cell cell, CellEvent Event) {
        switch (Event) {
            case OPEN:
                applyOpenStyle();
                break;
            case MARK:
                applyMarkStyle();
                break;
            case EXPLODE:
                 applyExplodeStyle();
                 break;
            default:
                applyDefauftStyle();
                break;
        }
        SwingUtilities.invokeLater(() ->{
            repaint();
            validate();
        });
    }

    private void applyDefauftStyle() {
        setBackground(BG_default);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }


    private void applyMarkStyle() {
        setBackground(BG_Mark);
        setForeground(Color.BLACK);
        setText("M");

    }

    private void applyExplodeStyle() {
        setBackground(BG_Explode);
        setForeground(Color.white);
        setText("X");

    }

    private void applyOpenStyle() {
       
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if(cell.isBomb()){
            setBackground(BG_Explode);
            return;
        }
        setBackground(BG_default);
    

        int bombsneighborhood = cell.bombs();
        switch (bombsneighborhood) {
            case 1:
                setForeground(BG_Green);
                break;
            case 2:
                setForeground(Color.blue);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.PINK);
            default:
                setForeground(Color.RED);
                break;
        }
        String text = cell.neighborhoodSafe() ? "": bombsneighborhood + "";
        setText(text);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1 ){
            cell.open();
        }else
            cell.toggleMarked();
    }

    
    public void mouseEntered(MouseEvent e) {
    }   

    public void mouseExited(MouseEvent e) {   
    }

    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
