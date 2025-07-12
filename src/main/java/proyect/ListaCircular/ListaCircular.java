package proyect.ListaCircular;
import proyect.Tramite;

/**
 * Representa una lista enlazada circular.
 * Es ideal para crear un carrusel de elementos que se repite de forma infinita.
 */
public class ListaCircular {
    private NodoLC cabeza;
    private int tamaño;

    public ListaCircular() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamaño() {
        return tamaño;
    }
    
    public void insertar(Tramite tramite) {
        NodoLC nuevo = new NodoLC(tramite);
        if (estaVacia()) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza; // El único nodo se apunta a sí mismo.
        } else {
            // Se inserta después de la cabeza para mantener el orden de rotación.
            nuevo.siguiente = cabeza.siguiente;
            cabeza.siguiente = nuevo;
        }
        tamaño++;
    }
    
    public Tramite obtenerSiguiente() {
        if(estaVacia()) {
            return null;
        }
        // Avanzamos la cabeza al siguiente nodo para "rotar" el carrusel.
        cabeza = cabeza.siguiente;
        return cabeza.dato;
    }
    
    /**
     * Remueve un trámite de la lista circular basado en su ID.
     * Esta es la versión corregida que maneja todos los casos correctamente.
     * @param idTramite El ID del trámite a eliminar.
     */
    public void removerPorId(String idTramite) {
        if (estaVacia()) {
            return; // No hay nada que remover.
        }

        // --- Caso 1: El nodo a eliminar es la cabeza de la lista ---
        if (cabeza.dato.getIdTramite().equals(idTramite)) {
            if (tamaño == 1) {
                // Si es el único nodo, la lista simplemente queda vacía.
                cabeza = null;
            } else {
                // Si hay más nodos, necesitamos encontrar el último nodo (el que apunta a la cabeza).
                NodoLC ultimo = cabeza;
                while (ultimo.siguiente != cabeza) {
                    ultimo = ultimo.siguiente;
                }
                // La nueva cabeza será el nodo siguiente a la cabeza actual.
                cabeza = cabeza.siguiente;
                // El último nodo ahora debe apuntar a la nueva cabeza para cerrar el círculo.
                ultimo.siguiente = cabeza;
            }
            tamaño--;
            return;
        }

        // --- Caso 2: El nodo a eliminar está en el cuerpo de la lista (no es la cabeza) ---
        NodoLC anterior = cabeza;
        NodoLC actual = cabeza.siguiente;

        // Recorremos la lista hasta dar la vuelta completa.
        while (actual != cabeza) {
            if (actual.dato.getIdTramite().equals(idTramite)) {
                // ¡Lo encontramos! Hacemos que el nodo anterior se salte al actual.
                anterior.siguiente = actual.siguiente;
                tamaño--;
                return; // Salimos del método una vez eliminado.
            }
            // Avanzamos en la lista.
            anterior = actual;
            actual = actual.siguiente;
        }
    }
}