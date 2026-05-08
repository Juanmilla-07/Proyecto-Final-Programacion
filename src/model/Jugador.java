package model;

import java.time.LocalDate;

/**
 * POJO que representa la tabla hija 'jugadores'.
 * Extiende los datos de 'usuarios' con atributos deportivos.
 */
public class Jugador extends Usuario {

    private String posicion;
    private int dorsal;
    private LocalDate fechaNac;

    // ── Constructores ───────────────────────────────────────────────────────

    public Jugador() {
        super();
        setRol("jugador");
    }

    public Jugador(int id, String username, String password,
            String email, String nombre, String apellidos, String dni,
            String posicion, int dorsal, LocalDate fechaNac) {
        super(id, username, password, email, nombre, apellidos, dni, "jugador");
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.fechaNac = fechaNac;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String p) {
        this.posicion = p;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int d) {
        this.dorsal = d;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate f) {
        this.fechaNac = f;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos()
                + " | Dorsal: " + dorsal
                + " | " + posicion;
    }
}