package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duytruong
 */

import com.team.btlon.LoginController;
import com.team.pojo.Khachhang;
import com.team.service.JdbcUtils;
import com.team.service.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class testkhachhang {
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
    }
    
    @AfterAll
    public static void endClass() throws SQLException {
        JdbcUtils.getConn().close();
    }
    
    @Test
    public void testLogin() throws SQLException {
        Khachhang k = new Khachhang("truong", "123");
        List<Khachhang> lkh = Utils.getLogin(k);
        
        assertEquals(1,lkh.size());
    }
    
    @Test
    public void testRegister() throws SQLException {
        Khachhang k = new Khachhang("test", "567890", "1234", 3);
        Utils.Register(k);
        
        List<Khachhang> lkh = Utils.getLogin(k);
        assertEquals(1,lkh.size());
    }
}
