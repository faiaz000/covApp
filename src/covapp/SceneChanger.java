/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covapp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class SceneChanger {
    
    private BorderPane borderPane;
    String path = "/Views/";
 
    public void changeScene(ActionEvent event, String viewpath, String title) throws IOException {
        path = path + viewpath;
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();     
        stage.getScene().setRoot(root);
        stage.setTitle(title);
        stage.sizeToScene();

    }

    public void changeScene(MouseEvent event, String viewpath, String title) throws IOException {
        path = path + viewpath;
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource(path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setTitle(title);
        stage.sizeToScene();
    }
    

}
