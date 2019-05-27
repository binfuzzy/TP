package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {

	
	public ConstructorEventoAveriaCoche() {
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] { "time", "vehicles", "duration" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				section.getValue("type") != null) return null;
		else
			return new EventoAveriaCoche(
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				ConstructorEventos.identificadoresValidos(section, "vehicles"),
				ConstructorEventos.parseaIntNoNegativo(section, "duration", 0)
				);
	}
	
	@Override
	public String toString() { return "New Vehicle Faulty "; }

}
