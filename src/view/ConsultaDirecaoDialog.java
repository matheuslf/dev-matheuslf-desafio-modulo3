package view;

import database.dao.DirecaoDAO;
import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaDirecaoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TB_REPLICACAO_DIRECAO selecionado;

    public ConsultaDirecaoDialog(Frame parent, DirecaoDAO dao) throws Exception {
        super(parent, "Consulta - Direções", true);
        setSize(900, 420);
        setLocationRelativeTo(parent);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO_ID");
        model.addColumn("ORIGEM");
        model.addColumn("DESTINO");
        model.addColumn("HABILITADO");

        ArrayList<TB_REPLICACAO_DIRECAO> lista = dao.selectAll();
        for (TB_REPLICACAO_DIRECAO d : lista) {
            model.addRow(new Object[]{
                    d.getId(),
                    d.getProcesso_id(),
                    d.getDirecao_origem(),
                    d.getDirecao_destino(),
                    d.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 10, 860, 300);
        add(scroll);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10, 320, 140, 30);
        add(btnSelecionar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(160, 320, 140, 30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            selecionado = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha.");
                return;
            }

            long id = Long.parseLong(table.getValueAt(row, 0).toString());
            TB_REPLICACAO_DIRECAO d = null; // pega completo (com usuários/senhas)
            try {
                d = dao.selectById(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            selecionado = d;
            dispose();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnSelecionar.doClick();
                }
            }
        });
    }

    public TB_REPLICACAO_DIRECAO getSelecionado() {
        return selecionado;
    }
}
