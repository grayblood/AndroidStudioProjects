package com.example.myapplication;

public class Usuario {

    private String nombre;
    private String nick;
    private String password;

    public Usuario(String nombre, String nick, String password) {
        this.nombre = nombre;
        this.nick = nick;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }
    public String getNick() {
        return nick;
    }
    public String getPassword () { return password; }
}
