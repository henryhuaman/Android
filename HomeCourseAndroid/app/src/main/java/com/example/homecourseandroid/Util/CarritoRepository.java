package com.example.homecourseandroid.Util;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.homecourseandroid.Model.CarritoDeCompras;

import java.util.ArrayList;
import java.util.List;

public class CarritoRepository {
    private static CarritoRepository instance;
    private List<CarritoDeCompras> carritoList;

    private CarritoRepository() {
        // Constructor privado para evitar la creación directa de instancias
        carritoList = new ArrayList<>();
    }

    public static synchronized CarritoRepository getInstance() {
        // Método para obtener la instancia única del Singleton
        if (instance == null) {
            instance = new CarritoRepository();
        }
        return instance;
    }

    public void add(CarritoDeCompras carrito) {
        carritoList.add(carrito);
    }

    public void delete(String id) {
        carritoList.removeIf(c->c.getIdCur().equals(id));

    }

    public void deleteAll() {
        carritoList.clear();
    }

    public List<CarritoDeCompras> getAllCarritoDeCompras() {
        return carritoList;
    }
}
