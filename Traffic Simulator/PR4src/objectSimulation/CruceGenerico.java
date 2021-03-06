package objectSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.BadSimulationObject;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String,T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;
	
	public CruceGenerico(String id) {
		super(id);
		this.indiceSemaforoVerde = 0;
		this.carreterasEntrantes = new ArrayList<T>();
		this.mapaCarreterasEntrantes = new HashMap<String, T>();
		this.carreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>();
	}
	
	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		return this.carreterasSalientes.get(cruce);
		
	}
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		T ri = creaCarreteraEntrante(carretera);
		this.mapaCarreterasEntrantes.put(idCarretera, ri);
		this.carreterasEntrantes.add(ri);
	}
	
	abstract protected T creaCarreteraEntrante(Carretera carretera);
	
	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {

		this.carreterasSalientes.put(destino, carr);
	
	}
	
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
		this.mapaCarreterasEntrantes.get(idCarretera).addVehiculo(vehiculo);
	}
	
	@Override
	public void avanza() throws BadSimulationObject {
		if(!this.carreterasEntrantes.isEmpty()){
			for(CarreteraEntrante ce: this.carreterasEntrantes) {
				if(ce.getSemaforo() && !ce.getColaVehiculos().isEmpty())
					ce.avanzaPrimerVehiculo();
			}
			actualizaSemaforos();
		}
	}

	abstract protected void actualizaSemaforos();
	
}
