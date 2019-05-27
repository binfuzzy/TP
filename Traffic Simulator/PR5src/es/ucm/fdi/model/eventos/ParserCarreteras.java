package es.ucm.fdi.model.eventos;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;

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
