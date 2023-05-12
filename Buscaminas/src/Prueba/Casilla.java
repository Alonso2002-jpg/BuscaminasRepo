package Prueba;

public class Casilla {
	private int numFil,numCol;
	private boolean mina;
	
	private boolean abierta;
	private int minasCerca;
	
	Casilla(int numFil, int numCol){
		this.numFil=numFil;
		this.numCol=numCol;
		mina=false;
	}
	
	void aumentarMinasCerca() {
		minasCerca++;
	}
	
	public int getNumFil() {
		return numFil;
	}
	public void setNumFil(int numFil) {
		this.numFil = numFil;
	}
	public int getNumCol() {
		return numCol;
	}
	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}
	public boolean isMina() {
		return mina;
	}
	public void setMina(boolean mina) {
		this.mina = mina;
	}
	public int getMinasCerca() {
		return minasCerca;
	}
	public void setMinasCerca(int minasCerca) {
		this.minasCerca = minasCerca;
	}
	public boolean isAbierta() {
		return abierta;
	}
	public void setAbierta(boolean abierta) {
		this.abierta = abierta;
	}
	
	
}
