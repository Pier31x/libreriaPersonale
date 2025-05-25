package libreriaPersonale.filtri;

import libreriaPersonale.Libro;


public class FiltroAutore extends FiltroDecorator {
    String autore;
    public FiltroAutore(Filtro filtro, String a) {
        super(filtro);
        autore = a;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && autore.contains(libro.getAutore());
    }
}
