package codes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Ciclista implements Runnable {

	private final Phaser barrera;
	private final Random random;
	DateTimeFormatter formatter;

	public Ciclista(Phaser barrera) {
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
			barrera.awaitAdvanceInterruptibly(barrera.arrive());
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras esperaba en la venta");
			return;
		}

		try {
			volverAGasolinera();
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras volvia a la gasolinera");
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
			volverACasa();
		} catch (InterruptedException e) {
			System.out.println(LocalDateTime.now().format(formatter) + " --- " + Thread.currentThread().getName()
					+ " --- Me han interrumpido mientras volvia a casa");
			return;
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
				+ " --- He vuelto a la gasolinera y me quedo esperando a todos los demas");
	}

	private void irAVenta() throws InterruptedException {
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- Salgo hacia la venta");
		TimeUnit.SECONDS.sleep(random.nextInt(6) + 5);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He llegado a la venta y estoy esperando a todos los demas");
	}

	private void irAGasolinera() throws InterruptedException {
		System.out.println(
				LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName() + " --- Salgo de casa");
		TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
		System.out.println(LocalDateTime.now().format(formatter) + " " + Thread.currentThread().getName()
				+ " --- He llegado a la gasolinera y estoy esperando a todos los demas");
	}
}
