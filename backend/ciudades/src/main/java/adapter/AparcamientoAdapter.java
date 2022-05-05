package adapter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AparcamientoAdapter {

	private String id;

	private String ciudad;

	private String direccion;
	private double latitud;
	private double longitud;

	public AparcamientoAdapter(String id, String ciudad, String direccion, double latitud, double longitud) {
		this.id = id;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
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

}
