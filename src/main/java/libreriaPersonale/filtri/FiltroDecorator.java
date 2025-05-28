package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;

public abstract class FiltroDecorator implements Filtro {
    protected Filtro filtro;

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro);
    }

    public FiltroDecorator(Filtro filtro) {
        this.filtro = filtro;
    }
}
