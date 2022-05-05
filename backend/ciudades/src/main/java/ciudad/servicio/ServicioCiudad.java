package ciudad.servicio;

import javax.jws.WebService;

import ciudad.repositorio.*;
import ciudad.servicio.ListadoAparcamientos.AparcamientoResumen;
import ciudad.servicio.ListadoCiudades.CiudadResumen;
import ciudad.servicio.ListadoSitios.SitiosResumen;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.SitioInteres;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService(endpointInterface = "ciudad.servicio.IServicioCiudad")
public class ServicioCiudad implements IServicioCiudad {

	private RepositorioCiudades repositorio = FactoriaRepositorioCiudades.getRepositorio();

	/** Patrón Singleton **/

	private static ServicioCiudad instancia;

	private ServicioCiudad() {

	}

	public static ServicioCiudad getInstancia() {

		if (instancia == null)
			instancia = new ServicioCiudad();

		return instancia;
	}

	@Override
	public String create(Ciudad ciudad) throws RepositorioException {

		// Control de integridad de los datos

		// 1. Campos obligatorios
		if (ciudad.getNombre() == null || ciudad.getNombre().isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");

		if (ciudad.getAparcamientos() == null || ciudad.getAparcamientos().isEmpty())
			throw new IllegalArgumentException("Aparcamientos: no debe ser nula ni vacia");

		if (ciudad.getSitios() == null || ciudad.getSitios().isEmpty())
			throw new IllegalArgumentException("sitios: no debe ser nula ni vacia");

		String id = repositorio.add(ciudad);

		return id;
	}

	@Override
	public Ciudad getById(String id) throws RepositorioException, EntidadNoEncontrada {

		return repositorio.getById(id);
	}

	@Override
	public void remove(String id) throws RepositorioException, EntidadNoEncontrada {

		Ciudad ciudad = repositorio.getById(id);

		repositorio.delete(ciudad);

	}

	@Override
	public ListadoCiudades getListadoResumen() throws RepositorioException, EntidadNoEncontrada {

		ListadoCiudades listado = new ListadoCiudades();

		for (Ciudad ciudad : repositorio.getAll()) {
			CiudadResumen resumen = new CiudadResumen();
			resumen.setId(ciudad.getId().toString());
			resumen.setNombre(ciudad.getNombre());

			listado.getCiudades().add(resumen);

		}

		return listado;
	}

	@Override
	public ListadoAparcamientos getListadoResumenAparcamientos(String id)
			throws RepositorioException, EntidadNoEncontrada {
		ListadoAparcamientos listado = new ListadoAparcamientos();

		Ciudad ciudad = repositorio.getById(id);

		for (Aparcamiento ap : ciudad.getAparcamientos()) {
			AparcamientoResumen resumen = new AparcamientoResumen();
			resumen.setId(ap.getId());
			resumen.setDireccion(ap.getDireccion());
			resumen.setLatitud(ap.getLatitud());
			resumen.setLongitud(ap.getLongitud());


			listado.getAparcamientos().add(resumen);
		}

		return listado;
	}

	@Override
	public ListadoSitios getListadoResumenSitios(String id) throws RepositorioException, EntidadNoEncontrada {
		Ciudad ciudad = repositorio.getById(id);

		ListadoSitios listado = new ListadoSitios();

		for (SitioInteres st : ciudad.getSitios()) {
			SitiosResumen resumen = new SitiosResumen();
			resumen.setId(st.getId());
			resumen.setNombre(st.getNombre());
			resumen.setComentario(st.getComentario());
			if(st.getImagen() != null && !st.getImagen().isEmpty()) resumen.setImagen(st.getImagen().get(0));
			resumen.setLatitud(st.getLatitud());
			resumen.setLongitud(st.getLongitud());
			if(st.getLinkExterno() != null && !st.getLinkExterno().isEmpty()) resumen.setLinkExternos(st.getLinkExterno());
			

			listado.getSitios().add(resumen);
		}

		return listado;
	}
}
