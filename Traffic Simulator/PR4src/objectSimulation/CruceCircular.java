package objectSimulation;

import es.ucm.fdi.ini.IniSection;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {

	protected int minValorIntervalo;
	protected int maxValorIntervalo;
	
	public CruceCircular(String id, Integer minValorIntervalo, Integer maxValorIntervalo) {
		super(id);
		this.minValorIntervalo = minValorIntervalo;
		this.maxValorIntervalo = maxValorIntervalo;
	}

	@Override
	protected void actualizaSemaforos() {
		if(this.indiceSemaforoVerde == 0) {
			this.carreterasEntrantes.get(0).ponSemaforo(true);
			this.indiceSemaforoVerde++;
		}
		
		else {
			CarreteraEntranteConIntervalo ri = this.carreterasEntrantes.get((indiceSemaforoVerde-1)%this.carreterasEntrantes.size());
			if (ri.tiempoConsumido()) {
				this.carreterasEntrantes.get((this.indiceSemaforoVerde-1)%this.carreterasEntrantes.size()).ponSemaforo(false);
				
				if(ri.usoCompleto()) {
					ri.setIntervaloDeTiempo(ri.getIntervaloDeTiempo()+1 < this.maxValorIntervalo ? 
							ri.getIntervaloDeTiempo()+1 : maxValorIntervalo);
				}
				else if(!ri.usada()) {
					ri.setIntervaloDeTiempo(ri.getIntervaloDeTiempo()-1 > this.minValorIntervalo ? 
							ri.getIntervaloDeTiempo()-1 : minValorIntervalo);
				}
				
			}
			
			ri.setUnidadesDeTiempoUsadas(0);
			this.indiceSemaforoVerde++;
			this.carreterasEntrantes.get((this.indiceSemaforoVerde-1)%this.carreterasEntrantes.size()).ponSemaforo(true);
			
			//else
				//ri.setIntervaloDeTiempo(ri.getIntervaloDeTiempo()-1);
			
		}
		
		/*- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == 0) entonces se
		selecciona la primera carretera entrante (la de la posición 0) y se pone su
		semáforo en verde.
		- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=
		-1) entonces:
		1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
		1.1. Se pone el semáforo de "ri" a rojo.
		1.2. Si ha sido usada en todos los pasos (“ri.usoCompleto()”), se fija
		el intervalo de tiempo a ... Sino, si no ha sido usada
		(“!ri.usada()”) se fija el intervalo de tiempo a ...
		1.3. Se coge como nueva carretera con semáforo a verde la inmediatamente
		Posterior a “ri”.*/
		
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
			String sem = (carreterasEntrantes.get(i).getSemaforo()) ? "green:" + carreterasEntrantes.get(i).getIntervaloDeTiempo(): "red";
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
		is.setValue("type", "rr\n");
	}


	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, this.maxValorIntervalo);
	}

}
