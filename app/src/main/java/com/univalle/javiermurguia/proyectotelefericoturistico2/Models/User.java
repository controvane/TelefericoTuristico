package com.univalle.javiermurguia.proyectotelefericoturistico2.Models;

import java.io.Serializable;

public class User implements Serializable {
    String email;
    boolean enabled;
    String firstName;
    String idToken;
    String lastName;
    String rol;
    String urlAvatar;
    String userName;
    String uuid;
    boolean show;

    public User(String email, boolean enabled, String firstName, String idToken, String lastName, String rol, String urlAvatar, String userName, String uuid) {
        this.email = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.idToken = idToken;
        this.lastName = lastName;
        this.rol = rol;
        this.urlAvatar = urlAvatar;
        this.userName = userName;
        this.uuid = uuid;
        this.show = false;
    }

    public User() {
        this.email = "";
        this.enabled = false;
        this.firstName = "";
        this.idToken = "";
        this.lastName = "";
        this.rol = "";
        this.urlAvatar = "";
        this.userName = "";
        this.uuid = "";
        this.show = false;
    }

    public User(String userName) {
        this.email = "";
        this.enabled = false;
        this.firstName = "";
        this.idToken = "";
        this.lastName = "";
        this.rol = "";
        this.urlAvatar = "";
        this.userName = userName;
        this.uuid = "";
        this.show = false;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRol() {
        return rol;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getUuid() {
        return uuid;
    }
}
