package com.example.tfg3.CarpetaAPI;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("search?q=chicken&app_id=f8a801f5&app_key=70a3be442007e101a7617c95f943525a&from=0&to=20")
    Call<Respuesta> obtenerDatos();
}
