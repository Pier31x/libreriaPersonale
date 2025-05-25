package libreriaPersonale.filtri;

import libreriaPersonale.Libro;

public class FiltroBase implements Filtro {

    @Override
    public boolean applicaFiltro(Libro libro) {
        return true;
    }
}
