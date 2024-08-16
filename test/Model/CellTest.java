package Model;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellTest {
    private Cell  cell;

    @BeforeEach
    void initcell(){
        cell = new Cell(3, 3);
    }
    @Test
    void testNeighborsTrue() {
        Cell neighbord1 = new Cell(3, 2);
        Cell neighbord2 = new Cell(3, 4);
        Cell neighbord3 = new Cell(2, 3);
        Cell neighbord4 = new Cell(4, 3);
        boolean resultado = cell.addNeighbors(neighbord1) && cell.addNeighbors(neighbord2)&& cell.addNeighbors(neighbord4) && cell.addNeighbors(neighbord3);
        assertTrue(resultado);
    }
    @Test
    void testNeighborsfalse(){
        Cell neighbord = new Cell(1, 1);
        boolean resultado = cell.addNeighbors(neighbord);
        assertFalse(resultado);
    }
    @Test 
    void testToggleMarked(){
        cell.toggleMarked();
        cell.toggleMarked();
        cell.toggleMarked();;
        assertTrue(cell.isMarked());
    }
    @Test 
    void testToggleMarked2(){
        cell.toggleMarked();
        cell.toggleMarked();
        assertFalse(cell.isMarked());
    }
    @Test 
    void testNotOpeneNotdMined(){
        assertTrue(cell.open());
    }
    @Test 
    void testNoOpeneIsdMined(){
        cell.toggleMarked();
        assertFalse(cell.open());
    }
    @Test 
    void testIsOpeneNotdMined(){
      /*   cell.insertBomb();
        assertThrows(ExploitationException.class, ()-> {
            cell.open();
        });
         */
    }
    @Test 
    void testIsOpeneisdMined(){
        cell.insertBomb();
        cell.toggleMarked();
        assertFalse(cell.open());
    }
    @Test 
    void testNeighborsOpen(){
        Cell neighbord11 = new Cell(1, 1);
        Cell neighbord22 = new Cell(2, 2);
        neighbord22.addNeighbors(neighbord11);
        cell.addNeighbors(neighbord22);
        cell.open();


        assertTrue(neighbord11.isOpen() && neighbord22.isOpen());
    }
    @Test
    void testNeighborsOpen2(){
        Cell neighbord11 = new Cell(1, 1);
        Cell neighbord12 = new Cell(1, 2);
        neighbord12.insertBomb();
        Cell neighbord22 = new Cell(2, 2);

        neighbord22.addNeighbors(neighbord12);
        neighbord22.addNeighbors(neighbord11);

        cell.addNeighbors(neighbord22);
        cell.open();


        assertTrue(neighbord22.isOpen() && !neighbord11.isOpen());
    }



}
