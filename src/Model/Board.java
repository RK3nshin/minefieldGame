package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import Exception.ExploitationException;

public class Board {
      private int amountRows;
      private int amountCols;
      private int amountBomb;

      private final List<Cell> listCell = new ArrayList<>();

      public Board(int amountRows, int  amountCols, int amountBomb){
        this.amountBomb = amountBomb;
        this.amountCols = amountCols;
        this.amountRows = amountRows;
        generateCells();
        generateNeighbors();
        generateBombs();
      }
   
    private void generateCells (){
        for (int rows = 0 ; rows < amountRows; rows++){
            for(int cols = 0 ; cols <amountCols; cols++){
                listCell.add(new Cell(rows, cols));
            }
        }
      }
      private void generateNeighbors(){
        for (Cell c1 : listCell) {
            for (Cell c2 : listCell) {
                c1.addNeighbors(c2);
            }
        }
      }
      private void generateBombs() {
       long amountCellMined = 0;
       Predicate<Cell> Mined = cell -> cell.isBomb();
       do {
        int ramdon = (int) (Math.random() * listCell.size());
        listCell.get(ramdon).insertBomb();
        amountCellMined = listCell.stream().filter(Mined).count();
       } while (amountCellMined < amountBomb);
    }
     
    public boolean endGame (){
        return listCell.stream().allMatch(c -> c.goals());
    }
    public void restart(){
        listCell.stream().forEach(c-> c.restart());
        generateBombs();
    }

    
    public void  open(int rows, int cols){
        try{
            Predicate<Cell> pox = c -> c.getRows() == rows && c.getCols()==cols;
            listCell.parallelStream().filter(pox).findFirst().ifPresent(c -> c.open());
        }catch(ExploitationException e){
            listCell.forEach(c -> c.open());
            throw e;
        }
    }
      
    public void  marked(int rows, int cols){
        Predicate<Cell> pox = c -> c.getRows() == rows && c.getCols()==cols;
        listCell.parallelStream().filter(pox).findFirst().ifPresent(c -> c.toggleMarked());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append("  ");
        for(int c = 0 ; c < amountCols;c++ ){
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append('\n');
        int i = 0;
        for (int rows = 0 ; rows < amountRows; rows++){
            sb.append(" ");
            sb.append(rows);
            sb.append(" ");
            for(int cols = 0 ; cols <amountCols; cols++){
                sb.append(" ");
                sb.append(listCell.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
