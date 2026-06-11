package gui;

import dao.RacaDAO;
import modelo.Raca;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroRaca extends JFrame {

    private JTextField txtNome;
    private JComboBox<String> cbTipo;

    public TelaCadastroRaca() {

        setTitle("Cadastro de Raça");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Nome da Raça:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Tipo do Animal:"));
        cbTipo = new JComboBox<>(new String[]{
                "Cachorro",
                "Gato"
        });
        painel.add(cbTipo);

        JButton btnSalvar = new JButton("Salvar");
        painel.add(btnSalvar);

        add(painel);

        btnSalvar.addActionListener(e -> salvar());

        setVisible(true);
    }

    private void salvar() {

        try {

            Raca r = new Raca();

            r.setNomeRaca(txtNome.getText());
            r.setTipoAnimal(cbTipo.getSelectedItem().toString());
            r.setStatus(true);

            new RacaDAO().cadastrar(r);

            JOptionPane.showMessageDialog(this,
                    "Raça cadastrada com sucesso!");

            txtNome.setText("");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
    }
}