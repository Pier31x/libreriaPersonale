package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;


public class FiltroValutazione extends FiltroDecorator {
    int valutazione;
    public FiltroValutazione(Filtro filtro, int v) {
        super(filtro);
        valutazione = v;
    }

    @Override
    public boolean applicaFiltro(Libro libro) {
        return filtro.applicaFiltro(libro) && valutazione == libro.getValutazione();
    }
}
