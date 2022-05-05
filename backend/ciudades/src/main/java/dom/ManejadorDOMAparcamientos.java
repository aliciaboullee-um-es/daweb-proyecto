package dom;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.um.ciudad.Aparcamiento;

public class ManejadorDOMAparcamientos {

	private DocumentBuilder analizador;

	public ManejadorDOMAparcamientos() throws ParserConfigurationException {
		// 1. Obtener una factoría
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();

		// 2. Pedir a la factoría la construcción del analizador
		this.analizador = factoria.newDocumentBuilder();
	}

	public void setDirecciones(List<Aparcamiento> aparcamientosVacios) throws SAXException, IOException {
		// 3. Analizar el documento
		Document documento = analizador.parse("xml/da_aparcamientosMovilidadReducida-25830.kml");

		NodeList entries = documento.getElementsByTagName("Folder");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList listaTitles = current.getElementsByTagName("SimpleData");

				if (listaTitles.getLength() == 0)
					throw new IllegalStateException("El documento no tiene titulos");

				int contador=0;
				for (int k = 0; k < listaTitles.getLength(); k++) {
					Element titulo = (Element) listaTitles.item(k);
					if(titulo.getAttribute("name").equals("description")) {
						String contenido = titulo.getTextContent();
						int inicio = contenido.indexOf("Ubicación:");
						int fin = contenido.indexOf("N° Plazas:");
						String elem="";
						for(int ini=inicio+14;ini<=fin-11;ini++) {
							elem+=contenido.charAt(ini);
						}
						aparcamientosVacios.add(new Aparcamiento());
						aparcamientosVacios.get(contador).setDireccion(elem);
						contador++;
					}
				}
			}
		}
		return;
	}
	
	public List<String> setCoordenadas(List<Aparcamiento> aparcamientos) throws SAXException, IOException {
		// 3. Analizar el documento
		Document documento = analizador.parse("xml/da_aparcamientosMovilidadReducida-25830.kml");

		List<String> lista = new LinkedList<>();

		NodeList entries = documento.getElementsByTagName("Folder");

		if (entries.getLength() == 0) {
			throw new IllegalStateException("No existe ninguna entrada.");
		} else {
			for (int i = 0; i < entries.getLength(); i++) {
				Element current = (Element) entries.item(i);
				NodeList listaTitles = current.getElementsByTagName("coordinates");

				if (listaTitles.getLength() == 0)
					throw new IllegalStateException("El documento no tiene titulos");

				for (int k = 0; k < listaTitles.getLength(); k++) {
					Element titulo = (Element) listaTitles.item(k);
					String contenido = titulo.getTextContent();
					String[] parts = contenido.split(",");
					Double longitud = Double.valueOf(parts[0]);
					Double latitud = Double.valueOf(parts[1]);
					aparcamientos.get(k).setLatitud(latitud);
					aparcamientos.get(k).setLongitud(longitud);
				}
			}
		}
		return lista;
	}
}
