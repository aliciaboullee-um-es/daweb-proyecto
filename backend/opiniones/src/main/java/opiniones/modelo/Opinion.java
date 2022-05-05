package opiniones.modelo;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlTransient;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Opinion {

	// TODO: identificador
	@JsonIgnore
	@BsonId
	private ObjectId id;

	private String url;

	private LinkedList<Valoracion> valoraciones = new LinkedList<>();

	// Propiedades calculadas
	public Integer getNumValoraciones() {
		return this.valoraciones.size();
	}

	public Double getCalificacionMedia() {
		double calificacionTotal = 0.0;

		for (Valoracion val : this.valoraciones) {
			calificacionTotal += val.getCalificacion();
		}

		return calificacionTotal / getNumValoraciones();
	}

	// Get & Set
	public String getIdentificador() {
		return this.id.toString();
	}

	public void setIdentificador(String identificador) {
		this.id = new ObjectId(identificador);
	}

	@XmlTransient
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LinkedList<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(LinkedList<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	@Override
	public String toString() {
		return "Opinion [id=" + id + ", url=" + url + ", valoraciones=" + valoraciones + "]";
	}

}
