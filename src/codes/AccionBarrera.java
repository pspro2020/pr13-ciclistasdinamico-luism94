package codes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;

public class AccionBarrera extends Phaser {
	
	public static final int SALIR_A_GASOLINERA = 0;
	public static final int SALIR_A_VENTA = 1;
	public static final int SALIR_A_CASA = 2;

	DateTimeFormatter formatter;
	private String name;
	
	public AccionBarrera(String name) {
		this.name = name;
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	}

	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		switch (phase) {
		case SALIR_A_GASOLINERA:
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + name + " --- Comienza la etapa / Todos salen a la venta");
			break;
		case SALIR_A_VENTA:
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + name + " --- Mitad etapa / Todos salen a la gasolinera");
			break;
		case SALIR_A_CASA:
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + name + " --- Etapa finalizada / Todos vuelven a casa");
			return true;
		}
		
		return super.onAdvance(phase, registeredParties);
	}
	
}
