package retrofit;


import modelo.Opinion;
import modelo.Valoracion;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface OpinionRestCliente {

	@POST("opiniones")
	Call<Void> createOpinion(@Body Opinion opinion);
	
	@GET("opiniones")
	Call<Opinion> getOpinion(@Query("url") String url);
	
	@POST("opiniones/valoraciones")
	Call<Void> valorar(@Query("url") String url, @Body Valoracion valoracion);
	
	@DELETE("opiniones")
	Call<Void> remove(@Query("url") String url);
}
