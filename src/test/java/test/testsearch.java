package test;


import com.team.pojo.Chuyenbay;
import com.team.service.JdbcUtils;
import com.team.service.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duytruong
 */
public class testsearch {
    
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
    public void testSearchCbByArrive() throws SQLException {
        List<Chuyenbay> lcb = Utils.searchCbByArrive("TP HCM (SGN)");
        assertEquals(1, lcb.size());
    }
    
    @Test
    public void testSearchCbByDepart() throws SQLException {
        List<Chuyenbay> lcb = Utils.searchCbByDepart("Hà nội (HAN)");
        assertEquals(1, lcb.size());
    }
}
