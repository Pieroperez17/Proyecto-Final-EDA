package proyect.ListaCircular;
import proyect.Tramite;

public class NodoLC {
    Tramite dato;
    NodoLC siguiente;

    public NodoLC(Tramite dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}