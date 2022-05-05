package dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.util.LinkedList;
import java.util.List;

public class ManejadorDOM {

	private DocumentBuilder analizador;

	public ManejadorDOM() throws ParserConfigurationException {
		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		this.analizador = factoria.newDocumentBuilder();
	}

	public List<String> enumerarCiudades() throws SAXException, IOException {
		// 3. Analizar el documento
		Document documento = analizador.parse("xml/geonames.xml");

		List<String> lista = new LinkedList<>();

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
				lista.add(titulo.getTextContent());
			}
		}
		return lista;
	}

	public String getLatitud(String ciudad) throws SAXException, IOException {
		Document documento = analizador.parse("xml/geonames.xml");

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
				if (titulo.getTextContent().equals(ciudad)) {
					NodeList latitudes = current.getElementsByTagName("lat");
					return latitudes.item(0).getTextContent();
				}
			}
		}
		throw new IllegalStateException("No existe una ciudad con ese nombre");
	}

	public String getLongitud(String ciudad) throws SAXException, IOException {
		Document documento = analizador.parse("xml/geonames.xml");

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
				if (titulo.getTextContent().equals(ciudad)) {
					NodeList longitudes = current.getElementsByTagName("lng");
					return longitudes.item(0).getTextContent();
				}
			}
		}
		throw new IllegalStateException("No existe una ciudad con ese nombre");
	}
}
