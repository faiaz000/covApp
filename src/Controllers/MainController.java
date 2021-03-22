/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import EntityClasses.Users;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class MainController implements Initializable {

    @FXML
    private JFXTextField loginId;
    @FXML
    private JFXPasswordField loginPass;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager userentity = emf.createEntityManager();
    @FXML
    BorderPane centerPane;
    Notifications not;

    @FXML
    private Label user_label, password_label;
    private Users result;

    @FXML
    private void onLogin(ActionEvent event) throws IOException, SQLException {
        //JOptionPane.showMessageDialog(null," hi" );
        String id = loginId.getText();
        String pass = loginPass.getText();
        boolean success = checkValidations(id, pass);
        if (success) {
            boolean exist = checkAuthentication(id, pass);
            clearFields();

            if (exist) {
                // pushNotification("Logged In", "Success", 1);

                Users selectedResult = (Users) result;
                String role = selectedResult.getRole();
                SharedData setUser = new SharedData();
                setUser.setUser(selectedResult.getUserid());
                setUser.setUserObject(selectedResult);
                setUser.setRole(role);

                if (role.matches("doctor") || role.matches("Doctor")) {

                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "doctorpanel.fxml", "Doctor Panel");
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (role.matches("nurse") || role.matches("Nurse")) {

                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "doctorpanel.fxml", "Doctor Panel");   // Same panel for Nurse but Restricted Access  
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (role.matches("receptionist") || role.matches("Receptionist")) {
                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "receptionpanel.fxml", "Reception Panel");
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (role.matches("admin") || role.matches("Admin")) {

                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "adminpanel.fxml", "Admin Panel");
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (role.matches("researcher") || role.matches("Researcher")) {
                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "reports.fxml", "Researchers Panel");
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        }

    }

    @FXML
    private void clearLabels() {
        user_label.setText("");
        password_label.setText("");
    }

    public boolean checkAuthentication(String user, String pass) throws SQLException {
        Query query = userentity.createNativeQuery("Select * from Users  where userid = ? ", Users.class);
        query.setParameter(1, user);
        boolean exists = false;
        Users temp = null;
        try {
            if (query.getResultList().size() > 0) {
                temp = (Users) query.getSingleResult();
                if (BCrypt.checkpw(pass, temp.getPassword())) {
                    exists = true;
                    result = temp;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Authentication Failed");
                    alert.setContentText("Password does not match");
                    alert.getDialogPane().setStyle(" -fx-font-weight: bold");
                    alert.show();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Authentication Failed");
                alert.setContentText("User not found!");
                alert.getDialogPane().setStyle(" -fx-font-weight: bold");
                alert.show();
            }

        } catch (Exception e) {

        }

        return exists;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //setPane(centerPane);

    }

    private boolean checkValidations(String id, String pass) {
        boolean success = true;
        if (id.isEmpty()) {
            success = false;
            user_label.setText("User field is empty ");
        }
        if (pass.isEmpty()) {
            success = false;
            password_label.setText("Password field is empty ");
        }
        return success;
    }

    private void clearFields() {

        loginId.clear();
        loginPass.clear();
    }

    private void pushNotification(String text, String title, int tm) {
        not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(tm));
        not.text(text);
        not.title(title);
        not.show();

    }
}
