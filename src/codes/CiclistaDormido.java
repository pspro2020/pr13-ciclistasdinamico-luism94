package codes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class CiclistaDormido implements Runnable {
	
	private final Phaser barrera;
	private final Random random;
	DateTimeFormatter formatter;

	public CiclistaDormido(Phaser barrera) {
		this.barrera = barrera;
		random = new Random();
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	}

	@Override
	public void run() {
		//Se levanta tarde cuando todos los demas han salido de la gasolinera
		
		if (!barrera.isTerminated()) {
			
			int fase = barrera.register();
			
			System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
					+ " --- ME HE QUEDADO DORMIDO Y ME APUNTO EN LA FASE " + fase);
			
			try {
				irAGasolinera();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (fase <= AccionBarrera.SALIR_A_GASOLINERA) {
				try {
					barrera.awaitAdvanceInterruptibly(barrera.arrive());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				irAVenta();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (fase <= AccionBarrera.SALIR_A_VENTA) {
				try {
					barrera.awaitAdvanceInterruptibly(barrera.arrive());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				volverAGasolinera();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (fase <= AccionBarrera.SALIR_A_CASA) {
				try {
					barrera.awaitAdvanceInterruptibly(barrera.arrive());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				volverACasa();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} else {
			System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
					+ " --- SE ME HA OLVIDADO LA CARRERA");
		}
	}

	private void volverACasa() throws InterruptedException {
		System.out.println(
				LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName() + " --- Vuelvo a casa");
		TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He vuelto a casa");

	}

	private void volverAGasolinera() throws InterruptedException {
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- Vuelvo a la gasolinera");
		TimeUnit.SECONDS.sleep(random.nextInt(6) + 5);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He llegado de nuevo a la gasolinera y me quedo esperando a los demas");
	}

	private void irAVenta() throws InterruptedException {
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- Salgo a la venta");
		TimeUnit.SECONDS.sleep(random.nextInt(6) + 5);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He llegado a la venta");
	}

	private void irAGasolinera() throws InterruptedException {
		System.out.println(
				LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName() + " --- Salgo de casa");
		TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He llegado a la gasolinera");
	}


}
