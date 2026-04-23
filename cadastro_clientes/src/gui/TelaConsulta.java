package gui;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaConsulta extends JFrame {

    private JTextField txtBusca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaConsulta() {
        setTitle("Consulta de Clientes");
        setSize(750, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Busca"));
        painelBusca.add(new JLabel("Nome ou CPF:"));
        txtBusca = new JTextField(25);
        painelBusca.add(txtBusca);
        JButton btnBuscar  = new JButton("Buscar");
        JButton btnLimpar  = new JButton("Limpar");
        JButton btnNovo    = new JButton("Novo Cadastro");
        painelBusca.add(btnBuscar);
        painelBusca.add(btnLimpar);
        painelBusca.add(btnNovo);
        add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Cidade", "Estado"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getColumnModel().getColumn(0).setMaxWidth(50);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton btnAlterar = new JButton("Alterar Selecionado");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.setForeground(Color.RED);
        painelAcoes.add(btnAlterar);
        painelAcoes.add(btnExcluir);
        add(painelAcoes, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscar());
        txtBusca.addActionListener(e -> buscar()); // Enter no campo também busca

        btnLimpar.addActionListener(e -> {
            txtBusca.setText("");
            modeloTabela.setRowCount(0);
            txtBusca.requestFocus();
        });

        btnNovo.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        btnAlterar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) { aviso("Selecione um cliente na tabela!"); return; }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            new TelaAlterar(id, this).setVisible(true);
            setVisible(false);
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) { aviso("Selecione um cliente na tabela!"); return; }

            int id   = (int) modeloTabela.getValueAt(linha, 0);
            String nome = (String) modeloTabela.getValueAt(linha, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirma a exclusão de:\n" + nome + " (ID " + id + ")?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new ClienteDAO().excluir(id);
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                    buscar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public void buscar() {
        try {
            ArrayList<Cliente> lista = new ClienteDAO().consultar(txtBusca.getText().trim());
            modeloTabela.setRowCount(0);
            for (Cliente c : lista) {
                modeloTabela.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(),
                    c.getTelefone(), c.getCidade(), c.getEstado()
                });
            }
            if (lista.isEmpty()) {
                aviso("Nenhum cliente encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.INFORMATION_MESSAGE);
    }
}
