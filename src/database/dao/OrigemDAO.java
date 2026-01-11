package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigemDAO {

    private final Connection conn;

    private PreparedStatement pstSelect;

    public OrigemDAO(Connection conn) {
        this.conn = conn;
    }

    public ResultSet SelectComandoOrigem(final String tabela, String where) throws SQLException {
        pstSelect = conn.prepareStatement("SELECT * FROM " + tabela + " WHERE " + where);
        System.out.println("Select na origem: "+"SELECT * FROM " + tabela + " WHERE " + where);
        return pstSelect.executeQuery();
    }
}
