package es.ucm.fdi.model;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {

	protected String id;
	
	public ObjetoSimulacion(String id) {
		this.id = id;
	}
	
	public String getId() {

		return this.id;
	
	}
	 @Override
	public String toString() {
		return id;
		 
	 }
	 
	protected abstract String getNombreSeccion();
		
	protected abstract void completaDetallesSeccion(IniSection ini);

	public String generaInforme(int tiempo) {
		
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		
		return is.toString();
	}
	
	
	public abstract void avanza() throws BadSimulationObject;


}
