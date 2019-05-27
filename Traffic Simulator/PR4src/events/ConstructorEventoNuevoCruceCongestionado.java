package events;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos {

	public ConstructorEventoNuevoCruceCongestionado() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		//this.valoresPorDefecto = new String[] { "", "", };
	}
	
	@Override
	public String toString() { return "New Most-crowed"; }
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				!section.getValue("type").equals("mc")) return null;
			else
				return new EventoNuevoCruceCongestionado(
				// 0 es el valor por defecto en caso de no especificar el tiempo
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				// extrae el valor del campo id de la seccion
				ConstructorEventos.identificadorValido(section, "id"));
	}

}
