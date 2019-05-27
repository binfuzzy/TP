package es.ucm.fdi.model.cruces;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.carreterasEntrantes.CarreteraEntranteConIntervalo;

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
		if(this.indiceSemaforoVerde == -1) {
			this.carreterasEntrantes.get(0).ponSemaforo(true);
			this.indiceSemaforoVerde++;
		}
		
		else {
			CarreteraEntranteConIntervalo ri = this.carreterasEntrantes.get(indiceSemaforoVerde);
			if (ri.tiempoConsumido()) {
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
				
				if(ri.usoCompleto()) {
					ri.setIntervaloDeTiempo(ri.getIntervaloDeTiempo()+1 < this.maxValorIntervalo ? 
							ri.getIntervaloDeTiempo()+1 : maxValorIntervalo);
				}
				else if(!ri.usada()) {
					ri.setIntervaloDeTiempo(ri.getIntervaloDeTiempo()-1 > this.minValorIntervalo ? 
							ri.getIntervaloDeTiempo()-1 : minValorIntervalo);
				}
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsoCompleto(true);
				ri.setUsadaPorUnVehiculo(false);
				this.indiceSemaforoVerde = (this.indiceSemaforoVerde+1)%this.carreterasEntrantes.size();
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(true);
			}
			
			
		}
		
		/*- Si no hay carretera con sem�foro en verde (indiceSemaforoVerde == 0) entonces se
		selecciona la primera carretera entrante (la de la posici�n 0) y se pone su
		sem�foro en verde.
		- Si hay carretera entrante "ri" con su sem�foro en verde, (indiceSemaforoVerde !=
		-1) entonces:
		1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
		1.1. Se pone el sem�foro de "ri" a rojo.
		1.2. Si ha sido usada en todos los pasos (�ri.usoCompleto()�), se fija
		el intervalo de tiempo a ... Sino, si no ha sido usada
		(�!ri.usada()�) se fija el intervalo de tiempo a ...
		1.3. Se coge como nueva carretera con sem�foro a verde la inmediatamente
		Posterior a �ri�.*/
		
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
			String sem = (carreterasEntrantes.get(i).getSemaforo()) ? "green:" + (carreterasEntrantes.get(i).getIntervaloDeTiempo()-carreterasEntrantes.get(i).getUnidadesDeTiempoUsadas()) : "red";
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
		ts += ce.getSemaforo() ? "green:" + (ce.getIntervaloDeTiempo() - ce.getUnidadesDeTiempoUsadas()) : "red";
		ts += "," + ce.getColaVehiculos() + ")";
		return ts;
	}

}
