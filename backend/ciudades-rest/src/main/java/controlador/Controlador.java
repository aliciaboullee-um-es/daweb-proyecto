package controlador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dom.ManejadorDOMAparcamientos;
import jsonP.ManejadorJSON;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.SitioInteres;
import sax.ManejadorSAX;
import sax.ManejadorSTCruz;

public class Controlador {

	// static String URL_LORCA =
	// "http://api.geonames.org/findNearbyWikipedia?postalcode=29008&lang=es&username=arso";
	// http://api.geonames.org/findNearbyWikipedia?lat=36.7196&lng=-4.42002&lang=es&username=abgn

	private SAXParser analizadorSAX;
	private DocumentBuilder analizadorDOM;
	private ManejadorJSON manejadorJSON;

	private String url;

	public Controlador(String codigoPostal) {

		url = "http://api.geonames.org/findNearbyWikipedia?postalcode=" + codigoPostal
				+ "&maxRows=500&lang=es&username=abgn";

		// 1. Obtener una factoría
		SAXParserFactory factoria = SAXParserFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		try {
			this.analizadorSAX = factoria.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 1. Obtener una factoría
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		try {
			this.analizadorDOM = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Controlador(double lat, double lng) {

		url = "http://api.geonames.org/findNearbyWikipedia?lat=" + lat + "&lng=" + lng
				+ "&lang=es&maxRows=500&username=abgn";

		// 1. Obtener una factoría
		SAXParserFactory factoria = SAXParserFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		try {
			this.analizadorSAX = factoria.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 1. Obtener una factoría
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		try {
			this.analizadorDOM = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Obtener info de aparcamientos Lorca
	public List<Aparcamiento> getAparcamientosXML(String rutaXML)
			throws ParserConfigurationException, SAXException, IOException {
		List<Aparcamiento> toReturn = new LinkedList<>();

		// Analizar el documento
		try {
			ManejadorSAX manejador = new ManejadorSAX();

			analizadorSAX.parse(rutaXML, manejador);

			for (Aparcamiento aparcamiento : manejador.getAparcamientos()) {
				toReturn.add(aparcamiento);
			}

		} catch (IOException e) {
			System.out.println("El documento no ha podido ser leído");
		} catch (SAXException e) {
			System.out.println("Error de pocesamiento: " + e.getMessage());
		}
		return toReturn;
	}

	// Obtener info de ciudad (Geonames)
	public List<String> getNombreCiudades() throws Exception {
		List<String> toReturn = new LinkedList<>();

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList listaTitles = current.getElementsByTagName("title");

				if (listaTitles.getLength() == 0)
					throw new IllegalStateException("El documento no tiene titulos");

				Element titulo = (Element) listaTitles.item(0);
				toReturn.add(titulo.getTextContent());
			}
		}
		return toReturn;
	}

	public String getSummary(String ciudad) throws Exception {

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList hijos = current.getChildNodes();
				for (int j = 0; j < hijos.getLength(); j++) {
					if (hijos.item(j).getNodeName().equals("title") && hijos.item(j).getTextContent().equals(ciudad)) {
						for (int k = j; k < hijos.getLength(); k++)
							if (hijos.item(k).getNodeName().equals("summary"))
								return hijos.item(k).getTextContent();
					}
				}
			}
		}
		throw new IllegalStateException("El documento no tiene resumen para esa ciudad");
	}

	public String getLatitud(String ciudad) throws Exception {

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList hijos = current.getChildNodes();
				for (int j = 0; j < hijos.getLength(); j++) {

					for (int k = j; k < hijos.getLength(); k++)
						if (hijos.item(k).getNodeName().equals("lat"))
							return hijos.item(k).getTextContent();

				}
			}
		}
		throw new IllegalStateException("El documento no tiene resumen para esa ciudad");
	}

	public String getLongitud(String ciudad) throws Exception {

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList hijos = current.getChildNodes();
				for (int j = 0; j < hijos.getLength(); j++) {

					for (int k = j; k < hijos.getLength(); k++)
						if (hijos.item(k).getNodeName().equals("lng"))
							return hijos.item(k).getTextContent();

				}
			}
		}
		throw new IllegalStateException("El documento no tiene resumen para esa ciudad");
	}

	// Obtener los enlaces de Wikipedia de una ciudad
	public List<String> getWikipediaURL() throws Exception {

		LinkedList<String> list = new LinkedList<String>();

		String link = "";

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList hijos = current.getChildNodes();
				for (int j = 0; j < hijos.getLength(); j++) {

					for (int k = j; k < hijos.getLength(); k++)
						if (hijos.item(k).getNodeName().equals("wikipediaUrl")) {
							link = java.net.URLDecoder.decode(hijos.item(k).getTextContent(),
									StandardCharsets.UTF_8.name());
							if (!list.contains(link))
								list.add(link);
						}

				}
			}
			return list;
		}

	}

	public String getCordenatesByLink(String link) throws DOMException, SAXException, IOException {

		String link2 = "";
		String lat = "";
		String longi = "";

		// Analizar el documento
		Document documento = analizadorDOM.parse(url);

		NodeList entries = documento.getElementsByTagName("entry");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList hijos = current.getChildNodes();
				for (int j = 0; j < hijos.getLength(); j++) {

					for (int k = j; k < hijos.getLength(); k++) {
						if (hijos.item(k).getNodeName().equals("lat"))
							lat = hijos.item(k).getTextContent();
						if (hijos.item(k).getNodeName().equals("lng"))
							longi = hijos.item(k).getTextContent();
						if (hijos.item(k).getNodeName().equals("wikipediaUrl")) {
							link2 = java.net.URLDecoder.decode(hijos.item(k).getTextContent(),
									StandardCharsets.UTF_8.name());
							if (link2.equals(link))
								return lat + "," + longi;
						}

					}
				}
			}

		}

		throw new IllegalStateException("Error");

	}

	// Obtener info de monumentos (dbpedia)
	public List<String> getLinksExternos(String url) {
		return manejadorJSON.getLinksExternos(url);
	}

	public String getComment(String url) {
		return manejadorJSON.getESComment(url);
	}

	public List<String> getImages(String url) {
		return manejadorJSON.getImages(url);
	}

	// Formar el enlace para DBPedia a partir de uno de Wikipedia
	public String getDBPediaURL(String wikiUrl) {

		String dbURl = "https://es.dbpedia.org/data/";
		String[] parts = wikiUrl.split("/");
		String monument = parts[parts.length - 1];

		return dbURl + monument + ".json";
	}

	// Llamar a DbPedia con los enlaces obtenidos
	public List<SitioInteres> getSitiosInteres() throws Exception {

		List<SitioInteres> lista = new LinkedList<SitioInteres>();

		List<String> list = getWikipediaURL();
		
		for (String url : list) {
			manejadorJSON = new ManejadorJSON(getDBPediaURL(url));

			SitioInteres st = new SitioInteres();

			st.setUrlWikipedia(url);
			
			st.setNombre(manejadorJSON.getName(url.replace("https", "http")));

			st.setComentario(getComment(url.replace("https", "http")));

			for (String l : getLinksExternos(url.replace("https", "http")))
				st.getLinkExterno().add(l);

			for (String l : getImages(url.replace("https", "http")))
				st.getImagen().add(l);

			String coordenadas = getCordenatesByLink(url);
			String[] parts = coordenadas.split(",");

			st.setLatitud(Double.parseDouble(parts[0]));
			st.setLongitud(Double.parseDouble(parts[1]));

			lista.add(st);

		}

		return lista;
	}

	// Obtener info de aparcamientos Malaga
	public List<Aparcamiento> getAparcamientosKML() throws ParserConfigurationException, SAXException, IOException {

		ManejadorDOMAparcamientos manejador = new ManejadorDOMAparcamientos();

		List<Aparcamiento> lista = new LinkedList<Aparcamiento>();
		manejador.setDirecciones(lista);
		manejador.setCoordenadas(lista);

		return lista;

	}

	// Obtener info de aparcamientos Sta Cruz de Tenerife
	public List<Aparcamiento> getAparcamientosSTA(String rutaKML)
			throws ParserConfigurationException, SAXException, IOException {
		List<Aparcamiento> toReturn = new LinkedList<>();

		// Analizar el documento
		try {
			ManejadorSTCruz manejador = new ManejadorSTCruz();

			analizadorSAX.parse(rutaKML, manejador);

			for (Aparcamiento aparcamiento : manejador.getAparcamientos()) {
				toReturn.add(aparcamiento);
			}

		} catch (IOException e) {
			System.out.println("El documento no ha podido ser leído");
		} catch (SAXException e) {
			System.out.println("Error de pocesamiento: " + e.getMessage());
		}
		return toReturn;
	}
}
