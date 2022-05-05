package opiniones.servicio;

import java.time.LocalDateTime;
import java.util.Collections;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;

public class Programa {

	public static void main(String[] args) throws Exception {

		ServicioOpiniones servicio = ServicioOpiniones.getInstancia();

		// Configuración de la opinion

		Opinion opinion = new Opinion();
		opinion.setUrl("http://test.com/");

		// Configuración de las valoraciones
		Valoracion valoracion1 = new Valoracion();
		valoracion1.setCalificacion(5);
		valoracion1.setComentario("Test");
		valoracion1.setEmail("alex@um.es");
		valoracion1.setFechaValoracion(LocalDateTime.now());

		Valoracion valoracion2 = new Valoracion();
		valoracion2.setCalificacion(2);
		valoracion2.setComentario("Test2");
		valoracion2.setEmail("pepito@um.es");
		valoracion2.setFechaValoracion(LocalDateTime.now());

		Collections.addAll(opinion.getValoraciones(), valoracion1, valoracion2);

		// Alta de la opinion
		String id = servicio.create(opinion);
		System.out.println("\nCiudad creada con id: " + id);

		// Añadir una valoración para una URL
		servicio.addValoracionToUrl(opinion.getUrl(), valoracion1);
		servicio.addValoracionToUrl(opinion.getUrl(), valoracion2);

		System.out.println("fin.");

	}
}
