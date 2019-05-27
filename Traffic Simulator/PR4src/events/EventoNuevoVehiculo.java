package events;

import java.util.List;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.CruceGenerico;
import objectSimulation.Vehiculo;

public class EventoNuevoVehiculo extends Evento{
	
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;
	
	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = itinerario;
	}

	@Override
	public String toString() {
		
		return "New Vehicle" + this.id;
	}

	public void ejecuta(MapaCarreteras mapa)  throws ErrorDeSimulacion, BadSimulationObject {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if((iti == null) || (iti.size() < 2)) {
			throw new BadSimulationObject("This vehicle is null or has less than 2 junctions");
		}
		//Vehiculo a = new Coche(id, velocidadMaxima, iti, velocidadMaxima, velocidadMaxima, velocidadMaxima, velocidadMaxima);
		Vehiculo v = new Vehiculo(id, velocidadMaxima, iti);
		mapa.addVehiculo(this.id, v);
		// en otro caso crear el vehículo y añadirlo al mapa.
	}

}
