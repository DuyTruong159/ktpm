/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.team.pojo.All;
import com.team.pojo.Ghe;
import com.team.pojo.Vechuyenbay;
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

/**
 *
 * @author duytruong
 */
public class testvechuyenbay {
    
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
    public static void testDatve() throws SQLException {
        Vechuyenbay vcb = new Vechuyenbay("8", "3", "14");
        Utils.datVe(vcb);
        
        List<All> la = Utils.xemVeCb("8", "3");
        assertEquals(1,la.size());
    }
    
//    @Test
//    public static void testDoiVechuyenbay() throws SQLException {
//        Vechuyenbay vcb = new Vechuyenbay("13", "8");
//        Utils.doiVechuyenbay(vcb);
//        
//        List<All> la = Utils.XemCbByGhe("8", "13");
//        assertEquals(1,la.size());      
//    }
//    
//    @Test 
//    public static void testDoighe() throws SQLException {
//        Ghe g = new Ghe(30, "13");
//        Utils.doiGhe(g);
//        
//        List<Ghe> lg = Utils.checkDoighe(30, "13");
//        assertEquals(1, lg.size());
//    }
//    
//    @Test
//    public static void huyVechuyenbay() throws SQLException {
//        Vechuyenbay vcb = new Vechuyenbay("3", "8");
//        Utils.huyVechuyenbay(vcb);
//        
//        List<All> lvcb = Utils.xemVeCb("8", "3");
//        assertEquals(0,lvcb.size());
//    }
}
