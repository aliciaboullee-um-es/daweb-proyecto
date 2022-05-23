package modelo;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Opinion {
	
	private String url;
	
	private LinkedList<Valoracion> valoraciones = new LinkedList<Valoracion>();
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LinkedList<Valoracion> getValoraciones() {
		return new LinkedList<Valoracion>(valoraciones);
	}

	public void setValoraciones(LinkedList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	// Propiedades calculadas
	@JsonIgnore
	public int getNumeroValoraciones() {
		return this.valoraciones.size();
	}
	
	@JsonIgnore
	public double getCalificacionMedia() {
		double media = 0.0;
		for(Valoracion v: this.valoraciones) {
			media=media + v.getCalificacion();
		}
		return media/getNumeroValoraciones();
	}
	
	//Funcionalidad extra
	public void add(Valoracion v) {
		valoraciones.addFirst(v);
	}

	@Override
	public String toString() {
		return "Opinion [url=" + url + ", valoraciones=" + valoraciones + ", getNumeroValoraciones()="
				+ getNumeroValoraciones() + ", getCalificacionMedia()=" + getCalificacionMedia() + "]";
	}
	
	
}
