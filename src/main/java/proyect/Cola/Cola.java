package proyect.Cola;
import proyect.Tramite;

public class Cola {
    private NodoCola frente;
    private NodoCola fin;
    private int tamaño;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void enqueue(Tramite tramite) {
        NodoCola nuevo = new NodoCola(tramite);
        if (estaVacia()) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamaño++;
    }

    public Tramite dequeue() {
        if (estaVacia()) {
            return null;
        }
        Tramite dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null;
        }
        tamaño--;
        return dato;
    }
    
    public Cola removerPorId(String idTramite) {
        Cola nuevaCola = new Cola();
        NodoCola actual = this.frente;
        while(actual != null) {
            if (!actual.dato.getIdTramite().equals(idTramite)) {
                nuevaCola.enqueue(actual.dato);
            }
            actual = actual.siguiente;
        }
        return nuevaCola;
    }
}