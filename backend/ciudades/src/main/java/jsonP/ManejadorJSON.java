package jsonP;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ManejadorJSON {

	private JsonReader reader;
	private JsonObject raiz;

	public ManejadorJSON(String rutaArchivoJson) throws MalformedURLException, IOException {
		InputStreamReader fuente;
		try {
			fuente = new InputStreamReader(new URL(rutaArchivoJson).openStream());

			reader = Json.createReader(fuente);
			raiz = reader.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getLinksExternos(String url) {
		List<String> toReturn = new LinkedList<>();

		String[] parts = url.split("/");
		String monument = parts[parts.length - 1];

		String link = "http://es.dbpedia.org/resource/" + monument;

		JsonObject enlace = raiz.getJsonObject(link);
		JsonArray linksExternos = enlace.getJsonArray("http://dbpedia.org/ontology/wikiPageExternalLink");
		// Verificar que tiene links externos (puede no tenerlos)
		if (linksExternos != null) {
			for (JsonObject elemento : linksExternos.getValuesAs(JsonObject.class)) {
				toReturn.add(
						elemento.get("value").toString().substring(1, elemento.get("value").toString().length() - 1));
			}
		}
		return toReturn;
	}

	public String getName(String url) {

		String[] parts = url.split("/");
		String monument = parts[parts.length - 1];

		String link = "http://es.dbpedia.org/resource/" + monument;

		JsonObject enlace = raiz.getJsonObject(link);
		JsonArray abstracts = enlace.getJsonArray("http://es.dbpedia.org/property/nombre");
		if (abstracts != null) {
			for (JsonObject elemento : abstracts.getValuesAs(JsonObject.class)) {
				if (elemento.get("lang") != null && elemento.get("lang").toString().equals("\"es\"")) {

					return elemento.get("value").toString().substring(1, elemento.get("value").toString().length() - 1);

				}

			}
		}
		return null;
	}

	public String getESComment(String url) {

		String[] parts = url.split("/");
		String monument = parts[parts.length - 1];

		String link = "http://es.dbpedia.org/resource/" + monument;

		JsonObject enlace = raiz.getJsonObject(link);
		JsonArray abstracts = enlace.getJsonArray("http://dbpedia.org/ontology/abstract");
		if (abstracts != null) {
			for (JsonObject elemento : abstracts.getValuesAs(JsonObject.class)) {
				if (elemento.get("lang").toString().equals("\"es\""))
					return elemento.get("value").toString().substring(1, elemento.get("value").toString().length() - 1);
			}
		}
		return null;
	}

	public List<String> getImages(String url) {

		String[] parts = url.split("/");
		String monument = parts[parts.length - 1];

		String link = "http://es.dbpedia.org/resource/" + monument;

		List<String> toReturn = new LinkedList<>();

		JsonObject enlace = raiz.getJsonObject(link);
		JsonArray abstracts = enlace.getJsonArray("http://es.dbpedia.org/property/imagen");
		if (abstracts != null) {
			for (JsonObject elemento : abstracts.getValuesAs(JsonObject.class)) {
				if (elemento.get("lang") != null && elemento.get("lang").toString().equals("\"es\""))
					toReturn.add(elemento.get("value").toString().substring(1,
							elemento.get("value").toString().length() - 1));
			}
		}
		return toReturn;
	}
}
