package sax;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import es.um.ciudad.Aparcamiento;

public class ManejadorSAX extends DefaultHandler {

	private boolean dentroDireccion = false;
	private boolean dentroLongitud = false;
	private boolean dentroLatitud = false;

	private LinkedList<Aparcamiento> aparcamientos = new LinkedList<>();

	@Override
	public void startDocument() throws SAXException {

		// Resetear estado
		this.setDentroDireccion(false);
		this.setDentroLatitud(false);
		this.setDentroLongitud(false);
		this.setAparcamientos(new LinkedList<>());

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equals("direccion")) {
			this.dentroDireccion = true;
		} else if (qName.equals("longitud")) {
			this.dentroLongitud = true;
		} else if (qName.equals("latitud")) {
			this.dentroLatitud = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals("direccion")) {
			this.dentroDireccion = false;
		} else if (qName.equals("longitud")) {
			this.dentroLongitud = false;
		} else if (qName.equals("latitud")) {
			this.dentroLatitud = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		if (this.dentroDireccion) {

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

		else if (this.dentroLongitud) {

			String longitudStr = new String(ch, start, length);

			Double longitud = 0.0;

			try {
				longitud = Double.parseDouble(longitudStr);
			} catch (Exception e) {
				System.out.println("No se ha podido convertir la cadena: " + longitudStr);
			}

			// Si no hay ningún elemento en la lista: Se crea con la longitud y se inserta
			if (this.getAparcamientos().isEmpty()) {
				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLongitud(longitud);
				this.getAparcamientos().add(aparcamiento);
			}

			// Si ya tiene todos los campos se crea uno nuevo con la longitud y se inserta
			if (this.getAparcamientos().getLast().getDireccion() != null
					&& (this.getAparcamientos().getLast().getLongitud() != 0.0)
					&& this.getAparcamientos().getLast().getLatitud() != 0.0) {

				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLongitud(longitud);
				this.getAparcamientos().add(aparcamiento);

			}

			// Si solo le falta la longitud se le rellena
			if (this.getAparcamientos().getLast().getLongitud() == 0.0)
				this.getAparcamientos().getLast().setLongitud(longitud);

		}

		else if (this.dentroLatitud) {

			String latitudStr = new String(ch, start, length);

			Double latitud = 0.0;

			try {
				latitud = Double.parseDouble(latitudStr);
			} catch (Exception e) {
				System.out.println("No se ha podido convertir la cadena: " + latitudStr);
			}

			// Si no hay ningún elemento en la lista: Se crea con la latitud y se inserta
			if (this.getAparcamientos().isEmpty()) {
				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLatitud(latitud);
				this.getAparcamientos().add(aparcamiento);
			}

			// Si ya tiene todos los campos se crea uno nuevo con la latitud y se inserta
			if (this.getAparcamientos().getLast().getDireccion() != null
					&& (this.getAparcamientos().getLast().getLongitud() != 0.0)
					&& this.getAparcamientos().getLast().getLatitud() != 0.0) {

				Aparcamiento aparcamiento = new Aparcamiento();
				aparcamiento.setLatitud(latitud);
				this.getAparcamientos().add(aparcamiento);

			}

			// Si solo le falta la latitud se le rellena
			if (this.getAparcamientos().getLast().getLatitud() == 0.0)
				this.getAparcamientos().getLast().setLatitud(latitud);

		}
	}

	/* Get & Set */

	public boolean isDentroDireccion() {
		return dentroDireccion;
	}

	public void setDentroDireccion(boolean dentroDireccion) {
		this.dentroDireccion = dentroDireccion;
	}

	public boolean isDentroLongitud() {
		return dentroLongitud;
	}

	public void setDentroLongitud(boolean dentroLongitud) {
		this.dentroLongitud = dentroLongitud;
	}

	public boolean isDentroLatitud() {
		return dentroLatitud;
	}

	public void setDentroLatitud(boolean dentroLatitud) {
		this.dentroLatitud = dentroLatitud;
	}

	public LinkedList<Aparcamiento> getAparcamientos() {
		return aparcamientos;
	}

	public void setAparcamientos(LinkedList<Aparcamiento> aparcamientos) {
		this.aparcamientos = aparcamientos;
	}

}
