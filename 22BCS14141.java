// Easy Level: Fetching data from Employee table
import java.sql.*;

public class EasyJDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "password";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {
            
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + " " + rs.getString("Name") + " " + rs.getDouble("Salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Medium Level: CRUD Operations on Product table
import java.sql.*;
import java.util.Scanner;

public class MediumJDBC {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    static String user = "root";
    static String password = "password";
    
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, user, password); Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Create\n2. Read\n3. Update\n4. Delete\n5. Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> create(conn, sc);
                    case 2 -> read(conn);
                    case 3 -> update(conn, sc);
                    case 4 -> delete(conn, sc);
                    case 5 -> System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void create(Connection conn, Scanner sc) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?)");
        ps.setInt(1, sc.nextInt());
        ps.setString(2, sc.next());
        ps.setDouble(3, sc.nextDouble());
        ps.setInt(4, sc.nextInt());
        ps.executeUpdate();
    }
    static void read(Connection conn) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Product");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getDouble(3) + " " + rs.getInt(4));
        }
    }
    static void update(Connection conn, Scanner sc) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE Product SET Price = ? WHERE ProductID = ?");
        ps.setDouble(1, sc.nextDouble());
        ps.setInt(2, sc.nextInt());
        ps.executeUpdate();
    }
    static void delete(Connection conn, Scanner sc) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Product WHERE ProductID = ?");
        ps.setInt(1, sc.nextInt());
        ps.executeUpdate();
    }
}

// Hard Level: Student Management using MVC
import java.sql.*;
import java.util.*;

class Student {
    int id;
    String name, department;
    double marks;
}

class StudentController {
    Connection conn;
    StudentController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
    }
    void create(Student s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?)");
        ps.setInt(1, s.id);
        ps.setString(2, s.name);
        ps.setString(3, s.department);
        ps.setDouble(4, s.marks);
        ps.executeUpdate();
    }
    List<Student> read() throws SQLException {
        List<Student> list = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Student");
        while (rs.next()) {
            Student s = new Student();
            s.id = rs.getInt(1);
            s.name = rs.getString(2);
            s.department = rs.getString(3);
            s.marks = rs.getDouble(4);
            list.add(s);
        }
        return list;
    }
    void update(int id, double marks) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE Student SET Marks = ? WHERE StudentID = ?");
        ps.setDouble(1, marks);
        ps.setInt(2, id);
        ps.executeUpdate();
    }
    void delete(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Student WHERE StudentID = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}

public class HardJDBC {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in); StudentController controller = new StudentController()) {
            while (true) {
                System.out.println("1. Create\n2. Read\n3. Update\n4. Delete\n5. Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        Student s = new Student();
                        s.id = sc.nextInt();
                        s.name = sc.next();
                        s.department = sc.next();
                        s.marks = sc.nextDouble();
                        controller.create(s);
                    }
                    case 2 -> controller.read().forEach(s -> System.out.println(s.id + " " + s.name + " " + s.department + " " + s.marks));
                    case 3 -> controller.update(sc.nextInt(), sc.nextDouble());
                    case 4 -> controller.delete(sc.nextInt());
                    case 5 -> System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
