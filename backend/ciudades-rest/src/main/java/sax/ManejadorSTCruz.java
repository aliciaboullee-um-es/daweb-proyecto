package sax;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import es.um.ciudad.Aparcamiento;

public class ManejadorSTCruz extends DefaultHandler {

	private boolean dentroName = false;
	private boolean dentroCoordinates = false;
	private boolean dentroPlaceMark = false;

	private LinkedList<Aparcamiento> aparcamientos = new LinkedList<>();

	@Override
	public void startDocument() throws SAXException {

		// Resetear estado
		this.setDentroCoordenadas(false);
		this.setDentroPlaceMark(false);
		this.setDentroName(false);
		this.setAparcamientos(new LinkedList<>());

	}

	@Override
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {
		
		if (qName.equals("name")) {
			this.dentroName = true;
		} else if (qName.equals("coordinates")) {
			this.dentroCoordinates = true;
		} else if (qName.equals("Placemark")) {
			this.dentroPlaceMark = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals("name")) {
			this.dentroName = false;
		} else if (qName.equals("coordinates")) {
			this.dentroCoordinates = false;
		} else if (qName.equals("Placemark")) {
			this.dentroPlaceMark = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if (this.dentroName && this.dentroPlaceMark) {

			String contenido = new String(ch, start, length);
			
			// Si no hay ningún elemento en la lista: Se crea con la dirección y se inserta
			if (this.getAparcamientos().isEmpty()) {
				Aparcamiento aparcamiento = new Aparcamiento();
				
				aparcamiento.setDireccion(contenido);
				this.getAparcamientos().add(aparcamiento);
			}

			// Si ya tiene todos los campos se crea uno nuevo con la dirección y se inserta
			else if (this.getAparcamientos().getLast().getDireccion() != null
					&& (this.getAparcamientos().getLast().getLongitud() != 0.0)
					&& this.getAparcamientos().getLast().getLatitud() != 0.0) {

				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setDireccion(contenido);
				this.getAparcamientos().add(aparcamiento);

			}

			// Si solo le falta la dirección se le rellena
			else if (this.getAparcamientos().getLast().getDireccion() == null)
				this.getAparcamientos().getLast().setDireccion(contenido);
		}

		else if (this.dentroCoordinates) {

			String coordenadas = new String(ch, start, length);

			Double longitud = 0.0;
			Double latitud = 0.0;

			String[] parts = coordenadas.split(",");
			
			try {
				latitud = Double.parseDouble(parts[1]);
				longitud = Double.parseDouble(parts[0]);
			} catch (Exception e) {
				System.out.println("No se ha podido convertir la cadena: " + coordenadas);
			}

			// Si no hay ningún elemento en la lista: Se crea y se inserta
			if (this.getAparcamientos().isEmpty()) {
				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLongitud(longitud);
				aparcamiento.setLatitud(latitud);
				this.getAparcamientos().add(aparcamiento);
			}

			// Si ya tiene todos los campos se crea uno nuevo y se inserta
			if (this.getAparcamientos().getLast().getDireccion() != null
					&& (this.getAparcamientos().getLast().getLongitud() != 0.0)
					&& this.getAparcamientos().getLast().getLatitud() != 0.0) {

				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLongitud(longitud);
				aparcamiento.setLatitud(latitud);
				this.getAparcamientos().add(aparcamiento);

			}

			// Si solo le falta se le rellena
			if (this.getAparcamientos().getLast().getLongitud() == 0.0) {
				this.getAparcamientos().getLast().setLongitud(longitud);
				this.getAparcamientos().getLast().setLatitud(latitud);
			}

		}
	}

	/* Get & Set */

	public boolean isDentroPlaceMark() {
		return dentroPlaceMark;
	}

	public void setDentroPlaceMark(boolean dentroPlaceMark) {
		this.dentroPlaceMark = dentroPlaceMark;
	}
	
	public boolean isDentroName() {
		return dentroName;
	}

	public void setDentroName(boolean dentroName) {
		this.dentroName = dentroName;
	}

	public boolean isDentroCoordenadas() {
		return dentroCoordinates;
	}

	public void setDentroCoordenadas(boolean dentroCoordinates) {
		this.dentroCoordinates = dentroCoordinates;
	}

	public LinkedList<Aparcamiento> getAparcamientos() {
		return aparcamientos;
	}

	public void setAparcamientos(LinkedList<Aparcamiento> aparcamientos) {
		this.aparcamientos = aparcamientos;
	}
}
