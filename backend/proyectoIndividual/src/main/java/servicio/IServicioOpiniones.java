package servicio;

import javax.jws.WebService;

import modelo.Opinion;
import modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService
public interface IServicioOpiniones {

	String create(Opinion opinion) throws RepositorioException;
	
	String valorar(String urlOpinion, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada;
	
	Opinion getByUrl(String url) throws RepositorioException, EntidadNoEncontrada;
	
	void remove(String id) throws RepositorioException, EntidadNoEncontrada;
}
