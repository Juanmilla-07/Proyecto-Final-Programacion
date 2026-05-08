package dto;

/**
 * DTO para mostrar un equipo junto con el nombre completo de su entrenador.
 * Resultado de un JOIN entre equipos y usuarios (entrenadores).
 */
public class EquipoEntrenadorDTO {

    private int id;
    private String nombre;
    private String categoria;
    private int puntos;
    private String nombreEntrenador; // resuelto via JOIN

    public EquipoEntrenadorDTO(int id, String nombre, String categoria,
            int puntos, String nombreEntrenador) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.puntos = puntos;
        this.nombreEntrenador = nombreEntrenador;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    @Override
    public String toString() {
        return nombre + " (" + categoria + ") — " + puntos + " pts | Entrenador: " + nombreEntrenador;
    }
}