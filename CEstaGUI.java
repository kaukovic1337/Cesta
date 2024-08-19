import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CestaGUI extends JFrame {
    private CestaTableModel tableModel;
    private JTextField imeField;
    private JTextField dolzinaField;
    private JTextField stevilkaField;
    private JTextField dodatneField;
    private JComboBox<String> vrstaComboBox;
    private JCheckBox asfaltiranaCheckBox;
    private JTextField maxHitrostField;
    private JTable table;

    public CestaGUI() {
        setTitle("Seznam Cest");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabela za prikaz cest
        tableModel = new CestaTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel za vnose
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("Ime:"));
        imeField = new JTextField();
        panel.add(imeField);

        panel.add(new JLabel("Dolžina (km):"));
        dolzinaField = new JTextField();
        panel.add(dolzinaField);

        panel.add(new JLabel("Številka:"));
        stevilkaField = new JTextField();
        panel.add(stevilkaField);

        panel.add(new JLabel("Vrsta:"));
        vrstaComboBox = new JComboBox<>(new String[]{"Avtocesta", "Kmečka cesta"});
        panel.add(vrstaComboBox);

        panel.add(new JLabel("Dodatne Informacije:"));
        dodatneField = new JTextField();
        panel.add(dodatneField);

        maxHitrostField = new JTextField();
        panel.add(new JLabel("Max Hitrost (le za avtoceste):"));
        panel.add(maxHitrostField);
        
        asfaltiranaCheckBox = new JCheckBox("Asfaltirana (le za kmečke ceste)");
        panel.add(asfaltiranaCheckBox);

        JButton dodajButton = new JButton("Dodaj Cesto");
        panel.add(dodajButton);

        JButton posodobiButton = new JButton("Posodobi Cesto");
        panel.add(posodobiButton);

        add(panel, BorderLayout.SOUTH);

        // Akcija za gumb dodaj
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajCesto();
            }
        });

        // Akcija za gumb posodobi
        posodobiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                posodobiCesto();
            }
        });

        // Dogodek klik na tabelo za izbiro ceste
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        prikaziIzbrano(row);
                    }
                }
            }
        });

        setVisible(true);
    }

    private void dodajCesto() {
        try {
            String ime = imeField.getText();
            double dolzina = Double.parseDouble(dolzinaField.getText());
            int stevilka = Integer.parseInt(stevilkaField.getText());
            String vrsta = (String) vrstaComboBox.getSelectedItem();

            if (ime.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ime ceste ne sme biti prazno!", "Napaka", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cesta cesta = null;
            if (vrsta.equals("Avtocesta")) {
                int maxHitrost = Integer.parseInt(maxHitrostField.getText());
                cesta = new Avtocesta(ime, dolzina, stevilka, maxHitrost);
            } else if (vrsta.equals("Kmečka cesta")) {
                boolean asfaltirana = asfaltiranaCheckBox.isSelected();
                cesta = new KmeckaCesta(ime, dolzina, stevilka, asfaltirana);
            }

            if (cesta != null) {
                tableModel.addCesta(cesta);
                imeField.setText("");
                dolzinaField.setText("");
                stevilkaField.setText("");
                dodatneField.setText("");
                maxHitrostField.setText("");
                asfaltiranaCheckBox.setSelected(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prosim, vnesite veljavne številke!", "Napaka", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void posodobiCesto() {
        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {
            try {
                String ime = imeField.getText();
                double dolzina = Double.parseDouble(dolzinaField.getText());
                int stevilka = Integer.parseInt(stevilkaField.getText());
                String vrsta = (String) vrstaComboBox.getSelectedItem();

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ime ceste ne sme biti prazno!", "Napaka", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cesta cesta = null;
                if (vrsta.equals("Avtocesta")) {
                    int maxHitrost = Integer.parseInt(maxHitrostField.getText());
                    cesta = new Avtocesta(ime, dolzina, stevilka, maxHitrost);
                } else if (vrsta.equals("Kmečka cesta")) {
                    boolean asfaltirana = asfaltiranaCheckBox.isSelected();
                    cesta = new KmeckaCesta(ime, dolzina, stevilka, asfaltirana);
                }

                if (cesta != null) {
                    tableModel.updateCesta(rowIndex, cesta);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Prosim, vnesite veljavne številke!", "Napaka", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Prosim, izberite cesto iz tabele!", "Napaka", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prikaziIzbrano(int rowIndex) {
        imeField.setText((String) tableModel.getValueAt(rowIndex, 0));
        dolzinaField.setText(tableModel.getValueAt(rowIndex, 1).toString());
        stevilkaField.setText(tableModel.getValueAt(rowIndex, 2).toString());
        vrstaComboBox.setSelectedItem(tableModel.getValueAt(rowIndex, 3).toString());

        String dodatneInfo = (String) tableModel.getValueAt(rowIndex, 4);
        if (dodatneInfo.startsWith("Max Hitrost:")) {
            maxHitrostField.setText(dodatneInfo.replace("Max Hitrost: ", ""));
            asfaltiranaCheckBox.setSelected(false);
        } else if (dodatneInfo.startsWith("Asfaltirana:")) {
            asfaltiranaCheckBox.setSelected(dodatneInfo.contains("true"));
            maxHitrostField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CestaGUI::new);
    }
}
