package ciudad.servicio;

import java.io.IOException;
import java.util.Map;

import javax.jws.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
		
		try {
			ConnectionFactory factory = new ConnectionFactory();
			
			factory.setUri("amqps://rhidihij:aW-xJyielmiyMRVVNOO2jHAW0YC1YRm4@whale.rmq.cloudamqp.com/rhidihij");

			Connection connection = factory.newConnection();

			Channel channel = connection.createChannel();
			
			final String exchangeName = "amq.direct";
			final String queueName = "arso-test";
			final String bindingKey = "arso";
			boolean durable = true;
			boolean exclusive = false;
			boolean autodelete = false;
			Map<String, Object> properties = null; // sin propiedades
			channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);
			channel.queueBind(queueName, exchangeName, bindingKey);
		
			boolean autoAck = false;
			channel.basicConsume("arso-test", autoAck, "arso-consumidor",
			new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
				AMQP.BasicProperties properties,byte[] body) throws IOException {
					long deliveryTag = envelope.getDeliveryTag();
					String contenido = new String(body);
					
					// Confirma el procesamiento
					channel.basicAck(deliveryTag, false);
					//Tratar el contenido mandando a hacer update a bbdd
					try {
						//System.out.println("String:"+contenido);
			            JSONObject jsonObject = new JSONObject(contenido);
			            //System.out.println("JSON"+jsonObject.toString());
			            
			            String url = jsonObject.getString("url");
			            int numeroValoraciones = jsonObject.getInt("numValoraciones");
			            double calificacionMedia = jsonObject.getDouble("calificacionMedia");
			         
			            String idAparcamiento = url.substring(url.length()-24);
			            String idCiudad = url.substring(28,28+36);
			            //Actualizamos el parking en el repositorio
			            
			            Ciudad c = repositorio.getById(idCiudad);
			            for(Aparcamiento a: c.getAparcamientos()) {
							if(a.getId().contentEquals(idAparcamiento)) {
								a.setUrl(url);
								a.setNumValoraciones(numeroValoraciones);
								a.setCalificacionMedia(calificacionMedia);
							}
			            }
			            repositorio.update(c);
			            
					} catch (EntidadNoEncontrada e) {
						e.printStackTrace();
					} catch (RepositorioException e) {
			        	e.printStackTrace();
					} catch (JSONException err) {
			            System.out.println("Exception : "+err.toString());
			        } 
				}
			});
		} catch(Exception e) {
			
			throw new RuntimeException(e);
		}
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
