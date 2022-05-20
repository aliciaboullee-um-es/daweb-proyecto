package ciudad.servicio;

import java.util.LinkedList;

public class ListadoCiudades {

	public static class CiudadResumen {

		private String id;
		private String nombre;

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

		@Override
		public String toString() {
			return "CiudadResumen [id=" + id + ", nombre=" + nombre + "]";
		}

	}

	private LinkedList<CiudadResumen> ciudades = new LinkedList<>();

	public LinkedList<CiudadResumen> getCiudades() {
		return ciudades;
	}

	public void setCiudades(LinkedList<CiudadResumen> ciudades) {
		this.ciudades = ciudades;
	}

	@Override
	public String toString() {
		return "CiudadResumen [ciudades=" + ciudades + "]";
	}

}
