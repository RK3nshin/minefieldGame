package Model;

@FunctionalInterface
public interface CellObserver {
    public void eventRun(Cell cell , CellEvent Event);
}
