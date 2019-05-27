package objectSimulation;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import exceptions.BadSimulationObject;

public class Vehiculo extends ObjetoSimulacion {
	
	private Carretera carretera; // carretera en la que est� el veh�culo
	private int velocidadMaxima; // velocidad m�xima
	private int velocidadActual; // velocidad actual
	private int kilometraje; // distancia recorrida
	private int localizacion; // localizaci�n en la carretera
	private int tiempoAveria; // tiempo que estara averiado
	private boolean haLlegado; //indica si ha terminado su itinerario
	private boolean estaEnUnCruce; //indica si esta en un cruce
	private int posItinerario;
	List<CruceGenerico<?>> itinerario; // itinerario a recorrer (m�nimo 2)

	public Vehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> itinerario) throws BadSimulationObject {
		super(id);
		if((velocidadMaxima >= 0) && (itinerario.size() >= 2)) {
			
			this.velocidadMaxima = velocidadMaxima;
			this.velocidadActual = 0;
			this.kilometraje = 0;
			this.localizacion = 0;
			this.tiempoAveria = 0;
			this.haLlegado = false;
			this.estaEnUnCruce = false;
			this.posItinerario = 0;
			this.itinerario = itinerario;
			this.carretera = itinerario.get(0).carreteraHaciaCruce(itinerario.get(1));
		}
			
		else {
			throw new BadSimulationObject("Velocidad maxima erronea o menos de dos cruces");
			
		}
		// comprobar que la velocidadMaxima es mayor o igual que 0, y
		 // que el itinerario tiene al menos dos cruces.
		 // En caso de no cumplirse lo anterior, lanzar una excepci�n.
		 // inicializar los atributos teniendo en cuenta los par�metros.
		 // al crear un veh�culo su �carretera� ser� inicalmene �null�.
	}

	public int getLocalizacion() {
		return this.localizacion;
	}
	public int getTiempoDeInfraccion() {
		return this.tiempoAveria;
	}
	
	public int getVelocidadMaxima() {
		return this.velocidadMaxima;
	}

	public int getVelocidadActual() {
		return this.velocidadActual;
	}

	public int getKilometraje() {
		return this.kilometraje;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public boolean getHaLlegado() {
		return haLlegado;
	}

	public List<CruceGenerico<?>> getItinerario() {
		return itinerario;
	}

	public void setVelocidadActual(int velocidad) {
		if(!this.estaEnUnCruce) {
		 // Si �velocidad� es negativa, entonces la �velocidadActual� es 0.
			if(velocidad < 0)
				this.velocidadActual = 0;
		 // Si �velocidad� excede a �velocidadMaxima�, entonces la
		 // �velocidadActual� es �velocidadMaxima�
			else if(velocidad > this.velocidadMaxima)
				this.velocidadActual = this.velocidadMaxima;
		 // En otro caso, �velocidadActual� es �velocidad�
			else
				this.velocidadActual = velocidad;
		}
		else
			this.velocidadActual = 0;
	}
	
	public void setTiempoAveria(Integer duracionAveria) {
		// Comprobar que �carretera� no es null.
		if(this.carretera != null) {
			this.tiempoAveria += duracionAveria;
		
			if(this.tiempoAveria > 0)
				this.velocidadActual = 0;
		}
		// Se fija el tiempo de aver�a de acuerdo con el enunciado.
		// Si el tiempo de aver�a es finalmente positivo, entonces
		// la �velocidadActual� se pone a 0
	}
	
	@Override
	public void avanza() {
		// si el coche est� averiado, decrementar tiempoAveria
		if(this.tiempoAveria != 0)
			this.tiempoAveria--;
		// si el coche est� esperando en un cruce, no se hace nada.
		// en otro caso:
		else if(!estaEnUnCruce) {
			//1. Actualizar su �localizacion�
			//2. Actualizar su �kilometraje�
			 
			if(this.localizacion + this.velocidadActual >= this.carretera.getLength()){
				this.kilometraje = this.kilometraje + this.carretera.getLength() - this.localizacion;
			 	this.localizacion = this.carretera.getLength();
			 	this.carretera.entraVehiculoAlCruce(this);/*mirar si es con esto solo*/
			 	this.velocidadActual = 0;
			 	this.estaEnUnCruce = true;
			}
			//3. Si el coche ha llegado a un cruce (localizacion >= carretera.getLength())
			//3.1. Poner la localizaci�n igual a la longitud de la carretera.
			//3.2. Corregir el kilometraje.
			//????????????????????
			//3.3. Indicar a la carretera que el veh�culo entra al cruce.
			//????coger vehiculo de la lista (se borra cuando llega al cruce o cuando pasa a la otra carretera)
			//borrarlo y añadirlo a la lista del cruce????
			//?????????
			
			//3.4. Marcar que este vehiculo esta en un cruce (this.estEnCruce = true)
			else{
			 	this.localizacion += velocidadActual;
			 	this.kilometraje += this.velocidadActual;
			}
		}
	}
	
	public void moverASiguienteCarretera() throws BadSimulationObject {
		if(this.carretera != null)
			this.carretera.saleVehiculo(this);
		// Si la carretera no es null, sacar el veh�culo de la carretera.
		if((estaEnUnCruce == true) && (this.itinerario.size() - 1 == this.posItinerario)) {
			//se llama desde cruce a esta???
			//el cruce src seria el valido para hacer el if o como
			this.haLlegado = true;
			this.carretera = null;
			this.velocidadActual = 0;
			this.localizacion = 0;
		}
		// Si hemos llegado al �ltimo cruce del itinerario, entonces:
		 //1. Se marca que el veh�culo ha llegado (this.haLlegado = true).
		 //2. Se pone su carretera a null.
		 //3. Se pone su �velocidadActual� y �localizacion� a 0.
		 // En otro caso:
		else {
			CruceGenerico<?> origen = this.itinerario.get(this.posItinerario);
			CruceGenerico<?> dest = this.itinerario.get(this.posItinerario + 1);
			Carretera c = origen.carreteraHaciaCruce(dest);
			if(c == null) {
				throw new BadSimulationObject("No existe siguiente carretera");
			}
			c.entraVehiculo(this);
			this.posItinerario++;
			this.localizacion = 0;
			this.carretera = c;
			this.carretera.getVehiculos().sort(this.carretera.comparadorVehiculo);
			//this.setVelocidadActual(this.carretera.calculaVelocidadBase());
			this.estaEnUnCruce = false;
		}
		 //1. Se calcula la siguiente carretera a la que tiene que ir.
		 //2. Si dicha carretera no existe, se lanza excepci�n.
		 //3. En otro caso, se introduce el veh�culo en la carretera.
		 //4. Se inicializa su localizaci�n.
		 // marcamos que el veh�culo no est� en un cruce (estaEnUnCruce = false).

	}
	
	
	protected String getNombreSeccion() {
		
		return "vehicle_report";
	}
	
	protected void completaDetallesSeccion(IniSection ini) {
		ini.setValue("speed", this.velocidadActual);
		ini.setValue("kilometrage", this.kilometraje);
		ini.setValue("faulty", this.tiempoAveria);
		ini.setValue("location", this.haLlegado ? "arrived" + "\n": "(" + this.carretera + "," + this.getLocalizacion() + ")" + "\n");
	}

}
