package dto;

import java.time.LocalDate;

/**
 * DTO para mostrar un jugador junto con el nombre de su equipo.
 * Resultado de un JOIN entre usuarios, jugadores y equipo_jugador.
 */
public class JugadorEquipoDTO {

    private int id;
    private String nombre;
    private String apellidos;
    private String username;
    private String posicion;
    private int dorsal;
    private LocalDate fechaNac;
    private String nombreEquipo; // resuelto via JOIN

    public JugadorEquipoDTO(int id, String nombre, String apellidos,
            String username, String posicion, int dorsal,
            LocalDate fechaNac, String nombreEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.posicion = posicion;
        this.dorsal = dorsal;
        this.fechaNac = fechaNac;
        this.nombreEquipo = nombreEquipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getUsername() {
        return username;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getDorsal() {
        return dorsal;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " | " + posicion + " | " + nombreEquipo;
    }
}