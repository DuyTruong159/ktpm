package com.team.btlon;

import com.team.pojo.Chuyenbay;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
     
    @FXML private TextField txtArrive;
    @FXML private TextField txtDepart;
    @FXML private TableView<Chuyenbay> tbCb;
    @FXML private Label lbname;
    @FXML private Button btClose;
    @FXML private Button btBack;
    @FXML private Button btXemcb;
    @FXML private Label p;
    
    @FXML private void btXem (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("xem.fxml"));
            Parent root = (Parent) loader.load();
            XemController xc = loader.getController();
            xc.getN(this.lbname.getText());
            xc.getP(this.p.getText());
            try {
                xc.getsodu(this.lbname.getText(), this.p.getText());
                xc.getcbdadat(this.lbname.getText(), this.p.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btXemcb.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void btLout (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btClose.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    @FXML private void btABack (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btBack.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Back() {
        this.btClose.setVisible(false);
        this.btBack.setVisible(true);
    }
    
    public void getName(String text) {
        this.lbname.setText(text);
    }
    
    public void getPass(String pass) {
        this.p.setText(pass);
    }
    
    private List<Chuyenbay> getChuyenbays() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM chuyenbay");
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Chuyenbay cb = new Chuyenbay();
            cb.setMa(rs.getString("ma"));
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("arrive_id"));
            while (rs1.next()) {
                cb.setArrive(rs1.getString("sanbay"));
            }
            rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("depart_id"));
            while (rs1.next()) {
                cb.setDepart(rs1.getString("sanbay"));
            }
            stm1.close();
            cb.setDaytime(rs.getString("daytime"));
            cb.setTimeflight(rs.getString("timeflight"));
            lcb.add(cb);
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);
    }
    
    private List<Chuyenbay> getChuyenbayByArrive(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM chuyenbay WHERE arrive_id = " + rs.getString("id_sanbay"));
            while (rs1.next()) {
                Chuyenbay cb = new Chuyenbay();
                cb.setMa(rs1.getString("ma"));
                cb.setArrive(rs.getString("sanbay"));
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs1.getString("depart_id"));
                while(rs2.next()) {
                    cb.setDepart(rs2.getString("sanbay"));
                }
                cb.setDaytime(rs1.getString("daytime"));
                cb.setTimeflight(rs1.getString("timeflight"));
                lcb.add(cb);
            }
            stm1.close();
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);   
    }
    
    private List<Chuyenbay> getChuyenbayByDepart(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM chuyenbay WHERE depart_id = " + rs.getString("id_sanbay"));
            while (rs1.next()) {
                Chuyenbay cb = new Chuyenbay();
                cb.setMa(rs1.getString("ma"));
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs1.getString("arrive_id"));
                while(rs2.next()) {
                    cb.setArrive(rs2.getString("sanbay"));
                }
                cb.setDepart(rs.getString("sanbay"));
                cb.setDaytime(rs1.getString("daytime"));
                cb.setTimeflight(rs1.getString("timeflight"));
                lcb.add(cb);
            }
            stm1.close();
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);   
    }
    
    private void loadChuyenbay() throws SQLException {
        TableColumn colMa = new TableColumn("Ma"); 
        colMa.setCellValueFactory(new PropertyValueFactory("ma"));
        
        TableColumn colArrive = new TableColumn("Arrive"); 
        colArrive.setCellValueFactory(new PropertyValueFactory("arrive"));
        
        TableColumn colDepart = new TableColumn("Depart"); 
        colDepart.setCellValueFactory(new PropertyValueFactory("depart"));
        
        TableColumn colDayTime = new TableColumn("DayTime"); 
        colDayTime.setCellValueFactory(new PropertyValueFactory("daytime"));
        
        TableColumn colTimeFlight = new TableColumn("TimeFlight"); 
        colTimeFlight.setCellValueFactory(new PropertyValueFactory("timeflight"));
        
        TableColumn colDatve = new TableColumn("Dat ve");
        colDatve.setCellFactory(p -> {
            Button bt = new Button("Dat ve");
            bt.setOnAction(et -> {
                try {
                    TableCell cl = (TableCell) ((Button)et.getSource()).getParent();
                    Chuyenbay cb = (Chuyenbay)cl.getTableRow().getItem();
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
                    Parent root = (Parent) loader.load();
                    
                    SecondaryController scl = loader.getController();
                    scl.show(cb.getMa(), cb.getArrive(), cb.getDepart(), cb.getDaytime(), cb.getTimeflight());
                    scl.getN(this.lbname.getText());
                    
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
            
                    Stage stage1 = (Stage) bt.getScene().getWindow();
                    stage1.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            TableCell cell = new TableCell();
            cell.setGraphic(bt);
            return cell;
        });
          
        this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        this.tbCb.getColumns().addAll(colMa, colArrive, colDepart,  colDayTime, colTimeFlight, colDatve); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtArrive.textProperty().addListener(et -> {
            try {
                this.tbCb.getItems().clear();
                this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbayByArrive(this.txtArrive.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        });
        
        this.txtDepart.textProperty().addListener(et -> {
            try {
                this.tbCb.getItems().clear();
                this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbayByDepart(this.txtDepart.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        });
        
        this.btBack.setVisible(false);
    }
}
        
        
    
            
        
                
         
        
        
    

