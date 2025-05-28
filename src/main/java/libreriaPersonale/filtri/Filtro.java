package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;

public interface Filtro {
    boolean applicaFiltro(Libro libro);
}
