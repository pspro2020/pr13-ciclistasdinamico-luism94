package codes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class CiclistaImpaciente implements Runnable {

	private final Phaser barrera;
	private final Random random;
	DateTimeFormatter formatter;

	public CiclistaImpaciente(Phaser barrera) {
		this.barrera = barrera;
		random = new Random();
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	}

	@Override
	public void run() {
		
		barrera.register();

		try {
			irAGasolinera();
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras iba a la gasolinera");
			return;
		}
		try {
			barrera.awaitAdvanceInterruptibly(barrera.arrive());
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras esperaba en la gasolinera");
			return;
		}

		try {
			irAVenta();
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras iba a la venta");
			return;
		}

		try {
			barrera.arriveAndDeregister();
			volverAGasolinera();
			volverACasa();
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras volvia a la gasolinera");
			return;
		}
	}

	private void volverACasa() throws InterruptedException {
		System.out.println(
				LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName() + " --- Vuelvo a casa");
		TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- HE VUELTO A CASA");

	}

	private void volverAGasolinera() throws InterruptedException {
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- SALGO DE LA VENTA");
		TimeUnit.SECONDS.sleep(random.nextInt(6) + 5);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- HE LLEGADO DE NUEVO A LA GASOLINERA / SIGO SIN ESPERAR A NADIE");
	}

	private void irAVenta() throws InterruptedException {
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- SALGO DE LA GASOLINERA Y VOY A LA VENTA");
		TimeUnit.SECONDS.sleep(random.nextInt(6) + 5);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- HE LLEGADO A LA VENTA Y YA NO ESPERO A NADIE MAS");
	}

	private void irAGasolinera() throws InterruptedException {
		System.out.println(
				LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName() + " --- Salgo de casa");
		TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- HE LLEGADO A LA GASOLINERA Y ESTOY ESPERANDO A TODOS LOS DEMAS");
	}

}
