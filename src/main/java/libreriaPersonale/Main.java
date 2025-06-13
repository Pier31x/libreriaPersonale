package libreriaPersonale;

import libreriaPersonale.GUI.libreriaGUI;
import libreriaPersonale.database.LibreriaDAO;
import libreriaPersonale.database.LibreriaDAOImplSQLite;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            libreriaGUI gui = new libreriaGUI();
            JFrame frame = new JFrame("Libreria Personale");
            frame.setContentPane(gui.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centro schermo
            frame.setVisible(true);

            //DAO + Gestore
            LibreriaDAO dao = new LibreriaDAOImplSQLite("jdbc:sqlite:libreria.db");
            GestoreCatalogo gestore = new GestoreCatalogo(dao);

            //Controller
            LibreriaController controller = new LibreriaController(gestore, gui);

            //Salvataggio a chiusura
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    int scelta = JOptionPane.showConfirmDialog(
                            gui.getMainPanel(),
                            "Vuoi salvare prima di uscire?",
                            "Salva catalogo",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );
                    if (scelta == JOptionPane.YES_OPTION) {
                        gestore.salvaCatalogo();
                        System.exit(0);
                    } else if (scelta == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }
            });

            gui.setFrame(frame);
        });
    }
}

