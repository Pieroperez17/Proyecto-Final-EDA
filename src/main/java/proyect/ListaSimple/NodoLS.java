package proyect.ListaSimple;
import proyect.Tramite;

public class NodoLS {
    public Tramite data;
    public NodoLS siguiente;
    
    public NodoLS(Tramite imputData) {
        this.data = imputData;
        this.siguiente = null;
    }
}
