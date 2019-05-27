package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventos{
	
	public ConstructorEventoNuevoCoche() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "max_speed", "itinerary", "type", "resistance", "max_fault_duration", "fault_probability", "seed" };
	}
	
	@Override
	public String toString() { return "New Car"; }
	
	@Override
	public Evento parser(IniSection section) {
		
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("car")) return null;
		
		else
			
			return new EventoNuevoCoche(
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				ConstructorEventos.identificadorValido(section, "id"),
				ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				ConstructorEventos.identificadoresValidos(section, "itinerary"),
				ConstructorEventos.identificadorValido(section, "type"),
				ConstructorEventos.parseaIntNoNegativo(section, "resistance", 0),
				ConstructorEventos.parseaIntNoNegativo(section, "max_fault_duration", 0),
				ConstructorEventos.parseaDoubleNoNegativo(section, "fault_probability", 0),
				ConstructorEventos.parseaLongNoNegativo(section, "seed", 0)
			);
	}

}
