package es.ucm.fdi.model.carreterasEntrantes;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class CarreteraEntrante {

	private Carretera carretera;
	private List<Vehiculo> colaVehiculos;
	private boolean semaforo; // true=verde, false=rojo
	
	public CarreteraEntrante(Carretera carretera) {
	// inicia los atributos.
	// el semáforo a rojo
		this.carretera = carretera;
		//falta la lista
		//crear una lista vacia
		this.colaVehiculos = new ArrayList<Vehiculo>();
		this.semaforo = false;
	}
	
	public Carretera getCarretera() {
		return this.carretera;
	}
	
	
	public List<Vehiculo> getColaVehiculos() {
		return colaVehiculos;
	}

	public boolean getSemaforo() {
		return semaforo;
	}

	public void ponSemaforo(boolean color) {
		this.semaforo = color;
	}
	
	public void avanzaPrimerVehiculo() throws BadSimulationObject {
		if(!this.getColaVehiculos().isEmpty()) {
			// coge el primer vehiculo de la cola, lo elimina,
			Vehiculo v = colaVehiculos.get(0);
			colaVehiculos.remove(v);
			// y le manda que se mueva a su siguiente carretera.
			v.moverASiguienteCarretera();
		}
	}
	
	public void addVehiculo(Vehiculo v) {
		this.colaVehiculos.add(v);
	}
	
	public String toString() {
		String ts = "(" + this.getCarretera().getId() + ",";
		ts += this.getSemaforo() ? "green" : "red";
		ts += "," + this.getColaVehiculos() + ")";
		return ts;
	}
	
}
