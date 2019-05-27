package events;

import java.util.List;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.Coche;
import objectSimulation.CruceGenerico;

public class EventoNuevoCoche extends EventoNuevoVehiculo {

	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected long numAleatorio;
	
	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario, String type, int durMax, int resis, double prob, long seed) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.resistenciaKm = resis;
		this.duracionMaximaAveria = durMax;
		this.probabilidadDeAveria = prob;
		this.numAleatorio = seed;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa)  throws ErrorDeSimulacion, BadSimulationObject {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if((iti == null) || (iti.size() < 2)) {
			throw new BadSimulationObject("This vehicle is null or has less than 2 junctions");
		}
		//Vehiculo a = new Coche(id, velocidadMaxima, iti, velocidadMaxima, velocidadMaxima, velocidadMaxima, velocidadMaxima);
		Coche c = new Coche(id, velocidadMaxima, iti, this.numAleatorio, this.resistenciaKm, this.duracionMaximaAveria, this.probabilidadDeAveria);
		mapa.addVehiculo(this.id, c);
		// en otro caso crear el vehículo y añadirlo al mapa.
	}

}
