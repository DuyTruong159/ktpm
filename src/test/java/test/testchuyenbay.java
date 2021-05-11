package test;


import com.team.pojo.All;
import com.team.pojo.Chuyenbay;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duytruong
 */
public class testchuyenbay {
    
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
    public void testAddCb() throws SQLException {
        Chuyenbay cb = new Chuyenbay("T01", "1", "2", "10 May - 15:42", "4h 00m");
        Utils.addCb(cb);
        
        List<Chuyenbay> lcb = Utils.timCb("T01");
        assertEquals(1,lcb.size());
    }
    
    @Test 
    public void testUpdateCb() throws SQLException {
        Chuyenbay cb = new Chuyenbay("A08", "13", "12", "10 May - 15:42", "4h 00m");
        Utils.updateCB(cb);
        
        List<Chuyenbay> lcb = Utils.timCb("A08");
        assertEquals(1,lcb.size());
    }
    
    @Test 
    public void testDeleteCb() throws SQLException {
        Chuyenbay cb = new Chuyenbay("T01");
        Utils.deleteCb(cb);
        
        List<Chuyenbay> lcb = Utils.timCb("T01");
        assertEquals(0,lcb.size());
    }
    
    @Test
    public void testAddGhe() throws SQLException {
        Ghe g1 = new Ghe("1000", "10");
        Ghe g2 = new Ghe("2000", "30");
        Utils.addGhe(g1, g2);
        
        List<Ghe> lg = Utils.timGheCb("8");
        assertEquals(2,lg.size());
    }
    
    @Test
    public void testDeleteGheCb() throws SQLException {
        Ghe g = new Ghe("8");
        Utils.deleteGheCb(g);
        
        List<Ghe> lg = Utils.timGheCb("8");
        assertEquals(0,lg.size());
    }
    
    @Test
    public void testXemVeCb() throws SQLException {
        List<All> la = Utils.xemVeCb("1", "3");
        assertEquals(1,la.size());
    }
}
