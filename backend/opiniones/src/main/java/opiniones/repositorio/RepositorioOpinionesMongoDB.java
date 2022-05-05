package opiniones.repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import opiniones.modelo.Opinion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class RepositorioOpinionesMongoDB implements RepositorioOpiniones {

	private MongoCollection<Opinion> opinions;

	public RepositorioOpinionesMongoDB() {

		// TODO: cadena de conexión

		String uriString = "mongodb://arso:arso-22@cluster0-shard-00-00.nmwgt.mongodb.net:27017,cluster0-shard-00-01.nmwgt.mongodb.net:27017,cluster0-shard-00-02.nmwgt.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-l2e4lb-shard-0&authSource=admin&retryWrites=true&w=majority";

		ConnectionString connectionString = new ConnectionString(uriString);

		CodecRegistry pojoCodecRegistry = CodecRegistries
				.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.codecRegistry(codecRegistry).build();

		MongoClient mongoClient = MongoClients.create(clientSettings);

		MongoDatabase db = mongoClient.getDatabase("arso");

		this.opinions = db.getCollection("opiniones", Opinion.class);

	}

	/** Métodos de apoyo **/

	protected boolean checkDocument(ObjectId id) {

		Opinion opinion = opinions.find(Filters.eq("_id", id)).first();

		return opinion != null;
	}

	/** fin métodos de apoyo **/

	@Override
	public String add(Opinion opinion) throws RepositorioException {

		try {

			ObjectId id = new ObjectId();
			opinion.setId(id);

			opinions.insertOne(opinion);

			return id.toString();

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido insertar la entidad", e);
		}
	}

	@Override
	public void update(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocument(opinion.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + opinion.getId());

		try {

			opinions.replaceOne(Filters.eq("_id", opinion.getId()), opinion);

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido actualizar la entidad, id:" + opinion.getId(), e);
		}

	}

	@Override
	public void delete(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		if (!checkDocument(opinion.getId()))
			throw new EntidadNoEncontrada("No existe la entidad con id:" + opinion.getId());

		try {
			opinions.deleteOne(Filters.eq("_id", opinion.getId()));

		} catch (Exception e) {
			throw new RepositorioException("No se ha podido borrar la entidad, id:" + opinion.getId(), e);
		}

	}

	@Override
	public Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada {

		Opinion opinion = opinions.find(Filters.eq("_id", new ObjectId(id))).first();

		if (opinion == null)
			throw new EntidadNoEncontrada("No existe la entidad con id:" + id);

		return opinion;
	}

	@Override
	public List<Opinion> getAll() throws RepositorioException {

		LinkedList<Opinion> allOpiniones = new LinkedList<>();

		opinions.find().into(allOpiniones);

		return allOpiniones;
	}

	@Override
	public List<String> getIds() {

		LinkedList<String> allIds = new LinkedList<String>();

		opinions.find().map(e -> e.getId().toString()).into(allIds);

		return allIds;
	}
}