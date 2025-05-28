package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;


public class FiltroTitolo extends FiltroDecorator {
    String titolo;
    public FiltroTitolo(Filtro filtro, String t) {
        super(filtro);
        titolo = t;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return !libro.getTitolo().isEmpty() && filtro.applicaFiltro(libro) && libro.getTitolo().contains(titolo);
    }
}
