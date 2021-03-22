/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.PatientData;
import EntityClasses.Patient;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class ReceptionpanelController implements Initializable {

    @FXML
    Button addpatient_button, search_by_age;
    @FXML
    RangeSlider age_range;
    @FXML
    JFXComboBox<String> filter_combobox;
    @FXML
    private TableView<Patient> table_view;
    @FXML
    private TableColumn<Patient, String> t_patient_name, t_sex, t_phone, t_email;
    @FXML
    private TableColumn<Patient, Integer> t_age, t_id;
    @FXML
    private TableColumn<Patient, String> t_addmission, t_release;
    @FXML
    TableColumn<Patient, String> t_address;
    @FXML
    private TextField search_field;
    private String filter_value = "No Filter";

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private ObservableList<Patient> patients = null;
    private final ObservableList<String> filter_items = FXCollections.observableArrayList("No Filter", "Filter By First Name",
            "Filter By Middle Name", "Filter By Last Name", "Filter By Age Range", "Filter By Age", "Filter By Sex",
            "Filter By Phone", " Filter By Email", "Filter By Addmission Date", "Filter By Release Date");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userTable();
        getUpdatedPatients();
        age_range.setHighValue(20.0);
        filter_combobox.setItems(filter_items);
        filter_combobox.setValue("No Filter");
        TableFilter<Patient> tf = TableFilter.forTableView(table_view).apply();
        enableSearchField();

    }

    @FXML
    private void onAddPatient(ActionEvent event) throws IOException {

        PatientData pdata = new PatientData();
        pdata.setId(null);
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "addpatient.fxml", "Add Patient");

    }

    private void userTable() {

        try {
            t_patient_name.setCellValueFactory((TableColumn.CellDataFeatures<Patient, String> p) -> new SimpleStringProperty(p.getValue().getFullName()));
            t_patient_name.setStyle("-fx-alignment: CENTER");
            t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            t_id.setStyle("-fx-alignment: CENTER");
            t_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            t_phone.setStyle("-fx-alignment: CENTER");
            t_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            t_email.setStyle("-fx-alignment: CENTER");
            t_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            t_age.setStyle("-fx-alignment: CENTER");
            t_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
            t_sex.setStyle("-fx-alignment: CENTER");
            t_addmission.setCellValueFactory(p -> {
                SimpleStringProperty property = new SimpleStringProperty();
                DateFormat dateFormat = new SimpleDateFormat("MM-dd- yyyy");
                property.setValue(dateFormat.format(p.getValue().getAddmissionDate()));
                return property;
            });
            t_addmission.setStyle("-fx-alignment: CENTER");
            t_address.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getAddressId().getHouse()
                    + " " + cellData.getValue().getAddressId().getRoad() + " " + cellData.getValue().getAddressId().getArea()+" " + cellData.getValue().getAddressId().getZip()
            ));
        } catch (Exception e) {

        }

    }

    private void getUpdatedPatients() {
        //
        Query request = entityManager.createNamedQuery("Patient.findAll");
        List result = request.getResultList();
        if (patients == null) {
            patients = FXCollections.observableArrayList(result);
        } else {
            patients.clear();
            patients.addAll(result);
        }

        table_view.setItems(patients);
    }

    @FXML
    public void onRowSelect(MouseEvent event) throws IOException {

        PatientData patientData = new PatientData();
        Patient tableRow = table_view.getSelectionModel().getSelectedItem();
        Integer id = tableRow.getId();
        patientData.setId(id);

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "addpatient.fxml", "Edit Patient");
    }

    @FXML
    private void filterListener(ActionEvent event) {
        filter_value = filter_combobox.getValue();

        // JOptionPane.showMessageDialog(null, " hi");
        if (filter_value == "No Filter") {
            getUpdatedPatients();
            enableSearchField();
        } else {
            if (filter_value == "Filter By Age Range") {
                search_field.setDisable(true);
                search_field.setVisible(false);
                age_range.setDisable(false);
                age_range.setVisible(true);
                search_field.clear();

            } else {
                enableSearchField();
            }

        }

    }

    @FXML
    private void searchByAgeRange() {

        FilteredList<Patient> filteredData = new FilteredList<>(patients, p -> true);

        age_range.lowValueProperty().addListener((obsrvable, oldvalue, newvalue) -> {

            filteredData.setPredicate(pred -> {
                if ((pred.getAge() >= newvalue.intValue()) && pred.getAge() <= (int) age_range.getHighValue()) {
                    return true;
                }
                return false;
            });

            SortedList<Patient> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table_view.comparatorProperty());
            table_view.setItems(sortedList);
        });
        age_range.highValueProperty().addListener((obsrvable, oldvalue, newvalue) -> {

            filteredData.setPredicate(pred -> {
                if ((pred.getAge() >= (int) age_range.getLowValue()) && (pred.getAge() <= newvalue.intValue())) {
                    return true;
                }
                return false;
            });

            SortedList<Patient> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(table_view.comparatorProperty());
            table_view.setItems(sortedList);
        });

    }

    private void enableSearchField() {

        age_range.setDisable(true);
        age_range.setVisible(false);
        search_field.setDisable(false);
        search_field.setVisible(true);
    }

    @FXML
    private void onSearchFieldInput() {
        //JOptionPane.showMessageDialog(null, "hi");
        FilteredList<Patient> filteredData = new FilteredList<>(patients, p -> true);
        search_field.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (filter_value == filter_items.get(0)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getFullName().toLowerCase().contains(search_field.getText().toLowerCase()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(1)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getFirstName().toLowerCase().contains(search_field.getText().toLowerCase()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(2)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getMiddleName().toLowerCase().contains(search_field.getText().toLowerCase()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(3)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getLastName().toLowerCase().contains(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(5)) {
                filteredData.setPredicate(pred -> {
                 
                    if (pred.getAge() == Integer.parseInt(newvalue)) {
                            return true;
                        }

                    return false;
                });
            } else if (filter_value == filter_items.get(6)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getSex().toLowerCase().matches(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(7)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getPhone().toLowerCase().contains(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(8)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getEmail().toLowerCase().contains(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            }
            /*  else if (filter_value == filter_items.get(9)) {
                DateFormat dateFormat = new SimpleDateFormat("MM-dd- yyyy");
                
                filteredData.setPredicate(pred -> {
                    if (pred.getAddmissionDate().equals(java.sql.Date.valueOf(search_field.getText())) ) {
                        return true;
                    }
                    return false;
                });
            }*/
        });

        SortedList<Patient> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortedList);

    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    private void pushNotification(String text) {
        Notifications not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(2));
        not.text(text);
        not.title("Notifiction");
        not.show();
    }

    private boolean chekcageField() {
        String regex = "\\d+";

        boolean b = search_field.getText().matches(regex);
//System.out.println(b);
        return b;
    }

}
