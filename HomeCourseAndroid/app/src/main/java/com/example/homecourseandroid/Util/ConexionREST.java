package com.example.homecourseandroid.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionREST {

    private static final String URL = "http://port2002-001-site1.ctempurl.com/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getConnection()
    {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
