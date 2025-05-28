package libreriaPersonale.comparatori;

import libreriaPersonale.modello.Libro;

import java.util.Comparator;
import java.util.List;

public class Comparators {

    //public static Comparator<Libro> perISBN = Comparator.comparing(Libro::getIsbn, Comparator.nullsLast(String::compareTo));

    public static Comparator<Libro> perTitolo = Comparator.comparing(Libro::getTitolo, Comparator.nullsLast(String::compareTo));

    public static Comparator<Libro> perAutore = Comparator.comparing(Libro::getAutore, Comparator.nullsLast(String::compareTo));

    public static Comparator<Libro> perGenere = Comparator.comparing(Libro::getGenere, Comparator.nullsLast(String::compareTo));

    public static Comparator<Libro> perValutazione = Comparator.comparingInt(Libro::getValutazione);

    public static Comparator<Libro> perStato = Comparator.comparing(Libro::getStato, Comparator.nullsLast(Enum::compareTo));


    public static Comparator<Libro> inverti(Comparator<Libro> comparator) {
        return comparator.reversed();
    }


    //Metodo che data l'enum ritorna il comparatore
    public static Comparator<Libro> getComparator(EnumComparatori comparator, List<Libro> ordineOriginale) {
        return switch (comparator) {
            case TITOLO -> Comparators.perTitolo;
            case AUTORE -> Comparators.perAutore;
            case GENERE -> Comparators.perGenere;
            case VALUTAZIONE -> Comparators.perValutazione;
            case STATO -> Comparators.perStato;
            default -> Comparator.comparingInt(ordineOriginale::indexOf);
        };
    }
}