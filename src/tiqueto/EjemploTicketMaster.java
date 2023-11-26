package tiqueto;

import java.util.ArrayList;
import java.util.List;

import tiqueto.model.FanGrupo;
import tiqueto.model.PromotoraConciertos;
import tiqueto.model.WebCompraConciertos;

public class EjemploTicketMaster {
	// Numero total de entradas
	public static int TOTAL_ENTRADAS = 10;
	// Numero de entradas que se reponen
	public static int REPOSICION_ENTRADAS = 6;
	// Numero maximo entradas que puede comprar un fan
	public static int MAX_ENTRADAS_POR_FAN = 3;
	// Numero total de fans
	public static int NUM_FANS = 3;

	public static void main(String[] args) throws InterruptedException {

		// Mensaje inicial que indica el inicio de la venta de entradas
		String mensajeInicial = "[ Empieza la venta de tickets. Se esperan %d fans, y un total de %d entradas ]";
		System.out.println(String.format(mensajeInicial, NUM_FANS, TOTAL_ENTRADAS));
		// Se crea un objeto de WebCompraConciertos
		WebCompraConciertos webCompra = new WebCompraConciertos();
		// Se crea un objeto de PromotoraConciertos al que se le pasa el objeto WebCompraConciertos
		PromotoraConciertos liveNacion = new PromotoraConciertos(webCompra);
		List<FanGrupo> fans = new ArrayList<>();

		//Lanzamos hilos de los fans
		for (int numFan = 1; numFan <= NUM_FANS; numFan++) {
			FanGrupo fan = new FanGrupo(webCompra, numFan);
			fans.add(fan);
			fan.start();
		}

		// Lanzamos el promotor
		liveNacion.start();
		Thread.sleep(5000);
		liveNacion.join();

		//Fase de venta
		System.out.println("\n [ Terminada la fase de venta - Sondeamos a pie de calle a los compradores ] \n");
		System.out.println("Total entradas ofertadas: " + TOTAL_ENTRADAS);
		System.out.println("Total entradas disponibles en la web: " + webCompra.entradasRestantes());
		// Los fans dicen cuantas entradas han comprado
		for (FanGrupo fan : fans) {
			fan.dimeEntradasCompradas();
		}
	}
}