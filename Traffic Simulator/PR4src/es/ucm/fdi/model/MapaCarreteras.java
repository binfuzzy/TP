package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exceptions.BadSimulationObject;
import exceptions.ErrorDeSimulacion;
import objectSimulation.Carretera;
import objectSimulation.CruceGenerico;
import objectSimulation.Vehiculo;

public class MapaCarreteras {
	
	private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	// estructuras para agilizar la bÃºsqueda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;
	
	public MapaCarreteras() {
		this.carreteras = new ArrayList<Carretera>();
		this.cruces = new ArrayList<CruceGenerico<?>>();
		this.vehiculos = new ArrayList<Vehiculo>();
		this.mapaDeCarreteras = new HashMap<String, Carretera>();
		this.mapaDeCruces = new HashMap<String, CruceGenerico<?>>();
		this.mapaDeVehiculos = new HashMap<String, Vehiculo>();
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y vehÃ­culos puede usarse ArrayList.
		// Para los mapas puede usarse HashMap
	}
	
	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws BadSimulationObject {
		// comprueba que â€œidCruceâ€� no existe en el mapa.
		if(!mapaDeCruces.containsKey(idCruce)) {
		// Si no existe, lo aÃ±ade a â€œcrucesâ€� y a â€œmapaDeCrucesâ€�.
			this.cruces.add(cruce);
			this.mapaDeCruces.put(idCruce, cruce);
		}
		else
			throw new BadSimulationObject("Junction already mapped");
		// Si existe lanza una excepciÃ³n.
	}
	
	public void addVehiculo(String idVehiculo,Vehiculo vehiculo) throws BadSimulationObject {
		// comprueba que â€œidVehiculoâ€� no existe en el mapa.
		if(!mapaDeVehiculos.containsKey(idVehiculo)) {
		// Si no existe, lo aÃ±ade a â€œvehiculosâ€� y a â€œmapaDeVehiculosâ€�,
			this.vehiculos.add(vehiculo);
			this.mapaDeVehiculos.put(idVehiculo, vehiculo);
		// y posteriormente solicita al vehiculo que se mueva a la siguiente
		// carretera de su itinerario (moverASiguienteCarretera).
			vehiculo.moverASiguienteCarretera();
			
		}
		else
			throw new BadSimulationObject("Vehicle already mapped");
		// Si existe lanza una excepciÃ³n.
	}
	
	public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino) throws BadSimulationObject {
		// comprueba que â€œidCarreteraâ€� no existe en el mapa.
		if(!mapaDeCarreteras.containsKey(idCarretera)) {
		// Si no existe, lo aÃ±ade a â€œcarreterasâ€� y a â€œmapaDeCarreterasâ€�,
			this.carreteras.add(carretera);
			this.mapaDeCarreteras.put(idCarretera, carretera);
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// - AÃ±ade al cruce origen la carretera, como â€œcarretera salienteâ€�
			origen.addCarreteraSalienteAlCruce(destino, carretera);
		// - AÃ±ade al crude destino la carretera, como â€œcarretera entranteâ€�
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		// Si existe lanza una excepciÃ³n.
		else
			throw new BadSimulationObject("Road already mapped");
	}
	
	public String generateReport(int time) {
		String report = "";
		for(CruceGenerico<?> c: cruces)
			report += c.generaInforme(time);
		// genera informe para cruces
		for(Carretera ca: carreteras)
			report += ca.generaInforme(time);
		// genera informe para carreteras
		for(Vehiculo v: vehiculos)
			report += v.generaInforme(time);
		// genera informe para vehiculos
		return report;
	}
	
	public void actualizar() throws BadSimulationObject {
		// llama al mÃ©todo avanza de cada carretera
		for(Carretera ca: carreteras)
			ca.avanza();
		// llama al mÃ©todo avanza de cada cruce
		for(CruceGenerico<?> c: cruces)
			c.avanza();

	}
	
	public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion {

		// devuelve el cruce con ese â€œidâ€� utilizando el mapaDeCruces.
		CruceGenerico<?> c = this.mapaDeCruces.get(id);
		// sino existe el cruce lanza excepciÃ³n.
		if(c == null) {
			throw new ErrorDeSimulacion("Null pointer for this junction with id: " + id);
		}
		return c;
		
		
	}
	
	public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion {
		// devuelve el vehÃ­culo con ese â€œidâ€� utilizando el mapaDeVehiculos.
		Vehiculo v = this.mapaDeVehiculos.get(id);
		// sino existe el vehÃ­culo lanza excepciÃ³n.
		if(v == null) {
			throw new ErrorDeSimulacion("Null pointer for this vehicle with id: " + id);
		}
		return v;
	}
	
	public Carretera getCarretera(String id) throws ErrorDeSimulacion {
		// devuelve la carretera con ese â€œidâ€� utilizando el mapaDeCarreteras.
		Carretera ca = this.mapaDeCarreteras.get(id);
		// sino existe la carretra lanza excepciÃ³n.
		if(ca == null) {
			throw new ErrorDeSimulacion("Null pointer for this road with id: " + id);
		}
		return ca;
	}
}
