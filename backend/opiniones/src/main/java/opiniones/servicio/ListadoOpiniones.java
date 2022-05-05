package opiniones.servicio;

import java.util.LinkedList;

public class ListadoOpiniones {

	public static class OpinionResumen {

		private String id;
		private String url;
		private double calificacionMedia;
		private Integer numValoraciones;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		@Override
		public String toString() {
			return "OpinionResumen [id=" + id + ", url=" + url + ", calificacionMedia=" + calificacionMedia
					+ ", numValoraciones=" + numValoraciones.toString() + "]";
		}

		public double getCalificacionMedia() {
			return calificacionMedia;
		}

		public void setCalificacionMedia(double calificacionMedia) {
			this.calificacionMedia = calificacionMedia;
		}

		public Integer getNumValoraciones() {
			return numValoraciones;
		}

		public void setNumValoraciones(Integer numValoraciones) {
			this.numValoraciones = numValoraciones;
		}

	}

	private LinkedList<OpinionResumen> opiniones = new LinkedList<>();

	public LinkedList<OpinionResumen> getOpiniones() {
		return opiniones;
	}

	public void setOpiniones(LinkedList<OpinionResumen> opiniones) {
		this.opiniones = opiniones;
	}

}
