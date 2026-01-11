package database.dao;

import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessoTabelaDAO {

    private Connection conn;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE PROCESSO_ID = ? AND HABILITADO = TRUE ORDER BY ORDEM";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO_TABELA (PROCESSO_ID, TABELA_ORIGEM, TABELA_DESTINO, ORDEM, HABILITADO, DS_WHERE) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO_TABELA SET PROCESSO_ID = ?, TABELA_ORIGEM = ?, TABELA_DESTINO = ?, ORDEM = ?, HABILITADO = ?, DS_WHERE = ? WHERE ID = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ProcessoTabelaDAO(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    public ArrayList<TB_REPLICACAO_PROCESSO_TABELA> selectByProcessoHabilitado(long processoId) throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public ArrayList<TB_REPLICACAO_PROCESSO_TABELA> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public TB_REPLICACAO_PROCESSO_TABELA selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcesso_id());
        pstInsert.setString(2, tb.getTabela_origem());
        pstInsert.setString(3, tb.getTabela_destino());
        pstInsert.setInt(4, tb.getOrdem());
        pstInsert.setBoolean(5, tb.isHabilitado());
        pstInsert.setString(6, tb.getDs_where());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstUpdate.setLong(1, tb.getProcesso_id());
        pstUpdate.setString(2, tb.getTabela_origem());
        pstUpdate.setString(3, tb.getTabela_destino());
        pstUpdate.setInt(4, tb.getOrdem());
        pstUpdate.setBoolean(5, tb.isHabilitado());
        pstUpdate.setString(6, tb.getDs_where());
        pstUpdate.setLong(7, tb.getId());
        pstUpdate.executeUpdate();
    }

    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_PROCESSO_TABELA map(ResultSet rs) throws SQLException {
        TB_REPLICACAO_PROCESSO_TABELA tb = new TB_REPLICACAO_PROCESSO_TABELA();
        tb.setId(rs.getLong("ID"));
        tb.setProcesso_id(rs.getLong("PROCESSO_ID"));
        tb.setTabela_origem(rs.getString("TABELA_ORIGEM"));
        tb.setTabela_destino(rs.getString("TABELA_DESTINO"));
        tb.setOrdem(rs.getInt("ORDEM"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        tb.setDs_where(rs.getString("DS_WHERE"));
        return tb;
    }

}










