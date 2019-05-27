package events;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventos {

	public ConstructorEventoNuevaBicicleta() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "max_speed", "itinerary", "type" };
	}
	
	@Override
	public String toString() { return "New Bike"; }

	@Override
	public Evento parser(IniSection section) {
		
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("bike")) return null;
		
		else
			return new EventoNuevaBicicleta(
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				ConstructorEventos.identificadorValido(section, "id"),
				ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				ConstructorEventos.identificadoresValidos(section, "itinerary")
			);
	}

}
