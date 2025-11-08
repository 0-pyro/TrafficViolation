import java.sql.*;

public class Officer {
    int officer_id;
    String name, email, password;
    String sql = "INSERT INTO officer (name, email, password) VALUES (?, ?, ?)";

    public Officer(int officer_id, String name, String email, String password) {
        this.officer_id = officer_id;
        this.name = name;
        this.email = email;
        this.password = password;
        insert();
    }

    public Officer() {
        this.name = InputHelper.getString("Enter Officer Name");
        this.email = InputHelper.getString("Enter Officer Email");
        this.password = InputHelper.getString("Enter Officer Password");
        insert();
    }

    private void insert() {
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean check(int id) {
        boolean exists = false;
        String q = "SELECT COUNT(*) FROM officer WHERE officer_id = ?";
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                exists = rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}
