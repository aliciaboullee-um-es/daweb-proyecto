package opiniones.rest;

import java.net.URI;
import java.util.LinkedList;

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

import javax.ws.rs.core.UriInfo;

import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import opiniones.rest.Listado.ResumenExtendido;
import opiniones.servicio.IServicioOpiniones;
import opiniones.servicio.ListadoOpiniones;
import opiniones.servicio.ListadoOpiniones.OpinionResumen;
import opiniones.servicio.ServicioOpiniones;

@Path("opiniones")
public class OpinionesControladorRest {

	private IServicioOpiniones servicio = ServicioOpiniones.getInstancia();

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Opinion opinion) throws Exception {

		String id = servicio.create(opinion);

		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).build();
	}

	// http://localhost:8080/api/opiniones/62607554c42db467019c9f4c

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) throws Exception {

		Opinion opinion = servicio.getById(id);

		return Response.status(Status.OK).entity(opinion).build();
	}

	@DELETE
	@Path("{id}")
	public Response remove(String id) throws Exception {

		servicio.remove(id);

		return Response.noContent().build();
	}

	@GET
	@Path("/{url:.*\\..*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUrl(@PathParam("url") String url) throws Exception {

		Opinion opinion = servicio.getByUrl(url);

		return Response.status(Status.OK).entity(opinion).build();

	}

	@DELETE
	@Path("/{url:.*\\..*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteByUrl(@PathParam("url") String url) throws Exception {

		servicio.removeByUrl(url);

		return Response.noContent().build();

	}

	// curl -i -X POST -H "Content-Type: application/json" -d
	// @testfiles/valoracion.json
	// http://localhost:8080/api/opiniones/ciudades.com/api/ciudades/2befa17a-27e9-4a6c-81d4-64ba990049fb/aparcamientos/1fc332e4-28ef-4c4c-80b7-27ce94d9cc12

	@POST
	@Path("/{url:.*\\..*}")
	//@Consumes(MediaType.APPLICATION_JSON)
	public Response addValoracionToUrl(@PathParam("url") String url, Valoracion valoracion) throws Exception {

		servicio.addValoracionToUrl(url, valoracion);
		

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListadoResumen() throws Exception {

		ListadoOpiniones listado = servicio.getListadoResumen();

		LinkedList<ResumenExtendido> resumenesExtendidos = new LinkedList<>();

		for (OpinionResumen resumen : listado.getOpiniones()) {

			ResumenExtendido resumenExtendido = new ResumenExtendido();
			resumenExtendido.setResumen(resumen);

			String url = uriInfo.getAbsolutePathBuilder().path(resumen.getId()).build().toString();

			resumenExtendido.setUrl(url);

			resumenesExtendidos.add(resumenExtendido);
		}

		Listado resultado = new Listado();
		resultado.setOpiniones(resumenesExtendidos);

		return Response.ok(resultado).build();
	}

}
