package opiniones.servicio;

import javax.jws.WebService;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService
public interface IServicioOpiniones {

	// Registrar una URL para ser valorada (crea una opinión).
	String create(Opinion opinion) throws RepositorioException;

	// Añadir una valoración para una URL
	void addValoracionToUrl(String url, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada;

	// Recuperar la opinión de una URL
	Opinion getByUrl(String url) throws RepositorioException, EntidadNoEncontrada;

	// Recuperar una opinión usando su id
	Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada;

	// Eliminar una URL del servicio (elimina una opinión y sus valoraciones)
	void removeByUrl(String url) throws RepositorioException, EntidadNoEncontrada;

	// Eliminar una opinión usando su id
	void remove(String id) throws RepositorioException, EntidadNoEncontrada;

	ListadoOpiniones getListadoResumen() throws RepositorioException, EntidadNoEncontrada;
}
