package es.ucm.fdi.model.cruces;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.carreterasEntrantes.CarreteraEntranteConIntervalo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {

	public CruceCongestionado(String id) {
		super(id);
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, 0);
	}

	@Override
	protected void actualizaSemaforos() {
		/*- Si no hay carretera con sem�foro en verde (indiceSemaforoVerde == -1) entonces se
		selecciona la carretera que tenga m�s veh�culos en su cola de veh�culos.*/
		
		if(this.indiceSemaforoVerde == -1) {
			CarreteraEntranteConIntervalo aux = this.carreterasEntrantes.get(0);
			for(int i = 1; i < this.carreterasEntrantes.size(); i++) {
				if(this.carreterasEntrantes.get(i).getColaVehiculos().size() > aux.getColaVehiculos().size()) {
					aux = this.carreterasEntrantes.get(i);
					this.indiceSemaforoVerde = i;
				}
			}
			if(this.indiceSemaforoVerde==-1)
				this.indiceSemaforoVerde = 0;
			aux.ponSemaforo(true);
		}
		
		else {
			CarreteraEntranteConIntervalo ri = this.carreterasEntrantes.get((indiceSemaforoVerde));
			if(ri.tiempoConsumido()) {
				ri.ponSemaforo(false);
				ri.setIntervaloDeTiempo(0);
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsoCompleto(false);
				ri.setUsadaPorUnVehiculo(false);
				
				CarreteraEntranteConIntervalo aux = (this.carreterasEntrantes.get(0) == ri) && this.carreterasEntrantes.size()>1  ? 
						this.carreterasEntrantes.get(1) : this.carreterasEntrantes.get(0);
						
				if(aux == this.carreterasEntrantes.get(0))
					this.indiceSemaforoVerde = 0;
				else
					this.indiceSemaforoVerde = 1;
				
				for(int i = 1; i < this.carreterasEntrantes.size(); i++) {
					if(this.carreterasEntrantes.get(i) != ri)
						if(this.carreterasEntrantes.get(i).getColaVehiculos().size() > aux.getColaVehiculos().size()) {
							aux = this.carreterasEntrantes.get(i);
							this.indiceSemaforoVerde = i;
					}
				}
				
				aux.ponSemaforo(true);
				
			}	
			
		}this.carreterasEntrantes.get(indiceSemaforoVerde).setIntervaloDeTiempo(Math.max((this.carreterasEntrantes.get(indiceSemaforoVerde).getColaVehiculos().size())/2, 1));
		
		/*
		- Si hay carretera entrante "ri" con su sem�foro en verde, (indiceSemaforoVerde != 0) entonces:
		
		1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
			1.1. Se pone el sem�foro de "ri" a rojo.
			1.2. Se inicializan los atributos de "ri".
			1.3. Se busca la posici�n "max" que ocupa la primera carretera entrante
			distinta de "ri" con el mayor n�mero de veh�culos en su cola.
			1.4. "indiceSemaforoVerde" se pone a "max".
			1.5. Se pone el sem�foro de la carretera entrante en la posici�n "max" ("rj")
			a verde y se inicializan los atributos de "rj", entre ellos el
			"intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).*/
		
	}

	@Override
	protected String getNombreSeccion() {
		return "junction_report";
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		
		String state = "";
		
		for(int i = 0; i < carreterasEntrantes.size(); i++) {
			state += "(" + carreterasEntrantes.get(i).getCarretera().getId();
			String sem = (carreterasEntrantes.get(i).getSemaforo()) ? "green:" + carreterasEntrantes.get(i).getIntervaloDeTiempo() : "red";
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
		is.setValue("queues", state);
		is.setValue("type", "mc\n");
		
	}
	@Override
	public String getCarreterasVerde() {
		String verdes = "[";
		for(CarreteraEntranteConIntervalo ce : this.carreterasEntrantes) {
			if(ce.getSemaforo())
				verdes+=toStringSem(ce);
		}
		verdes+="]";
		
		return verdes;
		
	}
	@Override
	public String getCarreterasRojo() {
		String rojos = "[";
		for(CarreteraEntranteConIntervalo ce : this.carreterasEntrantes) {
			if(!ce.getSemaforo())
				rojos+=toStringSem(ce);
		}
		rojos+="]";
		return rojos;
		
	}
	
	
	public String toStringSem(CarreteraEntranteConIntervalo ce) {
		String ts = "(" + ce.getCarretera().getId() + ",";
		ts += ce.getSemaforo() ? "green:" + ce.getIntervaloDeTiempo() : "red";
		ts += "," + ce.getColaVehiculos() + ")";
		return ts;
	}

}
