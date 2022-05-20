package adapter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AparcamientoAdapter {

	private String id;

	private String ciudad;

	private String direccion;
	private double latitud;
	private double longitud;
	protected int numValoraciones;
    protected double calificacionMedia;

	public AparcamientoAdapter(String id, String ciudad, String direccion, double latitud,
			double longitud, int numValoraciones, double calificacionMedia) {
		this.id = id;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.numValoraciones = numValoraciones;
		this.calificacionMedia = calificacionMedia;
	}

	public AparcamientoAdapter() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
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
