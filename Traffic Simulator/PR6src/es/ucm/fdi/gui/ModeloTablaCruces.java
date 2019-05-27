package es.ucm.fdi.gui;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaCruces(String[] columnIdCruces, Controlador ctrl) {
		super(columnIdCruces, ctrl);
	}
	
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCarreterasVerde(); break;
			case 2: s = this.lista.get(indiceFil).getCarreterasRojo(); break;
			
			default: assert (false);
		}
		return s;
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
				lista = mapa.getCruces();
				fireTableStructureChanged();

			}
			
		});
		
			}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = mapa.getCruces();
				fireTableStructureChanged();

			}
			
		});
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				lista = mapa.getCruces();
				fireTableStructureChanged();

			}
			
		});
	}

}
