package es.ucm.fdi.model.carreteras;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Camino extends Carretera {

	public Camino(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	@Override
	protected int calculaVelocidadBase() { return this.velocidadMaxima; }
	
	@Override
	protected int calculaFactorReduccion(int obstacles) {return obstacles + 1;}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		
		is.setValue("type", "dirt");
		String state = "";
		for(int i = 0; i < vehiculos.size(); i++) {
			state += "(" + vehiculos.get(i).getId() + "," + vehiculos.get(i).getLocalizacion() + ")";
			if(i != vehiculos.size()-1)
				state += ",";
			
		}
		state += "\n";
		is.setValue("state", state);
		
	}

}
