import java.sql.*;

public class DrivingLicense {
    int id, age;
    long phone;
    boolean validity;
    String address, type;
    String sql = "INSERT INTO driving_license (license_id, age, phone, validity, type, address) VALUES (?, ?, ?, ?, ?, ?)";

    public DrivingLicense(int id, int age, long phone, boolean validity, String address, String type) {
        this.id = id;
        this.age = age;
        this.phone = phone;
        this.validity = validity;
        this.address = address;
        this.type = type;
        insert();
    }

    public DrivingLicense() {
        this.id = InputHelper.getInt("License ID");
        this.age = InputHelper.getInt("Age");
        this.phone = InputHelper.getLong("Phone Number");
        this.address = InputHelper.getString("Address");
        this.type = InputHelper.getString("License Type");
        this.validity = InputHelper.getBoolean("Is License Valid (true/false): ");
        insert();
    }

    private void insert() {
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, age);
            ps.setLong(3, phone);
            ps.setBoolean(4, validity);
            ps.setString(5, type);
            ps.setString(6, address);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean check(int id) {
        boolean exists = false;
        String q = "SELECT COUNT(*) FROM driving_license WHERE license_id = ?";
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
