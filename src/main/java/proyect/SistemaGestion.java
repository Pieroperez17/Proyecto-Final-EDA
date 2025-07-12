package proyect;

import java.text.SimpleDateFormat;
import java.util.Date;

import proyect.Cola.Cola;
import proyect.ListaCircular.ListaCircular;
import proyect.ListaDoble.ListaDoble;

public class SistemaGestion {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234";

    private final ListaDoble registroMaestroDeTramites;
    
    private final Cola colaDeTramitesPendientes;
    
    final ListaCircular carruselDeAlertas;
    public SistemaGestion() {
        this.registroMaestroDeTramites = new ListaDoble();
        this.colaDeTramitesPendientes = new Cola();
        this.carruselDeAlertas = new ListaCircular();
    }

    public boolean validarCredenciales(String usuario, String contrasena) {
        return ADMIN_USER.equals(usuario) && ADMIN_PASS.equals(contrasena);
    }


    public void registrarNuevoTramite(Tramite tramite) {
        registroMaestroDeTramites.insertarAlFinal(tramite);

        if ("Pendiente".equalsIgnoreCase(tramite.getEstado())) {
            colaDeTramitesPendientes.enqueue(tramite);
            carruselDeAlertas.insertar(tramite);
        }
    }

    public Tramite buscarTramitePorId(String idTramite) {
        return registroMaestroDeTramites.buscarPorId(idTramite);
    }

    public Tramite[] obtenerTodosLosTramites() {
        return registroMaestroDeTramites.obtenerTodos();
    }
    
    public Tramite obtenerSiguienteAlerta() {
        return carruselDeAlertas.obtenerSiguiente();
    }
    
    public void agregarMovimientoAlHistorial(String idTramite, String descripcionMovimiento) {
        Tramite tramite = buscarTramitePorId(idTramite);
        if (tramite != null) {
            tramite.getHistorialDeMovimientos().push(descripcionMovimiento);
        }
    }

    public void resolverTramite(String idTramite) {
        Tramite tramite = buscarTramitePorId(idTramite);

        if (tramite != null && "Pendiente".equalsIgnoreCase(tramite.getEstado())) {
            
            tramite.setEstado("Finalizado");
            String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            tramite.setFechaFinal(fechaActual);
            
            
            tramite.getHistorialDeMovimientos().push("Trámite Finalizado - Fecha: " + fechaActual);
            
            this.carruselDeAlertas.removerPorId(idTramite);
        }
    }
}