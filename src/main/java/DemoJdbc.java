
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoJdbc {

    private static final Logger logger = LogManager.getLogger(DemoJdbc.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Demo";
        String username = "postgres";
        String password = "9981945154";

        int studentId = 21;
        String studentName = "Kavita Mishra";
        int studentMarks = 90;

        try {
            Class.forName("org.postgresql.Driver");
            logger.info("PostgreSQL JDBC Driver registered successfully.");
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL JDBC Driver not found.", e);
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String insertSql = "INSERT INTO student (sid, sname, marks) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                pstmt.setInt(1, studentId);
                pstmt.setString(2, studentName);
                pstmt.setInt(3, studentMarks);

                int rowsInserted = pstmt.executeUpdate();
                logger.info("{} row(s) inserted successfully.", rowsInserted);
            } catch (SQLException e) {
                logger.error("Error inserting student data.", e);
            }

            String selectSql = "SELECT * FROM student";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(selectSql)) {

                logger.info("Student Records:");
                while (rs.next()) {
                    int id = rs.getInt("sid");
                    String name = rs.getString("sname");
                    int marks = rs.getInt("marks");
                    logger.info("ID: {}, Name: {}, Marks: {}", id, name, marks);
                }
            } catch (SQLException e) {
                logger.error("Error retrieving student data.", e);
            }

            logger.info("Connection established and operations completed successfully.");

        } catch (SQLException e) {
            logger.error("Database connection failed.", e);
        }
    }
}
