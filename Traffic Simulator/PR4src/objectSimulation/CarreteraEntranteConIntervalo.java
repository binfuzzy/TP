package objectSimulation;

import exceptions.BadSimulationObject;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante {

	private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner
	// el semáforo de la carretera en rojo
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que
	// avanza un vehículo
	private boolean usoCompleto; // Controla que en cada paso con el semáforo
	// en verde, ha pasado un vehículo
	private boolean usadaPorUnVehiculo; // Controla que al menos ha pasado un
	// vehículo mientras el semáforo estaba en verde.
	
	public CarreteraEntranteConIntervalo(Carretera carretera, int interval) {
		super(carretera);
		this.intervaloDeTiempo = interval;
		this.unidadesDeTiempoUsadas = 0;
		this.usoCompleto = false;
		this.usadaPorUnVehiculo = false;
	}

	public boolean isUsoCompleto() {
		return usoCompleto;
	}

	public void setUsoCompleto(boolean usoCompleto) {
		this.usoCompleto = usoCompleto;
	}

	public boolean isUsadaPorUnVehiculo() {
		return usadaPorUnVehiculo;
	}

	public void setUsadaPorUnVehiculo(boolean usadaPorUnVehiculo) {
		this.usadaPorUnVehiculo = usadaPorUnVehiculo;
	}

	public int getUnidadesDeTiempoUsadas() {
		return unidadesDeTiempoUsadas;
	}

	public void setUnidadesDeTiempoUsadas(int unidadesDeTiempoUsadas) {
		this.unidadesDeTiempoUsadas = unidadesDeTiempoUsadas;
	}

	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}

	public void setIntervaloDeTiempo(int intervaloDeTiempo) {
		this.intervaloDeTiempo = intervaloDeTiempo;
	}

	@Override
	public void avanzaPrimerVehiculo() throws BadSimulationObject {
		// Incrementa unidadesDeTiempoUsadas
		this.unidadesDeTiempoUsadas++;
		// Actualiza usoCompleto:
		// - Si “colaVehiculos” es vacía, entonces “usoCompleto=false”
		if(this.getColaVehiculos().size()==0)
			this.usoCompleto = false;
		// - En otro caso saca el primer vehículo “v” de la “colaVehiculos”,
		// y le mueve a la siguiente carretera (“v.moverASiguienteCarretera()”)
		else {
			super.avanzaPrimerVehiculo();
			// Pone “usadaPorUnVehiculo” a true.
			this.usadaPorUnVehiculo = true;
		}
		
		
	}
	
	// comprueba si se ha agotado el intervalo de tiempo.
	// “unidadesDeTiempoUsadas >= “intervaloDeTiempo”
	public boolean tiempoConsumido() {
		return this.unidadesDeTiempoUsadas >= this.intervaloDeTiempo;
		
	}
	
	public boolean usoCompleto() {
		return this.usoCompleto;
	} // método get
	
	public boolean usada() {
		return this.usadaPorUnVehiculo;
	} // método get
	
}
