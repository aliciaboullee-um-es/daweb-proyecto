package modelo;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class Valoracion {

	@JsonIgnore
	@BsonId
	private ObjectId id = new ObjectId();
	
	private String correo;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime fecha;
	
	private int calificacion;
	private String comentario;
	
	public Valoracion() {
		fecha = LocalDateTime.now();
	}
	
	public String getIdentificador() {
		return this.id.toString();
	}
	
	public void setIdentificador(String identificador) {
		this.id = new ObjectId(identificador);
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@XmlJavaTypeAdapter(value = LocalDateTimeAdapter.class)
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Override
	public String toString() {
		return "Valoracion [id=" + id + ", correo=" + correo + ", fecha=" + fecha + ", calificacion=" + calificacion
				+ ", comentario=" + comentario + "]";
	}
}
