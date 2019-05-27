package objectSimulation;

import es.ucm.fdi.ini.IniSection;

public class Autopista extends Carretera {

	private int numCarriles;
	
	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest, int numCarriles) {
		super(id, length, maxSpeed, src, dest);
		this.numCarriles = numCarriles;
	}
	
	@Override
	protected int calculaVelocidadBase() {
		int n = this.vehiculos.size();
		int max = (n < 1) ? 1 : n; 
		return (this.velocidadMaxima < ((this.velocidadMaxima*this.numCarriles/max) +1) ) ? 
				this.velocidadMaxima : 
				(this.velocidadMaxima*this.numCarriles/max) + 1;
	}
	
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles < this.numCarriles ? 1 : 2;
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "lanes");
		String state = "";
		for(int i = 0; i < vehiculos.size(); i++) {
			state += "(" + vehiculos.get(i).getId() + "," + vehiculos.get(i).getLocalizacion() + ")";
			if(i != vehiculos.size()-1)
				state += ",";
			
		}
		state += "\n";
		is.setValue("state", state);
		//is.setValue("lanes", this.numCarriles);
	}

}
