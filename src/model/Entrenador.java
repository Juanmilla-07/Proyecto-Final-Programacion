package model;

/**
 * POJO que representa la tabla hija 'entrenadores'.
 * Extiende los datos de 'usuarios' con especialidad y licencia.
 */
public class Entrenador extends Usuario {

    private String especialidad;
    private String licencia;

    // ── Constructores ───────────────────────────────────────────────────────

    public Entrenador() {
        super();
        setRol("entrenador");
    }

    public Entrenador(int id, String username, String password,
            String email, String nombre, String apellidos, String dni,
            String especialidad, String licencia) {
        super(id, username, password, email, nombre, apellidos, dni, "entrenador");
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    // ── Getters & Setters ───────────────────────────────────────────────────

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String e) {
        this.especialidad = e;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String l) {
        this.licencia = l;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidos()
                + " | " + especialidad
                + " | Licencia: " + licencia;
    }
}
