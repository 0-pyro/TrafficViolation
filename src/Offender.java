import java.sql.*;

public class Offender {
    int offender_id;
    String name;
    int license_id;
    String sql = "INSERT INTO offender (name, license_id) VALUES (?, ?)";

    public Offender(int offender_id, String name, int license_id) {
        this.offender_id = offender_id;
        this.name = name;
        this.license_id = license_id;
        insert();
    }

    public Offender() {
        this.name = InputHelper.getString("Enter Offender Name");
        this.license_id = InputHelper.getInt("License ID");

        if (!DrivingLicense.check(license_id)) {
            System.out.println("License not found. Enter details for new license:");
            new DrivingLicense(license_id,
                    InputHelper.getInt("Age"),
                    InputHelper.getLong("Phone Number"),
                    InputHelper.getBoolean("Is License Valid (true/false): "),
                    InputHelper.getString("Address"),
                    InputHelper.getString("Type"));
        }
        insert();
    }

    private void insert() {
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, license_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean check(int id) {
        boolean exists = false;
        String q = "SELECT COUNT(*) FROM offender WHERE offender_id = ?";
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
