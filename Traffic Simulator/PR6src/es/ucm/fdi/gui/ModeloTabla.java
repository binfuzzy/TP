package es.ucm.fdi.gui;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;

//EL MODELO SE REGISTRA COMO OBSERVADOR
public abstract class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnIds;
	protected List<T> lista;
	

	public ModeloTabla(String[] columnIdEventos, Controlador ctrl) {
		this.lista = null;
		this.columnIds = columnIdEventos;
		ctrl.addObserver(this);
	}
	
	
	
	


	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) { return this.columnIds[col]; }

	@Override
	public int getColumnCount() {return this.columnIds.length;}

	@Override
	public int getRowCount() {return this.lista == null ? 0 : this.lista.size();}

}
