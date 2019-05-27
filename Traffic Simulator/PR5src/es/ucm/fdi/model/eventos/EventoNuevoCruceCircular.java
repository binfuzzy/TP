package es.ucm.fdi.model.eventos;

import es.ucm.fdi.model.cruces.CruceCircular;
import es.ucm.fdi.model.cruces.CruceGenerico;

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
