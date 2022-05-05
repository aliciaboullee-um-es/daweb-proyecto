package ciudad.repositorio.test;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import ciudad.repositorio.FactoriaRepositorioCiudades;
import ciudad.repositorio.RepositorioCiudades;
import controlador.Controlador;
import es.um.ciudad.Ciudad;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.SitioInteres;

public class Programa {

	public static void main(String[] args) throws Exception {

		RepositorioCiudades repositorio = FactoriaRepositorioCiudades.getRepositorio();

		// 1. Creación de una ciudad - Lorca

		Controlador controlador = new Controlador("30800");

		Ciudad ciudad = new Ciudad();
		ciudad.setNombre("Lorca");
		ciudad.setLongitud(-1.7017);
		ciudad.setLatitud(37.67119);

		// Aparcamientos

		List<Aparcamiento> aparcamientos = new LinkedList<Aparcamiento>();
		aparcamientos = controlador.getAparcamientosXML("xml/parking-movilidad-reducida.xml");

		for (Aparcamiento a : aparcamientos) {
			a.setId(UUID.randomUUID().toString());
			ciudad.getAparcamientos().add(a);

		}

		// Sitios de interes
		List<SitioInteres> sitios = new LinkedList<SitioInteres>();

		sitios = controlador.getSitiosInteres();
		for (SitioInteres sti : sitios) {
			sti.setId(UUID.randomUUID().toString());
			ciudad.getSitios().add(sti);
		}

		String id = repositorio.add(ciudad);

		System.out.println("Ciudad creada con id: " + id);

		// 2. Creación de una ciudad - Malaga

		Controlador controladorMalaga = new Controlador(36.7196, -4.42002);

		Ciudad malaga = new Ciudad();
		malaga.setNombre("Málaga");
		malaga.setLongitud(-4.42002);
		malaga.setLatitud(36.7196);

		// Aparcamientos

		List<Aparcamiento> aparcamientos2 = new LinkedList<Aparcamiento>();
		aparcamientos2 = controladorMalaga.getAparcamientosKML();

		for (Aparcamiento a : aparcamientos2) {
			a.setId(UUID.randomUUID().toString());
			malaga.getAparcamientos().add(a);

		}

		// Sitios de interes
		List<SitioInteres> sitiosMalaga = new LinkedList<SitioInteres>();

		sitiosMalaga = controladorMalaga.getSitiosInteres();
		for (SitioInteres sti : sitiosMalaga) {
			sti.setId(UUID.randomUUID().toString());
			malaga.getSitios().add(sti);

		}

		id = repositorio.add(malaga);

		System.out.println("Ciudad creada con id: " + id);

		// 3. Creación de una ciudad - Sta Cruz Tenerife
		Controlador controladorSTA = new Controlador(28.4698, -16.2549);

		Ciudad sta = new Ciudad();
		sta.setNombre("Sta Cruz Tenerife");
		sta.setLongitud(-16.2549);
		sta.setLatitud(28.4698);

		// Aparcamientos

		List<Aparcamiento> aparcamientos3 = new LinkedList<Aparcamiento>();
		aparcamientos3 = controladorSTA.getAparcamientosSTA("xml/apar_mov_red.kml");

		for (Aparcamiento a : aparcamientos3) {
			a.setId(UUID.randomUUID().toString());
			sta.getAparcamientos().add(a);
		}

		// Sitios de interes
		List<SitioInteres> sitios3 = new LinkedList<SitioInteres>();

		sitios3 = controladorSTA.getSitiosInteres();
		for (SitioInteres sti : sitios3) {
			sti.setId(UUID.randomUUID().toString());
			sta.getSitios().add(sti);

		}

		id = repositorio.add(sta);

		System.out.println("Ciudad creada con id: " + id);

		// 4. Creación de una ciudad - Vitoria-Gasteiz
		Controlador controladorVG = new Controlador(42.8467, -2.67164);

		Ciudad vg = new Ciudad();
		vg.setNombre("Vitoria-Gasteiz");
		vg.setLongitud(-2.67164);
		vg.setLatitud(42.8467);

		// Aparcamientos

		List<Aparcamiento> aparcamientos4 = new LinkedList<Aparcamiento>();
		aparcamientos4 = controladorVG.getAparcamientosSTA("xml/PMR_gasteiz.kml");

		for (Aparcamiento a : aparcamientos4) {
			a.setId(UUID.randomUUID().toString());
			vg.getAparcamientos().add(a);
		}

		// Sitios de interes
		List<SitioInteres> sitios4 = new LinkedList<SitioInteres>();

		sitios4 = controladorVG.getSitiosInteres();
		for (SitioInteres sti : sitios4) {
			sti.setId(UUID.randomUUID().toString());
			vg.getSitios().add(sti);

		}

		id = repositorio.add(vg);

		System.out.println("Ciudad creada con id: " + id);

		System.out.println("fin con tamaño " + repositorio.getAll().size());

	}
}
