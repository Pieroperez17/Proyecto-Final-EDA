package proyect.ListaSimple;

import proyect.Tramite;

public class ListaSimple {
    NodoLS cabeza;
    int tamaño;
    
    public ListaSimple() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public int obtenerTamaño() {
        return tamaño;
    }
    
    public void insertarInicio(Tramite tramite) {
        NodoLS nuevo = new NodoLS(tramite);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamaño++;
    }
    
    public void insertarFinal(Tramite tramite) {
        NodoLS nuevo = new NodoLS(tramite);
        
        if (estaVacia()) {
            cabeza = nuevo;
        } else {
            NodoLS actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }
    
    // Insertar en posición específica
    public void insertarEnPosicion(Tramite tramite, int posicion) {
        if (posicion < 0 || posicion > tamaño) {
            System.out.println("Posición inválida");
            return;
        }
        
        if (posicion == 0) {
            insertarInicio(tramite);
        } else {
            NodoLS nuevo = new NodoLS(tramite);
            NodoLS actual = cabeza;
            
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.siguiente;
            }
            
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
            tamaño++;
        }
    }
    
    // Eliminar del inicio
    public Tramite eliminarInicio() {
        if (estaVacia()) {
            System.out.println("Lista vacía");
            return null;
        }
        
        Tramite tramiteEliminado = cabeza.data;
        cabeza = cabeza.siguiente;
        tamaño--;
        return tramiteEliminado;
    }
    
    // Eliminar del final
    public Tramite eliminarFinal() {
        if (estaVacia()) {
            System.out.println("Lista vacía");
            return null;
        }
        
        if (cabeza.siguiente == null) {
            Tramite tramiteEliminado = cabeza.data;
            cabeza = null;
            tamaño--;
            return tramiteEliminado;
        } else {
            NodoLS actual = cabeza;
            while (actual.siguiente.siguiente != null) {
                actual = actual.siguiente;
            }
            Tramite tramiteEliminado = actual.siguiente.data;
            actual.siguiente = null;
            tamaño--;
            return tramiteEliminado;
        }
    }
    
    // Eliminar en posición específica
    public Tramite eliminarEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            System.out.println("Posición inválida");
            return null;
        }
        
        if (posicion == 0) {
            return eliminarInicio();
        } else {
            NodoLS actual = cabeza;
            
            for (int i = 0; i < posicion - 1; i++) {
                actual = actual.siguiente;
            }
            
            Tramite tramiteEliminado = actual.siguiente.data;
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            return tramiteEliminado;
        }
    }
    
    
    // Buscar si existe un trámite específico
    public boolean contiene(Tramite tramite) {
        NodoLS actual = cabeza;
        
        while (actual != null) {
            if (actual.data.equals(tramite)) {
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
    
    // Obtener trámite en posición específica
    public Tramite obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            System.out.println("Posición inválida");
            return null;
        }
        
        NodoLS actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.siguiente;
        }
        
        return actual.data;
    }
    
    // Obtener el primer trámite
    public Tramite obtenerPrimero() {
        if (estaVacia()) {
            return null;
        }
        return cabeza.data;
    }
    
    // Obtener el último trámite
    public Tramite obtenerUltimo() {
        if (estaVacia()) {
            return null;
        }
        
        NodoLS actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        
        return actual.data;
    }
    
    // Mostrar todos los trámites
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("Lista vacía");
            return;
        }
        
        NodoLS actual = cabeza;
        System.out.println("=== LISTA DE TRÁMITES ===");
        int posicion = 0;
        while (actual != null) {
            System.out.println("Posición " + posicion + ": " + actual.data.toString());
            actual = actual.siguiente;
            posicion++;
        }
        System.out.println("Total de trámites: " + tamaño);
    }
    
    
    
    // Limpiar toda la lista
    public void limpiar() {
        cabeza = null;
        tamaño = 0;
    }
    
    // Obtener todos los trámites como array
    public Tramite[] obtenerTodos() {
        Tramite[] tramites = new Tramite[tamaño];
        NodoLS actual = cabeza;
        int i = 0;
        
        while (actual != null) {
            tramites[i] = actual.data;
            actual = actual.siguiente;
            i++;
        }
        
        return tramites;
    }
    
    // Filtrar trámites por estado (si Tramite tiene método getEstado())
    public ListaSimple filtrarPorEstado(String estado) {
        ListaSimple listaFiltrada = new ListaSimple();
        NodoLS actual = cabeza;
        
        while (actual != null) {
            if (actual.data.getEstado().equals(estado)) {
                listaFiltrada.insertarFinal(actual.data);
            }
            actual = actual.siguiente;
        }
        
        return listaFiltrada;
    }
    
    // Contar trámites por estado
    public int contarPorEstado(String estado) {
        int contador = 0;
        NodoLS actual = cabeza;
        
        while (actual != null) {
            if (actual.data.getEstado().equals(estado)) {
                contador++;
            }
            actual = actual.siguiente;
        }
        
        return contador;
    }
}