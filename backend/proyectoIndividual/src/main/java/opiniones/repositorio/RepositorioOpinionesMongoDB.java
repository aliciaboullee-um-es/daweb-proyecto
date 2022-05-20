package opiniones.repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import modelo.Opinion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioOpinionesMongoDB implements RepositorioOpiniones {

	private MongoCollection<Opinion> opiniones;

	public RepositorioOpinionesMongoDB() {

		String uriString = "mongodb+srv://guillermo_nunezc:YmEuQj49@cluster0.b4khh.mongodb.net/arso?retryWrites=true&w=majority";	
		
		ConnectionString connectionString = new ConnectionString(uriString);

		CodecRegistry pojoCodecRegistry = CodecRegistries
				.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.codecRegistry(codecRegistry).build();

		MongoClient mongoClient = MongoClients.create(clientSettings);

		MongoDatabase db = mongoClient.getDatabase("arso");

		this.opiniones = db.getCollection("opiniones", Opinion.class);

	}

	/** Métodos de apoyo **/

	protected boolean checkDocument(String url) {

		Opinion opinion = opiniones.find(Filters.eq("url", url)).first();

		return opinion != null;
	}

	/** fin métodos de apoyo **/

	@Override
	public String add(Opinion opinion) throws RepositorioException {

		try {
			opiniones.insertOne(opinion);

			return opinion.getUrl();

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido insertar la entidad", e);
		}
	}

	@Override
	public void update(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocument(opinion.getUrl()))
			throw new EntidadNoEncontrada("No existe la entidad con url:" + opinion.getUrl());

		try {

			opiniones.replaceOne(Filters.eq("url", opinion.getUrl()), opinion);

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido actualizar la entidad, url:" + opinion.getUrl(), e);
		}

	}

	@Override
	public void delete(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocument(opinion.getUrl()))
			throw new EntidadNoEncontrada("No existe la entidad con url:" + opinion.getUrl());

		try {
			opiniones.deleteOne(Filters.eq("url", opinion.getUrl()));

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido borrar la entidad, url:" + opinion.getUrl(), e);
		}

	}

	@Override
	public List<Opinion> getAll() throws RepositorioException {

		LinkedList<Opinion> allOpiniones = new LinkedList<Opinion>();

		opiniones.find().into(allOpiniones);

		return allOpiniones;
	}

	@Override
	public Opinion getByUrl(String url) throws RepositorioException, EntidadNoEncontrada {
		Opinion opinion = opiniones.find(Filters.eq("url", url)).first();
		
		if (opinion == null)
			throw new EntidadNoEncontrada("No existe la entidad con url:" + url);

		return opinion;
	}
}