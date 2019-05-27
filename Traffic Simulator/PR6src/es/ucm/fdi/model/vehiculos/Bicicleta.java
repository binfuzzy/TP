package es.ucm.fdi.model.vehiculos;

import java.util.List;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.cruces.CruceGenerico;

public class Bicicleta extends Vehiculo {

	public Bicicleta(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws BadSimulationObject {
		super(id, velocidadMaxima, iti);
	}
	
	@Override
	public void setTiempoAveria(Integer duracionAveria) {
		
		if(this.getCarretera() != null && this.getVelocidadActual() >= this.getVelocidadMaxima() / 2) {
			super.setTiempoAveria(this.getTiempoDeInfraccion() + duracionAveria);
		
			if(this.getTiempoDeInfraccion() > 0)
				this.setVelocidadActual(0);
		}
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection ini) {
		ini.setValue("type", "bike");
		ini.setValue("speed", this.getVelocidadActual());
		ini.setValue("kilometrage", this.getKilometraje());
		ini.setValue("faulty", this.getTiempoDeInfraccion());
		ini.setValue("location", this.getHaLlegado() ? "arrived" + "\n": "(" + this.getCarretera() + "," + this.getLocalizacion() + ")" + "\n");
		
	}

}
