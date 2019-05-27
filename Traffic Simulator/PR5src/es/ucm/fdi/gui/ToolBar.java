package es.ucm.fdi.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.BadSimulationObject;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.eventos.Evento;

public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner steps;
	private JTextField time;
	
	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador){
			super();
			controlador.addObserver(this);
			this.addSeparator();
			creaBotonCargar(mainWindow);
			creaBotonSalvar(mainWindow);
			creaBotonLimpiar(mainWindow);
			this.addSeparator();
			creaBotonCheckin(mainWindow, controlador);
			creaBotonEjecutar(mainWindow, controlador);
			creaBotonReiniciar(mainWindow, controlador);
			this.addSeparator();
			creaSpinner();
			creaReloj();
			this.addSeparator();
			creaBotonGeneraInfo(mainWindow);
			creaBotonLimpiarInformes(mainWindow);
			creaBotonSalvarInfo(mainWindow);
			this.addSeparator();
			creaBotonSalir(mainWindow);
			this.addSeparator();
			
	}
	
	private void creaBotonCargar(VentanaPrincipal mainWindow) {
		JButton botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new ImageIcon(loadImage("src/resources/icons/open.png")));
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
				}
			});
		this.add(botonCargar);
	}
	
	private void creaBotonSalvar(VentanaPrincipal mainWindow) {
		JButton botonSalvar = new JButton();
		botonSalvar.setToolTipText("Salva un fichero de eventos");
		botonSalvar.setIcon(new ImageIcon(loadImage("src/resources/icons/save.png")));
		botonSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaEventos();
				}
			});
		this.add(botonSalvar);
	}
	
	private void creaBotonLimpiar(VentanaPrincipal mainWindow) {
		JButton botonLimpiar = new JButton();
		botonLimpiar.setToolTipText("Borra el panel de eventos");
		botonLimpiar.setIcon(new ImageIcon(loadImage("src/resources/icons/clear.png")));
		botonLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaEventos();
				}
			});
		this.add(botonLimpiar);
	}
	
	private void creaBotonCheckin(VentanaPrincipal mainWindow, Controlador controlador) {
		JButton botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(new ImageIcon(loadImage("src/resources/icons/events.png")));
		botonCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
					byte[] contenido = mainWindow.getPanelEditorEventos().getTexto().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));
				} catch (ErrorDeSimulacion err) { 
					mainWindow.setMensaje("Ha habido un error al cargar eventos.");
				}
				 	mainWindow.setMensaje("Eventos cargados al simulador!");
				}
			}
		);
		this.add(botonCheckIn);
	}
	
	private void creaSpinner() {
		
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(steps);
	}
	
	private void creaBotonEjecutar(VentanaPrincipal mainWindow, Controlador controlador) {
		JButton botonEjecutar = new JButton();
		botonEjecutar.setToolTipText("Borra el panel de eventos");
		botonEjecutar.setIcon(new ImageIcon(loadImage("src/resources/icons/play.png")));
		botonEjecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.ejecuta((Integer) steps.getValue());
				 } catch (IOException e1) {
					 mainWindow.setMensaje("Problema con la entrada/salida.");
				 } catch (ErrorDeSimulacion e1) {
					 mainWindow.setMensaje("Ha habido un error en la simulación.");
				 } catch (BadSimulationObject e1) {
					 mainWindow.setMensaje("Algún objeto del panel es incorrecto.");
				 }
				}
			});
		this.add(botonEjecutar);
	}
	
	private void creaBotonReiniciar(VentanaPrincipal mainWindow, Controlador controlador) {
		JButton botonReiniciar = new JButton();
		botonReiniciar.setToolTipText("Borra el panel de eventos");
		botonReiniciar.setIcon(new ImageIcon(loadImage("src/resources/icons/reset.png")));
		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					controlador.reinicia();
				}
			});
		this.add(botonReiniciar);
	}

	private void creaReloj() {
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);
	}
	
	private void creaBotonGeneraInfo(VentanaPrincipal mainWindow) {
	// OPCIONAL
		JButton botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Genera informes");
		botonGeneraReports.setIcon(new ImageIcon(loadImage("src/resources/icons/report.png")));
		botonGeneraReports.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 mainWindow.setMensaje("Funcionalidad aún por implementar.");
			 }
			});
		this.add(botonGeneraReports);
	}
	
	private void creaBotonLimpiarInformes(VentanaPrincipal mainWindow) {
		JButton botonLimpiar = new JButton();
		botonLimpiar.setToolTipText("Borra el panel de informes.");
		botonLimpiar.setIcon(new ImageIcon(loadImage("src/resources/icons/delete_report.png")));
		botonLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaPanel();
				}
			});
		this.add(botonLimpiar);
	}
	
	private void creaBotonSalvarInfo(VentanaPrincipal mainWindow) {
		JButton botonSalvar = new JButton();
		botonSalvar.setToolTipText("Salva un fichero de informes");
		botonSalvar.setIcon(new ImageIcon(loadImage("src/resources/icons/save_report.png")));
		botonSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaInformes();
				}
			});
		this.add(botonSalvar);
	}

	private void creaBotonSalir(VentanaPrincipal mainWindow) {
		JButton botonSalir = new JButton();
		botonSalir.setToolTipText("Salir del simulador.");
		botonSalir.setIcon(new ImageIcon(loadImage("src/resources/icons/exit.png")));
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salir();
				}
			});
		this.add(botonSalir);
	}
	
	public static Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.time.setText(""+tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.steps.setValue(1);
		this.time.setText("0");
	}
}
