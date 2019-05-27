package events;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.CruceGenerico;

public class ParserCarreteras {

	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws ErrorDeSimulacion, BadSimulationObject {
		List<CruceGenerico<?>> iti = new ArrayList<CruceGenerico<?>>();
		CruceGenerico<?> c;
		for(String s : itinerario) {
			c = mapa.getCruce(s);
			if(c != null)
				iti.add(c);
			else
				throw new BadSimulationObject("Algun cruce no existe en el mapa");
		}
		
		return iti;
	}

}
