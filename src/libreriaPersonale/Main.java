package libreriaPersonale;

import libreriaPersonale.filtri.*;

public class Main {
    public static void main(String[] args) {
        Libro l = new Libro("a", "a", "a", "a", 10, Libro.Stato.DA_LEGGERE);
        Filtro f = new FiltroBase();
        Filtro v = new FiltroValutazione(f, 10);
        Filtro g = new FiltroGenere(v, "10");
        System.out.println(g.applicaFiltro(l));

    }
}