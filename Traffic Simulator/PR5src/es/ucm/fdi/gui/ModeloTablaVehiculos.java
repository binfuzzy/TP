package es.ucm.fdi.gui;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaVehiculos(String[] columnIdVehiculos, Controlador ctrl) {
		super(columnIdVehiculos, ctrl); 
	}

	@Override // necesario para que se visualicen los datos
	/*
	 * "ID", "Carretera","Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" }*/
	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCarretera(); break;
			case 2: s = this.lista.get(indiceFil).getLocalizacion(); break;
			case 3: s = this.lista.get(indiceFil).getVelocidadActual(); break;
			case 4: s = this.lista.get(indiceFil).getKilometraje(); break;
			case 5: s = this.lista.get(indiceFil).getTiempoDeInfraccion(); break;
			case 6: s = this.lista.get(indiceFil).getItinerario().toString(); break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		fireTableStructureChanged();
		
	}
	
}
