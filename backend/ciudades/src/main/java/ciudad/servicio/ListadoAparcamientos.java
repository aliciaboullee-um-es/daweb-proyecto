package ciudad.servicio;

import java.util.LinkedList;
import java.util.List;

public class ListadoAparcamientos {

	public static class AparcamientoResumen {

		private String id;
		private String direccion;
		private double latitud;
		private double longitud;
		private List<String> linkExternos;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		@Override
		public String toString() {
			return "AparcamientoResumen [id=" + id + ", direccion=" + direccion + "]";
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

		public void setLinkExternos(List<String> linkExternos) {
			this.linkExternos = linkExternos;
		}

	}

	private LinkedList<AparcamientoResumen> aparcamientos = new LinkedList<>();

	public LinkedList<AparcamientoResumen> getAparcamientos() {
		return aparcamientos;
	}

	public void setAparcamientos(LinkedList<AparcamientoResumen> aparcamientos) {
		this.aparcamientos = aparcamientos;
	}

	@Override
	public String toString() {
		return "AparcamientoResumen [aparcamientos=" + aparcamientos + "]";
	}

}
