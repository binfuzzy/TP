package events;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	public ConstructorEventoNuevaCarretera() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length" };
		//this.valoresPorDefecto = new String[] { "", "", "", "", "", ""};
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
			section.getValue("type") != null) return null;
		else
			return new EventoNuevaCarretera(
			ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
			ConstructorEventos.identificadorValido(section, "id"),
			ConstructorEventos.identificadorValido(section, "src"),
			ConstructorEventos.identificadorValido(section, "dest"),
			ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
			ConstructorEventos.parseaIntNoNegativo(section, "length", 0));
		}
		
	@Override
	public String toString() { return "New Road"; }

}
