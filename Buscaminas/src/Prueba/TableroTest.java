package Prueba;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TableroTest {

	 @Test
	    public void testTableroConstructor() {
	        Tablero tablero = new Tablero(8, 8, 10);
	        assertEquals(8, tablero.getNumfila());
	        assertEquals(8, tablero.getNumCol());
	        assertEquals(10, tablero.getNumMinas());
	    }

	    @Test
	    public void testCeldaDescubierta() {
	        Tablero tablero = new Tablero(8, 8, 10);
	        assertFalse(tablero.obtenerCasilla(2, 2).isAbierta());
	        tablero.marcarCasilla(2, 2);
	        assertTrue(tablero.obtenerCasilla(2, 2).isAbierta());
	    }

	    @Test
	    public void testCeldaMina() {
	        Tablero tablero = new Tablero(8, 8, 10);
	        assertFalse(tablero.obtenerCasilla(2, 2).isMina());
	        tablero.obtenerCasilla(2, 2).setMina(true);
	        assertTrue(tablero.obtenerCasilla(2, 2).isMina());
	    }

	    @Test
	    public void testTableroGenerarMinas() {
	        Tablero tablero = new Tablero(8, 8, 10);
	        tablero.crearCasillas();;
	        int numMinas = 0;
	        for (int i = 0; i < tablero.getNumfila(); i++) {
	            for (int j = 0; j < tablero.getNumCol(); j++) {
	                if (tablero.obtenerCasilla(i, j).isMina()) {
	                    numMinas++;
	                }
	            }
	        }
	        assertEquals(10, numMinas);
	    }

//	    @Test
//	    public void testPartidaGanada() {
//	    	Tablero tablero = new Tablero(8, 8, 10);
//	    	tablero.crearCasillas();
//	    	for (int i = 0; i < tablero.getNumfila(); i++) {
//				for (int j = 0; j < tablero.getNumCol(); j++) {
//					if (tablero.obtenerCasilla(i, j).isMina()) {
//						tablero.seleccionarCasilla(i, j);
//					}
//				}
//			}
//	    	assertTrue(tablero.isJuegoTerminado());
//	    }

}
