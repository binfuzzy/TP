package events;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.Carretera;
import objectSimulation.CruceGenerico;

public class EventoNuevaCarretera extends Evento{

	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	
	
	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		
		super(tiempo);
		this.id = id;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
		this.velocidadMaxima = velocidadMaxima;
		this.longitud = longitud;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion, BadSimulationObject {
	// obten cruce origen y cruce destino utilizando el mapa
		CruceGenerico<?> origen = mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> destino = mapa.getCruce(this.cruceDestinoId);
	// crea la carretera
		Carretera c = creaCarretera(origen, destino);
	// añade al mapa la carretera
	
		mapa.addCarretera(this.id, origen, c, destino);
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		
		return new Carretera(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	
	}
	
	@Override
	public String toString() {
		return "New Road" + this.id;
	}
}

