package proyect;

import java.util.Random;
import proyect.Pila.Pila;


public class Tramite {
    private static final Random RANDOM_GENERATOR = new Random();

    private String idTramite;
    private String prioridad;
    private Interesado interesado;
    private String asunto;
    private String tipoTramite;
    private String estado;
    private String fechaInicio;
    private String fechaFinal;
    
    
    private Pila historialDeMovimientos;

    
    public Tramite(String prioridad, Interesado interesado, String asunto,
                   String tipoTramite, String estado, String fechaInicio, String fechaFinal) {
        this.idTramite = generarIDUnico();
        this.prioridad = prioridad;
        this.interesado = interesado;
        this.asunto = asunto;
        this.tipoTramite = tipoTramite;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        
        
        this.historialDeMovimientos = new Pila();
        this.historialDeMovimientos.push("Trámite Creado - Fecha: " + fechaInicio);
    }


    private String generarIDUnico() {
        int numeroAleatorio = 1000 + RANDOM_GENERATOR.nextInt(9000);
        return "EXP" + numeroAleatorio;
    }

    public String getIdTramite() { return idTramite; }
    public String getPrioridad() { return prioridad; }
    public Interesado getInteresado() { return interesado; }
    public String getAsunto() { return asunto; }
    public String getTipoTramite() { return tipoTramite; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; } 
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFinal() { return fechaFinal; }
    public Pila getHistorialDeMovimientos() { return historialDeMovimientos; }


    @Override
    public String toString() {
        return String.format("ID: %s | Estado: %s | Asunto: %s", idTramite, estado, asunto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tramite otroTramite = (Tramite) obj;
        return idTramite.equals(otroTramite.idTramite);
    }


    @Override
    public int hashCode() {
        return idTramite.hashCode();
    }
    public void setFechaFinal(String fechaFinal) { this.fechaFinal = fechaFinal; }
}