package servicio;

import java.util.LinkedList;

import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;
import javax.jws.WebService;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import modelo.Opinion;
import modelo.Valoracion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;
import rabbitmq.EventoValoracionCreada;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@WebService(endpointInterface = "servicio.IServicioOpiniones")
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
		
		try{
			Opinion o = getByUrl(opinion.getUrl());
			if(o!=null) {
				throw new IllegalArgumentException("Ya existe una opinión con esta url");
			}
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (EntidadNoEncontrada e) {
			return repositorio.add(opinion);
		}
	}
	
	@Override
	public String valorar(String url, Valoracion valoracion) throws RepositorioException, EntidadNoEncontrada {
		
		if (valoracion.getCorreo() == null || valoracion.getCorreo().isEmpty())
			throw new IllegalArgumentException("usuario: no debe ser nulo ni vacio");
		if (valoracion.getCalificacion()<1 || valoracion.getCalificacion()>5)
			throw new IllegalArgumentException("calificacion: debe estar entre 1 a 5");
		if (valoracion.getFecha() == null)
			throw new IllegalArgumentException("fecha: no debe ser nulo");
		
		Opinion opinion = repositorio.getByUrl(url);
		
		LinkedList<Valoracion> vals = opinion.getValoraciones();
		for(Valoracion v: vals) {
			if(v.getCorreo().contentEquals(valoracion.getCorreo())) {
				vals.remove(v);
			}
		}
		opinion.setValoraciones(vals);
		opinion.add(valoracion);
		repositorio.update(opinion);
			
			// 1. Crear el evento
					
			EventoValoracionCreada evento = new EventoValoracionCreada();
			evento.setValoracionEmitida(valoracion);
			evento.setNumValoraciones(opinion.getNumeroValoraciones());
			evento.setCalificacionMedia(opinion.getCalificacionMedia());
			evento.setUrl(url);
					
			// 2. Notificarlo
			notificarEvento(evento);
		//}
		return valoracion.getIdentificador();
	}

	@Override
	public Opinion getByUrl(String url) throws RepositorioException, EntidadNoEncontrada {
		
		return repositorio.getByUrl(url);
	}

	@Override
	public void remove(String url) throws RepositorioException, EntidadNoEncontrada {
		
		Opinion opinion = repositorio.getByUrl(url);
		
		repositorio.delete(opinion);
		
	}
	
protected void notificarEvento(EventoValoracionCreada evento) {
		
		try {
			String routingKey;
			if(evento.getUrl().contains("/aparcamientos/")) routingKey = "arso";
			else if(evento.getUrl().contains("wikipedia")) routingKey = "valoracion";
			else return;
			
		ConnectionFactory factory = new ConnectionFactory();
		
		factory.setUri("amqps://rhidihij:aW-xJyielmiyMRVVNOO2jHAW0YC1YRm4@whale.rmq.cloudamqp.com/rhidihij");

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		/** Declaración del Exchange **/

		final String exchangeName = "amq.direct";
		
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "direct", durable);

		/** Envío del mensaje **/
		
		Jsonb contexto = JsonbProvider.provider().create().build();

		String cadenaJSON = contexto.toJson(evento);

		String mensaje = cadenaJSON;
		
		
		channel.basicPublish(exchangeName, routingKey, new AMQP.BasicProperties.Builder()
				.contentType("application/json")
				.build(), mensaje.getBytes());

		channel.close();
		connection.close();
		} catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}
