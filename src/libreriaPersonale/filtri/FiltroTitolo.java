package libreriaPersonale.filtri;

import libreriaPersonale.Libro;

//TODO
public class FiltroTitolo extends FiltroDecorator {
    String titolo;
    public FiltroTitolo(Filtro filtro, String t) {
        super(filtro);
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && titolo.contains(libro.getTitolo());
    }
}
