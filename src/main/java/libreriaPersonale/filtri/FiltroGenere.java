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
        return libro.getGenere()!=null && filtro.applicaFiltro(libro) && libro.getGenere().toLowerCase().contains(genere.toLowerCase());
    }
}
