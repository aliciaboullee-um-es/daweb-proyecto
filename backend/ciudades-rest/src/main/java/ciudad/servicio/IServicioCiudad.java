package ciudad.servicio;

import javax.jws.WebService;

import es.um.ciudad.Ciudad;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService
public interface IServicioCiudad {

	String create(Ciudad ciudad) throws RepositorioException;

	Ciudad getById(String id) throws RepositorioException, EntidadNoEncontrada;

	void remove(String id) throws RepositorioException, EntidadNoEncontrada;

	ListadoCiudades getListadoResumen() throws RepositorioException, EntidadNoEncontrada;
	
	ListadoAparcamientos getListadoResumenAparcamientos(String id) throws RepositorioException, EntidadNoEncontrada;
	
	ListadoSitios getListadoResumenSitios(String id) throws RepositorioException, EntidadNoEncontrada;
}
