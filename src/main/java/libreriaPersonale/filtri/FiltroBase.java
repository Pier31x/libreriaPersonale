package libreriaPersonale.filtri;

import libreriaPersonale.modello.Libro;

public class FiltroBase implements Filtro {

    @Override
    public boolean applicaFiltro(Libro libro) {
        return true;
    }
}
