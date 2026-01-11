package view;

import database.dao.ProcessoTabelaDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;

public class TelaReplicacaoProcessoTabelaView extends JFrame {

    private enum ModoTela { NENHUM, INSERT, UPDATE }
    private ModoTela modo = ModoTela.NENHUM;

    private final Connection conn;
    private final ProcessoTabelaDAO daoTabela;
    private final ReplicacaoProcessoDAO daoProcesso;

    private JTextField txtId;
    private JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;
    private JTextField txtTabelaOrigem;
    private JTextField txtTabelaDestino;
    private JTextField txtOrdem;
    private JCheckBox chkHabilitado;
    private JTextArea txtWhere;

    private JButton btnBuscar;
    private JButton btnAdicionar;
    private JButton btnSalvar;
    private JButton btnExcluir;

    public TelaReplicacaoProcessoTabelaView(Connection conn) throws Exception {

        this.conn = conn;
        this.daoTabela = new ProcessoTabelaDAO(conn);
        this.daoProcesso = new ReplicacaoProcessoDAO(conn);

        setTitle("TB_REPLICACAO_PROCESSO_TABELA");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // BOTÕES TOPO
        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setBounds(10, 10, 160, 30);
        panel.add(btnBuscar);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setBounds(180, 10, 160, 30);
        panel.add(btnAdicionar);

        btnSalvar = new JButton("SALVAR");
        btnSalvar.setBounds(350, 10, 160, 30);
        panel.add(btnSalvar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setBounds(520, 10, 160, 30);
        panel.add(btnExcluir);

        // ID
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 140, 25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(160, 70, 220, 25);
        panel.add(txtId);

        // PROCESSO
        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 140, 25);
        panel.add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(160, 105, 520, 25);
        panel.add(cbProcesso);

        // TABELA ORIGEM
        JLabel lblTabelaOrigem = new JLabel("TABELA_ORIGEM:");
        lblTabelaOrigem.setBounds(10, 140, 140, 25);
        panel.add(lblTabelaOrigem);

        txtTabelaOrigem = new JTextField();
        txtTabelaOrigem.setBounds(160, 140, 520, 25);
        panel.add(txtTabelaOrigem);

        // TABELA DESTINO
        JLabel lblTabelaDestino = new JLabel("TABELA_DESTINO:");
        lblTabelaDestino.setBounds(10, 175, 140, 25);
        panel.add(lblTabelaDestino);

        txtTabelaDestino = new JTextField();
        txtTabelaDestino.setBounds(160, 175, 520, 25);
        panel.add(txtTabelaDestino);

        // ORDEM
        JLabel lblOrdem = new JLabel("ORDEM:");
        lblOrdem.setBounds(10, 210, 140, 25);
        panel.add(lblOrdem);

        txtOrdem = new JTextField();
        txtOrdem.setBounds(160, 210, 220, 25);
        panel.add(txtOrdem);

        // HABILITADO
        JLabel lblHabilitado = new JLabel("HABILITADO:");
        lblHabilitado.setBounds(10, 245, 140, 25);
        panel.add(lblHabilitado);

        chkHabilitado = new JCheckBox("Sim");
        chkHabilitado.setBounds(160, 245, 80, 25);
        panel.add(chkHabilitado);

        // WHERE
        JLabel lblWhere = new JLabel("DS_WHERE:");
        lblWhere.setBounds(10, 280, 140, 25);
        panel.add(lblWhere);

        txtWhere = new JTextArea();
        JScrollPane scrollWhere = new JScrollPane(txtWhere);
        scrollWhere.setBounds(160, 280, 520, 80);
        panel.add(scrollWhere);

        // CARREGA COMBO PROCESSOS
        cbProcesso.removeAllItems();
        ArrayList<TB_REPLICACAO_PROCESSO> processos = daoProcesso.selectAll();
        for (TB_REPLICACAO_PROCESSO p : processos) {
            cbProcesso.addItem(p);
        }

        // ESTADO INICIAL
        txtId.setEnabled(false); // BIGSERIAL
        cbProcesso.setEnabled(false);
        txtTabelaOrigem.setEnabled(false);
        txtTabelaDestino.setEnabled(false);
        txtOrdem.setEnabled(false);
        chkHabilitado.setEnabled(false);
        txtWhere.setEnabled(false);

        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        // =========================
        // AÇÕES
        // =========================

        btnAdicionar.addActionListener(e -> {
            modo = ModoTela.INSERT;

            txtId.setText("");
            if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);

            txtTabelaOrigem.setText("");
            txtTabelaDestino.setText("");
            txtOrdem.setText("");
            chkHabilitado.setSelected(true);
            txtWhere.setText("");

            cbProcesso.setEnabled(true);
            txtTabelaOrigem.setEnabled(true);
            txtTabelaDestino.setEnabled(true);
            txtOrdem.setEnabled(true);
            chkHabilitado.setEnabled(true);
            txtWhere.setEnabled(true);

            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(false);
        });

        btnSalvar.addActionListener(e -> {
            try {
                if (cbProcesso.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um PROCESSO.");
                    return;
                }
                if (txtTabelaOrigem.getText().trim().isEmpty() || txtTabelaDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe TABELA_ORIGEM e TABELA_DESTINO.");
                    return;
                }
                if (txtOrdem.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe ORDEM.");
                    return;
                }

                int ordem;
                try {
                    ordem = Integer.parseInt(txtOrdem.getText().trim());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "ORDEM deve ser número.");
                    return;
                }

                TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

                TB_REPLICACAO_PROCESSO_TABELA t = new TB_REPLICACAO_PROCESSO_TABELA();
                t.setProcesso_id(pSel.getId());
                t.setTabela_origem(txtTabelaOrigem.getText().trim());
                t.setTabela_destino(txtTabelaDestino.getText().trim());
                t.setOrdem(ordem);
                t.setHabilitado(chkHabilitado.isSelected());
                t.setDs_where(txtWhere.getText());

                if (modo == ModoTela.INSERT) {
                    daoTabela.insert(t);
                    JOptionPane.showMessageDialog(this, "Inserido com sucesso.");
                } else if (modo == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                        return;
                    }
                    t.setId(Long.parseLong(txtId.getText().trim()));
                    daoTabela.update(t);
                    JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                // trava após salvar
                modo = ModoTela.NENHUM;

                cbProcesso.setEnabled(false);
                txtTabelaOrigem.setEnabled(false);
                txtTabelaDestino.setEnabled(false);
                txtOrdem.setEnabled(false);
                chkHabilitado.setEnabled(false);
                txtWhere.setEnabled(false);

                btnSalvar.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum registro carregado para excluir.");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Confirma exclusão?", "Excluir",
                        JOptionPane.YES_NO_OPTION);

                if (op != JOptionPane.YES_OPTION) return;

                long id = Long.parseLong(txtId.getText().trim());
                daoTabela.delete(id);

                JOptionPane.showMessageDialog(this, "Excluído com sucesso.");

                modo = ModoTela.NENHUM;

                txtId.setText("");
                if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);

                txtTabelaOrigem.setText("");
                txtTabelaDestino.setText("");
                txtOrdem.setText("");
                chkHabilitado.setSelected(false);
                txtWhere.setText("");

                cbProcesso.setEnabled(false);
                txtTabelaOrigem.setEnabled(false);
                txtTabelaDestino.setEnabled(false);
                txtOrdem.setEnabled(false);
                chkHabilitado.setEnabled(false);
                txtWhere.setEnabled(false);

                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                ConsultaProcessoTabelaDialog dlg = new ConsultaProcessoTabelaDialog(this, daoTabela);
                dlg.setVisible(true);

                TB_REPLICACAO_PROCESSO_TABELA sel = dlg.getSelecionado();
                if (sel == null) return;

                modo = ModoTela.UPDATE;

                txtId.setText(String.valueOf(sel.getId()));
                txtTabelaOrigem.setText(sel.getTabela_origem());
                txtTabelaDestino.setText(sel.getTabela_destino());
                txtOrdem.setText(String.valueOf(sel.getOrdem()));
                chkHabilitado.setSelected(sel.isHabilitado());
                txtWhere.setText(sel.getDs_where());

                // seleciona processo no combo pelo id
                long pid = sel.getProcesso_id();
                for (int i = 0; i < cbProcesso.getItemCount(); i++) {
                    TB_REPLICACAO_PROCESSO item = cbProcesso.getItemAt(i);
                    if (item != null && item.getId() == pid) {
                        cbProcesso.setSelectedIndex(i);
                        break;
                    }
                }

                cbProcesso.setEnabled(true);
                txtTabelaOrigem.setEnabled(true);
                txtTabelaDestino.setEnabled(true);
                txtOrdem.setEnabled(true);
                chkHabilitado.setEnabled(true);
                txtWhere.setEnabled(true);

                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
            }
        });
    }
}
