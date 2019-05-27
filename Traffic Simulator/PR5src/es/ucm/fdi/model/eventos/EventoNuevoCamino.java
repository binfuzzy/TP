package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Camino;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class EventoNuevoCamino extends EventoNuevaCarretera {
	
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
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

}
