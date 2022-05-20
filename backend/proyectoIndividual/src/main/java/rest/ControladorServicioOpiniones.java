package rest;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import modelo.Opinion;
import modelo.Valoracion;
import servicio.IServicioOpiniones;
import servicio.ServicioOpiniones;

import javax.ws.rs.core.UriInfo;




@Api
@Path("opiniones")
public class ControladorServicioOpiniones {

	private IServicioOpiniones servicio = ServicioOpiniones.getInstancia();

	@Context
	private UriInfo uriInfo;
	
	@Context
	private SecurityContext securityContext;


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Crear una opinión", notes = "Crear una opinión en la BBDD Mongo", response = Opinion.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Opinion no creada") })
	public Response create(Opinion opinion) throws Exception {

		String id = servicio.create(opinion);

		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).build();
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Consulta una opinión", notes = "Retorna una opinión utilizando su url", response = Opinion.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Opinión no encontrada") })
	public Response getByUrl(@QueryParam("url") String url)
			throws Exception {

		System.out.println(url);
		Opinion opinion = servicio.getByUrl(url);

		return Response.status(Status.OK).entity(opinion).build();
	}


	@DELETE
	@ApiOperation(value = "Borrar una opinión", notes = "Borrar una opinión utilizando su url", response = Opinion.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Opinión no encontrada") })
	public Response remove(@QueryParam("url") String url)
			throws Exception {

		servicio.remove(url);

		return Response.noContent().build();
	}


	@POST
	@Path("/valoraciones")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añadir una valoración", notes = "Añadir una valoración a una opinión utilizando su url", response = Opinion.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Valoración no añadida") })
	public Response valorar(@QueryParam("url") String url, Valoracion valoracion) throws Exception {
		
		String id = servicio.valorar(url, valoracion);

		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).build();
		
	}

}
