package libreriaPersonale.GUI;

import libreriaPersonale.modello.Libro;
import libreriaPersonale.modello.Stato;

import javax.swing.*;
import java.awt.*;


public class AggiungiLibroDialog extends JDialog {
    private final JTextField tfIsbn = new JTextField(20);
    private final JTextField tfTitolo = new JTextField(20);
    private final JTextField tfAutore = new JTextField(20);
    private final JTextField tfGenere = new JTextField(20);
    private final JTextField tfValutazione = new JTextField(5);
    private final JComboBox<Stato> cbStato = new JComboBox<>(Stato.values());

    private final JButton btnConferma = new JButton("Conferma");
    private final JButton btnAnnulla = new JButton("Annulla");

    public interface LibroListener {
        void libroAggiunto(Libro libro);
    }

    public AggiungiLibroDialog(JFrame parent, LibroListener listener) {
        super(parent, "Aggiungi Libro", true);
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("ISBN:"));
        add(tfIsbn);
        add(new JLabel("Titolo:"));
        add(tfTitolo);
        add(new JLabel("Autore:"));
        add(tfAutore);
        add(new JLabel("Genere:"));
        add(tfGenere);
        add(new JLabel("Valutazione:"));
        add(tfValutazione);
        add(new JLabel("Stato lettura:"));
        add(cbStato);
        add(btnAnnulla);
        add(btnConferma);

        btnConferma.addActionListener(e -> {
            String isbn = tfIsbn.getText().trim();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "L'ISBN è obbligatorio.");
                return;
            }

            String titolo = tfTitolo.getText().trim();
            String autore = tfAutore.getText().trim();
            String genere = tfGenere.getText().trim();

            Integer valutazione = 0;
            String valText = tfValutazione.getText().trim();
            if (!valText.isEmpty()) {
                try {
                    valutazione = Integer.parseInt(valText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "La valutazione deve essere un numero intero.");
                    return;
                }
            }

            Stato stato = (Stato) cbStato.getSelectedItem();

            try {
                Libro libro = new Libro(
                        isbn,
                        titolo.isEmpty() ? null : titolo,
                        autore.isEmpty() ? null : autore,
                        genere.isEmpty() ? null : genere,
                        valutazione,
                        stato
                );
                listener.libroAggiunto(libro);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante la creazione del libro.");
            }
        });


        btnAnnulla.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
    }
}
