package view;

import database.dao.DirecaoDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;

public class TelaReplicacaoDirecaoView extends JFrame {

    private enum ModoTela { NENHUM, INSERT, UPDATE }
    private ModoTela modo = ModoTela.NENHUM;

    private final Connection conn;
    private final DirecaoDAO daoDirecao;
    private final ReplicacaoProcessoDAO daoProcesso;

    private JTextField txtId;
    private JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;

    private JTextField txtDirecaoOrigem;
    private JTextField txtUsuarioOrigem;
    private JPasswordField txtSenhaOrigem;

    private JTextField txtDirecaoDestino;
    private JTextField txtUsuarioDestino;
    private JPasswordField txtSenhaDestino;

    private JCheckBox chkHabilitado;

    private JButton btnBuscar;
    private JButton btnAdicionar;
    private JButton btnSalvar;
    private JButton btnExcluir;

    public TelaReplicacaoDirecaoView(Connection conn) throws Exception {

        this.conn = conn;
        this.daoDirecao = new DirecaoDAO(conn);
        this.daoProcesso = new ReplicacaoProcessoDAO(conn);

        setTitle("TB_REPLICACAO_DIRECAO");
        setSize(760, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // BOTÕES
        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setBounds(10, 10, 170, 30);
        panel.add(btnBuscar);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setBounds(190, 10, 170, 30);
        panel.add(btnAdicionar);

        btnSalvar = new JButton("SALVAR");
        btnSalvar.setBounds(370, 10, 170, 30);
        panel.add(btnSalvar);

        btnExcluir = new JButton("EXCLUIR");
        btnExcluir.setBounds(550, 10, 170, 30);
        panel.add(btnExcluir);

        // ID
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 140, 25);
        panel.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(160, 70, 220, 25);
        panel.add(txtId);

        // PROCESSO (COMBO)
        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 140, 25);
        panel.add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(160, 105, 320, 25);
        panel.add(cbProcesso);

        // HABILITADO
        JLabel lblHabilitado = new JLabel("HABILITADO:");
        lblHabilitado.setBounds(500, 105, 120, 25);
        panel.add(lblHabilitado);

        chkHabilitado = new JCheckBox("Sim");
        chkHabilitado.setBounds(620, 105, 80, 25);
        panel.add(chkHabilitado);

        // ORIGEM
        JLabel lblOrigemTitulo = new JLabel("ORIGEM");
        lblOrigemTitulo.setBounds(10, 150, 200, 25);
        lblOrigemTitulo.setFont(lblOrigemTitulo.getFont().deriveFont(java.awt.Font.BOLD));
        panel.add(lblOrigemTitulo);

        JLabel lblDirecaoOrigem = new JLabel("DIRECAO_ORIGEM:");
        lblDirecaoOrigem.setBounds(10, 185, 140, 25);
        panel.add(lblDirecaoOrigem);

        txtDirecaoOrigem = new JTextField();
        txtDirecaoOrigem.setBounds(160, 185, 560, 25);
        panel.add(txtDirecaoOrigem);

        JLabel lblUsuarioOrigem = new JLabel("USUARIO_ORIGEM:");
        lblUsuarioOrigem.setBounds(10, 220, 140, 25);
        panel.add(lblUsuarioOrigem);

        txtUsuarioOrigem = new JTextField();
        txtUsuarioOrigem.setBounds(160, 220, 560, 25);
        panel.add(txtUsuarioOrigem);

        JLabel lblSenhaOrigem = new JLabel("SENHA_ORIGEM:");
        lblSenhaOrigem.setBounds(10, 255, 140, 25);
        panel.add(lblSenhaOrigem);

        txtSenhaOrigem = new JPasswordField();
        txtSenhaOrigem.setBounds(160, 255, 560, 25);
        panel.add(txtSenhaOrigem);

        // DESTINO
        JLabel lblDestinoTitulo = new JLabel("DESTINO");
        lblDestinoTitulo.setBounds(10, 300, 200, 25);
        lblDestinoTitulo.setFont(lblDestinoTitulo.getFont().deriveFont(java.awt.Font.BOLD));
        panel.add(lblDestinoTitulo);

        JLabel lblDirecaoDestino = new JLabel("DIRECAO_DESTINO:");
        lblDirecaoDestino.setBounds(10, 335, 140, 25);
        panel.add(lblDirecaoDestino);

        txtDirecaoDestino = new JTextField();
        txtDirecaoDestino.setBounds(160, 335, 560, 25);
        panel.add(txtDirecaoDestino);

        JLabel lblUsuarioDestino = new JLabel("USUARIO_DESTINO:");
        lblUsuarioDestino.setBounds(10, 370, 140, 25);
        panel.add(lblUsuarioDestino);

        txtUsuarioDestino = new JTextField();
        txtUsuarioDestino.setBounds(160, 370, 280, 25);
        panel.add(txtUsuarioDestino);

        JLabel lblSenhaDestino = new JLabel("SENHA_DESTINO:");
        lblSenhaDestino.setBounds(450, 370, 120, 25);
        panel.add(lblSenhaDestino);

        txtSenhaDestino = new JPasswordField();
        txtSenhaDestino.setBounds(570, 370, 150, 25);
        panel.add(txtSenhaDestino);

        // CARREGA COMBO PROCESSOS
        cbProcesso.removeAllItems();
        ArrayList<TB_REPLICACAO_PROCESSO> processos = daoProcesso.selectAll();
        for (TB_REPLICACAO_PROCESSO p : processos) {
            cbProcesso.addItem(p);
        }

        // ESTADO INICIAL
        txtId.setEnabled(false); // BIGSERIAL
        cbProcesso.setEnabled(false);
        chkHabilitado.setEnabled(false);

        txtDirecaoOrigem.setEnabled(false);
        txtUsuarioOrigem.setEnabled(false);
        txtSenhaOrigem.setEnabled(false);

        txtDirecaoDestino.setEnabled(false);
        txtUsuarioDestino.setEnabled(false);
        txtSenhaDestino.setEnabled(false);

        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        // =========================
        // AÇÕES
        // =========================

        btnAdicionar.addActionListener(e -> {
            modo = ModoTela.INSERT;

            txtId.setText("");
            if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);

            chkHabilitado.setSelected(true);

            txtDirecaoOrigem.setText("");
            txtUsuarioOrigem.setText("");
            txtSenhaOrigem.setText("");

            txtDirecaoDestino.setText("");
            txtUsuarioDestino.setText("");
            txtSenhaDestino.setText("");

            cbProcesso.setEnabled(true);
            chkHabilitado.setEnabled(true);

            txtDirecaoOrigem.setEnabled(true);
            txtUsuarioOrigem.setEnabled(true);
            txtSenhaOrigem.setEnabled(true);

            txtDirecaoDestino.setEnabled(true);
            txtUsuarioDestino.setEnabled(true);
            txtSenhaDestino.setEnabled(true);

            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(false);
        });

        btnSalvar.addActionListener(e -> {
            try {
                if (cbProcesso.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um PROCESSO.");
                    return;
                }
                if (txtDirecaoOrigem.getText().trim().isEmpty() || txtDirecaoDestino.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe DIRECAO_ORIGEM e DIRECAO_DESTINO.");
                    return;
                }

                TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

                TB_REPLICACAO_DIRECAO d = new TB_REPLICACAO_DIRECAO();
                d.setProcesso_id(pSel.getId());
                d.setHabilitado(chkHabilitado.isSelected());

                d.setDirecao_origem(txtDirecaoOrigem.getText().trim());
                d.setUsuario_origem(txtUsuarioOrigem.getText().trim());
                d.setSenha_origem(new String(txtSenhaOrigem.getPassword()));

                d.setDirecao_destino(txtDirecaoDestino.getText().trim());
                d.setUsuario_destino(txtUsuarioDestino.getText().trim());
                d.setSenha_destino(new String(txtSenhaDestino.getPassword()));

                if (modo == ModoTela.INSERT) {
                    daoDirecao.insert(d);
                    JOptionPane.showMessageDialog(this, "Inserido com sucesso.");
                } else if (modo == ModoTela.UPDATE) {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ID não carregado para update.");
                        return;
                    }
                    d.setId(Long.parseLong(txtId.getText().trim()));
                    daoDirecao.update(d);
                    JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                // trava após salvar
                modo = ModoTela.NENHUM;

                cbProcesso.setEnabled(false);
                chkHabilitado.setEnabled(false);

                txtDirecaoOrigem.setEnabled(false);
                txtUsuarioOrigem.setEnabled(false);
                txtSenhaOrigem.setEnabled(false);

                txtDirecaoDestino.setEnabled(false);
                txtUsuarioDestino.setEnabled(false);
                txtSenhaDestino.setEnabled(false);

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
                daoDirecao.delete(id);

                JOptionPane.showMessageDialog(this, "Excluído com sucesso.");

                modo = ModoTela.NENHUM;

                txtId.setText("");
                if (cbProcesso.getItemCount() > 0) cbProcesso.setSelectedIndex(0);
                chkHabilitado.setSelected(false);

                txtDirecaoOrigem.setText("");
                txtUsuarioOrigem.setText("");
                txtSenhaOrigem.setText("");

                txtDirecaoDestino.setText("");
                txtUsuarioDestino.setText("");
                txtSenhaDestino.setText("");

                cbProcesso.setEnabled(false);
                chkHabilitado.setEnabled(false);

                txtDirecaoOrigem.setEnabled(false);
                txtUsuarioOrigem.setEnabled(false);
                txtSenhaOrigem.setEnabled(false);

                txtDirecaoDestino.setEnabled(false);
                txtUsuarioDestino.setEnabled(false);
                txtSenhaDestino.setEnabled(false);

                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                ConsultaDirecaoDialog dlg = new ConsultaDirecaoDialog(this, daoDirecao);
                dlg.setVisible(true);

                TB_REPLICACAO_DIRECAO sel = dlg.getSelecionado();
                if (sel == null) return;

                modo = ModoTela.UPDATE;

                txtId.setText(String.valueOf(sel.getId()));
                chkHabilitado.setSelected(sel.isHabilitado());

                txtDirecaoOrigem.setText(sel.getDirecao_origem());
                txtUsuarioOrigem.setText(sel.getUsuario_origem());
                txtSenhaOrigem.setText(sel.getSenha_origem());

                txtDirecaoDestino.setText(sel.getDirecao_destino());
                txtUsuarioDestino.setText(sel.getUsuario_destino());
                txtSenhaDestino.setText(sel.getSenha_destino());

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
                chkHabilitado.setEnabled(true);

                txtDirecaoOrigem.setEnabled(true);
                txtUsuarioOrigem.setEnabled(true);
                txtSenhaOrigem.setEnabled(true);

                txtDirecaoDestino.setEnabled(true);
                txtUsuarioDestino.setEnabled(true);
                txtSenhaDestino.setEnabled(true);

                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
            }
        });
    }
}
