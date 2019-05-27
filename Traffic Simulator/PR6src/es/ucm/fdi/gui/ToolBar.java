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
import javax.swing.JOptionPane;
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
	private JSpinner steps, delay;
	private JTextField time;
	private JButton botonCargar, botonSalvar, botonLimpiar, botonLimpiarInfo, botonCheckIn, botonEjecutar, botonReiniciar, botonGeneraReports, botonSalvarInfo, botonSalir;
	
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
			//creaBotonStop(mainWindow, controlador);
			creaBotonReiniciar(mainWindow, controlador);
			//this.addSeparator();
			//creaSpinnerDelay();
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
	
	public JSpinner getDelay() {
		return delay;
	}
	
	private void creaBotonCargar(VentanaPrincipal mainWindow) {
		botonCargar = new JButton();
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
		botonSalvar = new JButton();
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
		botonLimpiar = new JButton();
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
		botonCheckIn = new JButton();
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
					JOptionPane.showMessageDialog(null, "No se han cargado algunos eventos.", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
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
	
	/*private void creaSpinnerDelay() {
		  
		  this.add(new JLabel(" Delay: "));
		  this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 5000, 100));
		  this.delay.setToolTipText("delay entre pasos: 1-5000");
		  this.delay.setMaximumSize(new Dimension(70, 70));
		  this.delay.setMinimumSize(new Dimension(70, 70));
		  this.delay.setValue(500);
		  this.add(delay);
	}*/
	
	private void creaBotonEjecutar(VentanaPrincipal mainWindow, Controlador controlador) {
		botonEjecutar = new JButton();
		botonEjecutar.setToolTipText("Ejecuta el simulador los pasos del spinner");
		botonEjecutar.setIcon(new ImageIcon(loadImage("src/resources/icons/play.png")));
		botonEjecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 try {
					controlador.ejecuta((Integer) steps.getValue());
				 } catch (IOException e1) {
					 JOptionPane.showMessageDialog(null, "Problema con la entrada/salida", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (ErrorDeSimulacion e1) {
					 JOptionPane.showMessageDialog(null, "Error durante la simulacion", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (BadSimulationObject e1) {
					 JOptionPane.showMessageDialog(null, "Algun objeto del panel es incorrecto", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				 } catch (Exception e1) {
					 JOptionPane.showMessageDialog(null, "Excepcion desconocida", "Ha habido un error", JOptionPane.WARNING_MESSAGE);
				} 
					

			}
		});
		this.add(botonEjecutar);
	}
	
	/*private void creaBotonStop(VentanaPrincipal mainWindow, Controlador controlador) {
		  JButton botonStop = new JButton();
		  botonStop.setToolTipText("Parar el simulador.");
		  botonStop.setIcon(new ImageIcon(loadImage("src/resources/icons/stop.png")));
		  botonStop.addActionListener(new ActionListener() {
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   mainWindow.stop();
		   }
		   });
		  this.add(botonStop);
	}*/
	
	private void creaBotonReiniciar(VentanaPrincipal mainWindow, Controlador controlador) {
		botonReiniciar = new JButton();
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
		botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Genera informes");
		botonGeneraReports.setIcon(new ImageIcon(loadImage("src/resources/icons/report.png")));
		botonGeneraReports.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 mainWindow.setMensaje("Funcionalidad aun por implementar.");
			 }
			});
		this.add(botonGeneraReports);
	}
	
	private void creaBotonLimpiarInformes(VentanaPrincipal mainWindow) {
		botonLimpiarInfo = new JButton();
		botonLimpiarInfo.setToolTipText("Borra el panel de informes.");
		botonLimpiarInfo.setIcon(new ImageIcon(loadImage("src/resources/icons/delete_report.png")));
		botonLimpiarInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiaPanel();
				}
			});
		this.add(botonLimpiarInfo);
	}
	
	private void creaBotonSalvarInfo(VentanaPrincipal mainWindow) {
		botonSalvarInfo = new JButton();
		botonSalvarInfo.setToolTipText("Salva un fichero de informes");
		botonSalvarInfo.setIcon(new ImageIcon(loadImage("src/resources/icons/save_report.png")));
		botonSalvarInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvaInformes();
				}
			});
		this.add(botonSalvarInfo);
	}

	private void creaBotonSalir(VentanaPrincipal mainWindow) {
		botonSalir = new JButton();
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
	
	public void enableButtons(boolean mode) {
		botonCargar.setEnabled(mode);
		botonSalvar.setEnabled(mode); 
		botonLimpiar.setEnabled(mode); 
		botonLimpiarInfo.setEnabled(mode); 
		botonCheckIn.setEnabled(mode); 
		botonEjecutar.setEnabled(mode); 
		botonReiniciar.setEnabled(mode); 
		botonGeneraReports.setEnabled(mode); 
		botonSalvarInfo.setEnabled(mode); 
		botonSalir.setEnabled(mode); 
		
		steps.setEnabled(mode);
		delay.setEnabled(mode);
	}

}
