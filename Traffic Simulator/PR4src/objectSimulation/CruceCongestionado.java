package objectSimulation;

import es.ucm.fdi.ini.IniSection;

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
		/*- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == 0) entonces se
		selecciona la carretera que tenga más vehículos en su cola de vehículos.*/
		
		if(this.indiceSemaforoVerde == 0) {
			CarreteraEntranteConIntervalo aux = this.carreterasEntrantes.get(0);
			for(int i = 1; i < this.carreterasEntrantes.size(); i++) {
				if(this.carreterasEntrantes.get(i).getColaVehiculos().size() > aux.getColaVehiculos().size()) {
					aux = this.carreterasEntrantes.get(i);
					this.indiceSemaforoVerde = i+1;
				}
			}
			if(this.indiceSemaforoVerde==0)
				this.indiceSemaforoVerde++;
			aux.ponSemaforo(true);
			//aux.setIntervaloDeTiempo(aux.getColaVehiculos().size() > 1 ? aux.getColaVehiculos().size() : 1);
		}
		
		else {
			CarreteraEntranteConIntervalo ri = this.carreterasEntrantes.get((indiceSemaforoVerde-1));
			if(ri.tiempoConsumido()) {
				ri.ponSemaforo(false);
				ri.setIntervaloDeTiempo(0);
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsoCompleto(false);
				ri.setUsadaPorUnVehiculo(false);
				
				CarreteraEntranteConIntervalo aux = (this.carreterasEntrantes.get(0) == ri) && this.carreterasEntrantes.size()>1  ? 
						this.carreterasEntrantes.get(1) : this.carreterasEntrantes.get(0);
				
				for(int i = 1; i < this.carreterasEntrantes.size(); i++) {
					if(this.carreterasEntrantes.get(i) != ri)
						if(this.carreterasEntrantes.get(i).getColaVehiculos().size() > aux.getColaVehiculos().size()) {
							aux = this.carreterasEntrantes.get(i);
							this.indiceSemaforoVerde = i+1;
					}
				}
				
				aux.ponSemaforo(true);
				//aux.setIntervaloDeTiempo(Math.max((aux.getColaVehiculos().size())/2, 1));
			}	
			
		}this.carreterasEntrantes.get((indiceSemaforoVerde-1)).setIntervaloDeTiempo(Math.max((this.carreterasEntrantes.get((indiceSemaforoVerde-1)).getColaVehiculos().size())/2, 1));
		
		/*
		- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde != 0) entonces:
		
		1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
			1.1. Se pone el semáforo de "ri" a rojo.
			1.2. Se inicializan los atributos de "ri".
			1.3. Se busca la posición "max" que ocupa la primera carretera entrante
			distinta de "ri" con el mayor número de vehículos en su cola.
			1.4. "indiceSemaforoVerde" se pone a "max".
			1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
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

}
