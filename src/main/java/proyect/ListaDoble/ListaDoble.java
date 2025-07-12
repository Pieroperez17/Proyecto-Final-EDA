package proyect.ListaDoble;
import proyect.Tramite;

public class ListaDoble {
    private NodoLD cabeza;
    private NodoLD cola;
    private int tamaño;

    public ListaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int obtenerTamaño() {
        return tamaño;
    }

    // --- MÉTODOS CORREGIDOS ---
    
    // Insertar al inicio (ahora recibe un Trámite)
    public void insertarAlInicio(Tramite tramite) {
        NodoLD nuevoNodo = new NodoLD(tramite);
        if (estaVacia()) {
            cabeza = cola = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cabeza;
            cabeza.anterior = nuevoNodo;
            cabeza = nuevoNodo;
        }
        tamaño++;
    }

    // Insertar al final (ahora recibe un Trámite)
    public void insertarAlFinal(Tramite tramite) {
        NodoLD nuevoNodo = new NodoLD(tramite);
        if (estaVacia()) {
            cabeza = cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
        tamaño++;
    }

    // Eliminar del inicio
    public Tramite eliminarDelInicio() {
        if (estaVacia()) {
            return null;
        }
        NodoLD nodoEliminado = cabeza;
        if (cabeza == cola) {
            cabeza = cola = null;
        } else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        }
        tamaño--;
        return nodoEliminado.dato;
    }

    // Eliminar del final
    public Tramite eliminarDelFinal() {
        if (estaVacia()) {
            return null;
        }
        NodoLD nodoEliminado = cola;
        if (cabeza == cola) {
            cabeza = cola = null;
        } else {
            cola = cola.anterior;
            cola.siguiente = null;
        }
        tamaño--;
        return nodoEliminado.dato;
    }

    // Buscar trámite por su ID (más eficiente)
    public Tramite buscarPorId(String id) {
        NodoLD actual = cabeza;
        while (actual != null) {
            if (actual.dato.getIdTramite().equals(id)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null; // No encontrado
    }

    // Obtener todos los trámites como un array
    public Tramite[] obtenerTodos() {
        Tramite[] tramites = new Tramite[tamaño];
        NodoLD actual = cabeza;
        int i = 0;
        while (actual != null) {
            tramites[i] = actual.dato;
            actual = actual.siguiente;
            i++;
        }
        return tramites;
    }

    // Mostrar lista (para depuración)
    public void mostrarHaciaAdelante() {
        if (estaVacia()) {
            System.out.println("Lista doble vacía");
            return;
        }
        NodoLD actual = cabeza;
        while (actual != null) {
            System.out.print(actual.dato.getIdTramite() + " <-> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }
}