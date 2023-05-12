package Prueba;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Tablero {
	
	Casilla casillas[][];
	int numfila;
	int numCol;
	int numMinas;
	
	int numCasillasAbierta;
	boolean juegoTerminado;
	
	private Consumer<List<Casilla>> partidaPerdida;
	private Consumer<List<Casilla>> partidaGanada;
	
	private Consumer<Casilla> casillaAbierta;

	public Tablero(int numFila,int numCol, int numMinas) {
		casillas=new Casilla[numFila][numCol];
		this.numfila=numFila;
		this.numCol=numCol;
		this.numMinas=numMinas;
		crearCasillas();
	}
	
	public void crearCasillas() {
		for(int i = 0; i<casillas.length;i++) {
			for(int j=0; j<casillas[i].length;j++) {
				casillas[i][j]=new Casilla(i, j);
			}
		}
		crearMinas();
	}
	public void crearMinas() {
		int minasCreadas=0;
		while(minasCreadas!=numMinas) {
			int posiFila=(int)(Math.random()*casillas.length);
			int posiColumna=(int)(Math.random()*casillas[0].length);
			if (!casillas[posiFila][posiColumna].isMina()) {
				casillas[posiFila][posiColumna].setMina(true);
				minasCreadas++;
			}
		}
		actualizarNumeroMinas();
	}
	

	public void mostrar() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				if (casillas[i][j].isMina() && casillas[i][j].isEncontrada()) {
					System.out.print(" * ");
				}else if(casillas[i][j].isEncontrada() && !casillas[i][j].isEncontrada()){
					System.out.print(" "+casillas[i][j].getMinasCerca()+" ");
				}else {
					System.out.print(" # ");
				}
			}
			System.out.println();
		}
	}
	
	public void mostrarTodo() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				System.out.print(casillas[i][j].isMina()?"*":casillas[i][j].getMinasCerca());
			}
			System.out.println("");
		}
	}
	public void mostrarPistas() {
			for (int i = 0; i < casillas.length; i++) {
				for (int j = 0; j < casillas[i].length; j++) {
					System.out.print(casillas[i][j].getMinasCerca());
				}
				System.out.println("");
			}
	}
	private List<Casilla> obtenerCasillasCerca(int posFil, int posCol){
		List<Casilla> listCasillas= new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			int tmpFil=posFil;
			int tmpCol=posCol;
			switch(i) {
			case 0->{//ARRIBA
						tmpFil--;
					}
			case 1->{//ARRIBA DERECHA
						tmpFil--;
						tmpCol++;
					}
			case 2->{//ARRIBA IZQUIERDA
						tmpFil--;
						tmpCol--;
					}
			case 3->{//ABAJO
						tmpFil++;
					}
			case 4->{//ABAJO DERECHA
						tmpFil++;
						tmpCol++;
					}
			case 5->{//ABAJO IZQUIERDA
						tmpFil++;
						tmpCol--;
					}
			case 6->{//DERECHA
						tmpCol++;
					}
			case 7->{//IZQUIERDA
						tmpCol--;
					}
			}
			if (tmpFil>=0 && tmpFil<casillas.length && tmpCol >=0 && tmpCol<casillas[0].length) {
					listCasillas.add(casillas[tmpFil][tmpCol]);	
			}
		}
		return listCasillas;
	}
	
	private void actualizarNumeroMinas() {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[0].length; j++) {
				if (casillas[i][j].isMina()) {
					List<Casilla> alrededor=obtenerCasillasCerca(i, j);
					for (Casilla cAlrededor : alrededor) {
						cAlrededor.aumentarMinasCerca();
					}
				}
			}
		}
	}
	
	public List<Casilla> obtenerCasillasMinadas() {
		
		List<Casilla> casMinadas=new ArrayList<>();
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[0].length; j++) {
				if (casillas[i][j].isMina()) {
					casMinadas.add(casillas[i][j]);
				}
			}
		}
		
		return casMinadas;
	}
	
	public void seleccionarCasilla(int posFila,int posCol) {
		casillaAbierta.accept(casillas[posFila][posCol]);
		
		if (casillas[posFila][posCol].isMina()) {
			setJuegoTerminado(true);
			partidaPerdida.accept(obtenerCasillasMinadas());
			
		}else if(casillas[posFila][posCol].getMinasCerca()==0) {
			List<Casilla> casillasAlredor=obtenerCasillasCerca(posFila, posCol);
			marcarCasilla(posFila, posCol);
			for (Casilla casilla : casillasAlredor) {
				if (!casilla.isAbierta()) {
					seleccionarCasilla(casilla.getNumFil(), casilla.getNumCol());
				}
			}
		}else {
			marcarCasilla(posFila, posCol);
		}
		
		if (partidaGanada()) {
			setJuegoTerminado(true);
			partidaGanada.accept(obtenerCasillasMinadas());
		}
	}

	public void marcarCasilla(int posFila,int posCol) {
		if (!casillas[posFila][posCol].isAbierta()) {
			casillas[posFila][posCol].setAbierta(true);
			numCasillasAbierta++;
		}
	}
	public boolean partidaGanada() {
		return numCasillasAbierta>=(numfila*numCol)-numMinas;
	}
	public Casilla obtenerCasilla(int posFil, int posCol) {
		return casillas[posFil][posCol];
	}
	public Casilla[][] getCasillas() {
		return casillas;
	}

	public void setCasillas(Casilla[][] casillas) {
		this.casillas = casillas;
	}

	public int getNumMinas() {
		return numMinas;
	}

	public void setNumMinas(int numMinas) {
		this.numMinas = numMinas;
	}

	
	public int getNumfila() {
		return numfila;
	}

	public void setNumfila(int numfila) {
		this.numfila = numfila;
	}

	public int getNumCol() {
		return numCol;
	}

	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}

	public Consumer<List<Casilla>> getPartidaPerdida() {
		return partidaPerdida;
	}

	public void setPartidaPerdida(Consumer<List<Casilla>> partidaPerdida) {
		this.partidaPerdida = partidaPerdida;
	}

	public Consumer<List<Casilla>> getPartidaGanada() {
		return partidaGanada;
	}

	public void setPartidaGanada(Consumer<List<Casilla>> partidaGanada) {
		this.partidaGanada = partidaGanada;
	}

	public Consumer<Casilla> getCasillaAbierta() {
		return casillaAbierta;
	}

	public void setCasillaAbierta(Consumer<Casilla> casillaAbierta) {
		this.casillaAbierta = casillaAbierta;
	}

	public int getNumCasillasAbierta() {
		return numCasillasAbierta;
	}

	public void setNumCasillasAbierta(int numCasillasAbierta) {
		this.numCasillasAbierta = numCasillasAbierta;
	}

	public boolean isJuegoTerminado() {
		return juegoTerminado;
	}

	public void setJuegoTerminado(boolean juegoTerminado) {
		this.juegoTerminado = juegoTerminado;
	}
	
	
}


