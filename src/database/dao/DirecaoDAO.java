package database.dao;

import database.model.TB_REPLICACAO_DIRECAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirecaoDAO {

    private Connection conn;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE PROCESSO_ID = ? AND HABILITADO = TRUE";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_DIRECAO";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_DIRECAO (PROCESSO_ID, DIRECAO_ORIGEM, DIRECAO_DESTINO, USUARIO_ORIGEM, USUARIO_DESTINO, SENHA_ORIGEM, SENHA_DESTINO, HABILITADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_DIRECAO SET PROCESSO_ID = ?, DIRECAO_ORIGEM = ?, DIRECAO_DESTINO = ?, USUARIO_ORIGEM = ?, USUARIO_DESTINO = ?, SENHA_ORIGEM = ?, SENHA_DESTINO = ?, HABILITADO = ? WHERE ID = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_DIRECAO WHERE ID = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public DirecaoDAO(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    public ArrayList<TB_REPLICACAO_DIRECAO> selectByProcessoHabilitado(long processoId) throws SQLException {
        ArrayList<TB_REPLICACAO_DIRECAO> lista = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processoId);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public ArrayList<TB_REPLICACAO_DIRECAO> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_DIRECAO> lista = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public TB_REPLICACAO_DIRECAO selectById(long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try (ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcesso_id());
        pstInsert.setString(2, tb.getDirecao_origem());
        pstInsert.setString(3, tb.getDirecao_destino());
        pstInsert.setString(4, tb.getUsuario_origem());
        pstInsert.setString(5, tb.getUsuario_destino());
        pstInsert.setString(6, tb.getSenha_origem());
        pstInsert.setString(7, tb.getSenha_destino());
        pstInsert.setBoolean(8, tb.isHabilitado());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstUpdate.setLong(1, tb.getProcesso_id());
        pstUpdate.setString(2, tb.getDirecao_origem());
        pstUpdate.setString(3, tb.getDirecao_destino());
        pstUpdate.setString(4, tb.getUsuario_origem());
        pstUpdate.setString(5, tb.getUsuario_destino());
        pstUpdate.setString(6, tb.getSenha_origem());
        pstUpdate.setString(7, tb.getSenha_destino());
        pstUpdate.setBoolean(8, tb.isHabilitado());
        pstUpdate.setLong(9, tb.getId());
    }

    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_DIRECAO map(ResultSet rs) throws SQLException {
        TB_REPLICACAO_DIRECAO tb = new TB_REPLICACAO_DIRECAO();
        tb.setId(rs.getLong("ID"));
        tb.setProcesso_id(rs.getLong("PROCESSO_ID"));
        tb.setDirecao_origem(rs.getString("DIRECAO_ORIGEM"));
        tb.setDirecao_destino(rs.getString("DIRECAO_DESTINO"));
        tb.setUsuario_origem(rs.getString("USUARIO_ORIGEM"));
        tb.setUsuario_destino(rs.getString("USUARIO_DESTINO"));
        tb.setSenha_origem(rs.getString("SENHA_ORIGEM"));
        tb.setSenha_destino(rs.getString("SENHA_DESTINO"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        return tb;
    }

}
