package es.ucm.fdi.gui;

import java.util.List;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.eventos.Evento;

public class ModeloTablaEventos extends ModeloTabla<Evento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = indiceFil; break;
			case 1: s = this.lista.get(indiceFil).getTiempo(); break;
			case 2: s = this.lista.get(indiceFil).toString(); break;
			default: assert (false);
		}
		return s;
	}
	
	public void setListaEventos(List<Evento> listaEventos) {
		this.lista = listaEventos;
		fireTableStructureChanged();
	}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos; 
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos;
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos; 
		this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}
}
