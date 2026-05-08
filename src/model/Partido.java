package model;

import java.time.LocalDate;

/**
 * POJO que representa la tabla 'partidos'.
 * Relación N:M entre equipos con resultado y jornada.
 */
public class Partido {

    private int id;
    private int equipoLocalId;
    private int equipoVisitanteId;
    private LocalDate fecha;
    private int golesLocal;
    private int golesVisitante;
    private int jornada;

    // ── Constructores ───────────────────────────────────────────────────────

    public Partido() {
    }

    public Partido(int id, int equipoLocalId, int equipoVisitanteId,
            LocalDate fecha, int golesLocal, int golesVisitante, int jornada) {
        this.id = id;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.fecha = fecha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jornada = jornada;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEquipoLocalId() {
        return equipoLocalId;
    }

    public void setEquipoLocalId(int e) {
        this.equipoLocalId = e;
    }

    public int getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public void setEquipoVisitanteId(int e) {
        this.equipoVisitanteId = e;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate f) {
        this.fecha = f;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int g) {
        this.golesLocal = g;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int g) {
        this.golesVisitante = g;
    }

    public int getJornada() {
        return jornada;
    }

    public void setJornada(int j) {
        this.jornada = j;
    }

    @Override
    public String toString() {
        return "Jornada " + jornada + " | Equipo " + equipoLocalId
                + " " + golesLocal + "-" + golesVisitante
                + " Equipo " + equipoVisitanteId
                + " (" + fecha + ")";
    }
}