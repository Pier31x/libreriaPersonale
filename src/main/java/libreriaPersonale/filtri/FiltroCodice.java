package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;


public class FiltroCodice extends FiltroDecorator {
    String codice;
    public FiltroCodice(Filtro filtro, String c) {
        super(filtro);
        codice = c;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return libro.getIsbn()!=null && filtro.applicaFiltro(libro) && libro.getIsbn().contains(codice);
    }
}
