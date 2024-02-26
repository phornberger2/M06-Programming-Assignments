import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TitledPane;
import javafx.stage.Stage;
import java.sql.*;

public class exercise_34_01 extends Application {
    private TextField jTextField1 = new TextField();
    private TextField jTextField2 = new TextField();
    private TextField jTextField3 = new TextField();
    private TextField jTextField4 = new TextField();
    private TextField jTextField5 = new TextField();
    private TextField jTextField6 = new TextField();
    private TextField jTextField7 = new TextField();
    private TextField jTextField8 = new TextField();
    private TextField jTextField9 = new TextField();

    private Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        initializeDB();

        // Create a panel for staff information with a titled border
        TitledPane jPanel1 = new TitledPane();
        jPanel1.setText("Staff Information");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.addRow(0, new Label("ID"), jTextField1);
        gridPane.addRow(1, new Label("Last Name"), jTextField2);
        gridPane.addRow(2, new Label("First Name"), jTextField3);
        gridPane.addRow(3, new Label("mi"), jTextField4);
        gridPane.addRow(4, new Label("Address"), jTextField5);
        gridPane.addRow(5, new Label("City"), jTextField6);
        gridPane.addRow(6, new Label("State"), jTextField7);
        gridPane.addRow(7, new Label("Telephone"), jTextField8);
        gridPane.addRow(8, new Label("E-mail"), jTextField9);
        jPanel1.setContent(gridPane);
        root.setCenter(jPanel1);

        // Create a panel for buttons with FlowPane layout
        FlowPane jPanel2 = new FlowPane();
        jPanel2.setHgap(5);
        jPanel2.getChildren().addAll(
                new Button("View"),
                new Button("Insert"),
                new Button("Update"),
                new Button("Clear")
        );
        root.setBottom(jPanel2);

        // ActionListener for View button
        ((Button) jPanel2.getChildren().get(0)).setOnAction(event -> {
            // Define SQL query for selecting staff information based on ID
            String queryString = "select lastName, firstName, mi, address, city, state, telephone, email from Staff where id = ?";
            try {
                // Prepare the SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, jTextField1.getText());

                // Execute the query
                ResultSet rset = preparedStatement.executeQuery();

                // Check if a result is found and populate text fields accordingly
                if (rset.next()) {
                    jTextField2.setText(rset.getString(1));
                    jTextField3.setText(rset.getString(2));
                    jTextField4.setText(rset.getString(3));
                    jTextField5.setText(rset.getString(4));
                    jTextField6.setText(rset.getString(5));
                    jTextField7.setText(rset.getString(6));
                    jTextField8.setText(rset.getString(7));
                    jTextField9.setText(rset.getString(8));
                } else {
                    // No matching record found, clear text fields
                    jTextField2.clear();
                    jTextField3.clear();
                    jTextField4.clear();
                    jTextField5.clear();
                    jTextField6.clear();
                    jTextField7.clear();
                    jTextField8.clear();
                    jTextField9.clear();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // ActionListener for Insert button
        ((Button) jPanel2.getChildren().get(1)).setOnAction(event -> {
            // Define SQL query for inserting a new staff record
            String queryString = "insert into Staff (id, lastName, firstName, mi, address, city, state, telephone, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                // Prepare the SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, jTextField1.getText());
                preparedStatement.setString(2, jTextField2.getText());
                preparedStatement.setString(3, jTextField3.getText());
                preparedStatement.setString(4, jTextField4.getText());
                preparedStatement.setString(5, jTextField5.getText());
                preparedStatement.setString(6, jTextField6.getText());
                preparedStatement.setString(7, jTextField7.getText());
                preparedStatement.setString(8, jTextField8.getText());
                preparedStatement.setString(9, jTextField9.getText());

                // Execute the SQL insert statement
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // ActionListener for Update button
        ((Button) jPanel2.getChildren().get(2)).setOnAction(event -> {
            // Define SQL query for updating an existing staff record based on ID
            String queryString = "update Staff set lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? where id = ?";
            try {
                // Prepare the SQL statement for updating
                PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                preparedStatement.setString(1, jTextField2.getText());
                preparedStatement.setString(2, jTextField3.getText());
                preparedStatement.setString(3, jTextField4.getText());
                preparedStatement.setString(4, jTextField5.getText());
                preparedStatement.setString(5, jTextField6.getText());
                preparedStatement.setString(6, jTextField7.getText());
                preparedStatement.setString(7, jTextField8.getText());
                preparedStatement.setString(8, jTextField9.getText());
                preparedStatement.setString(9, jTextField1.getText());

                // Execute the SQL update statement
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // ActionListener for Clear button
        ((Button) jPanel2.getChildren().get(3)).setOnAction(event -> {
            try {
                // Create a statement for deleting all records from the Staff table
                Statement statement = connection.createStatement();
                statement.executeUpdate("delete from Staff");

                // Clear all text fields
                jTextField1.clear();
                jTextField2.clear();
                jTextField3.clear();
                jTextField4.clear();
                jTextField5.clear();
                jTextField6.clear();
                jTextField7.clear();
                jTextField8.clear();
                jTextField9.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setTitle("Exercise01");
        primaryStage.show();
    }

    private void initializeDB() {
        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "root", "root");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
