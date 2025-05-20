package libreriaPersonale.filtri;

import libreriaPersonale.Libro;

public abstract class FiltroDecorator implements Filtro {
    Filtro filtro;

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro);
    }
}
