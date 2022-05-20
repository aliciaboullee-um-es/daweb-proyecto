package ciudades.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ciudad.servicio.IServicioCiudad;
import ciudad.servicio.ListadoAparcamientos;
import ciudad.servicio.ListadoAparcamientos.AparcamientoResumen;
import ciudad.servicio.ListadoCiudades;
import ciudad.servicio.ListadoCiudades.CiudadResumen;
import ciudad.servicio.ListadoSitios;
import ciudad.servicio.ListadoSitios.SitiosResumen;
import ciudad.servicio.ServicioCiudad;
import ciudades.rest.Listado.ResumenExtendido;
import ciudades.rest.ListadoAparcamientoResumen.ResumenExtendidoAparcamiento;
import ciudades.rest.ListadoSitiosResumen.ResumenExtendidoSitios;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import es.um.ciudad.SitioInteres;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.core.UriInfo;

import org.w3._2005.atom.Author;
import org.w3._2005.atom.Entry;
import org.w3._2005.atom.Feed;
import org.w3._2005.atom.Link;

import adapter.AparcamientoAdapter;
import adapter.SitioInteresAdapter;

import java.util.UUID;

@Api
@Path("ciudades")
public class CiudadesControladorRest {

	private IServicioCiudad servicio = ServicioCiudad.getInstancia();

	@Context
	private UriInfo uriInfo;

	// curl -i -X POST -H "Accept: application/xml" -d @testfiles/1.xml
	// curl -i -X POST -H "Accept: application/xml" -d @testfiles/2.xml

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value = "Crear una ciudad", notes = "Crear una ciudad utilizando un xml", response = Ciudad.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no creada") })
	public Response create(Ciudad ciudad) throws Exception {

		String id = servicio.create(ciudad);

		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).build();
	}

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb' \
	 * -H 'accept: application/xml'
	 */

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb' \
	 * -H 'accept: application/json'
	 */

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta una ciudad", notes = "Retorna una ciudad utilizando su id", response = Ciudad.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getById(@ApiParam(value = "id de la ciudad", required = true) @PathParam("id") String id)
			throws Exception {

		Ciudad ciudad = servicio.getById(id);

		return Response.status(Status.OK).entity(ciudad).build();
	}

	// curl -X 'DELETE'
	// http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "Borrar una ciudad", notes = "Borrar una ciudad utilizando su id", response = Ciudad.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response remove(@ApiParam(value = "id de la ciudad", required = true) @PathParam("id") String id)
			throws Exception {

		servicio.remove(id);

		return Response.noContent().build();
	}

	// Conocer los sitios de interés turístico de una ciudad

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres' \ -H 'accept: application/xml'
	 */

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres' \ -H 'accept: application/json'
	 */

	@GET
	@Path("/{id}/sitiosInteres")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Sitios de interes de una ciudad", notes = "Sitios de interes de una ciudad utilizando su id", response = SitioInteres.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getSitiosInteresByCiudad(@PathParam("id") String id) throws Exception {

		ListadoSitios listado = servicio.getListadoResumenSitios(id);

		LinkedList<ResumenExtendidoSitios> resumenesExtendidos = new LinkedList<>();

		for (SitiosResumen resumen : listado.getSitios()) {

			ResumenExtendidoSitios resumenExtendido = new ResumenExtendidoSitios();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		ListadoSitiosResumen resultado = new ListadoSitiosResumen();
		resultado.setSitios(resumenesExtendidos);

		return Response.ok(resultado).build();
	}

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres/atom' \ -H 'accept: application/xml'
	 */

	@GET
	@Path("/{id}/sitiosInteres/atom")
	@Produces({ MediaType.APPLICATION_XML })
	@ApiOperation(value = "Sitios de interes de una ciudad en formato ATOM", notes = "Sitios de interes de una ciudad en formato ATOM utilizando su id", response = SitioInteres.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getSitiosInteresByCiudadAtom(@PathParam("id") String id) throws Exception {

		ListadoSitios listado = servicio.getListadoResumenSitios(id);

		LinkedList<ResumenExtendidoSitios> resumenesExtendidos = new LinkedList<>();

		for (SitiosResumen resumen : listado.getSitios()) {

			ResumenExtendidoSitios resumenExtendido = new ResumenExtendidoSitios();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		ListadoSitiosResumen resultado = new ListadoSitiosResumen();
		resultado.setSitios(resumenesExtendidos);

		Feed f = new Feed();
		f.setId("urn:uuid:" + UUID.randomUUID().toString());
		f.setTitle("SitiosInteres");
		f.setUpdated("2022-04-07T18:30:02Z");

		Author author = new Author();
		author.setName("ArSo");

		f.setAuthor(author);

		Link l = new Link();

		l.setHref(uriInfo.getAbsolutePathBuilder().build().toString());
		l.setRel("self");

		f.setLink(l);

		for (ResumenExtendidoSitios r : resultado.getSitios()) {
			Entry entry = new Entry();
			entry.setId("urn:uuid:" + r.getResumen().getId());

			String title = r.getResumen().getNombre();

			if (title == null || title.isEmpty())
				title = " - ";
			entry.setTitle(title);

			Link link = new Link();

			link.setHref(r.getUrl());
			link.setRel("alternate");

			entry.setLink(link);
			entry.setUpdated("2022-04-02T16:10:02Z");

			f.getEntry().add(entry);
		}

		return Response.ok(f).build();
	}

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * aparcamientos/atom' \ -H 'accept: application/xml
	 */

	@GET
	@Path("/{id}/aparcamientos/atom")
	@Produces({ MediaType.APPLICATION_XML })
	@ApiOperation(value = "Aparcamientos de una ciudad en formato ATOM", notes = "Aparcamientosde una ciudad en formato ATOM utilizando su id", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getAparcamientosByCiudadAtom(@PathParam("id") String id) throws Exception {

		ListadoAparcamientos listado = servicio.getListadoResumenAparcamientos(id);

		LinkedList<ResumenExtendidoAparcamiento> resumenesExtendidos = new LinkedList<>();

		for (AparcamientoResumen resumen : listado.getAparcamientos()) {

			ResumenExtendidoAparcamiento resumenExtendido = new ResumenExtendidoAparcamiento();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		ListadoAparcamientoResumen resultado = new ListadoAparcamientoResumen();
		resultado.setAparcamientos(resumenesExtendidos);

		Feed f = new Feed();
		f.setId("urn:uuid:" + UUID.randomUUID().toString());
		f.setTitle("Aparcamientos");
		f.setUpdated("2022-04-07T18:30:02Z");

		Author author = new Author();
		author.setName("ArSo");

		f.setAuthor(author);

		Link l = new Link();

		l.setHref(uriInfo.getAbsolutePathBuilder().build().toString());
		l.setRel("self");

		f.setLink(l);

		for (ResumenExtendidoAparcamiento r : resultado.getAparcamientos()) {
			Entry entry = new Entry();
			entry.setId("urn:uuid:" + r.getResumen().getId());

			String title = r.getResumen().getDireccion();

			if (title == null || title.isEmpty())
				title = " - ";
			entry.setTitle(title);

			Link link = new Link();

			link.setHref(r.getUrl());
			link.setRel("alternate");

			entry.setLink(link);
			entry.setUpdated("2022-04-02T16:10:02Z");

			f.getEntry().add(entry);
		}

		return Response.ok(f).build();
	}

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * aparcamientos' \ -H 'accept: application/xml'
	 */

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * aparcamientos' \ -H 'accept: application/json'
	 */

	@GET
	@Path("/{id}/aparcamientos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Aparcamientos de una ciudad", notes = "Aparcamientos de una ciudad utilizando su id", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getAparcamientosByCiudad(@PathParam("id") String id) throws Exception {

		ListadoAparcamientos listado = servicio.getListadoResumenAparcamientos(id);

		LinkedList<ResumenExtendidoAparcamiento> resumenesExtendidos = new LinkedList<>();

		for (AparcamientoResumen resumen : listado.getAparcamientos()) {

			ResumenExtendidoAparcamiento resumenExtendido = new ResumenExtendidoAparcamiento();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		ListadoAparcamientoResumen resultado = new ListadoAparcamientoResumen();
		resultado.setAparcamientos(resumenesExtendidos);

		return Response.ok(resultado).build();
	}

	// Obtener las ciudades que pueden ser consultadas (las que gestiona el
	// servicio).

	/*
	 * curl -X 'GET' \ 'http://localhost:8080/api/ciudades' \ -H 'accept:
	 * application/xml'
	 */

	/*
	 * curl -X 'GET' \ 'http://localhost:8080/api/ciudades' \ -H 'accept:
	 * application/json'
	 */

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Ciudades disponibles", notes = "Resumen de las ciudades disponibles", response = Ciudad.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "No hay ninguna ciudad") })
	public Response getListadoResumen() throws Exception {

		ListadoCiudades listado = servicio.getListadoResumen();

		LinkedList<ResumenExtendido> resumenesExtendidos = new LinkedList<>();

		for (CiudadResumen resumen : listado.getCiudades()) {

			ResumenExtendido resumenExtendido = new ResumenExtendido();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		Listado resultado = new Listado();
		resultado.setCiudades(resumenesExtendidos);

		return Response.ok(resultado).build();
	}

	// Dado un sitio de interés, obtener plazas de aparcamiento cercanas

	/* --------------- LORCA ------------------------------------
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres/95c0dff7-a7de-4c47-bed1-77a2baf4b09b/aparcamientos' \ -H
	 * 'accept: application/json'
	 */

	/* --------------- LORCA ------------------------------------
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres/95c0dff7-a7de-4c47-bed1-77a2baf4b09b/aparcamientos' \ -H
	 * 'accept: application/xml'
	 */

	/* --------------- VITORIA ------------------------------------
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/7ac16116-14cc-4caa-b739-3597bbf00f28/
	 * sitiosInteres/38c3f938-9317-4b18-a780-11aca514b65a/aparcamientos' \ -H
	 * 'accept: application/xml'
	 */

	@GET
	@Path("/{id}/sitiosInteres/{idSitio}/aparcamientos")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Plazas de aparcamientos cercanas", notes = "Dado un sitio de interés, obtener plazas de aparcamiento cercanas", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	public Response getAparcamientosCercanos(@PathParam("id") String id, @PathParam("idSitio") String idSitio)
			throws Exception {

		Ciudad ciudad = servicio.getById(id);
		SitioInteres sitio = null;

		for (SitioInteres st : ciudad.getSitios()) {
			if (st.getId().equals(idSitio))
				sitio = st;
		}

		// Verificar que existe el sitio de interes
		if (sitio == null)
			throw new IllegalArgumentException("El identificador no coincide: " + idSitio);

		List<Aparcamiento> aparcamientos = new LinkedList<Aparcamiento>();

		Double lat = sitio.getLatitud();
		Double lng = sitio.getLongitud();

		for (Aparcamiento ap : ciudad.getAparcamientos()) {

			if (Math.abs(ap.getLatitud() - lat) + Math.abs(ap.getLongitud() - lng) <= 0.003) {
				aparcamientos.add(ap);
			}
		}

		LinkedList<ResumenExtendidoAparcamiento> resumenesExtendidos = new LinkedList<>();

		for (Aparcamiento ap : aparcamientos) {

			AparcamientoResumen resumen = new AparcamientoResumen();

			resumen.setId(ap.getId());
			resumen.setDireccion(ap.getDireccion());
			resumen.setLatitud(ap.getLatitud());
			resumen.setLongitud(ap.getLongitud());
			ResumenExtendidoAparcamiento resumenExtendido = new ResumenExtendidoAparcamiento();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			int inicio = url.indexOf("sitio");
			int fin = url.indexOf("aparcamiento");

			// http://localhost:8080/api/ciudades/50a3afec-9049-4f41-826b-1cf36a5f8c17/sitiosInteres/1/aparcamientos/89

			url = url.substring(0, inicio) + url.subSequence(fin, url.length());

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		ListadoAparcamientoResumen resultado = new ListadoAparcamientoResumen();
		resultado.setAparcamientos(resumenesExtendidos);

		return Response.ok(resultado).build();
	}

	// Obtener la información de una plaza de aparcamiento

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * aparcamientos/1fc332e4-28ef-4c4c-80b7-27ce94d9cc12' \ -H 'accept:
	 * application/xml'
	 */

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * aparcamientos/1fc332e4-28ef-4c4c-80b7-27ce94d9cc12' \ -H 'accept:
	 * application/json'
	 */

	@GET
	@Path("/{id}/aparcamientos/{idAparcamiento}")
	@ApiOperation(value = "Consulta una plaza de aparcamiento", notes = "Dada una ciudad, obtener una plazas de aparcamiento usando su id", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAparcamiento(@PathParam("id") String id, @PathParam("idAparcamiento") String idAparcamiento)
			throws Exception {

		Ciudad ciudad = servicio.getById(id);

		List<Aparcamiento> aparcamientos = ciudad.getAparcamientos();
		Aparcamiento resultado = null;

		for (Aparcamiento ap : aparcamientos) {
			if (ap.getId().equals(idAparcamiento))
				resultado = ap;
		}

		// Verificar que existe el aparcamiento
		if (resultado == null)
			throw new IllegalArgumentException("El identificador del aparcamiento no coincide: " + idAparcamiento);

		AparcamientoAdapter adapter = new AparcamientoAdapter(resultado.getId(), resultado.getCiudad(),
				resultado.getDireccion(), resultado.getLatitud(), resultado.getLongitud(), 
				resultado.getNumValoraciones(), resultado.getCalificacionMedia());

		return Response.status(Status.OK).entity(adapter).build();
	}

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres/7f27c555-72fc-40f1-98e2-671103d2ebbd' \ -H 'accept:
	 * application/xml'
	 */

	/*
	 * curl -X 'GET' \
	 * 'http://localhost:8080/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/
	 * sitiosInteres/7f27c555-72fc-40f1-98e2-671103d2ebbd' \ -H 'accept:
	 * application/json'
	 */

	@GET
	@Path("/{id}/sitiosInteres/{idSitio}")
	@ApiOperation(value = "Consulta un sitio de interes", notes = "Dada una ciudad, obtener un sitio de interes usando su id", response = Aparcamiento.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Ciudad no encontrada") })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSitio(@PathParam("id") String id, @PathParam("idSitio") String idSitio) throws Exception {

		Ciudad ciudad = servicio.getById(id);

		List<SitioInteres> sitios = ciudad.getSitios();
		SitioInteres resultado = null;

		for (SitioInteres ap : sitios) {
			if (ap.getId().equals(idSitio))
				resultado = ap;
		}

		// Verificar que existe el aparcamiento
		if (resultado == null)
			throw new IllegalArgumentException("El identificador del sitio no coincide: " + idSitio);

		SitioInteresAdapter adapter = new SitioInteresAdapter();
		adapter.setId(resultado.getId());
		adapter.setComentario(resultado.getComentario());
		adapter.setImagen(resultado.getImagen());
		adapter.setLatitud(resultado.getLatitud());
		adapter.setLongitud(resultado.getLongitud());
		adapter.setLinkExterno(resultado.getLinkExterno());
		adapter.setNombre(resultado.getNombre());

		return Response.status(Status.OK).entity(adapter).build();
	}

}
