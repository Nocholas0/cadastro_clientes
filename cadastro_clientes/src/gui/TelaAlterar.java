package gui;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TelaAlterar extends JFrame {

    private JTextField txtNome, txtCpf, txtData, txtTelefone;
    private JTextField txtEndereco, txtBairro, txtCidade, txtEstado, txtCep;
    private final int clienteId;
    private final TelaConsulta telaAnterior;

    public TelaAlterar(int id, TelaConsulta telaAnterior) {
        this.clienteId    = id;
        this.telaAnterior = telaAnterior;

        setTitle("Alterar Cliente (ID: " + id + ")");
        setSize(520, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 4, 4, 4);

        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        JLabel titulo = new JLabel("Alterar Dados do Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(titulo, g);
        g.gridwidth = 1;

        txtNome     = addCampo(painel, g, "Nome:",                   1);
        txtCpf      = addCampo(painel, g, "CPF:",                    2);
        txtData     = addCampo(painel, g, "Data Nasc. (AAAA-MM-DD):",3);
        txtTelefone = addCampo(painel, g, "Telefone:",               4);
        txtEndereco = addCampo(painel, g, "Endereço:",               5);
        txtBairro   = addCampo(painel, g, "Bairro:",                 6);
        txtCidade   = addCampo(painel, g, "Cidade:",                 7);
        txtEstado   = addCampo(painel, g, "Estado (UF):",            8);
        txtCep      = addCampo(painel, g, "CEP:",                    9);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnSalvar   = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        g.gridx = 0; g.gridy = 10; g.gridwidth = 2;
        painel.add(painelBotoes, g);

        add(painel);

        carregarDados();

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> voltar());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { voltar(); }
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

    private void carregarDados() {
        try {
            Cliente c = new ClienteDAO().buscarPorId(clienteId);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
                voltar();
                return;
            }
            txtNome.setText(c.getNome());
            txtCpf.setText(c.getCpf());
            txtData.setText(c.getDataNascimento().toString());
            txtTelefone.setText(c.getTelefone());
            txtEndereco.setText(c.getEndereco());
            txtBairro.setText(c.getBairro());
            txtCidade.setText(c.getCidade());
            txtEstado.setText(c.getEstado());
            txtCep.setText(c.getCep());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Cliente c = new Cliente();
            c.setId(clienteId);
            c.setNome(txtNome.getText().trim());
            c.setCpf(txtCpf.getText().trim());
            c.setDataNascimento(LocalDate.parse(txtData.getText().trim()));
            c.setTelefone(txtTelefone.getText().trim());
            c.setEndereco(txtEndereco.getText().trim());
            c.setBairro(txtBairro.getText().trim());
            c.setCidade(txtCidade.getText().trim());
            c.setEstado(txtEstado.getText().trim());
            c.setCep(txtCep.getText().trim());

            new ClienteDAO().alterar(c);
            JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");
            voltar();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use o formato AAAA-MM-DD.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voltar() {
        telaAnterior.setVisible(true);
        telaAnterior.buscar();
        dispose();
    }
}
