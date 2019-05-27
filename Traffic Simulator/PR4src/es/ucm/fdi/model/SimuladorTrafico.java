package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

import events.Evento;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;

public class SimuladorTrafico {
	
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private int contadorListaEventos;
	
	public SimuladorTrafico() {
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		this.contadorListaEventos = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {

			@Override
			public int compare(Evento arg0, Evento arg1) {
				return (arg0.getTiempo() - arg1.getTiempo());
			} 
		};
		this.eventos = new SortedArrayList<>(cmp); //estructura ordenada por tiempo
	}

	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws IOException, ErrorDeSimulacion, BadSimulationObject {
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		String report;
		while (this.contadorTiempo <= limiteTiempo) {
		// ejecutar todos los eventos correspondienes a â€œthis.contadorTiempoâ€�
			
			//ver donde se aumenta el contadorTiempo
			while((this.eventos.size() > this.contadorListaEventos) && (this.contadorTiempo == this.eventos.get(this.contadorListaEventos).getTiempo())) {
				this.eventos.get(this.contadorListaEventos).ejecuta(this.mapa);
				this.contadorListaEventos++;
			}
		// actualizar â€œmapaâ€�
			this.mapa.actualizar();
			this.contadorTiempo++;
			report = mapa.generateReport(this.contadorTiempo);
			if(!report.equals(null)) {
				ficheroSalida.write(report.getBytes());
			}
			
			// escribir el informe en â€œficheroSalidaâ€�, controlando que no sea null.
			
		}
	}

	public void insertaEvento(Evento e) {
		// inserta un evento en â€œeventosâ€�, controlando que el tiempo de
		// ejecuciÃ³n del evento sea menor que â€œcontadorTiempoâ€�
		if(e.getTiempo() >= this.contadorTiempo)
			this.eventos.add(e);
		
	}

}
