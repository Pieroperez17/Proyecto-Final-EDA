package proyect.ListaDoble;
import proyect.Tramite;

public class NodoLD {
    Tramite dato;
    NodoLD siguiente;
    NodoLD anterior;
    
    public NodoLD(Tramite dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}
