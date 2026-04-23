package gui;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TelaCadastro extends JFrame {

    private JTextField txtNome, txtCpf, txtData, txtTelefone;
    private JTextField txtEndereco, txtBairro, txtCidade, txtEstado, txtCep;

    public TelaCadastro() {
        setTitle("Cadastro de Cliente");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 4, 4, 4);

        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        JLabel titulo = new JLabel("Cadastro de Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(titulo, g);
        g.gridwidth = 1;

        txtNome      = addCampo(painel, g, "Nome:",                   1);
        txtCpf       = addCampo(painel, g, "CPF:",                    2);
        txtData      = addCampo(painel, g, "Data Nasc. (AAAA-MM-DD):",3);
        txtTelefone  = addCampo(painel, g, "Telefone:",               4);
        txtEndereco  = addCampo(painel, g, "Endereço:",               5);
        txtBairro    = addCampo(painel, g, "Bairro:",                 6);
        txtCidade    = addCampo(painel, g, "Cidade:",                 7);
        txtEstado    = addCampo(painel, g, "Estado (UF):",            8);
        txtCep       = addCampo(painel, g, "CEP:",                    9);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar    = new JButton("Limpar");
        JButton btnConsulta  = new JButton("Consultar");
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnConsulta);

        g.gridx = 0; g.gridy = 10; g.gridwidth = 2;
        painel.add(painelBotoes, g);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());
        btnConsulta.addActionListener(e -> {
            new TelaConsulta().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JTextField addCampo(JPanel p, GridBagConstraints g, String label, int linha) {
        g.gridx = 0; g.gridy = linha; g.weightx = 0.3;
        p.add(new JLabel(label), g);
        JTextField campo = new JTextField();
        g.gridx = 1; g.weightx = 0.7;
        p.add(campo, g);
        return campo;
    }

    private void cadastrar() {
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Cliente c = new Cliente();
            c.setNome(txtNome.getText().trim());
            c.setCpf(txtCpf.getText().trim());
            c.setDataNascimento(LocalDate.parse(txtData.getText().trim()));
            c.setTelefone(txtTelefone.getText().trim());
            c.setEndereco(txtEndereco.getText().trim());
            c.setBairro(txtBairro.getText().trim());
            c.setCidade(txtCidade.getText().trim());
            c.setEstado(txtEstado.getText().trim());
            c.setCep(txtCep.getText().trim());

            new ClienteDAO().cadastrar(c);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            limpar();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use o formato AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpar() {
        txtNome.setText(""); txtCpf.setText(""); txtData.setText("");
        txtTelefone.setText(""); txtEndereco.setText(""); txtBairro.setText("");
        txtCidade.setText(""); txtEstado.setText(""); txtCep.setText("");
        txtNome.requestFocus();
    }
}
