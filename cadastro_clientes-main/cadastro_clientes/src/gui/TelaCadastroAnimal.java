package gui;

import dao.AnimalDAO;
import modelo.Animal;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaCadastroAnimal extends JFrame {

    private JTextField txtNome;
    private JTextField txtData;
    private JTextField txtCor;
    private JTextField txtObs;

    private JComboBox<String> cbSexo;

    private JTextField txtIdCliente;
    private JTextField txtIdRaca;

    public TelaCadastroAnimal() {

        setTitle("Cadastro de Animal");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(8,2,5,5));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("Data Nascimento (AAAA-MM-DD):"));
        txtData = new JTextField();
        painel.add(txtData);

        painel.add(new JLabel("Sexo:"));
        cbSexo = new JComboBox<>(new String[]{"M","F"});
        painel.add(cbSexo);

        painel.add(new JLabel("Cor:"));
        txtCor = new JTextField();
        painel.add(txtCor);

        painel.add(new JLabel("Observações:"));
        txtObs = new JTextField();
        painel.add(txtObs);

        painel.add(new JLabel("ID Cliente:"));
        txtIdCliente = new JTextField();
        painel.add(txtIdCliente);

        painel.add(new JLabel("ID Raça:"));
        txtIdRaca = new JTextField();
        painel.add(txtIdRaca);

        JButton btnSalvar = new JButton("Salvar");
        painel.add(btnSalvar);

        add(painel);

        btnSalvar.addActionListener(e -> salvar());

        setVisible(true);
    }

    private void salvar() {

        try {

            Animal a = new Animal();

            a.setNome(txtNome.getText());

            a.setDataNascimento(
                    LocalDate.parse(txtData.getText())
            );

            a.setSexo(cbSexo.getSelectedItem().toString());

            a.setCor(txtCor.getText());

            a.setObservacoes(txtObs.getText());

            a.setIdCliente(
                    Integer.parseInt(txtIdCliente.getText())
            );

            a.setIdRaca(
                    Integer.parseInt(txtIdRaca.getText())
            );

            a.setStatus(true);

            new AnimalDAO().cadastrar(a);

            JOptionPane.showMessageDialog(this,
                    "Animal cadastrado com sucesso!");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }
    }
}