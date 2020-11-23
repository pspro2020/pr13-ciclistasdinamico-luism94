package codes;

import java.util.concurrent.TimeUnit;

public class Main {
	
	static final int NUM_OF_CYCLIST = 10;
	
	public static void main(String[] args) {
		int i ;
		AccionBarrera barrera = new AccionBarrera("Barrera");
		
		for (i = 0; i < NUM_OF_CYCLIST; i++) {
			new Thread(new Ciclista(barrera), "Ciclista " + (i+1)).start();
		}
		i++;
		new Thread(new CiclistaImpaciente(barrera), "IMPACIENTE Nº" + (i)).start();
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		new Thread(new CiclistaDormido(barrera), "DORMIDO nº " + (i)).start();
		try {
			TimeUnit.SECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new CiclistaDormido(barrera), "PASOTA").start();
	}

}
