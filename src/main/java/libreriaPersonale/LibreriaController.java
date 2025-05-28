package libreriaPersonale;

import libreriaPersonale.GUI.AggiungiLibroDialog;
import libreriaPersonale.GUI.libreriaGUI;
import libreriaPersonale.comparatori.Comparators;
import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.filtri.*;
import libreriaPersonale.modello.Libro;
import libreriaPersonale.modello.Stato;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;


public class LibreriaController {
    private final GestoreCatalogo gestore;
    private final libreriaGUI gui;
    private List<Libro> catalogoOriginale;
    private List<Libro> catalogoDaMostrare;

    public LibreriaController(GestoreCatalogo gestore, libreriaGUI gui) {
        this.gestore = gestore;
        this.gui = gui;
        catalogoOriginale = gestore.caricaCatalogo();
        catalogoDaMostrare = gestore.getCatalogo().getCatalogo();
        aggiornaTabella();

        setUpMouseListener();

        gui.getBtnAdd().addActionListener(e -> {
            new AggiungiLibroDialog(gui.getFrame(), libro -> {
                if(gestore.aggiungiLibro(libro)) {
                    catalogoDaMostrare.add(libro);
                    aggiornaTabella();
                }
                else{
                    JOptionPane.showMessageDialog(gui.getMainPanel(), "Libro già presente!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).setVisible(true);
        });


        gui.getBtnSalva().addActionListener(e -> salvaCatalogo());
        gui.getBtnFiltra().addActionListener(e -> filtraCatalogo());
        gui.getCbOrder().addActionListener(e -> ordinaCatalogo());
        gui.getCbInvertiOrdine().addActionListener(e -> ordinaCatalogo());



    }//COSTRUTTORE

    private void ordinaCatalogo() {
        EnumComparatori comando =(EnumComparatori) gui.getCbOrder().getSelectedItem();
        Comparator<Libro> comp = Comparators.getComparator(comando, catalogoOriginale);
        if(gui.getCbInvertiOrdine().isSelected())
            comp = Comparators.inverti(comp);

        System.out.println(comando);
        catalogoDaMostrare.sort(comp);
        aggiornaTabella();
    }

    private void filtraCatalogo() {
        Filtro f = new FiltroBase();

        String codiceISBN = gui.getTfCodice().getText().trim();
        if(!codiceISBN.isEmpty())
            f = new FiltroCodice(f, codiceISBN);

        String titolo = gui.getTfTitolo().getText().trim();
        System.out.println(titolo);
        if(!titolo.isEmpty())
            f = new FiltroTitolo(f, titolo);

        String autore = gui.getTfAutore().getText().trim();
        if(!autore.isEmpty())
            f = new FiltroAutore(f, autore);

        String genere = gui.getTfGenere().getText().trim();
        if(!genere.isEmpty())
            f = new FiltroGenere(f, genere);

        String valutazione = gui.getTfValutazione().getText().trim();
        if(!valutazione.isEmpty()){
            try {
                f = new FiltroValutazione(f, Integer.parseInt(valutazione));
            }catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(gui.getMainPanel(), "Valutazione non valida");
            }
        }
        if(!gui.getCbStatoLettura().getSelectedItem().toString().equals("NON_SPECIFICATO")){
        Stato stato = (Stato) gui.getCbStatoLettura().getSelectedItem();
        f = new FiltroStato(f, stato);
        }

        catalogoDaMostrare = gestore.filtraCatalogo(f);
        aggiornaTabella();

    }
/*
    private void aggiungiLibro() {
        try {
            Libro l = new Libro(
                    gui.getTfCodice().getText(),
                    gui.getTfTitolo().getText(),
                    gui.getTfAutore().getText(),
                    gui.getTfGenere().getText(),
                    Integer.parseInt(gui.getTfValutazione().getText()),
                    Libro.Stato.valueOf(gui.getTfStatoLettura().getText().toUpperCase())
            );

            boolean successo = gestore.aggiungiLibro(l);
            if (successo) {
                JOptionPane.showMessageDialog(null, "Libro aggiunto al catalogo.");
                catalogoDaMostrare.add(l);
                aggiornaTabella();
            } else {
                JOptionPane.showMessageDialog(null, "Libro con lo stesso ISBN già presente.");
            }

        } catch (Exception ex) {
            mostraErrore("Errore nei dati inseriti");
        }
    }*/

    private void salvaCatalogo() {
        gestore.salvaCatalogo();
        JOptionPane.showMessageDialog(null, "Catalogo salvato nel database.");
    }

    private void aggiornaTabella() {
        String[] colonne = {"ISBN", "Titolo", "Autore", "Genere", "Valutazione", "Stato"};

        DefaultTableModel model = new DefaultTableModel(colonne, 0){
            //Non permetto la modifica dell'ISBN
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 0;
            }
        };


        for (Libro l : catalogoDaMostrare) {
            model.addRow(new Object[]{
                    l.getIsbn(),
                    l.getTitolo(),
                    l.getAutore(),
                    l.getGenere(),
                    l.getValutazione(),
                    l.getStato().toString()
            });
        }
        aggiungiListenerAlModello(model);
        gui.getCatalogo().setModel(model);
    }

    private void aggiungiListenerAlModello(DefaultTableModel model) {
            model.addTableModelListener(e -> {
                if (e.getType() != TableModelEvent.UPDATE) return;

                int viewRow = e.getFirstRow();
                int column = e.getColumn();
                JTable table = gui.getCatalogo();
                int modelRow = table.convertRowIndexToModel(viewRow);

                if (modelRow < 0 || modelRow >= catalogoDaMostrare.size()) return;

                Object newValue = table.getValueAt(viewRow, column);
                Libro libro = catalogoDaMostrare.get(modelRow);

                switch (column) {
                    case 1 -> libro.setTitolo(newValue.toString());
                    case 2 -> libro.setAutore(newValue.toString());
                    case 3 -> libro.setGenere(newValue.toString());
                    case 4 -> {
                        try {
                            libro.setValutazione(Integer.parseInt(newValue.toString()));
                        } catch (NumberFormatException ex) {
                            mostraErrore("Valutazione non valida");
                            aggiornaTabella(); // ripristina i dati
                            return;
                        }
                    }
                    case 5 -> {
                        try {
                            libro.setStato(Stato.valueOf(newValue.toString().toUpperCase()));
                        } catch (IllegalArgumentException ex) {
                            mostraErrore("Stato non valido");
                            aggiornaTabella(); // ripristina i dati
                            return;
                        }
                    }
                }

                gestore.modificaLibro(libro); // salva nel database
            });
    }

    private void setUpMouseListener() {
        gui.getCatalogo().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostraPopup(e);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostraPopup(e);
                }
            }
            private void mostraPopup(java.awt.event.MouseEvent e) {
                int row = gui.getCatalogo().rowAtPoint(e.getPoint());
                if (row >= 0) {
                    gui.getCatalogo().setRowSelectionInterval(row, row);
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem elimina = new JMenuItem("Rimuovi libro");
                    elimina.addActionListener(ae -> {
                        int modelRow = gui.getCatalogo().convertRowIndexToModel(row);
                        Libro libroDaRimuovere = catalogoDaMostrare.get(modelRow);

                        int conferma = JOptionPane.showConfirmDialog(gui.getMainPanel(),
                                "Vuoi rimuovere il libro: " + libroDaRimuovere.getIsbn() + "?",
                                "Conferma rimozione", JOptionPane.YES_NO_OPTION);

                        if (conferma == JOptionPane.YES_OPTION) {
                            gestore.rimuoviLibro(libroDaRimuovere);
                            catalogoDaMostrare.remove(libroDaRimuovere);
                            aggiornaTabella();
                        }
                    });
                    popup.add(elimina);
                    popup.show(gui.getCatalogo(), e.getX(), e.getY());
                }
            }
        });
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(gui.getMainPanel(), messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

}
