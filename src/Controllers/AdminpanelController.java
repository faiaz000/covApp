/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import EntityClasses.Users;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class AdminpanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    BorderPane centerPane;
    @FXML
    BorderPane border_pane;

    @FXML
    private TextField first_name, middle_name, last_name, email, phone;
    @FXML
    private PasswordField password, confirm_password;
    @FXML
    private TextField user_id, department;
    @FXML
    private TextField search_field;
    @FXML
    private TableView<Users> table_view;
    @FXML
    private TableColumn<Users, String> t_fullname;

    @FXML
    private TableColumn<Users, String> t_userid;
    @FXML
    private TableColumn<Users, String> t_role;
    @FXML
    private TableColumn<Users, String> t_department;
    @FXML
    private TableColumn<Users, String> t_email;
    @FXML
    private TableColumn<Users, String> t_phone;
    @FXML
    private Button save_button, cancel_button;
    @FXML
    private Button update_button;
    @FXML
    private Button delete_button, logout;
    @FXML
    private Label fname_label, mname_label, lname_label, userid_label, role_label, password_label, cpassword_label, search_label, user_name;
    @FXML
    private Label department_label, email_label, phone_label;
    @FXML
    JFXComboBox<String> filter_combobox, role_combobox;
    private String filter_value = "Full Name";
    private String f_name, m_name, l_name, pass, user_role, user_department, confpass, user_email, user_phone, userId;

    @FXML
    private ComboBox<String> department_combobox, role_combo;
    ObservableList<String> role_list = FXCollections.observableArrayList("Admin", "Receptionist", "Doctor", "Nurse", "Researcher");
    ObservableList<String> role_filterList = FXCollections.observableArrayList("All", "Admin", "Receptionist", "Doctor", "Nurse", "Researcher");

    ObservableList<String> filter_list = FXCollections.observableArrayList("Full Name", "First Name", "Middle Name", "Last Name", "User Id", "Department", "Role");
    ObservableList<String> departmemt_list = FXCollections.observableArrayList("ICU", "General Ward", "CCU", "Front Desk", "OT");

    private ObservableList<Users> users;
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager userEntity = emf.createEntityManager();
    private Users userModel;
    private String updateId;
    private Integer p_id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        user_name.setText(SharedData.getUser());
        role_combobox.setItems(role_filterList);
        role_combo.setItems(role_list);
        filter_combobox.setItems(filter_list);
        department_combobox.setItems(departmemt_list);
        filter_combobox.setValue(filter_list.get(0));

        // populate role combobox
        userTable();
        // table_view.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // Define table collumns
        getUpdatedUsers();
        update_button.setDisable(true);
        delete_button.setDisable(true);
        save_button.setDisable(false);
    }

    public void userTable() {

        t_userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
        t_userid.setStyle("-fx-alignment: CENTER");
        t_fullname.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Users, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Users, String> p) {
                return new SimpleStringProperty(p.getValue().getFullName());

            }
        });
        t_fullname.setStyle("-fx-alignment: CENTER");
        t_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        t_role.setStyle("-fx-alignment: CENTER");
        t_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        t_department.setStyle("-fx-alignment: CENTER");
        t_email.setStyle("-fx-alignment: CENTER");
        t_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        t_phone.setStyle("-fx-alignment: CENTER");
        t_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }
    // get updated userlist

    private void getUpdatedUsers() {
        //
        Query request = userEntity.createNamedQuery("Users.findAll");
        List<Users> result = request.getResultList();
        if (users == null) {
            users = FXCollections.observableArrayList(result);
        } else {
            users.clear();
            users.addAll(result);

        }
        table_view.setItems(users);
    }

    @FXML
    public void getSelectedRow(MouseEvent event) throws SQLException {
        Users tableRow = table_view.getSelectionModel().getSelectedItem();
        if (tableRow != null) {

            save_button.setDisable(true);
            save_button.setVisible(false);
            update_button.setDisable(false);
            update_button.setVisible(true);
            delete_button.setDisable(false);
            delete_button.setVisible(true);
            password.setDisable(true);
            confirm_password.setDisable(true);

            updateId = tableRow.getUserid();

            if (tableRow != null) {
                Users selectedresult = getSelectedUser(updateId);
                if (selectedresult != null) {

                    first_name.setText(selectedresult.getFirstName());
                    last_name.setText(selectedresult.getLastName());
                    middle_name.setText(selectedresult.getMiddleName());
                    user_id.setText(selectedresult.getUserid());
                    user_id.setEditable(false);
                    department.setText(selectedresult.getDepartment());
                    role_combo.setValue(selectedresult.getRole());
                    p_id = selectedresult.getId();
                    email.setText(selectedresult.getEmail());
                    phone.setText(selectedresult.getPhone());
                    setInputValues();
                }
            }

        }

    }

    private void setInputValues() {

        f_name = first_name.getText();
        if (!middle_name.getText().isEmpty()) {
            m_name = middle_name.getText();
        }
        l_name = last_name.getText();

        if (!password.getText().isEmpty()) {
            pass = password.getText();

        }
        userId = user_id.getText();
        user_role = role_combo.getValue();

        user_department = department.getText();
        if (!confirm_password.getText().isEmpty()) {
            confpass = confirm_password.getText();
        }

        user_email = email.getText();
        user_phone = phone.getText();

    }

    @FXML
    private void addUser() throws SQLException {
        setInputValues();
        if (inputValidations()) {

            if (pass.matches(confpass)) {
                String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());
                userModel = new Users();
                userModel.setUserid(userId);
                userModel.setFirstName(f_name);
                if (m_name.isEmpty()) {
                    userModel.setMiddleName("");
                }
                userModel.setLastName(l_name);
                userModel.setPassword(hashPass);
                userModel.setRole(user_role);
                userModel.setEmail(user_email);
                userModel.setPhone(user_phone);
                userModel.setDepartment(user_department);
                try {
                    updateSharedData(userModel);

                    Optional<ButtonType> selection = getDialogSelection("Do you want to save this user?");
                    if (selection.get() == ButtonType.YES) {
                        userEntity.getTransaction().begin();
                        userEntity.persist(userModel);
                        userEntity.getTransaction().commit();
                        getUpdatedUsers();
                        pushNotification("Record saved successfully");
                        clearInputFields();
                    }
                } catch (Exception e) {
                }

            }
        }
    }

    @FXML
    private void editUser(ActionEvent event) throws SQLException, IOException {

        Users selectedItem = getSelectedUser(updateId);
        if (selectedItem != null) {

            userEntity.getTransaction().begin();

            if (!f_name.matches(first_name.getText())) {
                selectedItem.setFirstName(first_name.getText());
            }
            if (!middle_name.getText().isEmpty()) {

                if (middle_name.getText() != m_name) {

                    selectedItem.setMiddleName(middle_name.getText());
                }

            }
            if (!l_name.matches(last_name.getText())) {
                selectedItem.setLastName(last_name.getText());
            }
            if (!user_role.matches(role_combo.getValue())) {
                selectedItem.setRole(role_combo.getValue().toString());
            }
            if (!user_role.matches(department.getText())) {
                selectedItem.setDepartment(department.getText());
            }
            if (!user_email.matches(email.getText())) {
                selectedItem.setEmail(email.getText());
            }
            if (!user_phone.matches(phone.getText())) {
                selectedItem.setPhone(phone.getText());
            }
            updateSharedData(selectedItem);

            Optional<ButtonType> selection = getDialogSelection("Do you want to update this user?");
            if (selection.get() == ButtonType.YES) {
                userEntity.getTransaction().commit();

                getUpdatedUsers();
                pushNotification("Changes are saved successfully");

                disableModificationButtons();
                clearInputFields();

            }

        }

    }

    @FXML
    private void filterTable(KeyEvent event) {

        FilteredList<Users> filteredData = new FilteredList<>(users, p -> true);
        search_field.textProperty().addListener((observable, oldvalue, newvalue) -> {
            String lowercase_newvalue = newvalue.toLowerCase();
            filteredData.setPredicate(pred -> {

                if (filter_value == filter_list.get(0)) {

                    if (pred.getFullName().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }
                } else if (filter_value == filter_list.get(1)) {
                    if (pred.getFirstName().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }
                } else if (filter_value == filter_list.get(2)) {

                    if (pred.getMiddleName().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }
                } else if (filter_value == filter_list.get(3)) {
                    if (pred.getLastName().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }
                } else if (filter_value == filter_list.get(4)) {
                    if (pred.getUserid().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }
                }

                return false;
            });

            SortedList<Users> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table_view.comparatorProperty());
            table_view.setItems(sortedList);
        });

    }

    @FXML
    private void deleteUsers(ActionEvent event) throws IOException {

        Users selectedItem = userEntity.find(Users.class, p_id);
        updateSharedData(selectedItem);
        Optional<ButtonType> selection = getDialogSelection("Do you want to delete this user?"); 
        if (selection.get() == ButtonType.YES) {
            userEntity.getTransaction().begin();
            userEntity.remove(selectedItem);
            userEntity.getTransaction().commit();
            getUpdatedUsers();
            pushNotification("Record deleted successfully");
            disableModificationButtons();
        }

    }

    private void clearInputFields() {

        user_id.clear();
        first_name.clear();
        middle_name.clear();
        last_name.clear();
        password.clear();
        confirm_password.clear();
        role_combobox.getSelectionModel().clearSelection();
        department.clear();
        email.clear();
        phone.clear();
        //role_combobox.selectionModelProperty().set(" ");
    }

    private Users getSelectedUser(String id) throws SQLException {

        TypedQuery<Users> query = userEntity.createNamedQuery("Users.findByUserid", Users.class);
        query.setParameter("userid", id);

        List<Users> resultSet = query.getResultList();
        if (resultSet.size() > 0) {
            Users user = (Users) resultSet.get(0);
            return user;
        }

        return null;
    }

    private Boolean inputValidations() {

        Boolean success = true;
        success = validateInputOnEdit();

        if (user_id.getText().isEmpty()) {
            validationAlertBox("Empty", "User Id is required");
            success = false;
        }
        if (email.getText().isEmpty()) {

            validationAlertBox("Empty", "Email is required");
            success = false;
        } else {
            if (emailExists()) {
                validationAlertBox("Duplicate", "Email is required");
                success = false;
            }
        }
        if (phone.getText().isEmpty()) {

            validationAlertBox("Empty", "Phone number is required");
            success = false;
        }

        if (password.getText().isEmpty()) {
            validationAlertBox("Empty", "Password is required");
            success = false;
        }
        if (confirm_password.getText().isEmpty()) {

            validationAlertBox("Empty", "Confirm Password is required");
            success = false;
        }

        if (!password.getText().isEmpty() && !confirm_password.getText().isEmpty()) {
            if (!password.getText().matches(confirm_password.getText())) {

                validationAlertBox("No match", "Password doesn't match");
                success = false;

            }
        }

        return success;
    }

    private Boolean validateInputOnEdit() {
        Boolean success = true;
        if (first_name.getText().isEmpty()) {

            validationAlertBox("Empty", "First Name is required");
            success = false;
        }
        if (last_name.getText().isEmpty()) {
            validationAlertBox("Empty", "Last Name is required");
            success = false;
        }
        if (department.getText().isEmpty()) {

            validationAlertBox("Empty", "Department is required");
            success = false;
        }
        if (role_combo.getSelectionModel().isEmpty()) {

            validationAlertBox("No Selection", "A Role Must be Selected");

            success = false;
        }

        return success;
    }

    @FXML
    private void onFilterChange(ActionEvent event) {
        filter_value = filter_combobox.getValue();

        if (filter_value == "Role") {

            search_field.setDisable(true);
            search_field.setVisible(false);
            department_combobox.setDisable(true);
            department_combobox.setVisible(false);
            role_combobox.setDisable(false);
            role_combobox.setVisible(true);
            role_combobox.getSelectionModel().selectFirst();
            search_label.setDisable(true);
            search_label.setVisible(false);
        } else if (filter_value == filter_list.get(5)) {
            search_field.setDisable(true);
            search_field.setVisible(false);
            department_combobox.setDisable(false);
            department_combobox.setVisible(true);
            role_combobox.setDisable(true);
            role_combobox.setVisible(false);
            search_label.setDisable(true);
            search_label.setVisible(false);
        } else {
            department_combobox.setDisable(true);
            department_combobox.setVisible(false);
            role_combobox.setDisable(true);
            role_combobox.setVisible(false);
            search_label.setDisable(false);
            search_label.setVisible(true);
            search_field.setDisable(false);
            search_field.setVisible(true);
        }
        table_view.setItems(users);
    }

    @FXML
    private void cancelOperation() {
        clearInputFields();

        role_combo.setValue(role_list.get(0));
        user_id.setEditable(true);

        disableModificationButtons();

    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    @FXML
    private void filterByRole(ActionEvent event) {

        FilteredList<Users> filteredData = new FilteredList<>(users, p -> true);
        role_combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newvalue) -> {
            String lowercase_newvalue = newvalue.toLowerCase();

            if (!lowercase_newvalue.matches("all")) {
                filteredData.setPredicate(pred -> {

                    if (pred.getRole().toLowerCase().contains(lowercase_newvalue)) {
                        return true;
                    }

                    return false;
                });

            }

            SortedList<Users> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table_view.comparatorProperty());
            table_view.setItems(sortedList);
        });

    }

    private void disableModificationButtons() {
        update_button.setDisable(true);
        update_button.setVisible(false);
        delete_button.setVisible(false);
        delete_button.setDisable(true);
        save_button.setDisable(false);
        save_button.setVisible(true);
        user_id.setEditable(true);
        password.setDisable(false);
        confirm_password.setDisable(false);
    }

    private boolean emailExists() {
        boolean success = false;
        int size = 0;
        Query query = userEntity.createNativeQuery("Select * from Users  where email = ? ", Users.class);

        query.setParameter(1, user_email);

        size = query.getResultList().size();
        if (size > 0) {
            success = true;
        } else {
            success = false;
        }
        return success;
    }

    private void validationAlertBox(String title, String contentText) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle(" -fx-font-weight: bold");
        alert.showAndWait();

    }

    private void pushNotification(String text) {
        Notifications not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(2));
        not.text(text);
        not.title("Notifiction");
        not.show();
    }

    private void updateSharedData(Users userItem) {
        SharedData sharedData = new SharedData();
        sharedData.setModal("Admin");
        sharedData.setUserObject(userItem);

    }

    private Optional<ButtonType> getDialogSelection(String headerText) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Views/admindialog.fxml"));

        Pane confirmPane = fxmlLoader.load();
       
        Dialog dialog = new Dialog();
        dialog.setDialogPane((DialogPane) confirmPane);
        dialog.getDialogPane().setStyle("-fx-font-weight: bold;");
        //dialog.getDialogPane().setStyle("-fx-background-color: linear-gradient(#18F3FD, #073656)");
        
        dialog.headerTextProperty().set(headerText);
        
        Optional<ButtonType> selection = dialog.showAndWait();

        return selection;
    }
}
