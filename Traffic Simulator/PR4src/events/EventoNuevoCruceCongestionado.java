package events;

import objectSimulation.CruceCongestionado;
import objectSimulation.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce {

	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
	}
	
	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCongestionado(this.id);
	}

}
