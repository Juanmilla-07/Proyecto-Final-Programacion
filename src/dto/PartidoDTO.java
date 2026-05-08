package dto;

import java.time.LocalDate;

/**
 * DTO para mostrar partidos con los nombres de equipos resueltos via JOIN.
 * Incluye también los IDs de los equipos para poder editar el registro.
 */
public class PartidoDTO {

    private int id;
    private int equipoLocalId; // necesario para editar
    private int equipoVisitanteId; // necesario para editar
    private String equipoLocalNombre;
    private String equipoVisitanteNombre;
    private LocalDate fecha;
    private int golesLocal;
    private int golesVisitante;
    private int jornada;

    public PartidoDTO(int id, int equipoLocalId, int equipoVisitanteId,
            String equipoLocalNombre, String equipoVisitanteNombre,
            LocalDate fecha, int golesLocal, int golesVisitante, int jornada) {
        this.id = id;
        this.equipoLocalId = equipoLocalId;
        this.equipoVisitanteId = equipoVisitanteId;
        this.equipoLocalNombre = equipoLocalNombre;
        this.equipoVisitanteNombre = equipoVisitanteNombre;
        this.fecha = fecha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jornada = jornada;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public int getEquipoLocalId() {
        return equipoLocalId;
    }

    public int getEquipoVisitanteId() {
        return equipoVisitanteId;
    }

    public String getEquipoLocalNombre() {
        return equipoLocalNombre;
    }

    public String getEquipoVisitanteNombre() {
        return equipoVisitanteNombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public int getJornada() {
        return jornada;
    }

    /** Resultado legible: "FC Ayala Senior 2 - 1 FC Ayala Juvenil" */
    public String getResultado() {
        return equipoLocalNombre + "  " + golesLocal + " - " + golesVisitante + "  " + equipoVisitanteNombre;
    }

    @Override
    public String toString() {
        return "J" + jornada + " | " + getResultado() + " (" + fecha + ")";
    }
}