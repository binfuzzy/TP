package events;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos {

	public ConstructorEventoNuevoCruceCircular() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id", "max_time_slice", "min_time_slice" };
		//this.valoresPorDefecto = new String[] { "", "", };
	}
	
	@Override
	public String toString() { return "New Round-robin"; }
	
	@Override
	public Evento parser(IniSection section) {
		
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rr")) 
			return null;
		
		else
			return new EventoNuevoCruceCircular(
				// 0 es el valor por defecto en caso de no especificar el tiempo
				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				// extrae el valor del campo id de la seccion
				ConstructorEventos.identificadorValido(section, "id"),
				ConstructorEventos.parseaIntNoNegativo(section, "max_time_slice", 0),
				ConstructorEventos.parseaIntNoNegativo(section, "min_time_slice", 0)

			);
	
	}

}
