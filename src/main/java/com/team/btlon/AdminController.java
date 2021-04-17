/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class AdminController implements Initializable {

    @FXML private TableView<Chuyenbay> tbCb;
    @FXML private Button btClose;
    @FXML private TextField txtMa;
    @FXML private TextField txtArrive;
    @FXML private TextField txtDepart;
    @FXML private TextField txtDaytime;
    @FXML private TextField txtTimeflight;
    @FXML private Label lbmess;
    @FXML private Button btUpdate;
    
    @FXML private void btLogout (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btClose.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int addArrive() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement sta = conn.prepareStatement("SELECT count(1) FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        sta.setString(1, this.txtArrive.getText());
        ResultSet rsa = sta.executeQuery();
        
        int a = 0;
        while(rsa.next()) {
            if(rsa.getInt(1) == 1) {
                PreparedStatement stga = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stga.setString(1, this.txtArrive.getText());
                ResultSet rsga = stga.executeQuery();
                while(rsga.next()) {
                    a = rsga.getInt("id_sanbay");
                }
                stga.close();
            } else {
                Statement sti = conn.createStatement();
                sti.executeUpdate("INSERT INTO sanbay (sanbay) VALUES ('" + this.txtArrive.getText() + "')"); 
                
                PreparedStatement stga = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stga.setString(1, this.txtArrive.getText());
                ResultSet rsga = stga.executeQuery();
                while(rsga.next()) {
                    a = rsga.getInt("id_sanbay");
                }
                stga.close();
            } 
        }
        sta.close();
        conn.close();
        
        return a;
    }
    
    private int addDepart() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement std = conn.prepareStatement("SELECT count(1) FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        std.setString(1, this.txtDepart.getText());
        ResultSet rsd = std.executeQuery();
        
        int d = 0;
        while(rsd.next()) {
            if(rsd.getInt(1) == 1) {
                PreparedStatement stgd = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stgd.setString(1, this.txtDepart.getText());
                ResultSet rsga = stgd.executeQuery();
                while(rsga.next()) {
                    d = rsga.getInt("id_sanbay");
                }
                stgd.close();
            } else {
                Statement sti = conn.createStatement();
                sti.executeUpdate("INSERT INTO sanbay (sanbay) VALUES ('" + this.txtDepart.getText() + "')");  
                
                PreparedStatement stgd = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
                stgd.setString(1, this.txtDepart.getText());
                ResultSet rsga = stgd.executeQuery();
                while(rsga.next()) {
                    d = rsga.getInt("id_sanbay");
                }
                stgd.close();
            } 
        }
        
        std.close();
        conn.close();
        
        return d;
    }
    
    @FXML private void btAdd (ActionEvent Event) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        if(this.txtMa.getText() == "" || this.txtDepart.getText() == "" || 
                this.txtArrive.getText() == "" || this.txtDaytime.getText() == "" 
                || this.txtTimeflight.getText() == "") {
            
            this.lbmess.setText("Information Empty!!");
            this.txtMa.setStyle("-fx-border-color: red;");
            this.txtArrive.setStyle("-fx-border-color: red;");
            this.txtDepart.setStyle("-fx-border-color: red;");
            this.txtDaytime.setStyle("-fx-border-color: red;");
            this.txtTimeflight.setStyle("-fx-border-color: red;");
            
        } else {
            PreparedStatement sta = conn.prepareStatement("INSERT INTO chuyenbay (ma, arrive_id, depart_id, daytime, timeflight) VALUES "
                + "('" + this.txtMa.getText() + "', " + this.addArrive() + ", " + this.addDepart() 
                + ", '" + this.txtDaytime.getText() + "', '" + this.txtTimeflight.getText() + "')");
            sta.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("New chuyenbay has added!!");
            alert.setHeaderText(null);
            alert.showAndWait();

            this.tbCb.getItems().clear();
            this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        }
    
        conn.close();
    }
    
    @FXML private void btUdate (ActionEvent Event) {

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
    
    private void deleteChuyenbay(String id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        conn.setAutoCommit(false);
        Statement ps = conn.createStatement();
        ps.executeUpdate("DELETE FROM chuyenbay WHERE id_chuyenbay = " + id);
        
        conn.commit();
    }
    
    private void loadChuyenbay() throws SQLException {
        
        TableColumn colId = new TableColumn("Id"); 
        colId.setCellValueFactory(new PropertyValueFactory("id_chuyenbay"));
        
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
        
        TableColumn colDelete = new TableColumn("Delete");
        colDelete.setCellFactory(p -> {
            Button btDelete = new Button("Delete");
            btDelete.setOnAction(et -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure to delete this chuyenbay ??");
                alert.showAndWait().ifPresent(res -> {
                    if(res == ButtonType.OK) {
                        TableCell cl = (TableCell) ((Button)et.getSource()).getParent();
                        Chuyenbay cb = (Chuyenbay)cl.getTableRow().getItem();
                        try {
                            this.deleteChuyenbay(cb.getId_chuyenbay());
                            this.lbmess.setText("Sao nó ko xóa, coi clip sửa dùm t bay");
                            
                            this.tbCb.getItems().clear();
                            this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btDelete);
            return cell;
        });
        
        this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        this.tbCb.getColumns().addAll(colMa, colArrive, colDepart,  colDayTime, colTimeFlight, colDelete); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.btUpdate.setVisible(false);
        
        this.tbCb.setRowFactory(et -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(t -> {
                this.btUpdate.setVisible(true);
                Chuyenbay q = this.tbCb.getSelectionModel().getSelectedItem();
                this.txtMa.setText(q.getMa());
                this.txtArrive.setText(q.getArrive());
                this.txtDepart.setText(q.getDepart());
                this.txtDaytime.setText(q.getDaytime());
                this.txtTimeflight.setText(q.getTimeflight());
            });
            return row;
        });
    }    
    
}
