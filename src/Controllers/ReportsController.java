/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SymptomsList;
import Reusables.SharedData;
import EntityClasses.Signs;
import EntityClasses.Symptoms;
import Reusables.SymptomsObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class ReportsController implements Initializable {

    @FXML
    BarChart<String, Integer> bar_chart;
    @FXML
    private Label year_label;
    @FXML
    PieChart pie_chart;
    @FXML
    JFXComboBox<Integer> year_comboBox;
    @FXML
    final Label caption = new Label("");
    @FXML
    Button back_button, logout, covidArea;
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Symptoms symptoms;
    private Signs signs;
    private String role = "";
    public List<SymptomsObject> symptomsList = new ArrayList<>();
    Integer totalCases = 0;
    Integer totalSymptomsFrequency = 0;

    @FXML
    private JFXButton covid_button, outcomes_button, symptoms_button;
    private int totalRecords = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        role = SharedData.getRole();
        /* cov_bar.setDisable(true);
        cov_bar.setVisible(false);*/
        year_comboBox.setDisable(true);
        year_comboBox.setVisible(false);
        year_label.setVisible(false);
        generateBarChart();
    }

    private void generateBarChart() {
        totalSymptomsFrequency = 0;

        disabeleYearSelection();
        SymptomsList symp = new SymptomsList();
        // List<SymptomsObject> symptomsList = symp.getSymptoms();
        ObservableList<PieChart.Data> pieChartSymptoms = FXCollections.observableArrayList();

        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        symp.getSymptoms().forEach(obj -> {
            totalSymptomsFrequency = obj.getFrequency() + totalSymptomsFrequency;
            series.getData().add(new XYChart.Data<String, Integer>(obj.getSymptoms(), obj.getFrequency()));
            pieChartSymptoms.add(new PieChart.Data(obj.getSymptoms(), obj.getFrequency()));

        });

        // bar_chart.getData().add(series);
        bar_chart.setData(FXCollections.observableArrayList(series));
        bar_chart.setTitle("Frequency of Different Symptoms");
        pie_chart.getData().clear();
        pie_chart.setTitle("Symptoms Distribution");
        // pie_chart.setData(pieChartSymptoms);
        pie_chart.setData(FXCollections.observableArrayList(pieChartSymptoms));
        setToolTip(totalSymptomsFrequency);

    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {

        previousScene(event);

    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    private void previousScene(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "doctorpanel.fxml", role + " Panel");

    }

    @FXML
    private void onSymptomsClick(ActionEvent event) {
        generateBarChart();
    }

    @FXML
    private void onCovidClick(ActionEvent event) {
        year_label.setVisible(true);
        populateYearBox();

        bar_chart.getData().clear();
        getCovidInfoByYear();

    }

    @FXML
    private void onYearSelection(ActionEvent event) {
        getCovidInfoByYear();
    }

    private void populateYearBox() {
        year_comboBox.setVisible(true);
        year_comboBox.setDisable(false);
        ObservableList<Integer> yearList = FXCollections.observableArrayList(2019, 2020, 2021, 2022);
        year_comboBox.setItems(yearList);
        LocalDate now = LocalDate.now();
        int temp = now.getYear();
        year_comboBox.getSelectionModel().select(2);
        // year_comboBox.setValue(temp);

    }

    private void getCovidInfoByYear() {
        totalRecords = 0;
        Integer yr = year_comboBox.getValue();
        Query query = entityManager.createNativeQuery("SELECT YEAR(addmission_date) AS y, MONTHNAME(addmission_date) "
                + "AS m, COUNT(DISTINCT patient.id) FROM patient WHERE YEAR(addmission_date)"
                + "= ? GROUP BY y, m ORDER By MONTH(addmission_date)");
        query.setParameter(1, yr);

        List<Object[]> covInfoList = query.getResultList();
        XYChart.Series<String, Integer> cov = new XYChart.Series<String, Integer>();
        ObservableList<PieChart.Data> pieChartCovidFrquency = FXCollections.observableArrayList();
        covInfoList.forEach(action -> {
            String s = action[1].toString();
            int count = Integer.parseInt(action[2].toString());
            totalRecords = totalRecords + count;
            cov.getData().add(new XYChart.Data<String, Integer>(s, count));
            pieChartCovidFrquency.add(new PieChart.Data(s, count));
        });
        //  bar_chart = new BarChart<String,Integer>(cov);
        bar_chart.getData().clear();
        bar_chart.setData(FXCollections.observableArrayList(cov));
        bar_chart.setTitle("Covid Frequency By Months");

        pie_chart.getData().clear();
        pie_chart.setTitle("Covid Frequency By Months");
        pie_chart.setData(pieChartCovidFrquency);
        setToolTip(totalRecords);

    }

    private void disabeleYearSelection() {
        year_comboBox.setDisable(true);
        year_comboBox.setVisible(false);
        year_label.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    private void effectedByArea() {
        Query totalCaseQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM address");
        totalCases = ((Long) totalCaseQuery.getSingleResult()).intValue();

        Query query = entityManager.createNativeQuery("SELECT area, COUNT(*) AS freq FROM address GROUP BY area");
        List<Object[]> areaInfoList = query.getResultList();
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        ObservableList<PieChart.Data> covByArea = FXCollections.observableArrayList();
        areaInfoList.forEach(action -> {
            String areaName = action[0].toString();
            int count = Integer.parseInt(action[1].toString());
            series.getData().add(new XYChart.Data<String, Integer>(areaName, count));
            covByArea.add(new PieChart.Data(areaName + " (" + count + " case)", count));
        });
        /*cov_bar.setVisible(true);
        cov_bar.setDisable(false);*/
        bar_chart.getData().clear();
        bar_chart.setTitle("Frequency Of covid cases by area");

        bar_chart.setData(FXCollections.observableArrayList(series));
        pie_chart.getData().clear();
        pie_chart.setTitle("Covid Cases by Area");
        pie_chart.setData(FXCollections.observableArrayList(covByArea));
        setToolTip(totalCases);

    }

    @FXML
    private void covidCasesByArea(ActionEvent event) {
        // bar_chart.setVisible(false);
        // bar_chart.setDisable(true);
        disabeleYearSelection();
        effectedByArea();

    }

    private void setToolTip(int total) {
            pie_chart.getData().forEach(data -> {
            double intVal = (data.getPieValue());
            double calc = intVal / (double) total * 100;
            String percentage = String.valueOf(String.format("%.2f", calc) + "%");
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
        });
    }

}
