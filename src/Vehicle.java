import java.sql.*;

public class Vehicle {
    String vehicle_number;
    int offender_id;
    boolean insured;
    java.sql.Date reg_date;
    String type, model, fuel, colour;
    String sql = "INSERT INTO vehicle (vehicle_number, offender_id, insured, reg_date, type, model, fuel, colour) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public Vehicle(String vehicle_number, int offender_id, boolean insured, java.sql.Date reg_date,
            String type, String model, String fuel, String colour) {
        this.vehicle_number = vehicle_number;
        this.offender_id = offender_id;
        this.insured = insured;
        this.reg_date = reg_date;
        this.type = type;
        this.model = model;
        this.fuel = fuel;
        this.colour = colour;
        insert();
    }

    public Vehicle() {
        this.vehicle_number = InputHelper.getString("Enter Vehicle Number");
        this.offender_id = InputHelper.getInt("Offender ID");

        if (!Offender.check(offender_id)) {
            System.out.println("Offender not found. Enter details for new offender:");
            new Offender();
        }

        this.insured = InputHelper.getBoolean("Is Vehicle Insured (true/false): ");
        this.reg_date = java.sql.Date.valueOf(InputHelper.getString("Enter Registration Date (YYYY-MM-DD)"));
        this.type = InputHelper.getString("Enter Vehicle Type");
        this.model = InputHelper.getString("Enter Model");
        this.fuel = InputHelper.getString("Enter Fuel Type");
        this.colour = InputHelper.getString("Enter Colour");
        insert();
    }

    private void insert() {
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, vehicle_number);
            ps.setInt(2, offender_id);
            ps.setBoolean(3, insured);
            ps.setDate(4, reg_date);
            ps.setString(5, type);
            ps.setString(6, model);
            ps.setString(7, fuel);
            ps.setString(8, colour);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean check(String number) {
        boolean exists = false;
        String q = "SELECT COUNT(*) FROM vehicle WHERE vehicle_number = ?";
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                exists = rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}
