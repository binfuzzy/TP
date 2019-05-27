package events;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.Cruce;
import objectSimulation.CruceGenerico;

public class EventoNuevoCruce extends Evento {

	protected String id;
	
	public EventoNuevoCruce(int time, String id) {

		super(time);
		this.id = id;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa)  throws ErrorDeSimulacion, BadSimulationObject {
		mapa.addCruce(this.id, this.creaCruce());
	}
	
	protected CruceGenerico<?> creaCruce() {
		return new Cruce(this.id);
	}
	
	@Override
	public String toString() {
		return "New Junction" + this.id;
		
	}

}
