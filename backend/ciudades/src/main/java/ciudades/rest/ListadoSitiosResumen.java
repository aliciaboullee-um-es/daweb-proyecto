package ciudades.rest;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

import ciudad.servicio.ListadoSitios.SitiosResumen;

@XmlRootElement
public class ListadoSitiosResumen {

	public static class ResumenExtendidoSitios {

		private String url;
		private SitiosResumen resumen;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public SitiosResumen getResumen() {
			return resumen;
		}

		public void setResumen(SitiosResumen resumen) {
			this.resumen = resumen;
		}

	}

	private LinkedList<ResumenExtendidoSitios> sitios = new LinkedList<>();

	public LinkedList<ResumenExtendidoSitios> getSitios() {
		return sitios;
	}

	public void setSitios(LinkedList<ResumenExtendidoSitios> sitios) {
		this.sitios = sitios;
	}
}
