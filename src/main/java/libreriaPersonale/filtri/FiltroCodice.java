package libreriaPersonale.filtri;

import libreriaPersonale.Libro;


public class FiltroCodice extends FiltroDecorator {
    String codice;
    public FiltroCodice(Filtro filtro, String c) {
        super(filtro);
        codice = c;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && codice.equals(libro.getIsbn());
    }
}
