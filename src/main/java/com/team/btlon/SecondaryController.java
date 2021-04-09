package com.team.btlon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SecondaryController implements Initializable{
    
    @FXML private void btBack() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML private RadioButton rbFirstclass;
    @FXML private RadioButton rdSecondclass;
    
    public void radioButton (ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}