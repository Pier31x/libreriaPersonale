package libreriaPersonale.filtri;

import libreriaPersonale.Libro;


public class FiltroStato extends FiltroDecorator {
    String stato;
    public FiltroStato(Filtro filtro, String s) {
        super(filtro);
        stato = s;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && stato.equals(libro.getStato());
    }
}
