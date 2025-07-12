package proyect.Cola;
import proyect.Tramite;

public class NodoCola {
    Tramite dato;
    NodoCola siguiente;

    public NodoCola(Tramite dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}