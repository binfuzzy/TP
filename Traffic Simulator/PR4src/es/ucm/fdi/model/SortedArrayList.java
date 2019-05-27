package es.ucm.fdi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import exceptions.ErrorDeSimulacion;

public class SortedArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparator<E> cmp;
	
	public SortedArrayList(Comparator<E> cmp) {this.cmp = cmp;}
	
	@Override
	public boolean add(E e) {
		
		if(this.size() == 0) {
			super.add(0, e);
			return true;
		}
		
		for(int i = this.size()-1; i >= 0; i--) {
			if(cmp.compare(this.get(i), e)<=0) {
				super.add(i+1, e);
				return true;
			}
			if(i == 0) {
				super.add(0, e);
				return true;
			}
		}
		
		return false;
		// programar la inserción ordenada
	}
	
	@Override
	public void add(int index, E e) {
		try {
			throw new ErrorDeSimulacion("Cant add an element at the specified position since is a sorted list.");
		} catch (ErrorDeSimulacion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			throw new ErrorDeSimulacion("Cant add a collection at the specified position since is a sorted list.");
		} catch (ErrorDeSimulacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		 boolean flag = true;
		  for (E e: c) {
		   flag &= add(e);
		  }
		  return flag;
		// programar inserción ordenada (invocando a add)
	}
	
	@Override
	public E set(int index, E element) {
		
		try {
			throw new ErrorDeSimulacion("Cant set this element at any position");
		} catch (ErrorDeSimulacion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return element;
		
	}
	
	
	
	// sobreescribir los métodos que realizan operaciones de
	// inserción basados en un índice para que lancen excepcion.
	// Ten en cuenta que esta operación rompería la ordenación.
	// estos métodos son add(int index, E element),
	// addAll(int index, Colection<? Extends E>) y .
	
	
}
