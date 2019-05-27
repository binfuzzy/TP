package es.ucm.fdi.model.eventos;

import es.ucm.fdi.ini.IniSection;

public abstract class ConstructorEventos {

	// cada clase darÃ¡ los valores correspondientes a estos atributos
	// en la constructora
	protected String etiqueta; // etiqueta de la entrada (â€œnew_roadâ€�, etc..)
	protected String[] claves; // campos de la entrada (â€œtimeâ€�, â€œvehiclesâ€�, etc.)
	protected String[] valoresPorDefecto;
	
	
	public ConstructorEventos() {
		this.etiqueta = null;
		this.claves = null;
	}
	
	protected static int parseaInt(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		
		else return Integer.parseInt(seccion.getValue(clave));
	}

	protected static int parseaInt(IniSection seccion, String clave, int valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Integer.parseInt(seccion.getValue(clave)) : valorPorDefecto;
	}
	
	protected static int parseaIntNoNegativo(IniSection seccion, String clave, int valorPorDefecto) {
		
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave + " no es un ID valido");
		else return i;
	}
	
	
	
	
	protected static double parseaDouble(IniSection seccion, String clave, double valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Double.parseDouble(seccion.getValue(clave)) : valorPorDefecto;
	}
	
	
	protected static double parseaDouble(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		
		else return Double.parseDouble(seccion.getValue(clave));
		
	}
	
	protected static double parseaDoubleNoNegativo(IniSection seccion, String clave, double valorPorDefecto) {
		
		double i = ConstructorEventos.parseaDouble(seccion, clave, valorPorDefecto);
		if (i < 0 || i > 1)
			throw new IllegalArgumentException("El valor " + i + " para " + clave + " no es un ID valido");
		else return i;
	}
	
	
	
	
	protected static long parseaLong(IniSection seccion, String clave, long valorPorDefecto) {
		String v = seccion.getValue(clave);
		return (v != null) ? Long.parseLong(seccion.getValue(clave)) : valorPorDefecto;
	}
	
	
	protected static long parseaLong(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		
		else return Long.parseLong(seccion.getValue(clave));
		
	}
	
	protected static long parseaLongNoNegativo(IniSection seccion, String clave, long valorPorDefecto) {
		
		long i = ConstructorEventos.parseaLong(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave + " no es un ID valido");
		else return i;
	}
	
	
	
	
	
	protected static String identificadorValido(IniSection seccion, String clave) {
		String s = seccion.getValue(clave);
		if (!esIdentificadorValido(s))
			throw new IllegalArgumentException("El valor " + s + " para " + clave + " no es un ID valido");
		else return s;
	}
	
	public static String[] identificadoresValidos(IniSection section, String clave) {
		String a = section.getValue(clave);
		String[] s = a.split(",");
		for(int i = 0; i < s.length; i++) {
			if(!esIdentificadorValido(s[i]))
				throw new IllegalArgumentException("El valor " + s[i] + " para " + clave + " no es un ID valido");
				
		}
		return s;
	}
	
	// identificadores vÃ¡lidos
	// sÃ³lo pueden contener letras, nÃºmeros y subrayados
	private static boolean esIdentificadorValido(String id) {
		return id != null && id.matches("[a-z0-9_]+");
	}
	
	public abstract Evento parser(IniSection section);

	
	
}
