package Model;

import java.util.ArrayList;

public class Cell {
    private final int rows, cols;
    private boolean bomb = false;
    private boolean open = false;
    private boolean marked = false;
    private ArrayList<Cell> neighbors = new ArrayList<>();
    private ArrayList<CellObserver> observers = new ArrayList<>();

    Cell(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    private void notifyObservers(CellEvent event) {
        observers.stream().forEach(o -> o.eventRun(this, event));
    }

    public void registerObservers(CellObserver observer) {
        observers.add(observer);
    }

    void setOpen(boolean status) {
        this.open = status;
        if (open) {
            notifyObservers(CellEvent.OPEN);
        }
    }

    boolean addNeighbors(Cell Neighbors) {

        boolean rowsDifferent = this.rows != Neighbors.rows;
        boolean colsDifferent = this.cols != Neighbors.cols;
        boolean diagonally = rowsDifferent && colsDifferent;

        int deltaRows = Math.abs(this.rows - Neighbors.getRows());
        int deltaCols = Math.abs(this.cols - Neighbors.getCols());
        int delta = deltaCols + deltaRows;

        if (delta == 1 && !diagonally) {
            neighbors.add(Neighbors);
            return true;
        } else if (delta == 2 && diagonally) {
            neighbors.add(Neighbors);
            return true;
        } else
            return false;
    }

    void insertBomb() {
        if (!bomb)
            this.bomb = true;
    }
    void restart() {
        bomb = open = marked = false;
        notifyObservers(CellEvent.RESTART);
    }
    

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isMarked() {
        return marked;
    }

    public void toggleMarked() {
        if (!open) {
            marked = !marked;
            if (marked) {
                notifyObservers(CellEvent.MARK);
            } else
                notifyObservers(CellEvent.UNMARK);
        }
    }

    public boolean neighborhoodSafe() {
        return neighbors.stream().noneMatch(n -> n.bomb);

    }

    public boolean open() {
        if (!open && !marked) {
            if (isBomb()) {
                notifyObservers(CellEvent.EXPLODE);
                return true;
            }
            setOpen(true);

            if (neighborhoodSafe()) {
                neighbors.forEach(v -> v.open());
            }
            return true;
        } else
            return false;
    }

    boolean goals() {
        boolean unraveled = !bomb && open;
        boolean safe = bomb && marked;
        return unraveled || safe;
    }

    public int bombs() {
        return (int) neighbors.stream().filter(n -> n.bomb).count();
    }

}
