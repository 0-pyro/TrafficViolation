import java.sql.*;

public class Ticket {
    int ticket_id, officer_id, offender_id, fee;
    String vehicle_number, city, town, road, penalty, violation;
    java.sql.Timestamp date;
    String sql = "INSERT INTO ticket (officer_id, offender_id, vehicle_number, city, town, road, penalty, violation, fee, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public Ticket() {
        this.officer_id = InputHelper.getInt("Officer ID");
        if (!Officer.check(officer_id)) {
            System.out.println("Officer not found. Enter details for new officer:");
            new Officer();
        }

        this.offender_id = InputHelper.getInt("Offender ID");
        if (!Offender.check(offender_id)) {
            System.out.println("Offender not found. Enter details for new offender:");
            new Offender();
        }

        this.vehicle_number = InputHelper.getString("Enter Vehicle Number");
        if (!Vehicle.check(vehicle_number)) {
            System.out.println("Vehicle not found. Enter details for new vehicle:");
            new Vehicle();
        }

        this.city = InputHelper.getString("Enter City");
        this.town = InputHelper.getString("Enter Town");
        this.road = InputHelper.getString("Enter Road");
        this.penalty = InputHelper.getString("Enter Penalty Description");
        this.violation = InputHelper.getString("Enter Violation Description");
        this.fee = InputHelper.getInt("Enter Fee Amount");
        this.date = new java.sql.Timestamp(System.currentTimeMillis());
        insert();
    }

    private void insert() {
        try (Connection c = DBC.conDB();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, officer_id);
            ps.setInt(2, offender_id);
            ps.setString(3, vehicle_number);
            ps.setString(4, city);
            ps.setString(5, town);
            ps.setString(6, road);
            ps.setString(7, penalty);
            ps.setString(8, violation);
            ps.setInt(9, fee);
            ps.setTimestamp(10, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
