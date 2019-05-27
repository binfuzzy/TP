package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.control.Observador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.eventos.Evento;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {
	
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private int contadorListaEventos;
	
	private List<ObservadorSimuladorTrafico> observadores;
	
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
	
		this.observadores = new ArrayList<>();

	}

	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws IOException, ErrorDeSimulacion, BadSimulationObject {
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		String report;
		while (this.contadorTiempo <= limiteTiempo) {
		// ejecutar todos los eventos correspondienes a â€œthis.contadorTiempoâ€�
			
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
				this.notificaAvanza();
			}
			//si falla mirar aqui
			else {
				ErrorDeSimulacion err = new ErrorDeSimulacion("error al avanzar");
				this.notificaError(err);
				throw err;
			}
			
			
			// escribir el informe en â€œficheroSalidaâ€�, controlando que no sea null.
			
		}
	}

	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if (e != null) {
			 if (e.getTiempo() < this.contadorTiempo) {
				 ErrorDeSimulacion err = new ErrorDeSimulacion("No se ha insertado el evento");
				 this.notificaError(err);
				 throw err;
				 
			 } 
			 
			 else {
				 this.eventos.add(e);
				 this.notificaNuevoEvento(); // se notifica a los observadores
			 }
			 
		} 
		
		else {
			 ErrorDeSimulacion err = new ErrorDeSimulacion("No hay eventos");
			 this.notificaError(err); // se notifica a los observadores
			 throw err;
		}
		
	}
	
	private void notificaError(ErrorDeSimulacion err) {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, err);
		 }
	}

	private void notificaNuevoEvento() {
		 for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.addEvento(this.contadorTiempo,this.mapa,this.eventos);
		 }
	}
	
	private void notificaReinicia() {
		 for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
		 }
	}
	
	private void notificaAvanza() {
		 for (ObservadorSimuladorTrafico o : this.observadores) {
			 o.avanza(this.contadorTiempo,this.mapa,this.eventos);
		 }
	}

	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
		
	}

	public void reinicia() {
		
		this.contadorTiempo = 0;
		this.contadorListaEventos = 0;
		// llamar a mapa.reinicia();
		this.mapa = new MapaCarreteras();
		this.eventos.clear();
		
		this.notificaReinicia();
		
	}
}
