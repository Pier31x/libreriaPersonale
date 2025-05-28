package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;


public class FiltroGenere extends FiltroDecorator {
    String genere;

    public FiltroGenere(Filtro filtro, String g) {
        super(filtro);
        genere = g;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return !libro.getGenere().isEmpty() && filtro.applicaFiltro(libro) && libro.getGenere().contains(genere);
    }
}
