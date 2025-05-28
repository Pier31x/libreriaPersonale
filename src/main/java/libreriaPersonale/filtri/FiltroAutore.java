package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;


public class FiltroAutore extends FiltroDecorator {
    String autore;
    public FiltroAutore(Filtro filtro, String a) {
        super(filtro);
        autore = a;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return !libro.getAutore().isEmpty() && filtro.applicaFiltro(libro) && libro.getAutore().contains(autore);
    }
}
