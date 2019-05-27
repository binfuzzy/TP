package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarreteraTipica extends ConstructorEventos{

	public ConstructorEventoNuevaCarreteraTipica() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length" };
	}
	
	@Override
	public String toString() { return "New Typical "; }
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("typical")) return null;
		else
			return new EventoNuevaCarreteraTipica(
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				ConstructorEventos.identificadorValido(section, "id"),
				ConstructorEventos.identificadorValido(section, "src"),
				ConstructorEventos.identificadorValido(section, "dest"),
				ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				ConstructorEventos.parseaIntNoNegativo(section, "length", 0)
			);
	}

}
