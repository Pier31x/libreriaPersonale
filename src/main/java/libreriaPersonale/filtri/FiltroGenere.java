package libreriaPersonale.filtri;

import libreriaPersonale.Libro;


public class FiltroGenere extends FiltroDecorator {
    String genere;

    public FiltroGenere(Filtro filtro, String g) {
        super(filtro);
        genere = g;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && genere.contains(libro.getGenere());
    }
}
