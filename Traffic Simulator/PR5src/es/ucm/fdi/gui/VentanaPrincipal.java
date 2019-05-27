package es.ucm.fdi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.control.Controlador;
import es.ucm.fdi.control.ObservadorSimuladorTrafico;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.model.MapaCarreteras;
import es.ucm.fdi.model.carreteras.Carretera;
import es.ucm.fdi.model.cruces.CruceGenerico;
import es.ucm.fdi.model.eventos.Evento;
import es.ucm.fdi.model.vehiculos.Vehiculo;

public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
	// SUPERIOR PANEL
	static private final String[] columnIdEventos =
	 { "#", "Tiempo", "Tipo" };

	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;
	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	// STATUS BAR (INFO AT THE BOTTOM OF THE WIN)
	private PanelBarraEstado panelBarraEstado;
	
	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera",
	 "Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen",
	 "Destino", "Longitud", "Vel. Max", "Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;
	// REPORT DIALOG
	//private DialogoInformes dialogoInformes; // opcional
	// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;
	
	public PanelAreaTexto getPanelEditorEventos() {
		return panelEditorEventos;
	}

	public VentanaPrincipal(String ficheroEntrada,Controlador ctrl) throws FileNotFoundException {
		 super("Simulador de Trafico");
		 this.controlador = ctrl;
		 this.ficheroActual = ficheroEntrada != null ?
		 new File(ficheroEntrada) : null;
		 this.initGUI();
		 // a�adimos la ventana principal como observadora
		 ctrl.addObserver(this);
	}
	
	private void initGUI() {
		 this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		 this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				System.out.println("WindowListener method called: windowActivated.");
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				
				salir();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				
			}
		 // al salir pide confirmaci�n
		 });
		 JPanel panelPrincipal = this.creaPanelPrincipal();
		 this.setContentPane(panelPrincipal);
		 
		 // MENU
		 BarraMenu menubar = new BarraMenu(this,this.controlador);
		 this.setJMenuBar(menubar);
		 
		// BARRA DE HERRAMIENTAS

		this.addToolBar(panelPrincipal);
		
		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral,BorderLayout.CENTER);
		 
		// PANEL SUPERIOR
		 this.createPanelSuperior(panelCentral);
		
		// PANEL INFERIOR
		 this.createPanelInferior(panelCentral);
		 
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado delsimulador)
		this.addBarraEstado(panelPrincipal);
		 
		// FILE CHOOSER
		 this.fc = new JFileChooser();
		 // REPORT DIALOG (OPCIONAL)
		 //this.dialogoInformes = new DialogoInformes(this,this.controlador);
		 this.pack();
		 this.setVisible(true);
		 this.setSize(1200, 900);
		 
	}
	
	private void addToolBar(JPanel panelPrincipal) {
		this.toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
		this.toolbar.setFloatable(false);
	}

	private JPanel creaPanelPrincipal() {
		
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		return jp;
	}
	
	private JPanel createPanelCentral() {
		 JPanel panelCentral = new JPanel();
		 // para colocar el panel superior e inferior
		 panelCentral.setLayout(new GridLayout(2,1));
		 return panelCentral;
	}

	private void createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
		try {
			String texto = this.leeFichero(this.ficheroActual);
			this.panelEditorEventos = new PanelEditorEventos("Eventos: " + this.ficheroActual.getName(),texto,true,this);
			this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ", new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
			this.panelInformes = new PanelInformes("Informes: ",false, this.controlador);
		}
		catch (FileNotFoundException e) {
			this.ficheroActual = null;
			this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
		}
		panelSuperior.add(this.panelEditorEventos);
		panelSuperior.add(this.panelColaEventos);
		panelSuperior.add(this.panelInformes);
		panelCentral.add(panelSuperior);
	}
	
	private void createPanelInferior(JPanel panelCentral) {
		//mirar aqui si divide mal los paneles
		JPanel panelInferior = new JPanel();
		JPanel panelInferiorIz = new JPanel();
		JPanel panelInferiorDr = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
		panelInferiorIz.setLayout(new GridLayout(3,1));
		panelInferiorDr.setLayout(new GridLayout(1,1));
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos",
				new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));
		
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras",
				new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera, this.controlador));
				
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces",
				new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));
		
		this.componenteMapa = new ComponenteMapa(this.controlador);
		// añadir un ScroolPane al panel inferior donde se coloca la
		// componente.
		panelInferiorDr.add(new JScrollPane(componenteMapa,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		panelInferiorIz.add(this.panelVehiculos);
		panelInferiorIz.add(this.panelCarreteras);
		panelInferiorIz.add(this.panelCruces);
		//panelInferior.add(this.componenteMapa);
		panelInferior.add(panelInferiorIz);
		panelInferior.add(panelInferiorDr);
		panelCentral.add(panelInferior);
		//panelCentral.add(panelInferior,BorderLayout.SOUTH);
	}
	
	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador !",this.controlador);
		// se añade al panel principal (el que contiene al panel
		// superior y al inferior)
		panelPrincipal.add(this.panelBarraEstado,BorderLayout.PAGE_END);
	}
	
	public void cargaFichero() {
		String path = System.getProperty("user.dir");
		this.fc.setCurrentDirectory(new File(path));
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			try {
				String s = leeFichero(fichero);
				this.controlador.reinicia();
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.panelEditorEventos.setBorde("Evento: "+ this.ficheroActual.getName());
				this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() +
						" de eventos cargado into the editor");
			}
			catch (FileNotFoundException e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
				
			}
		}
	}
	
	public void escribeFichero(File fichero, String texto) throws IOException {
	
		FileWriter fileOutput = new FileWriter (fichero);
		BufferedWriter out = new BufferedWriter(fileOutput);
		
		out.write(texto);
		out.close();
			
			
	}
	
	public void salir() {
		int n = JOptionPane.showOptionDialog(this, "Estas seguro de que quieres salir?", "Salir",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if (n == 0) System.exit(0);
	}
	
	public void salvaEventos() {
		 
		String path = System.getProperty("user.dir");
		this.fc.setCurrentDirectory(new File(path));
		int returnVal = this.fc.showSaveDialog(null);
		 
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
			 File fichero = this.fc.getSelectedFile();
			 try {
				 this.escribeFichero(fichero, this.panelEditorEventos.getTexto());
				 this.ficheroActual = fichero;
				 this.panelBarraEstado.setMensaje("Eventos guardados en el fichero " + fichero.getName());
				 this.panelEditorEventos.setBorde("Eventos: " + this.ficheroActual.getName());
			 }
			 catch (IOException e) {
				 System.out.println("Error durante la escritura del fichero: " +
				 e.getMessage());
			 }
		 }
	}
	
	public void salvaInformes() {
		 
		String path = System.getProperty("user.dir");
		this.fc.setCurrentDirectory(new File(path));
		int returnVal = this.fc.showSaveDialog(null);
		 
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
			 File fichero = this.fc.getSelectedFile();
			 try {
				 this.escribeFichero(fichero, this.panelInformes.getTexto());
				 this.panelBarraEstado.setMensaje("Informes guardados en el fichero " + fichero.getName());
			 }
			 catch (IOException e) {
				 System.out.println("Error durante la escritura del fichero: " +
				 e.getMessage());
			 }
		 }
	}

	private void muestraDialogoError(String string) {
		JOptionPane.showMessageDialog(null ,string);
		
	}

	private String leeFichero(File fichero) throws FileNotFoundException {
		String texto = "";
		FileReader fr = null;
	    BufferedReader br = null;
        try {
    	    fr = new FileReader (fichero);
            br = new BufferedReader(fr);
            
            String linea;
			while((linea = br.readLine())!=null) {
				texto += linea + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( null != fr ){   
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}     
			}
		}
        
		// TODO Auto-generated method stub
		return texto;
	}

	public int getSteps() {
		JSpinner steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		steps.setToolTipText("pasos a ejecutar: 1-1000");
		steps.setMaximumSize(new Dimension(70, 70));
		steps.setMinimumSize(new Dimension(70, 70));
		steps.setValue(0);
		JOptionPane.showMessageDialog(null, steps, "Cu�ntos pasos quieres ejecutar?", 1);
		return (Integer) steps.getValue();
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	public void limpiaPanel() {
		this.panelInformes.limpiar();
		
	}
	
	public void limpiaEventos() {
		this.panelEditorEventos.limpiar();
		
	}

	public void setMensaje(String string) {
		this.panelBarraEstado.setMensaje(string);
		
	}
}
