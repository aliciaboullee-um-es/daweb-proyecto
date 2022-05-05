package opiniones.modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


public class Valoracion {

	private String email;
	private Integer calificacion;
	private String comentario;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime fechaValoracion;

	// Get & Set

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFechaValoracion() {
		return fechaValoracion;
	}

	public void setFechaValoracion(LocalDateTime fechaValoracion) {
		this.fechaValoracion = fechaValoracion;
	}

	@Override
	public String toString() {
		return "Valoracion [ email=" + email + ", calificacion=" + calificacion + ", comentario=" + comentario
				+ ", fechaValoracion=" + fechaValoracion + "]";
	}

}
