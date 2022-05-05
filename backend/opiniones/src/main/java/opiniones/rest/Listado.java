package opiniones.rest;

import java.util.LinkedList;

import opiniones.servicio.ListadoOpiniones.OpinionResumen;

public class Listado {

	public static class ResumenExtendido {

		private String url;
		private OpinionResumen resumen;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public OpinionResumen getResumen() {
			return resumen;
		}

		public void setResumen(OpinionResumen resumen) {
			this.resumen = resumen;
		}

	}

	private LinkedList<ResumenExtendido> opiniones = new LinkedList<>();

	public LinkedList<ResumenExtendido> getOpiniones() {
		return opiniones;
	}

	public void setOpiniones(LinkedList<ResumenExtendido> opiniones) {
		this.opiniones = opiniones;
	}
}
