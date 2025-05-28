package libreriaPersonale.GUI;

import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.modello.Stato;

import javax.swing.*;

public class libreriaGUI {
    private JFrame frame;
    private JTable catalogo;
    private JButton btnFiltra;
    private JCheckBox cbInvertiOrdine;
    private JButton btnAdd;
    private JButton btnSalva;
    private JComboBox<EnumComparatori> cbOrder;
    private JTextField tfCodice;
    private JTextField tfStatoLettura;
    private JTextField tfValutazione;
    private JTextField tfGenere;
    private JTextField tfAutore;
    private JTextField tfTitolo;
    private JPanel mainPanel;
    private JLabel labelOrdina;
    private JTextArea taTestoModifiche;
    private JComboBox cbStatoLettura;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    //TODO
    public libreriaGUI() {
        // Creo un array o lista di oggetti da mettere nella combo:
        // Prima la voce speciale "NON_SPECIFICATO" (può essere una stringa o un oggetto custom)
        // poi tutti gli enum Stato.values()

        Object[] statiConDefault = new Object[Stato.values().length + 1];
        statiConDefault[0] = "NON_SPECIFICATO";  // voce speciale non presente nell'enum
        System.arraycopy(Stato.values(), 0, statiConDefault, 1, Stato.values().length);

        // Setto il modello del JComboBox usando questa lista mista
        cbStatoLettura.setModel(new DefaultComboBoxModel<>(statiConDefault));

        // Similmente per EnumComparatori se vuoi (se ti serve)
        cbOrder.setModel(new DefaultComboBoxModel<>(EnumComparatori.values()));
    }

    public JTable getCatalogo() {
        return catalogo;
    }

    public JButton getBtnFiltra() {
        return btnFiltra;
    }

    public JCheckBox getCbInvertiOrdine() {
        return cbInvertiOrdine;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnSalva() {
        return btnSalva;
    }

    public JComboBox getCbOrder() {
        return cbOrder;
    }

    public JComboBox getCbStatoLettura() {
        return cbStatoLettura;
    }

    public JTextField getTfCodice() {
        return tfCodice;
    }

    public JTextField getTfValutazione() {
        return tfValutazione;
    }

    public JTextField getTfGenere() {
        return tfGenere;
    }

    public JTextField getTfAutore() {
        return tfAutore;
    }

    public JTextField getTfTitolo() {
        return tfTitolo;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JLabel getLabelOrdina() {
        return labelOrdina;
    }

}
