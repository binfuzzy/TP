package es.ucm.fdi.gui;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaCarreteras(String[] columnIdCarreteras, Controlador ctrl) {
		super(columnIdCarreteras, ctrl); 
	}
	/*columnIdCarretera = { "ID", "Origen","Destino", "Longitud", "Vel. Max", "Vehiculos" };*/
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCruceOrigen(); break;
			case 2: s = this.lista.get(indiceFil).getCruceDestino(); break;
			case 3: s = this.lista.get(indiceFil).getLength(); break;
			case 4: s = this.lista.get(indiceFil).getVelocidadMaxima(); break;
			case 5: s = this.lista.get(indiceFil).getVehiculos(); break;
			
			default: assert (false);
		}
		return s;
	}

	public void setListaCarreteras(List<Carretera> listaCarreteras) {
		this.lista = listaCarreteras;
		fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {

		
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = mapa.getCarreteras();
				fireTableStructureChanged();

			}
			
		});
		
		
	}
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = mapa.getCarreteras();
				fireTableStructureChanged();

			}
			
		});
		
		
	}
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = mapa.getCarreteras();
				fireTableStructureChanged();

			}
			
		});
		
	}
}
