package com.example.homecourseandroid.Util;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.homecourseandroid.Model.CarritoDeCompras;

import java.util.ArrayList;
import java.util.List;

public class CarritoRepository extends ViewModel {
    private MutableLiveData<List<CarritoDeCompras>> car;

    public void add(CarritoDeCompras carrito){
        List<CarritoDeCompras> carActual = car.getValue();
        carActual.add(carrito);
        car.setValue(carActual);
    }
    public void delete(String id) {
        List<CarritoDeCompras> carActual = car.getValue();
        carActual.removeIf(c -> c.getIdCur().equals(id));
        car.setValue(carActual);
    }
    public void deleteAll(){
        car.setValue(new ArrayList<>());
    }
    public MutableLiveData<List<CarritoDeCompras>> getAllCarritoDeCompras(){
        if(car==null){
            //inicializa
            car = new MutableLiveData<>();
            car.setValue(new ArrayList<>());
        }
        return car;
    }
}
