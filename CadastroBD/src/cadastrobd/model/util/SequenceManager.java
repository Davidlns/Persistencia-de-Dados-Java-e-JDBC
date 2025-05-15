package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SequenceManager {

    public static int getValue(String sequenceName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int nextVal = -1;

        try {
            conn = ConectorBD.getConnection();
            stmt = conn.prepareStatement("SELECT NEXT VALUE FOR " + sequenceName);
            rs = ConectorBD.getSelect(stmt);

            if (rs.next()) {
                nextVal = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConectorBD.close(rs);
            ConectorBD.close(stmt);
            ConectorBD.close(conn);
        }

        return nextVal;
    }
}