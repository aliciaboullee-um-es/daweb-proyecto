package dom;

import java.util.LinkedList;
import java.util.List;

import es.um.ciudad.Aparcamiento;

public class Main {

	public static void main(String[] args) throws Exception {

		ManejadorDOMAparcamientos manejador = new ManejadorDOMAparcamientos();

		List<Aparcamiento> aparcamientos = new LinkedList<Aparcamiento>();
		manejador.setDirecciones(aparcamientos);
		manejador.setCoordenadas(aparcamientos);

		for (Aparcamiento a : aparcamientos) {

			System.out.println("Ciudad:" + a.getCiudad());
			System.out.println("Direccion:" + a.getDireccion());
			System.out.println("Coordenadas:" + a.getLatitud() + "," + a.getLongitud());
		}

		System.out.println("fin.");
	}

}
