package es.ucm.fdi.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.SimuladorTrafico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.eventos.ParserEventos;

public class Controlador {

	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;
	
	
	public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {

		this.simulador = sim;
		this.pasosSimulacion = limiteTiempo;
		this.ficheroEntrada = is;
		this.ficheroSalida = os;
		
	}
	
	public SimuladorTrafico getSimulador() {
		return simulador;
	}

	public void ejecuta() throws ErrorDeSimulacion, IOException, BadSimulationObject {
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
	}
	
	public void ejecuta(int pasos) throws IOException, ErrorDeSimulacion, BadSimulationObject {
		 this.simulador.ejecuta(pasos,this.ficheroSalida);
	}

	public void reinicia() { this.simulador.reinicia(); }
	
	public void addObserver(ObservadorSimuladorTrafico o) {
		 this.simulador.addObservador(o);
	}
	
	public void removeObserver(ObservadorSimuladorTrafico o) {
		 this.simulador.removeObservador(o);
	}
	
	
	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini;
		 
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		}
		catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}
		
		// recorremos todas los elementos de iniSections para generar el evento
		// correspondiente
		for (IniSection sec : ini.getSections()) {
		// parseamos la sección para ver a que evento corresponde
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null) this.simulador.insertaEvento(e);
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
	}
	
}

