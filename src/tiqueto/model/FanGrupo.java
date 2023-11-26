package tiqueto.model;

import tiqueto.EjemploTicketMaster;
public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	// Constructor del Fan
	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}
	// Metodo run del hilo
	@Override
	public void run() {
		do {
			synchronized (webCompra) {
				if (webCompra.hayEntradas() && entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
					mensajeFan("Voy a comprar una entrada");
					webCompra.comprarEntrada();
					entradasCompradas++;
					mensajeFan("Entrada comprada :)");
					dimeEntradasCompradas();
				} else {
					try {
						webCompra.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}

			try {
				Thread.sleep((int) (Math.random() * 2000) + 1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		} while (entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN);
	}

	// Metodo que dice las entradas compradas
	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores + " Fan " + this.numeroFan + ": " + mensaje);
	}
}