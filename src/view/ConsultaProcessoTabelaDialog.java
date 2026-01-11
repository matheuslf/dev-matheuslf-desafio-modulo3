package view;

import database.dao.ProcessoTabelaDAO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaProcessoTabelaDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TB_REPLICACAO_PROCESSO_TABELA selecionado;

    public ConsultaProcessoTabelaDialog(Frame parent, ProcessoTabelaDAO dao) throws Exception {
        super(parent, "Consulta - Processo Tabela", true);
        setSize(1000, 420);
        setLocationRelativeTo(parent);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO_ID");
        model.addColumn("TABELA_ORIGEM");
        model.addColumn("TABELA_DESTINO");
        model.addColumn("ORDEM");
        model.addColumn("HABILITADO");

        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = dao.selectAll();
        for (TB_REPLICACAO_PROCESSO_TABELA t : lista) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getProcesso_id(),
                    t.getTabela_origem(),
                    t.getTabela_destino(),
                    t.getOrdem(),
                    t.isHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(10, 10, 960, 300);
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
            TB_REPLICACAO_PROCESSO_TABELA t = null;
            try {
                t = dao.selectById(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            selecionado = t;
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

    public TB_REPLICACAO_PROCESSO_TABELA getSelecionado() {
        return selecionado;
    }
}
