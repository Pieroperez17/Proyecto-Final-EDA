package proyect.Pila;

public class Pila {
    private NodoPila cima;
    private int tamaño;

    public Pila() {
        this.cima = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public void push(Object dato) {
        NodoPila nuevo = new NodoPila(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamaño++;
    }

    public Object pop() {
        if (estaVacia()) {
            return null;
        }
        Object dato = cima.dato;
        cima = cima.siguiente;
        tamaño--;
        return dato;
    }

    public Object peek() {
        return estaVacia() ? null : cima.dato;
    }

    public int getTamaño() {
        return tamaño;
    }

    public String[] obtenerTodosComoArray() {
        String[] elementos = new String[tamaño];
        NodoPila actual = cima;
        int i = 0;
        while(actual != null) {
            elementos[i] = actual.dato.toString();
            actual = actual.siguiente;
            i++;
        }
        return elementos;
    }
}