package es.ucm.fdi.model.eventos;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Autopista;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class EventoNuevaAutopista extends EventoNuevaCarretera {
	
	protected Integer numCarriles;
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, int numCarriles) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.numCarriles = numCarriles;
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Autopista(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino, this.numCarriles);
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
