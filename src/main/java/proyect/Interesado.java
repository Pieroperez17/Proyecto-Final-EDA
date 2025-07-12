package proyect;

/**
 * Representa a la persona o entidad que inicia un trámite.
 * Almacena la información de contacto básica del interesado.
 */
public class Interesado {
    // Atributos que describen al interesado
    private String dni;
    private String nombres;
    private String telefono;
    private String email;

    /**
     * Constructor para crear un nuevo objeto Interesado.
     * @param dni El Documento Nacional de Identidad del interesado.
     * @param nombres Los nombres y apellidos completos.
     * @param telefono El número de contacto telefónico.
     * @param email La dirección de correo electrónico.
     */
    public Interesado(String dni, String nombres, String telefono, String email) {
        this.dni = dni;
        this.nombres = nombres;
        this.telefono = telefono;
        this.email = email;
    }

    // --- Métodos Getters (para obtener los datos) ---
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    
    @Override
    public String toString() {
        return "Interesado [DNI=" + dni + ", Nombres=" + nombres + "]";
    }
}