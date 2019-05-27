package events;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos {

	public ConstructorEventoNuevoVehiculo() {
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "max_speed", "itinerary", "type" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		//si no funciona cambiar por dos elseif llamando a las clases
		else
			return new EventoNuevoVehiculo(

				ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				ConstructorEventos.identificadorValido(section, "id"),
				ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				ConstructorEventos.identificadoresValidos(section, "itinerary")
			);
		
		
	}
		
	@Override
	public String toString() { return "New Vehicle"; }
}
