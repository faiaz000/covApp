/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import Reusables.PatientData;
import EntityClasses.Patient;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
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
public class DoctorpanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField search_field;
    @FXML
    RangeSlider age_range;
    @FXML
    private TableView<Patient> table_view;
    @FXML
    private TableColumn<Patient, String> t_name, t_sex, t_marriage;
    @FXML
    private TableColumn<Patient, Integer> t_age, t_id, t_children;

    @FXML
    Label user_name;
    @FXML
    private JFXComboBox<String> filter_combobox;
    private final ObservableList<String> filter_items = FXCollections.observableArrayList("No Filter", "Filter By Id", "Filter By First Name",
            "Filter By Middle Name", "Filter By Last Name", "Filter By Age Range", "Filter By Age", "Filter By Sex",
            "Children", "Filter By Marital Status");

    private String filter_value = "No Filter";
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private ObservableList patients = null;

    private String role = "";

    //private Observable<ArrayList> result;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user_name.setText(SharedData.getUserObject().getUserid());
        filter_combobox.setItems(filter_items);
        age_range.setHighValue(20.0);
        filter_combobox.setValue("No Filter");
        role = SharedData.getRole();
        patientsTable();
        getUpdatedPatients();
        enableSearchField();
        TableFilter filter;
        filter = new TableFilter(table_view);

    }

    private void patientsTable() {

        try {
            t_name.setCellValueFactory((TableColumn.CellDataFeatures<Patient, String> p) -> new SimpleStringProperty(p.getValue().getFullName()));
            t_name.setStyle("-fx-alignment: CENTER");
            t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            t_id.setStyle("-fx-alignment: CENTER");

            t_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            t_age.setStyle("-fx-alignment: CENTER");
            t_children.setCellValueFactory(new PropertyValueFactory<>("childrenNo"));
            t_children.setStyle("-fx-alignment: CENTER");
            t_sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
            t_sex.setStyle("-fx-alignment: CENTER");
            t_marriage.setCellValueFactory(new PropertyValueFactory<>("maritalStatus"));
            t_marriage.setStyle("-fx-alignment: CENTER");

        } catch (Exception e) {

        }

        // t_release.setCellValueFactory((TableColumn.CellDataFeatures<Patient,Date>p) -> new SimpleObjectProperty(p.getValue().getReleaseDate()));
        // t_release.setStyle("-fx-alignment: CENTER");
    }

    private void getUpdatedPatients() {
        //
        Query request = entityManager.createNamedQuery("Patient.findAll");
        List<Patient> result = request.getResultList();
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
        if (tableRow != null) {
            Integer id = tableRow.getId();
            patientData.setD_Id(id);

            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "patientinfo.fxml", "Patient Info");
        }

        //// AddpatientController controller = loader.getController();
        // JOptionPane.showMessageDialog(null, id);
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    @FXML
    private void filterListener(ActionEvent event) {
        filter_value = filter_combobox.getValue();

        // JOptionPane.showMessageDialog(null, " hi");
        if (filter_value.equals("Filter By Age Range")) {
            search_field.setDisable(true);
            search_field.setVisible(false);
            age_range.setDisable(false);
            age_range.setVisible(true);
            search_field.clear();

        } else {
            enableSearchField();
        }
    }

    private void enableSearchField() {
        age_range.setDisable(true);
        age_range.setVisible(false);
        search_field.setDisable(false);
        search_field.setVisible(true);
    }

    @FXML
    private void onSearchFieldInput() {

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
                    if ((pred.getId() == Integer.parseInt(search_field.getText()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(2)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getFirstName().toLowerCase().contains(search_field.getText().toLowerCase()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(3)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getMiddleName().toLowerCase().contains(search_field.getText().toLowerCase()))) {

                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(4)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getLastName().toLowerCase().contains(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(6)) {
                filteredData.setPredicate(pred -> {
                    if (pred.getAge() == Integer.parseInt(newvalue)) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(7)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getSex().toLowerCase().matches(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(8)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getChildrenNo() == Integer.parseInt(newvalue))) {
                        return true;
                    }
                    return false;
                });
            } else if (filter_value == filter_items.get(9)) {
                filteredData.setPredicate(pred -> {
                    if ((pred.getMaritalStatus().toLowerCase().contains(search_field.getText().toLowerCase()))) {
                        return true;
                    }
                    return false;
                });
            }
        });
        SortedList<Patient> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortedList);
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

    @FXML
    private void onReportButtonClick(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "reports.fxml", "Reports");
    }

    private void pushNotification(String text) {
        Notifications not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(2));
        not.text(text);
        not.title("Notifiction");
        not.show();
    }

}
