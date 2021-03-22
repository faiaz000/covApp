/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import EntityClasses.MedicalTests;
import EntityClasses.Medicine;
import EntityClasses.Patient;
import EntityClasses.Treatment;
import Reusables.MedicineObject;
import Reusables.PatientData;
import Reusables.SharedData;
import Reusables.TestObject;
import Reusables.TreatmentComboClass;
import Reusables.TreatmentData;
import com.jfoenix.controls.JFXButton;
import covapp.SceneChanger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class TreatmentController implements Initializable {
    //ObservaleArrayList<String> testItems =  new FXCollections.observableArrayList();

    @FXML
    private Label user_name;

    @FXML
    private Button logout, jasper_treatment;

    @FXML
    private Button back_button;

    @FXML
    private JFXButton edit_button;

    @FXML
    private Label patient_id_label, testName_label;

    @FXML
    private Label medicineName_label, medType_label, medAmount_label, amountUnit_label, time_label, duration_label;

    @FXML
    private Label patient_name_label;

    @FXML
    private JFXButton medicine_button;

    @FXML
    private JFXButton test_button;

    @FXML
    private TableView<Medicine> table_view;

    @FXML
    private TableColumn<Medicine, Integer> t_id;

    @FXML
    private TableColumn<Medicine, String> t_medicineName;

    @FXML
    private TableColumn<Medicine, String> t_medType;

    @FXML
    private TableColumn<Medicine, String> t_amount;

    @FXML
    private TableColumn<Medicine, String> t_morning;

    @FXML
    private TableColumn<Medicine, String> t_noon;

    @FXML
    private TableColumn<Medicine, String> t_night;

    @FXML
    private TableColumn<Medicine, Integer> t_duration;

    @FXML
    private ListView<MedicalTests> test_list;

    @FXML
    private TextArea comments;

    @FXML
    private Label current_date, age_label;

    @FXML
    private TextField med_name;

    @FXML
    private ComboBox<String> medType_comboBox;

    @FXML
    private ComboBox<TreatmentComboClass> previous_treatment;

    @FXML
    private TextField med_amount;

    @FXML
    private CheckBox day_checkbox;

    @FXML
    private CheckBox noon_checkbox;

    @FXML
    private CheckBox night_checkbox;

    @FXML
    private TextField duration;

    @FXML
    private TextField test_name;

    @FXML
    private JFXButton add_medicine, delete_medicine, clear_medicine, save_button, edit_medicine;

    @FXML
    private JFXButton add_test, edit_test, delete_test, clear_test;

    private Date presentDate;

    private ObservableList<String> medTypelist = FXCollections.observableArrayList("Tablet", "Capscule", "Syrup");
    private String currentDateString;
    private String tempName, tempAmount, tempType;
    private boolean tempMorning, tempNoon, tempNight;
    private Integer tempDuration;

    List<Medicine> medicineList = new ArrayList<Medicine>();
    List<MedicalTests> medicalTestList = new ArrayList<MedicalTests>();

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();

    private Medicine medicine = null;
    private Patient patient = null;
    private Treatment treatment = null;
    private MedicalTests medicalTests = null;
    List<Treatment> treatmentList = new ArrayList<>();
    int tableItem;
    int listItem;
    private Medicine medicineRow;
    private MedicalTests testItem;
    private boolean emergency;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        emergency = PatientData.getTreatmentState();
        definetable();
        intializeValues();

    }

    private void definetable() {
        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t_id.setStyle("-fx-alignment: CENTER");
        t_medicineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        t_medicineName.setStyle("-fx-alignment: CENTER");
        t_medType.setCellValueFactory(new PropertyValueFactory<>("type"));
        t_medType.setStyle("-fx-alignment: CENTER");
        t_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        t_amount.setStyle("-fx-alignment: CENTER");
        t_morning.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getDay();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_noon.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getNoon();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_night.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getNight();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        t_duration.setStyle("-fx-alignment: CENTER");
    }

    @FXML
    void addMedicine(ActionEvent event) {
        boolean success = true;
        getMedicineValuesFrominput();
        success = checkValidations();
        if (success) {
            success = checkDuplicateMedicine();
            if (!success) {
                medicine = new Medicine();
                medicine.setName(tempName);
                medicine.setType(tempType);
                medicine.setAmount(tempAmount);
                medicine.setDay(tempMorning);
                medicine.setNight(tempNight);
                medicine.setNoon(tempNoon);
                medicine.setDuration(tempDuration);
                //medicineList.add(medicine);
                // medicine = saveMedicineToDB();
                table_view.getItems().add(medicine);
                clearFields();

            } else {
                validationAlertBox(Alert.AlertType.ERROR, "Duplication", "This medicine is already added");

            }
        }

    }

    @FXML
    void addTest(ActionEvent event) {

        if (!test_name.getText().isEmpty()) {
            String testName = test_name.getText();

            if (!checkDuplicateTest()) {

                medicalTests = new MedicalTests();
                medicalTests.setName(testName);
                test_list.getItems().add(medicalTests);
                test_name.clear();

            } else {
                validationAlertBox(Alert.AlertType.ERROR, "Duplicate", "This Test is already added");
            }

        } else {

            validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Test Name must be inserted");
        }

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        String role = SharedData.getRole();
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "patientinfo.fxml", role + " Panel");
    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");
    }

    @FXML
    void onClear(ActionEvent event) {
        clearFields();
        disableModificationButtonns();
        disableTestModificationButtions();
    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        if (table_view.getItems().size() > 0) {
            treatment = addPrescription();
            if (treatment != null) {
                TreatmentData treatmentData = new TreatmentData();
                treatmentData.setPreviousPrescription(true);
                saveMedicinesToDB();
                saveTestToDb();
                changeToExistingTreatments(event);
            } else {
                //
            }
        }

    }

    private void setPresentDateTime() {
        Date in = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        presentDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        currentDateString = dateFormat.format(in).toString();
    }

    private boolean checkValidations() {
        boolean success = true;
        if (tempName.isEmpty()) {
            success = validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Medicine name must be inserted");
        }
        if (tempType == null) {
            success = validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Medicine type must be inserted");
        }
        if (tempDuration == null) {
            success = validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Medicine duration must be inserted");
        }
        if (tempAmount.isEmpty()) {
            success = validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Medicine amount must be inserted");
        }

        return success;
    }

    private boolean validationAlertBox(Alert.AlertType alertType, String title, String contentText) {

        boolean success = false;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle(" -fx-font-weight: bold");

        if (alertType.equals(Alert.AlertType.ERROR)) {

            alert.showAndWait();
        } else if (alertType.equals(Alert.AlertType.CONFIRMATION)) {
            alert.getButtonTypes().remove(0, 2);

            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);

            Optional<ButtonType> selection = alert.showAndWait();

            if (selection.get() == ButtonType.YES || selection.get() == ButtonType.OK) {
                success = true;
            } else if (selection.get() == ButtonType.CANCEL || selection.get() == ButtonType.NO) {
                success = false;
            }
        }

        return success;

    }

    private void clearFields() {
        medType_comboBox.getSelectionModel().clearSelection();
        night_checkbox.setSelected(false);
        noon_checkbox.setSelected(false);
        day_checkbox.setSelected(false);
        med_name.clear();
        med_amount.clear();
        duration.clear();
        test_name.clear();
        comments.clear();
    }

    private Treatment addPrescription() {
        treatment = new Treatment();
        treatment.setComments(comments.getText());
        treatment.setPatientId(patient);
        //check role
        treatment.setDoctorId(SharedData.getUserObject());
        setPresentDateTime();
        treatment.setDate(presentDate);
        treatment.setEtreatment(emergency);
        entityManager.getTransaction().begin();
        entityManager.persist(treatment);
        entityManager.getTransaction().commit();
        //entityManager.close();
        return treatment;
    }

    private void saveMedicinesToDB() {

        table_view.getItems().forEach(med -> {
            if (!entityManager.getTransaction().isActive()) {

                entityManager.getTransaction().begin();
            }

            medicine = new Medicine();
            medicine.setName(med.getName());
            medicine.setType(med.getType());
            medicine.setAmount(med.getAmount());
            medicine.setDuration(med.getDuration());
            medicine.setTreatmentId(treatment);
            medicine.setDay(med.getDay());
            medicine.setNoon(med.getNoon());
            medicine.setNight(medicine.getNight());
            entityManager.persist(medicine);
            entityManager.getTransaction().commit();

        });

    }

    private void saveTestToDb() {
        ObservableList<MedicalTests> medTestList = test_list.getItems();
        if (medTestList.size() > 0) {
            medTestList.forEach(medicaltest -> {
                if (!entityManager.getTransaction().isActive()) {

                    entityManager.getTransaction().begin();
                }

                medicalTests = new MedicalTests();
                medicalTests.setName(medicaltest.getName());
                medicalTests.setTreatmentId(treatment);
            });

            entityManager.persist(medicalTests);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    }

    private void intializeValues() {

        user_name.setText(SharedData.getUser());

        medType_comboBox.setItems(medTypelist);
        patient = PatientData.getPatient();
        patient_id_label.setText("Patient Id:   " + patient.getId());
        patient_name_label.setText("Patient Name:   " + patient.getFullName());
        age_label.setText(Integer.toString(patient.getAge()));
        setPresentDateTime();
        current_date.setText("Date: " + currentDateString);
        if (TreatmentData.getExistence()) {
            previous_treatment.setVisible(true);
            previous_treatment.setDisable(false);
            treatmentList = getTreatmentList();
            setDateComboBox();
        }

    }

    @FXML
    private void onDateSelect(ActionEvent event) {
        int treatmentId;
        TreatmentComboClass treatmentComboClass = previous_treatment.getSelectionModel().getSelectedItem();
        treatmentId = treatmentComboClass.getId();
        getMedicineListFromDb(treatmentId);
        getTestListFromDb(treatmentId);

        String commentText = treatmentList.stream()
                .filter(x -> treatmentId == x.getId())
                .map(Treatment::getComments) //convert stream to String
                .findAny()
                .orElse("");
        comments.setText(commentText);
    }

    private void getMedicineListFromDb(int selectedTreatmentId) {
        Query query = entityManager.createNativeQuery("Select * from medicine  where  treatment_id = ? ", Medicine.class);
        query.setParameter(1, selectedTreatmentId);
        List<Medicine> result = query.getResultList();
        if (result.size() > 0) {
            table_view.getItems().clear();
            table_view.getItems().addAll(result);
        }
    }

    private void getTestListFromDb(int selectedTreatmentId) {
        test_list.getItems().clear();
        Query query = entityManager.createNativeQuery("Select id,name  from medical_tests  where  treatment_id = ? ", MedicalTests.class);
        query.setParameter(1, selectedTreatmentId);
        List<MedicalTests> result = query.getResultList();
        if (result.size() > 0) {

            test_list.getItems().addAll(result);
        }
    }

    @FXML
    private void onTableRowSelect(MouseEvent event) {
        if (table_view.getItems().size() > 0) {

            enableModificationButtonns();
            medicineRow = table_view.getSelectionModel().getSelectedItem();
            tableItem = table_view.getSelectionModel().selectedIndexProperty().get();
            med_name.setText(medicineRow.getName());
            medType_comboBox.setValue(medicineRow.getType());
            med_amount.setText(medicineRow.getAmount());
            duration.setText(String.valueOf(medicineRow.getDuration()));
            day_checkbox.setSelected(medicineRow.getDay());
            noon_checkbox.setSelected(medicineRow.getNoon());
            night_checkbox.setSelected(medicineRow.getNight());

        }

    }

    @FXML
    private void loadPreviousTreatment(ActionEvent event) throws IOException {
        //  changeToExistingTreatments(event);
    }

    private void changeToExistingTreatments(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "existingtreatment.fxml", "Treatment Panel");
    }

    private void enableModificationButtonns() {

        edit_medicine.setVisible(true);
        edit_medicine.setDisable(false);
        delete_medicine.setVisible(true);
        delete_medicine.setDisable(false);
        save_button.setDisable(true);
        save_button.setVisible(false);

    }

    private void disableModificationButtonns() {
        edit_medicine.setVisible(false);
        edit_medicine.setDisable(true);
        delete_medicine.setVisible(false);
        delete_medicine.setDisable(true);
        add_medicine.setVisible(true);
        add_medicine.setDisable(false);
        save_button.setDisable(false);
        save_button.setVisible(true);
    }

    @FXML
    private void onEditMedicine(ActionEvent event) {
        getMedicineValuesFrominput();
        boolean success = checkValidations();
        if (success) {
            Medicine editedMed = new Medicine();
            editedMed.setName(tempName);
            editedMed.setType(tempType);
            editedMed.setAmount(tempAmount);
            editedMed.setDuration(tempDuration);
            editedMed.setNight(tempNight);
            editedMed.setNoon(tempNoon);
            editedMed.setDay(tempMorning);
            table_view.getItems().set(tableItem, editedMed);
            table_view.refresh();
            disableModificationButtonns();
            clearFields();

        }

    }

    private void getMedicineValuesFrominput() {
        tempName = med_name.getText();
        tempAmount = med_amount.getText();
        tempMorning = day_checkbox.isSelected();
        tempNoon = noon_checkbox.isSelected();
        tempNight = night_checkbox.isSelected();
        tempDuration = duration.getText().isEmpty() ? null : Integer.parseInt(duration.getText());
        tempType = medType_comboBox.getValue();

    }

    @FXML
    private void onTestSelect(MouseEvent event) {
        if (test_list.getItems().size() > 0) {

            testItem = test_list.getSelectionModel().getSelectedItem();
            listItem = test_list.getSelectionModel().selectedIndexProperty().get();
            test_name.setText(testItem.getName());
            enableTestModificationButtions();

        }

    }

    @FXML
    private void editTest(ActionEvent event) {
        String testName = test_name.getText();

        if (!testName.isEmpty() && !testItem.getName().matches(testName)) {
            MedicalTests medTest = new MedicalTests();
            medicalTests.setName(testName);
            test_list.getItems().set(listItem, testItem);
            test_name.clear();
            disableTestModificationButtions();

        } else {
            if (testName.matches(testItem.getName())) {
                validationAlertBox(Alert.AlertType.ERROR, "No Modification", "No modificsation is made");
                disableTestModificationButtions();
            } else {
                validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Test Name must be inserted");
                disableTestModificationButtions();
            }
            validationAlertBox(Alert.AlertType.ERROR, "Empty Field Validation", "Test Name must be inserted");
            disableTestModificationButtions();

        }
    }

    private void enableTestModificationButtions() {
        edit_test.setVisible(true);
        edit_test.setDisable(false);
        delete_test.setVisible(true);
        delete_test.setDisable(false);
        add_test.setVisible(false);
        add_test.setDisable(true);
    }

    private void disableTestModificationButtions() {
        edit_test.setVisible(false);
        edit_test.setDisable(true);
        delete_test.setVisible(false);
        delete_test.setDisable(true);
        add_test.setVisible(true);
        add_test.setDisable(false);
    }

    @FXML
    private void deleteMedicine(ActionEvent event) {

        table_view.getItems().remove(tableItem);
        disableModificationButtonns();
    }

    @FXML
    private void deleteTest(ActionEvent event) {
        boolean success = success = validationAlertBox(Alert.AlertType.CONFIRMATION, "Confirmation", "Are you sure , you wan't to delete this item");
        if (success) {
            test_list.getItems().remove(listItem);
        }
        test_name.clear();
        disableTestModificationButtions();

    }

    private boolean checkDuplicateMedicine() {

        boolean success = table_view.getItems().stream().filter(predicate
                -> predicate.getName().matches(med_name.getText())
        ).findFirst().isPresent();

        return success;
    }

    private boolean checkDuplicateTest() {

        boolean success = test_list.getItems().stream().filter(predicate
                -> predicate.getName().matches(test_name.getText())
        ).findFirst().isPresent();

        return success;
    }

    private void setDateComboBox() {
        ObservableList<TreatmentComboClass> comboClass = FXCollections.observableArrayList();

        treatmentList.forEach(prescription -> {

            String datetSring = formatDateCombobox(prescription.getDate());
            comboClass.add(new TreatmentComboClass(prescription.getId(), datetSring));

        });
        previous_treatment.setItems(comboClass);
        int size = comboClass.size() - 1;

        //previous_treatment.setValue(comboClass.get(size));
    }

    private String formatDateCombobox(Date treatmentDate) {
        String formattedDate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = dateFormat.format(treatmentDate).toString();
        return formattedDate;
    }

    private List<Treatment> getTreatmentList() {
        int id = PatientData.getPatient().getId();
        Query treatmentquery = entityManager.createNativeQuery("Select * from treatment  where  patient_id = ? ", Treatment.class);
        treatmentquery.setParameter(1, id);

        List<Treatment> list = treatmentquery.getResultList();
        return list;

    }

    @FXML
    private void onPrescription() throws JRException {

        Platform.runLater(() -> {
            try {

                ArrayList<MedicineObject> mList = new ArrayList<MedicineObject>(getMed());
                ArrayList<TestObject> tList = new ArrayList<TestObject>(getTests());
                if (mList.size() > 0) {
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(mList);
                    JRBeanCollectionDataSource beanCollectionDataSource1 = new JRBeanCollectionDataSource(tList);
                    JasperDesign subDesign = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/Reports/prescription.jrxml");
                    JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/Reports/prescription.jrxml");

                    HashMap params = new HashMap();
                    params.put("doctorName", user_name.getText());
                    params.put("doctorPhone", SharedData.getUserObject().getPhone());
                    params.put("doctorEmail", SharedData.getUserObject().getEmail());
                    params.put("pName", PatientData.getPatient().getFullName());
                    params.put("pAge", String.valueOf(PatientData.getPatient().getAge()));
                    params.put("date", formatDateCombobox(presentDate));
                    params.put("testList", beanCollectionDataSource1);

                    JasperReport subReport = JasperCompileManager.compileReport(subDesign);
                    JasperReport jr = JasperCompileManager.compileReport(jd);
                    JasperPrint jp = JasperFillManager.fillReport(jr, params, beanCollectionDataSource);
                    JasperViewer viewer = new JasperViewer(jp, false);
                    viewer.setVisible(true);
                }

            } catch (JRException ex) {
                Logger.getLogger(TreatmentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    private List<MedicineObject> getMed() {
        List<MedicineObject> list = new ArrayList<MedicineObject>();
        table_view.getItems().forEach(action -> {
            MedicineObject med = new MedicineObject();
            med.setMedicineName(action.getName());
            med.setMedicineAmount(action.getAmount());
            med.setMedicineType(action.getType());
            med.setMedicineDuration(action.getDuration());
            med.setNoon(action.getNoon());
            med.setNight(action.getNight());
            med.setMorning(action.getDay());

            list.add(med);

        });

        return list;
    }

    private List<TestObject> getTests() {
        List<TestObject> list = new ArrayList<TestObject>();
        test_list.getItems().forEach(action -> {
            TestObject testObject = new TestObject();
            testObject.setTestName(action.getName());
            list.add(testObject);
        });
        if(list.size()<1){
            TestObject testObject = new TestObject();
            testObject.setTestName("No Tests Prescribed");
            list.add(testObject);
        }
        return list;
    }

    private void pushNotification(String text) {
        Notifications not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(2));
        not.text(text);
        not.title("Notifiction");
        not.show();
    }

    @FXML
    private void clearTest(ActionEvent event) {
        test_name.clear();

    }
}
