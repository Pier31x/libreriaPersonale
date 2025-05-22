package libreriaPersonale;

import javax.swing.*;

public class libreriaGUI {
    private JTable catalogo;
    private JButton btnFiltra;
    private JButton btnAdd;
    private JComboBox cbOrder;
    private JTextField tfOrder;
    private JTextField tfCodice;
    private JTextField tfStatoLettura;
    private JTextField tfValutazione;
    private JTextField tfGenere;
    private JTextField tfAutore;
    private JTextField tfTitolo;
    private JPanel mainPanel;

    public libreriaGUI() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Libreria");
            frame.setContentPane(new libreriaGUI().mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centra la finestra
            frame.setVisible(true);
        });
    }
}
