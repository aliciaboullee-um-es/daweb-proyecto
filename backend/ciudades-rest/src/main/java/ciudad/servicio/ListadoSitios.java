package ciudad.servicio;

import java.util.LinkedList;
import java.util.List;

public class ListadoSitios {

	public static class SitiosResumen {

		private String id;
		private String nombre;
		private String comentario;
		private String imagen;
		private double latitud;
		private double longitud;
		private List<String> linkExternos;

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

		public String getComentario() {
			return comentario;
		}

		public void setComentario(String comentario) {
			this.comentario = comentario;
		}

		public String getImagen() {
			return imagen;
		}

		public void setImagen(String imagen) {
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
		
		public List<String> getLinkExternos() {
			return linkExternos;
		}

		public void setLinkExternos(List<String> string) {
			this.linkExternos = string;
		}

		@Override
		public String toString() {
			return "SitiosResumen [id=" + id + ", nombre=" + nombre + "]";
		}

	}

	private LinkedList<SitiosResumen> sitios = new LinkedList<>();

	public LinkedList<SitiosResumen> getSitios() {
		return sitios;
	}

	public void setSitios(LinkedList<SitiosResumen> sitios) {
		this.sitios = sitios;
	}

	@Override
	public String toString() {
		return "SitiosResumen [sitios=" + sitios + "]";
	}

}
