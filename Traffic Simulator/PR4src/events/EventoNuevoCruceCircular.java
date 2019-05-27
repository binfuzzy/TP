package events;

import objectSimulation.CruceCircular;
import objectSimulation.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {

	protected Integer maxValorIntervalo, minValorIntervalo;
	
	public EventoNuevoCruceCircular(int time, String id, int max, int min) {
		super(time, id);
		this.maxValorIntervalo = max;
		this.minValorIntervalo = min;
	}
	
	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
	}

}
