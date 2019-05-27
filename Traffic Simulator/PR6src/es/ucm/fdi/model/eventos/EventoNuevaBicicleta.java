package es.ucm.fdi.model.eventos;

import java.util.List;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Bicicleta;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo {

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa)  throws ErrorDeSimulacion, BadSimulationObject {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if((iti == null) || (iti.size() < 2)) {
			throw new BadSimulationObject("This vehicle is null or has less than 2 junctions");
		}
		//Vehiculo a = new Coche(id, velocidadMaxima, iti, velocidadMaxima, velocidadMaxima, velocidadMaxima, velocidadMaxima);
		Bicicleta b = new Bicicleta(id, velocidadMaxima, iti);
		mapa.addVehiculo(this.id, b);
		// en otro caso crear el vehículo y añadirlo al mapa.
	}

}
