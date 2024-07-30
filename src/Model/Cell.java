package Model;

import java.util.ArrayList;

import Exception.ExploitationException;

public class Cell {
    private final int  rows , cols ;
    private boolean bomb = false;
    private boolean open = false;
    private boolean marked =false;

    private  ArrayList<Cell> neighbors = new ArrayList<>();

    Cell(int rows , int cols ){
        this.rows = rows;
        this.cols = cols;
    }
    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }
    public boolean isBomb(){
        return bomb;
    }
     void setOpen(){
        this.open = true;
    }
    public boolean isOpen(){
        return open;
    }
    public boolean isMarked(){
        return marked;
    }

    public boolean addNeighbors(Cell Neighbors){

        boolean rowsDifferent =  this.rows != Neighbors.rows;
        boolean colsDifferent =  this.cols != Neighbors.cols;
        boolean diagonally = rowsDifferent && colsDifferent;

        int deltaRows = Math.abs(this.rows - Neighbors.getRows());
        int deltaCols = Math.abs(this.cols - Neighbors.getCols());
        int delta = deltaCols+ deltaRows;

        if(delta == 1 && !diagonally){
            neighbors.add(Neighbors);
            return true;
        }else if (delta == 2 && diagonally){
            neighbors.add(Neighbors);
            return true;
        }else 
            return false;
 }

    public void toggleMarked(){
        if(!open){
            marked = !marked;
        }
    }
    public void insertBomb(){
        if(!bomb)
          this.bomb = true;
    }
    public boolean neighborhoodSafe(){
        System.out.println(neighbors.stream().noneMatch(n -> n.bomb));
        return neighbors.stream().noneMatch(n -> n.bomb);

    }
    public boolean open(){
        if(!open && !marked){
            open = true;
            if(bomb){
                throw new  ExploitationException();
            }
            if(neighborhoodSafe()){
                neighbors.forEach(v -> v.open());
            }
            return true;
        }else
        return false;
    }

    public boolean goals (){
        boolean unraveled = !bomb && open;
        boolean safe = bomb && marked;
        return unraveled || safe;
    }

    public long bombs (){
        return neighbors.stream().filter(n -> n.bomb).count();
    }

    public void restart(){
        bomb = open =marked = false;

    }
    @Override
    public String toString() {
        if(marked){
            return "X";
        }else if(open && bomb){
            return "*";
        }else if(open && bombs() > 0){
            return Long.toString( bombs());
        }else if (open){
            return " ";
        }else 
            return "?";
    }

}
