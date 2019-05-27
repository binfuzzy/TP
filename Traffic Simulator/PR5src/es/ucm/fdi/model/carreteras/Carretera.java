package es.ucm.fdi.model.carreteras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.ObjetoSimulacion;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class Carretera extends ObjetoSimulacion{

	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad m�xima


	protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	protected List<Vehiculo> vehiculos; // lista ordenada de veh�culos en la carretera (ordenada por localizaci�n)
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre veh�culos
	
	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id);
		this.longitud=length;
		this.velocidadMaxima=maxSpeed;
		this.cruceOrigen= src;
		this.cruceDestino=dest;
		this.vehiculos = new ArrayList<Vehiculo>();
		this.comparadorVehiculo = new Comparator<Vehiculo>() {

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				return o2.getLocalizacion() - o1.getLocalizacion();
			}
			
		};
		
		// se inicializan los atributos de acuerdo con los par�metros.
		// se fija el orden entre los vehiculos: (inicia comparadorVehiculo)
		//- la localizacion 0 es la menor
		
	}
	
	public int getLength() {
		return this.longitud;
	}
	
	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public CruceGenerico<?> getCruceOrigen() {
		return this.cruceOrigen;
	}

	public CruceGenerico<?> getCruceDestino() {
		return this.cruceDestino;
	}

	public List<Vehiculo> getVehiculos() {
		return this.vehiculos;
	}

	@Override
	public void avanza() {
		int vbase = calculaVelocidadBase();
		int obstaculos = 0;
		// inicializar obstaculos a 0
		// Para cada vehiculo de la lista �vehiculos�:
			//1. Si el veh�culo esta averiado se incrementa el n�mero de obstaculos.
		 	//2. Se fija la velocidad actual del veh�culo
			//3. Se pide al veh�culo que avance.
		
		for(Vehiculo v: vehiculos) {
			if(v.getTiempoDeInfraccion()>0) {//solo si es estricto
				obstaculos++;
				v.setVelocidadActual(0);
			}
			else
				v.setVelocidadActual(vbase/calculaFactorReduccion(obstaculos));
			v.avanza();
		}
		this.vehiculos.sort(this.comparadorVehiculo);	
	}
	
	public void entraVehiculo(Vehiculo vehiculo) {
		if(!this.vehiculos.contains(vehiculo)){
			this.vehiculos.add(vehiculo);
			this.vehiculos.sort(this.comparadorVehiculo);
		}
		// Si el veh�culo no existe en la carretera, se a�ade a la lista de veh�culos y
		// se ordena la lista.
		// Si existe no se hace nada.
	}
	
	public void saleVehiculo(Vehiculo vehiculo) {
		// elimina el vehiculo de la lista de vehiculos
		this.vehiculos.remove(vehiculo);
	}
	
	public void entraVehiculoAlCruce(Vehiculo v) {
		// a�ade el veh�culo al �cruceDestino� de la carretera�
		this.cruceDestino.entraVehiculoAlCruce(this.id, v);
	
		
	}
	
	protected int calculaVelocidadBase() {
		int n = this.vehiculos.size();
		int max = (n < 1) ? 1 : n; 
		return (this.velocidadMaxima < ((this.velocidadMaxima/max) +1) ) ? this.velocidadMaxima : (this.velocidadMaxima/max) + 1;
	}
	protected int calculaFactorReduccion(int obstaculos) {
		
		return obstaculos == 0 ? 1 : 2;
	}

	@Override
	protected String getNombreSeccion() {
		return "road_report";
	}

	@Override
	protected void completaDetallesSeccion(IniSection ini) {
		// state = (v2,80),(v3,67)
		String state = "";
		for(int i = 0; i < vehiculos.size(); i++) {
			state += "(" + vehiculos.get(i).getId() + "," + vehiculos.get(i).getLocalizacion() + ")";
			if(i != vehiculos.size()-1)
				state += ",";
			
		}
		state += "\n";
		ini.setValue("state", state);
		
	}

}
