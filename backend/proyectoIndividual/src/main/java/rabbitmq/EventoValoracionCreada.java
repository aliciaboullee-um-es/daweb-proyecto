package rabbitmq;


import modelo.Valoracion;

public class EventoValoracionCreada {

	private String url;
	private Valoracion valoracionEmitida;
	private int numValoraciones;
	private double calificacionMedia;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Valoracion getValoracionEmitida() {
		return valoracionEmitida;
	}
	public void setValoracionEmitida(Valoracion valoracionEmitida) {
		this.valoracionEmitida = valoracionEmitida;
	}
	public int getNumValoraciones() {
		return numValoraciones;
	}
	public void setNumValoraciones(int numValoraciones) {
		this.numValoraciones = numValoraciones;
	}
	public double getCalificacionMedia() {
		return calificacionMedia;
	}
	public void setCalificacionMedia(double calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}
}
