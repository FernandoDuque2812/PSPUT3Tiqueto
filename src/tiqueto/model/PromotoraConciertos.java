package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	// Constructor del Promotor
	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	// Metodo run del hilo
	@Override
	public void run() {

		int entradasRepuestas = 0;
		Random random = new Random();
		while (entradasRepuestas < EjemploTicketMaster.TOTAL_ENTRADAS && entradasRepuestas<(EjemploTicketMaster.MAX_ENTRADAS_POR_FAN*EjemploTicketMaster.NUM_FANS)) {
			synchronized (webCompra) {
				if (!webCompra.hayEntradas()) {
					int entradasRepuestasNuevas = webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
					entradasRepuestas+=entradasRepuestasNuevas;
					mensajePromotor("Se ha repuesto "+entradasRepuestasNuevas+" entradas y total de entradas repuestas " + entradasRepuestas+" de las "+EjemploTicketMaster.TOTAL_ENTRADAS+ " entradas totales que hay previstas vender");
					webCompra.notifyAll();
				}
			}

			try {
				int tiempo = random.nextInt(5000) + 3000;
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		synchronized (webCompra) {
			webCompra.cerrarVenta();
			webCompra.notifyAll();
		}
	}
	/**
	 }
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);
	}
}
