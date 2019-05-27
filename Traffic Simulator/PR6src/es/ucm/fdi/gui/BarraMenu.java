package es.ucm.fdi.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;

public class BarraMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu menuFicheros, menuSimulador, menuReport;

	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		// MANEJO DE FICHEROS
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros,mainWindow);
		// SIMULADOR
		menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador,controlador,mainWindow);
		// INFORMES
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport,mainWindow);
	}
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		JMenuItem generar = new JMenuItem("Genera Informes");
		
		
		generar.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
					
			}
		});
		
		JMenuItem limpiar = new JMenuItem("Limpia Informes");
		
		limpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					mainWindow.limpiaPanel();
			}
		});
		
		menuReport.add(generar);
		menuReport.add(limpiar);

	}

	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) {
		JMenuItem ejecutar = new JMenuItem("Ejecutar");
		JMenuItem reiniciar = new JMenuItem("Reiniciar");
		JMenuItem redirecciona = new JMenuItem("Redireccionar salida");
		
		
		JMenuItem checkIn = new JMenuItem("Cargar tablas");
		checkIn.setMnemonic(KeyEvent.VK_C);
		checkIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		
		
		ejecutar.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 int pasos = mainWindow.getSteps();
				 try {
					controlador.ejecuta(pasos);
				 } catch (IOException e1) {
					 JOptionPane.showMessageDialog(null, "Problema con la entrada/salida", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (ErrorDeSimulacion e1) {
					 JOptionPane.showMessageDialog(null, "Problema en la simulacion", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (BadSimulationObject e1) {
					 JOptionPane.showMessageDialog(null, "Problema al parsear un objeto", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (Exception e1) {
					 JOptionPane.showMessageDialog(null, "Excepcion desconocida", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				}
			 }
		 });
		
		reiniciar.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 controlador.reinicia();
			 }
		 });
		
		checkIn.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 try {
					 controlador.reinicia();
					 byte[] contenido = mainWindow.getPanelEditorEventos().getTexto().getBytes();
					 controlador.cargaEventos(new ByteArrayInputStream(contenido));
				 } catch (ErrorDeSimulacion err) {
					 JOptionPane.showMessageDialog(null, "Problema al cargar alguno de los eventos", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 }
				 
				 mainWindow.setMensaje("Eventos cargados al simulador!");
			 }
		});

		menuSimulador.add(checkIn);
		menuSimulador.addSeparator();
		menuSimulador.add(ejecutar);
		menuSimulador.add(reiniciar);
		menuSimulador.add(redirecciona);

	}

	private void creaMenuFicheros(JMenu menu,VentanaPrincipal mainWindow) {
		
		JMenuItem cargar = new JMenuItem("Carga Eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		
		JMenuItem salvar = new JMenuItem("Salva Eventos");
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		
		JMenuItem salvarInformes = new JMenuItem("Salvar Informes");
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		//FALTAN LOS ACTION PERFORMED DE TODOS LOS DEMAS
		cargar.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
					mainWindow.cargaFichero();
					
			}
		});
		
		
		salvar.addActionListener(new ActionListener(){
			 @Override
			 public void actionPerformed(ActionEvent arg0) {
				 mainWindow.salvaEventos();
			 }
		});
		
		salvarInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaInformes();
			}
		
		});

		
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					mainWindow.salir();
			}
		
		});
		
		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(salvarInformes);
		menu.addSeparator();
		menu.add(salir);
	}
	
	public void enableMenu(boolean mode) {
		menuReport.setEnabled(mode);
		menuFicheros.setEnabled(mode);
		menuSimulador.setEnabled(mode);
	}
}
