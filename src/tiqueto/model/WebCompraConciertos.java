package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb {
	private int entradasDisponibles;
	private int entradas;
	private int entradasVendidas = 0;
	public WebCompraConciertos() {
		super();
		this.entradasDisponibles= EjemploTicketMaster.TOTAL_ENTRADAS;
		this.entradas=0;
	}

	// Metodo de comprar entradas
	@Override
	public synchronized boolean comprarEntrada() {
		if (entradas > 0 || entradas <=EjemploTicketMaster.MAX_ENTRADAS_POR_FAN){
			entradas--;
			entradasVendidas++;
			mensajeWeb("Venta hecha, quedan: "+entradas+" entradas");
			return true;
		}else {
			try{
				mensajeWeb("Ya no quedan entradas, por favor espere a que se repongan mas entradas");
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return false;
		}
	}

	// Metodo de reponer entradas
	@Override
	public synchronized int reponerEntradas(int entradasAux) {
		if (entradasDisponibles > 0 || hayEntradas()==false){
			entradas += entradasAux;
			int entradasReponer = Math.min(entradasAux, entradasDisponibles);
			entradasDisponibles -= entradasReponer;
			notifyAll();
			return entradasReponer;
		}
		return 0;
	}

	// Metodo de cerrar la venta
	@Override
	public synchronized void cerrarVenta() {

		// TODO Auto-generated method stub
		mensajeWeb("Se ha terminado la venta de entradas.Existencia de entradas agotadas.");
		mensajeWeb("Venta Termianda. Gracias por comprar");
		entradas = EjemploTicketMaster.TOTAL_ENTRADAS-entradasVendidas;
		notify();

	}

	// Metodo para ver si hay entradas disponibles
	@Override
	public boolean hayEntradas() {
		if (entradas>0){
			return true;
		}else{
			return false;
		}
	}

	// Metodo para ver el numero de entradas restantes
	@Override
	public int entradasRestantes() {
		// TODO Auto-generated method stub
		mensajeWeb("Quedan "+entradasDisponibles+" entradas disponibles.");
		return entradas;
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
	}
}