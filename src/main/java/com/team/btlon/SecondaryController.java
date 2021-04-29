package com.team.btlon;

import com.team.pojo.Nganhang;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SecondaryController implements Initializable {
    
    @FXML private Button btPBack;
    @FXML private Label lbmess;
    @FXML private Label lbMa;
    @FXML private Label lbArrive;
    @FXML private Label lbDepart;
    @FXML private Label lbDaytime;
    @FXML private Label lbTimeflight;
    @FXML private RadioButton rbFirstclass;
    @FXML private RadioButton rdSecondclass;
    @FXML private Label lbPrice;
    @FXML private TextField txtStk;
    @FXML private ComboBox<Nganhang> cbBank;
    @FXML private Button btDatve;
    @FXML private Label test;
    String u;
    int i = 1;
    
    @FXML private void btBack(ActionEvent Event) throws IOException {
        if("admin".equals(u)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            PrimaryController prc = loader.getController();
            prc.Back();
            prc.getName(u);
            prc.getPass(this.test.getText());
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage1 = (Stage) this.btPBack.getScene().getWindow();
            stage1.close();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            PrimaryController prc = loader.getController();
            prc.getName(u);
            prc.getPass(this.test.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage1 = (Stage) this.btPBack.getScene().getWindow();
            stage1.close();
        }
    }
    
    public void getN(String name) {
        u = name;
    }
    
    public void getP(String test) {
        this.test.setText(test);
    }
    
    @FXML private void btPuchare (ActionEvent Event) throws SQLException {
        if (this.txtStk.getText().isBlank() || (!this.rbFirstclass.isSelected() && !this.rdSecondclass.isSelected())) {
            this.lbmess.setText("Fill Informations !!");
            this.txtStk.setStyle("-fx-border-color: red;");
            this.rbFirstclass.setStyle("-fx-border-color: red;");
            this.rdSecondclass.setStyle("-fx-border-color: red;");
        } else {     
            Connection conn = JdbcUtils.getConn();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT count(1) FROM khachhang WHERE stk_id = " + this.getstk() + " AND nganhang_id = " +
                    this.cbBank.getSelectionModel().getSelectedItem().getId_nganhang() + " AND name like '" + u + "'");
                
            while(rs.next()) {
                if (rs.getInt(1) == 1) {
                    Statement stp = conn.createStatement();
                    ResultSet rsp = stp.executeQuery("SELECT * FROM khachhang WHERE stk_id = " + this.getstk() + " AND nganhang_id = " + 
                            this.cbBank.getSelectionModel().getSelectedItem().getId_nganhang() + " AND name like '" + u + "'");
                    while(rsp.next()) {
                        TextInputDialog inp = new TextInputDialog();
                        inp.setHeaderText("Confirm password: ");
                        Optional<String> num = inp.showAndWait();
                        num.ifPresent(name -> {
                            try {
                                if(name.equals(rsp.getString("password"))) {
                                    this.getDatve(rsp.getString("id_khachhang"));
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText(null);
                                    alert.setContentText("You have Purchase succesfull!!");
                                    alert.showAndWait();  
                                    if("admin".equals(u)) {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
                                        Parent root = (Parent) loader.load();
                                        PrimaryController prc = loader.getController();
                                        prc.Back();
                                        prc.getName(u);
                                        prc.getPass(this.test.getText());

                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(root));
                                        stage.show();
                                        
                                        Stage stage1 = (Stage) this.btDatve.getScene().getWindow();
                                        stage1.close();
                                    } else {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
                                        Parent root = (Parent) loader.load();
                                        PrimaryController prc = loader.getController();
                                        prc.getName(u);
                                        prc.getPass(this.test.getText());

                                        Stage stage = new Stage();
                                        stage.setScene(new Scene(root));
                                        stage.show();

                                        Stage stage1 = (Stage) this.btDatve.getScene().getWindow();
                                        stage1.close();
                                    }
                                } else {  
                                    if (i <= 3) {
                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert1.setHeaderText(null);
                                        alert1.setContentText("You entered incorrectly the " + i + " time. Try again");
                                        alert1.showAndWait();
                                        i++;
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                            
                        if (i == 4) {
                            try {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText(null);
                                alert.setContentText("You have entered over the allowed limit");
                                alert.showAndWait();
                                
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                                Parent root = (Parent) loader.load();
                                
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                
                                Stage stage1 = (Stage) this.btDatve.getScene().getWindow();
                                stage1.close();
                            } catch (IOException ex) {
                                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }  
                    }
                } else {
                    this.lbmess.setText("Uncorrect !!!");
                }         
            }
               
            st.close();
            conn.close();
            
        }
    }
    
    public void show(String ma, String arrive, String depart, String daytime, String timeflight) {
        this.lbMa.setText(ma);
        this.lbArrive.setText(arrive);
        this.lbDepart.setText(depart);
        this.lbDaytime.setText(daytime);
        this.lbTimeflight.setText(timeflight);     
    }
    
    private String getPrimaryclass(String ma) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma = '" + ma + "'");
        
        int p = 0;
        while (rs.next())
            p = rs.getInt("id_chuyenbay");
        
        Statement stp = conn.createStatement();
        ResultSet rsp = stp.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + p + " and loai = 1");
        
        int pc = 0;
        while (rsp.next()) {
            pc = Integer.parseInt(rsp.getString("gia"));
        }
        stp.close();
        
        st.close();
        conn.close();
        
        return String.format("%,d", pc);
    }
    
    private String getSecondaryclass(String ma) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma = '" + ma + "'");
        
        int p = 0;
        while (rs.next())
            p = rs.getInt("id_chuyenbay");
        
        Statement stp = conn.createStatement();
        ResultSet rsp = stp.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + p + " and loai = 2");
        
        int pc = 0;
        while (rsp.next()) {
            pc = Integer.parseInt(rsp.getString("gia"));
        }
        stp.close();
        
        st.close();
        conn.close();
        
        return String.format("%,d", pc);
    }
    
    private void rdButton() {
        ToggleGroup chair = new ToggleGroup();
        this.rbFirstclass.setToggleGroup(chair);
        this.rdSecondclass.setToggleGroup(chair);
        
        this.rbFirstclass.selectedProperty().addListener(cl -> {
            try {
                this.lbPrice.setText(this.getPrimaryclass(this.lbMa.getText()) + " VND");
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        this.rdSecondclass.selectedProperty().addListener(cl -> {
            try {
                this.lbPrice.setText(this.getSecondaryclass(this.lbMa.getText()) + " VND");
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private List<Nganhang> getNganhang() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM nganhang");
        List<Nganhang> nhl = new ArrayList<>();
        
        while(rs.next()) {
            Nganhang nh = new Nganhang();
            nh.setId_nganhang(rs.getInt("id_nganhang"));
            nh.setTen(rs.getString("ten"));
            nhl.add(nh);
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(nhl);     
    }
    
    private String getstk() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM taikhoan WHERE stk like " + this.txtStk.getText());
        
        while(rs.next()) {
            return rs.getString("id_taikhoan");
        }
        
        return null;
    }
    
    private void getDatve(String khid) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stcb = conn.createStatement();
        ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE ma like '" + this.lbMa.getText() + 
                "' AND daytime like '" + this.lbDaytime.getText() + "' AND timeflight like '" + this.lbTimeflight.getText() + "'");
        
        String idcb = null;
        while(rscb.next()) {
            idcb = rscb.getString("id_chuyenbay");
        }
        stcb.close();
        
        String idg = null;
        if(this.rbFirstclass.isSelected()) {
            Statement stg = conn.createStatement();
            ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + idcb + " AND loai = 1");

            while(rsg.next()) {
                int sl = Integer.parseInt(rsg.getString("soluong")) - 1;
                Statement scl = conn.createStatement();
                scl.executeUpdate("UPDATE ghe SET soluong = " + sl + " WHERE chuyenbay_id = " + idcb + " AND loai = 1");
                
                idg = rsg.getString("id_ghe");
                Statement stk = conn.createStatement();
                ResultSet rstk = stk.executeQuery("SELECT * FROM taikhoan WHERE stk like '" + this.txtStk.getText() + "'");

                while(rstk.next()) {
                    int mn = Integer.parseInt(rstk.getString("money")) - Integer.parseInt(rsg.getString("gia"));
                    Statement stup = conn.createStatement();
                    stup.executeUpdate("UPDATE taikhoan SET money = " + mn + " WHERE id_taikhoan = " + this.getstk());
                }
                stk.close();
            }
            stg.close();
        } else {
            Statement stg = conn.createStatement();
            ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + idcb + " AND loai = 2");

            while(rsg.next()) {
                int sl = Integer.parseInt(rsg.getString("soluong")) - 1;
                Statement scl = conn.createStatement();
                scl.executeUpdate("UPDATE ghe SET soluong = " + sl + " WHERE chuyenbay_id = " + idcb + " AND loai = 2");
                
                idg = rsg.getString("id_ghe");
                Statement stk = conn.createStatement();
                ResultSet rstk = stk.executeQuery("SELECT * FROM taikhoan WHERE stk like '" + this.txtStk.getText() + "'");

                while(rstk.next()) {
                    int mn = Integer.parseInt(rstk.getString("money")) - Integer.parseInt(rsg.getString("gia"));
                    Statement stup = conn.createStatement();
                    stup.executeUpdate("UPDATE taikhoan SET money = " + mn + " WHERE id_taikhoan = " + this.getstk());
                }
                stk.close();
            }
            stg.close();
        }
        
        Statement dv = conn.createStatement();
        dv.executeUpdate("INSERT INTO vechuyenbay (chuyenbay_id, khachhang_id, ghe_id) VALUES (" + idcb + ", " + khid + ", " + idg + ")");
        
        conn.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rdButton();
        try {
            this.cbBank.setItems((ObservableList) this.getNganhang());
            this.cbBank.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtStk.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtStk.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
}