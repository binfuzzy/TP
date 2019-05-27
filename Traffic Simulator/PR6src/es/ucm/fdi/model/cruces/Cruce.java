package es.ucm.fdi.model.cruces;

import java.util.List;

import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.carreterasEntrantes.CarreteraEntrante;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class Cruce extends CruceGenerico<CarreteraEntrante> {
	
	public Cruce(String id) {
		super(id);
	}
	
	public List<CarreteraEntrante> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}

	public Carretera carreteraHaciaCruce(Cruce cruce) {
		return this.carreterasSalientes.get(cruce);
		
	}
	@Override
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		CarreteraEntrante ce = new CarreteraEntrante(carretera);
		this.mapaCarreterasEntrantes.put(idCarretera, ce);
		this.carreterasEntrantes.add(ce);
	}
	
	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
		this.carreterasSalientes.put(destino, road);
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
		this.mapaCarreterasEntrantes.get(idCarretera).addVehiculo(vehiculo);
		// añade el â€œvehiculoâ€� a la carretera entrante â€œidCarreteraâ€�
	}

	protected void actualizaSemaforos(){
		if(this.indiceSemaforoVerde == -1) {
			this.indiceSemaforoVerde++;
			this.carreterasEntrantes.get(0).ponSemaforo(true);
			
		}
		else {
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
		// pone el semÃ¡foro de la carretera actual a â€œrojoâ€�, y busca la siguiente
		// carretera entrante para ponerlo a â€œverdeâ€�
			
			this.indiceSemaforoVerde = (this.indiceSemaforoVerde+1)%this.carreterasEntrantes.size();
			this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
		}
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
	// Si â€œcarreterasEntrantesâ€� es vacÃ­o, no hace nada.
	// en otro caso â€œavanzaPrimerVehiculoâ€� de la carretera con el semÃ¡foro verde.
	// Posteriormente actualiza los semÃ¡foros.
	}
	
	@Override
	protected String getNombreSeccion() {return "junction_report";}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
	// genera la secciÃ³n queues = (r2,green,[]),...
		
		String state = "";
		
		for(int i = 0; i < carreterasEntrantes.size(); i++) {
			state += "(" + carreterasEntrantes.get(i).getCarretera().getId();
			String sem = (carreterasEntrantes.get(i).getSemaforo()) ? "green" : "red";
			state += "," + sem + ",";
			state += "[";
			for(int j = 0; j < carreterasEntrantes.get(i).getColaVehiculos().size(); j++) {
				state += carreterasEntrantes.get(i).getColaVehiculos().get(j);
				if(j != carreterasEntrantes.get(i).getColaVehiculos().size()-1)
					state += ",";	
				
			}
			state += "]" + ")";
			
			if(i != carreterasEntrantes.size()-1)
					state += ",";
		}
		state += "\n";
		is.setValue("queues", state);
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntrante(carretera);
	}
	
}
