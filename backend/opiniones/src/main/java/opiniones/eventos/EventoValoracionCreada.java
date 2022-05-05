package opiniones.eventos;

import opiniones.modelo.Valoracion;

public class EventoValoracionCreada {

	private String url;
	private Integer NumValoraciones;
	private double CalificacionMedia;
	private Valoracion valoracion;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNumValoraciones() {
		return NumValoraciones;
	}

	public void setNumValoraciones(Integer numValoraciones) {
		NumValoraciones = numValoraciones;
	}

	public double getCalificacionMedia() {
		return CalificacionMedia;
	}

	public void setCalificacionMedia(double calificacionMedia) {
		CalificacionMedia = calificacionMedia;
	}

	public Valoracion getValoracion() {
		return valoracion;
	}

	public void setValoracion(Valoracion valoracion) {
		this.valoracion = valoracion;
	}

}
