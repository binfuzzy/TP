package objectSimulation;

import java.util.List;
import java.util.Random;

import es.ucm.fdi.ini.IniSection;
import exceptions.BadSimulationObject;

public class Coche extends Vehiculo {
	
	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	
	public Coche(String id, int velocidadMaxima, List<CruceGenerico<?>> itinerario, long semilla, int durMax, int resistencia, double prob) throws BadSimulationObject {
		super(id, velocidadMaxima, itinerario);
		this.duracionMaximaAveria = durMax;
		this.kmUltimaAveria = 0;
		this.resistenciaKm = resistencia;
		this.probabilidadDeAveria = prob;
		this.numAleatorio = new Random(semilla);
	}
	
	@Override
	public void avanza() {
		// - Si el coche está averiado poner “kmUltimaAveria” a “kilometraje”.
		if(this.getTiempoDeInfraccion()>0) {
			this.kmUltimaAveria = this.getKilometraje();
		}
		
		// - Sino el coche no está averiado y ha recorrido “resistenciakm”, y además
		// “numAleatorio”.nextDouble() < “probabilidadDeAveria”, entonces
		// incrementar “tiempoAveria” con “numAleatorio.nextInt(“duracionMaximaAveria”)+1
		
		else if((this.getTiempoDeInfraccion() == 0 ) && ((this.getKilometraje()-this.kmUltimaAveria) >= this.resistenciaKm) && (this.numAleatorio.nextDouble() < this.probabilidadDeAveria)) {
			
			this.setTiempoAveria(this.numAleatorio.nextInt(this.duracionMaximaAveria)+1);
	
		}
		// - Llamar a super.avanza();
		super.avanza();
	
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "car");
		is.setValue("speed", this.getVelocidadActual());
		is.setValue("kilometrage", this.getKilometraje());
		is.setValue("faulty", this.getTiempoDeInfraccion());
		is.setValue("location", this.getHaLlegado() ? "arrived" + "\n": "(" + this.getCarretera() + "," + this.getLocalizacion() + ")" + "\n");
		
	}

}
