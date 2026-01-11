package view;

import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaReplicacaoProcessoView extends JFrame {

    private enum ModoTela { NENHUM, INSERT, UPDATE }
    private ModoTela modo = ModoTela.NENHUM;

    private final Connection conn;
    private final ReplicacaoProcessoDAO dao;

    private JTextField txtId;
    private JTextField txtProcesso;
    private JTextField txtDescricao;
    private JCheckBox chkHabilitado;

    private JButton btnBuscar;
    private JButton btnAdicionar;
    private JButton btnSalvar;
    private JButton btnExcluir;

    public TelaReplicacaoProcessoView(Connection conn) throws SQLException {

        this.conn = conn;
        this.dao = new ReplicacaoProcessoDAO(conn);

        setTitle("TB_REPLICACAO_PROCESSO");
        setSize(620, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // Botões topo
        btnBuscar = new JButton("BUSCAR");
        btnAdicionar = new JButton("ADICIONAR");
        btnSalvar = new JButton("SALVAR");
        btnExcluir = new JButton("EXCLUIR");

        btnBuscar.setBounds(10, 10, 130, 30);
        btnAdicionar.setBounds(150, 10, 130, 30);
        btnSalvar.setBounds(290, 10, 130, 30);
        btnExcluir.setBounds(430, 10, 130, 30);

        panel.add(btnBuscar);
        panel.add(btnAdicionar);
        panel.add(btnSalvar);
        panel.add(btnExcluir);

        // Campos
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 120, 25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(140, 70, 200, 25);
        panel.add(txtId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 120, 25);
        panel.add(lblProcesso);

        txtProcesso = new JTextField();
        txtProcesso.setBounds(140, 105, 420, 25);
        panel.add(txtProcesso);

        JLabel lblDescricao = new JLabel("DESCRIÇÃO:");
        lblDescricao.setBounds(10, 140, 120, 25);
        panel.add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(140, 140, 420, 25);
        panel.add(txtDescricao);

        JLabel lblHabilitado = new JLabel("HABILITADO:");
        lblHabilitado.setBounds(10, 175, 120, 25);
        panel.add(lblHabilitado);

        chkHabilitado = new JCheckBox("Sim");
        chkHabilitado.setBounds(140, 175, 80, 25);
        panel.add(chkHabilitado);

        // ESTADO INICIAL
        txtId.setEnabled(false); // ID sempre bloqueado (normalmente PK/serial)
        txtProcesso.setEnabled(false);
        txtDescricao.setEnabled(false);
        chkHabilitado.setEnabled(false);

        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        // AÇÕES
        btnAdicionar.addActionListener(e -> {
            modo = ModoTela.INSERT;

            txtId.setText("");
            txtProcesso.setText("");
            txtDescricao.setText("");
            chkHabilitado.setSelected(true);

            txtProcesso.setEnabled(true);
            txtDescricao.setEnabled(true);
            chkHabilitado.setEnabled(true);

            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(false);
        });

        btnSalvar.addActionListener(e -> {
            try {
                // validação mínima
                if (txtProcesso.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO.");
                    return;
                }

                TB_REPLICACAO_PROCESSO p = new TB_REPLICACAO_PROCESSO();
                p.setProcesso(txtProcesso.getText().trim());
                p.setDescricao(txtDescricao.getText().trim());
                p.setHabilitado(chkHabilitado.isSelected());

                if (modo == ModoTela.INSERT) {
                    dao.insert(p);
                    JOptionPane.showMessageDialog(this, "Inserido com sucesso.");
                } else if (modo == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                        return;
                    }
                    p.setId(Long.parseLong(txtId.getText().trim()));
                    dao.update(p);
                    JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                // após salvar, trava campos
                modo = ModoTela.NENHUM;
                txtProcesso.setEnabled(false);
                txtDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);
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
                dao.delete(id);

                JOptionPane.showMessageDialog(this, "Excluído com sucesso.");

                // limpa e trava
                modo = ModoTela.NENHUM;
                txtId.setText("");
                txtProcesso.setText("");
                txtDescricao.setText("");
                chkHabilitado.setSelected(false);

                txtProcesso.setEnabled(false);
                txtDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);

                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                ConsultaProcessoDialog dlg = new ConsultaProcessoDialog(this, dao);
                dlg.setVisible(true);

                TB_REPLICACAO_PROCESSO selecionado = dlg.getSelecionado();
                if (selecionado == null) return;

                // carrega na tela e entra em modo UPDATE
                modo = ModoTela.UPDATE;

                txtId.setText(String.valueOf(selecionado.getId()));
                txtProcesso.setText(selecionado.getProcesso());
                txtDescricao.setText(selecionado.getDescricao());
                chkHabilitado.setSelected(selecionado.isHabilitado());

                txtProcesso.setEnabled(true);
                txtDescricao.setEnabled(true);
                chkHabilitado.setEnabled(true);

                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
            }
        });
    }
}