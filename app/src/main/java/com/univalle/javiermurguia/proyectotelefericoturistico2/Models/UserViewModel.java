package com.univalle.javiermurguia.proyectotelefericoturistico2.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> liveUser = new MutableLiveData<>();

    public void setUser(User user){liveUser.setValue(user);}

    public LiveData<User> getUser(){return liveUser;}
}
