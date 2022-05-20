package adapter;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SitioInteresAdapter {

	protected String id;

	protected String nombre;
	protected List<String> linkExterno;

	protected String comentario;
	protected List<String> imagen;
	protected double latitud;
	protected double longitud;

	public SitioInteresAdapter() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getLinkExterno() {
		return linkExterno;
	}

	public void setLinkExterno(List<String> linkExterno) {
		this.linkExterno = linkExterno;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public List<String> getImagen() {
		return imagen;
	}

	public void setImagen(List<String> imagen) {
		this.imagen = imagen;
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
