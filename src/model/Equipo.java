package model;

/**
 * POJO que representa la tabla 'equipos'.
 * Entidad principal del dominio Club Deportivo.
 */
public class Equipo {

    private int id;
    private String nombre;
    private String categoria;
    private int entrenadorId; // FK → entrenadores.usuario_id (0 = sin entrenador)
    private int puntos;

    // ── Constructores ───────────────────────────────────────────────────────

    public Equipo() {
    }

    public Equipo(int id, String nombre, String categoria,
            int entrenadorId, int puntos) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.entrenadorId = entrenadorId;
        this.puntos = puntos;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String c) {
        this.categoria = c;
    }

    public int getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(int e) {
        this.entrenadorId = e;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int p) {
        this.puntos = p;
    }

    @Override
    public String toString() {
        return nombre + " (" + categoria + ") — " + puntos + " pts";
    }
}
