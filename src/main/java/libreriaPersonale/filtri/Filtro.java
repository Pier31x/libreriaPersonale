package libreriaPersonale.filtri;

import libreriaPersonale.Libro;

public interface Filtro {
    boolean applicaFiltro(Libro libro);
}
