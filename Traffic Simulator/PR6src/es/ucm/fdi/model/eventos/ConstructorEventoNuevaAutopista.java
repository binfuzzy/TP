package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaAutopista extends ConstructorEventos {

	public ConstructorEventoNuevaAutopista() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length", "lanes" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("lanes")) return null;
			else
				return new EventoNuevaAutopista(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.identificadorValido(section, "src"),
					ConstructorEventos.identificadorValido(section, "dest"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
					ConstructorEventos.parseaIntNoNegativo(section, "length", 0),
					ConstructorEventos.parseaIntNoNegativo(section, "lanes", 0)
					);
	}
	
	@Override
	public String toString() { return "New Lanes "; }
}

