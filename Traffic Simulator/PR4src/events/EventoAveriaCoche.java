package events;

import es.ucm.fdi.model.MapaCarreteras;
import exceptions.ErrorDeSimulacion;
import objectSimulation.Vehiculo;

public class EventoAveriaCoche extends Evento {
	
	protected String[] vehicles;
	protected Integer duration;
	
	
	public EventoAveriaCoche(int tiempo, String[] vehicles, int  duration) {
		super(tiempo);
		this.vehicles = vehicles;
		this.duration = duration;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		Vehiculo v;
		for(String s : vehicles) {
			v = mapa.getVehiculo(s);
			v.setTiempoAveria(duration);
		}
		
	}
	
	@Override
	public String toString() {
		
		return "New Vehicle Faulty";
		
	}
	
	
}
