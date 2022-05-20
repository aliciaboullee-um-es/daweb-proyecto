package servicio;

import java.util.LinkedList;

public class ListadoOpiniones {

	public static class OpinionResumen {
		
		private String id;
		private String url;
		
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
			return "OpinionResumen [id=" + id + ", url=" + url + "]";
		}
	}
	
	private LinkedList<OpinionResumen> opiniones = new LinkedList<>();
	
	public LinkedList<OpinionResumen> getEncuestas() {
		return opiniones;
	}
	
	public void setEncuestas(LinkedList<OpinionResumen> opiniones) {
		this.opiniones = opiniones;
	}

	@Override
	public String toString() {
		return "ListadoEncuestas [encuestas=" + opiniones + "]";
	}
	
}
