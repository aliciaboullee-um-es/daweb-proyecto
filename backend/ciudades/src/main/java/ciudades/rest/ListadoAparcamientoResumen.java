package ciudades.rest;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

import ciudad.servicio.ListadoAparcamientos.AparcamientoResumen;

@XmlRootElement
public class ListadoAparcamientoResumen {

	public static class ResumenExtendidoAparcamiento {

		private String url;
		private AparcamientoResumen resumen;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public AparcamientoResumen getResumen() {
			return resumen;
		}

		public void setResumen(AparcamientoResumen resumen) {
			this.resumen = resumen;
		}

	}

	private LinkedList<ResumenExtendidoAparcamiento> aparcamientos = new LinkedList<>();

	public LinkedList<ResumenExtendidoAparcamiento> getAparcamientos() {
		return aparcamientos;
	}

	public void setAparcamientos(LinkedList<ResumenExtendidoAparcamiento> aparcamientos) {
		this.aparcamientos = aparcamientos;
	}
}
