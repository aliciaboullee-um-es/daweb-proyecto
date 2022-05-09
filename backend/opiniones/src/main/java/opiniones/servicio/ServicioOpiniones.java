package opiniones.servicio;

import javax.jws.WebService;

import opiniones.eventos.EventoValoracionCreada;
import opiniones.modelo.Opinion;
import opiniones.modelo.Valoracion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;
import opiniones.servicio.ListadoOpiniones.OpinionResumen;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.LinkedList;

import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;

@WebService(endpointInterface = "opiniones.servicio.IServicioOpiniones")
public class ServicioOpiniones implements IServicioOpiniones {

	private RepositorioOpiniones repositorio = FactoriaRepositorioOpiniones.getRepositorio();

	/** Patrón Singleton **/

	private static ServicioOpiniones instancia;

	private ServicioOpiniones() {

	}

	public static ServicioOpiniones getInstancia() {

		if (instancia == null)
			instancia = new ServicioOpiniones();

		return instancia;
	}

	@Override
	public String create(Opinion opinion) throws RepositorioException {

		// Control de integridad de los datos

		// 1. Campos obligatorios
		if (opinion.getUrl() == null || opinion.getUrl().isEmpty())
			throw new IllegalArgumentException("url: no debe ser nulo ni vacio");

		String id = repositorio.add(opinion);

		return id;
	}

	@Override
	public Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada {

		return repositorio.getById(id);
	}

	@Override
	public void remove(String id) throws RepositorioException, EntidadNoEncontrada {

		Opinion opinion = repositorio.getById(id);

		repositorio.delete(opinion);

	}

	protected void notificarEvento(EventoValoracionCreada evento) {

		try {
			ConnectionFactory factory = new ConnectionFactory();

			factory.setUri("amqps://lfzxyxdj:l7Hm9eiFcaXC870LsQs5pNZ70Od5jWo4@squid.rmq.cloudamqp.com/lfzxyxdj");

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();

			/** Declaración del Exchange **/

			final String exchangeName = "arso-exchange";
			boolean durable = true;
			channel.exchangeDeclare(exchangeName, "direct", durable);

			/** Envío del mensaje **/

			Jsonb contexto = JsonbProvider.provider().create().build();

			String cadenaJSON = contexto.toJson(evento);

			String mensaje = cadenaJSON;

			String routingKey = "arso-guias";

			if (evento.getUrl().contains("aparcamientos")) {
				routingKey = "arso-aparcamientos";
			}

			channel.basicPublish(exchangeName, routingKey,
					new AMQP.BasicProperties.Builder().contentType("application/json").build(), mensaje.getBytes());

			channel.close();
			connection.close();
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public void addValoracionToUrl(String url, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada {

		Opinion opinion = null;

		try {

			opinion = getByUrl(url);

		} catch (Exception e) {
			Opinion op = new Opinion();
			op.setUrl(url);
			create(op);
			opinion = op;
		}

		if (opinion == null) {
			Opinion op = new Opinion();
			op.setUrl(url);
			op.setValoraciones(new LinkedList<>());
			create(op);
			opinion = op;
		}

		boolean existe = false;

		// Comprobar si un usuario registra una segunda valoración para una misma URL
		for (Valoracion val : opinion.getValoraciones()) {
			if (val.getEmail().equals(valoracion.getEmail())) {
				// Si es la segunda valoración se sustituye por la primera
				int index = opinion.getValoraciones().indexOf(val);
				opinion.getValoraciones().set(index, valoracion);
				existe = true;
			}

		}
		// Si es la primera valoración del usuario
		if (!existe)
			opinion.getValoraciones().add(valoracion);

		repositorio.update(opinion);

		// Notificar evento valoración creada

		// 1. Crear el evento

		EventoValoracionCreada evento = new EventoValoracionCreada();

		evento.setCalificacionMedia(opinion.getCalificacionMedia());
		evento.setNumValoraciones(opinion.getNumValoraciones());
		evento.setUrl(opinion.getUrl());
		evento.setValoracion(valoracion);

		// 2. Notificarlo
		notificarEvento(evento);

	}

	@Override
	public Opinion getByUrl(String url) throws RepositorioException, EntidadNoEncontrada {

		// Buscar la opinion con dicha url
		for (Opinion opinion : repositorio.getAll()) {
			if (opinion.getUrl().equals(url))
				return opinion;
		}
		// Si no existe ninguna opinion con esa url devuelve null
		return null;
	}

	@Override
	public void removeByUrl(String url) throws RepositorioException, EntidadNoEncontrada {

		Opinion opinion = getByUrl(url);

		repositorio.delete(opinion);
	}

	@Override
	public ListadoOpiniones getListadoResumen() throws RepositorioException, EntidadNoEncontrada {
		ListadoOpiniones listado = new ListadoOpiniones();

		for (Opinion opinion : repositorio.getAll()) {
			OpinionResumen resumen = new OpinionResumen();
			resumen.setId(opinion.getId().toString());
			resumen.setUrl(opinion.getUrl());
			resumen.setCalificacionMedia(opinion.getCalificacionMedia());
			resumen.setNumValoraciones(opinion.getNumValoraciones());

			listado.getOpiniones().add(resumen);

		}

		return listado;
	}

}
