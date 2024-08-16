package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements CellObserver {
    private final int amountRows;
    private final int amountCols;
    private final int amountBomb;
    private final List<Cell> listCell = new ArrayList<>();
    private HashSet<Consumer<Boolean>> observers = new HashSet<>();

    public Board(int amountRows, int amountCols, int amountBomb) {
        this.amountBomb = amountBomb;
        this.amountCols = amountCols;
        this.amountRows = amountRows;
        generateCells();
        generateNeighbors();
        generateBombs();
    }

    private void notifyObservers(Boolean result) {
        observers.stream().forEach(o -> o.accept(result));
    }

    private void generateCells() {
        for (int rows = 0; rows < amountRows; rows++) {
            for (int cols = 0; cols < amountCols; cols++) {
                Cell cell = new Cell(rows, cols);
                listCell.add(cell);
                cell.registerObservers(this);
            }
        }
    }
    private void generateNeighbors() {
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

    public void registerObservers(Consumer<Boolean> observer) {
        observers.add(observer);
    }

    public int getAmountBomb() {
        return amountBomb;
    }

    public int getAmountRows() {
        return amountRows;
    }

    public int getAmountCols() {
        return amountCols;
    }

    public boolean endGame() {
        return listCell.stream().allMatch(c -> c.goals());
    }

    public void restart() {
        listCell.stream().forEach(c -> c.restart());
        generateBombs();
    }

    public void open(int rows, int cols) {

        Predicate<Cell> pox = c -> c.getRows() == rows && c.getCols() == cols;
        listCell.parallelStream().filter(pox).findFirst().ifPresent(c -> c.open());

    }

    private void showBomb() {
        listCell.stream()
                .filter(c -> c.isBomb())
                .filter(c -> !c.isMarked())
                .forEach(c -> c.setOpen(true));
    }

    public void marked(int rows, int cols) {
        Predicate<Cell> pox = c -> c.getRows() == rows && c.getCols() == cols;
        listCell.parallelStream().filter(pox).findFirst().ifPresent(c -> c.toggleMarked());
    }

    @Override
    public void eventRun(Cell cell, CellEvent Event) {
        if (Event == CellEvent.EXPLODE) {
            showBomb();
            notifyObservers(false);

        } else if (endGame()) {
            notifyObservers(true);
            ;
        }
    }

    public void toExecute(Consumer<Cell> fn) {
        listCell.forEach(fn);
    }

}
