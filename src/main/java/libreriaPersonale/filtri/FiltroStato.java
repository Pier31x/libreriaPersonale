package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;
import libreriaPersonale.modello.Stato;


public class FiltroStato extends FiltroDecorator {
    Stato stato;
    public FiltroStato(Filtro filtro, Stato s) {
        super(filtro);
        stato = s;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && stato.equals(libro.getStato());
    }
}
