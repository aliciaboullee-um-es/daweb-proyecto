package servicio;

import java.time.LocalDateTime;
import java.util.LinkedList;

import modelo.Opinion;
import modelo.Valoracion;

public class Programa {

	public static void main(String[] args) throws Exception {

		
		ServicioOpiniones servicio = ServicioOpiniones.getInstancia();
		
		//Registrar una URL para ser valorada (crea una opini�n).
		Opinion o = new Opinion();
		o.setUrl("algo/algo2");
		o.setValoraciones(new LinkedList<Valoracion>());
		servicio.create(o);
		System.out.println("Comprobar la inserci�n en la bbdd de la opinion");
		
		//A�adir una valoraci�n para una URL. Si un usuario registra una segunda valoraci�n para una misma URL, �sta reemplazar� a la primera.
		Valoracion v1 = new Valoracion();
		v1.setCorreo("algo");
		v1.setCalificacion(3);
		v1.setFecha(LocalDateTime.now());
		servicio.valorar(o.getUrl(), v1);
		System.out.println("Comprobar la inserci�n en la bbdd de la valoracion1");
		Valoracion v2 = new Valoracion();
		v2.setCorreo("algo2");
		v2.setCalificacion(1);
		v2.setFecha(LocalDateTime.now());
		v2.setComentario("comment");
		servicio.valorar(o.getUrl(), v2);
		System.out.println("Comprobar la inserci�n en la bbdd de la valoracion2");
		
		//Recuperar la opini�n de una URL.
		Opinion o2 = servicio.getByUrl("algo/algo2");
		System.out.println("o2 = " + o2);
		/*
		//Eliminar una URL del servicio (elimina una opini�n y sus valoraciones).
		servicio.remove(o.getIdentificador());
		System.out.println("Comprobar la eliminaci�n en la bbdd de la opinion");
		*/
		System.out.println("fin.");

	}
}
