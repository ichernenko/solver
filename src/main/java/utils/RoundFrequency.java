package utils;

        import java.io.IOException;
        import java.math.BigDecimal;
        import java.math.RoundingMode;
        import java.sql.*;

class RoundFrequency {
    private static final String selectSql = "select id,frequency from dic_lemmas where frequency is not null";
    private static final String updateSql = "update dic_lemmas set frequency_new=? where id=?";

    private void loadDBDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:db\\solverDB.s3db");
             PreparedStatement selectPS = con.prepareStatement(selectSql);
             PreparedStatement updatePS = con.prepareStatement(updateSql);
             ResultSet rs = selectPS.executeQuery()) {
            con.setAutoCommit(false);
            int i = 0;
            while(rs.next()) {
                int id = rs.getInt("id");
                double frequency = rs.getDouble("frequency");
                frequency = new BigDecimal(frequency).setScale(2, RoundingMode.HALF_UP).doubleValue();
                updateRow(updatePS, id, frequency);
                System.out.println(id + "   " + frequency);
                if (++i == 1000) {
                    i=0;
                    System.out.println("-------");
                    updatePS.executeBatch();
                    con.commit();
                }
            }
            updatePS.executeBatch();
            con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateRow(PreparedStatement ps, int id, double frequency) throws SQLException {
        ps.setDouble(1, frequency);
        ps.setInt(2, id);
        ps.addBatch();
    }


    public static void main(String[] args) throws IOException {
        utils.RoundFrequency roundFrequency = new utils.RoundFrequency();
        roundFrequency.loadDBDriver();
        roundFrequency.update();
    }
}