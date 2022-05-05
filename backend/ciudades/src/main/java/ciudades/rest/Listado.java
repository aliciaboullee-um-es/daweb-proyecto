package ciudades.rest;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

import ciudad.servicio.ListadoCiudades.CiudadResumen;

@XmlRootElement
public class Listado {

	public static class ResumenExtendido {

		private String url;
		private CiudadResumen resumen;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public CiudadResumen getResumen() {
			return resumen;
		}

		public void setResumen(CiudadResumen resumen) {
			this.resumen = resumen;
		}

	}

	private LinkedList<ResumenExtendido> ciudades = new LinkedList<>();

	public LinkedList<ResumenExtendido> getCiudades() {
		return ciudades;
	}

	public void setCiudades(LinkedList<ResumenExtendido> ciudades) {
		this.ciudades = ciudades;
	}
}
