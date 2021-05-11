/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.service;

import com.team.pojo.All;
import com.team.pojo.Chuyenbay;
import com.team.pojo.Ghe;
import com.team.pojo.Khachhang;
import com.team.pojo.Vechuyenbay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duytruong
 */
public class Utils {
    
    public static List<Khachhang> getLogin(Khachhang k) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM khachhang WHERE name = '" 
                                        + k.getName() + "' and password = '" + k.getPassword() + "'");
        List<Khachhang> lkh = new ArrayList<>();
        while(rs.next()) {
            Khachhang kh = new Khachhang();
            kh.setName(rs.getString("name"));
            kh.setPassword(rs.getString("password"));
            lkh.add(kh);
        }
        
        return lkh;
    }
    
    public static void Register(Khachhang k) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        stm.executeUpdate("INSERT INTO khachhang (name, cmnd, nganhang_id, password) VALUES "
                + "('" + k.getName() +"','" + k.getCmnd() + "','" + k.getNganhang_id() + "','" + k.getPassword() + "')");    
    }
    
    public static void addCb(Chuyenbay cb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement sta = conn.prepareStatement("INSERT INTO chuyenbay (ma, arrive_id, depart_id, daytime, timeflight) VALUES "
                        + "('" + cb.getMa() + "', " + cb.getArrive() + ", " + cb.getDepart()
                        + ", '" + cb.getDaytime() + "', '" + cb.getTimeflight() + "')");
        sta.executeUpdate();
        conn.close();
    }
    
    public static List<Chuyenbay> timCb(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM chuyenbay WHERE ma like '" + kw + "'");
        List<Chuyenbay> lcb = new ArrayList<>();
        while(rs.next()) {
            Chuyenbay cb = new Chuyenbay();
            cb.setMa(rs.getString("ma"));
            cb.setArrive(rs.getString("arrive_id"));
            cb.setDepart(rs.getString("depart_id"));
            cb.setDaytime(rs.getString("daytime"));
            cb.setTimeflight(rs.getString("timeflight"));
            
            lcb.add(cb);
        }
        return lcb;
    }
    
    public static void updateCB(Chuyenbay cb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stu = conn.createStatement();
        stu.executeUpdate("UPDATE chuyenbay SET arrive_id = " + cb.getArrive() + ", depart_id = " + cb.getDepart() +
                            ", daytime = '" + cb.getDaytime() + "', timeflight = '" + cb.getTimeflight() + 
                            "' WHERE ma = '" + cb.getMa() + "'");
    }
    
    public static void deleteCb(Chuyenbay cb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        conn.setAutoCommit(false);
        Statement psg = conn.createStatement();
        ResultSet rs = psg.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma like '" + cb.getMa() + "'");
        while(rs.next()) {
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM chuyenbay WHERE id_chuyenbay = " + rs.getString(1));
        }
        conn.commit();
    }
    
    public static void deleteGheCb(Ghe g) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        conn.setAutoCommit(false);
        Statement ps = conn.createStatement();
        ps.executeUpdate("DELETE FROM ghe WHERE chuyenbay_id = " + g.getCb_id());
        
        conn.commit();
    }
    
    public static List<Ghe> timGheCb(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + kw);
        List<Ghe> lg = new ArrayList<>();
        while(rs.next()) {
            Ghe g = new Ghe();
            g.setGia(rs.getString("gia"));
            g.setSoluong(rs.getString("soluong"));
            lg.add(g);
        }
        return lg;     
    }
    
    public static List<All> xemVeCb(String id_cb, String kh_id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM vechuyenbay WHERE chuyenbay_id = " + id_cb + " AND khachhang_id = " + kh_id);
        List<All> lvcb = new ArrayList<>();
        while (rs.next()) {
            All a = new All();
            Statement stcb = conn.createStatement();
            ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE id_chuyenbay = " + rs.getString("chuyenbay_id"));
            while(rscb.next()) {
                a.setMa(rscb.getString("ma"));
                Statement stm1 = conn.createStatement();
                ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("arrive_id"));
                while (rs1.next()) {
                    a.setArrive(rs1.getString("sanbay"));
                }
                rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("depart_id"));
                while (rs1.next()) {
                    a.setDepart(rs1.getString("sanbay"));
                }
                stm1.close();
                a.setDaytime(rscb.getString("daytime"));
                a.setTimeflight(rscb.getString("timeflight"));
            }
            Statement stkh = conn.createStatement();
            ResultSet rskh = stkh.executeQuery("SELECT name FROM khachhang WHERE id_khachhang = " + rs.getString("khachhang_id"));
            while(rskh.next()) {
                a.setName(rskh.getString(1));
            }
            Statement stg = conn.createStatement();
            ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + rs.getString("ghe_id"));
            while(rsg.next()) {
                a.setGia(String.format("%,d", Integer.parseInt(rsg.getString("gia"))) + " VND");
                if("1".equals(rsg.getString("loai"))) {
                    a.setLoai("Primary Class");
                } else {
                    a.setLoai("Secondary Class"); 
                }
            }
            lvcb.add(a);      
        }
        return lvcb;
    }
    
    public static List<All> XemCbByGhe(String cb_id, String ghe_id) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM vechuyenbay WHERE chuyenbay_id = " + cb_id + " AND ghe_id = " + ghe_id);
        List<All> lvcb = new ArrayList<>();
        while (rs.next()) {
            All a = new All();
            Statement stcb = conn.createStatement();
            ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE id_chuyenbay = " + rs.getString("chuyenbay_id"));
            while(rscb.next()) {
                a.setMa(rscb.getString("ma"));
                Statement stm1 = conn.createStatement();
                ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("arrive_id"));
                while (rs1.next()) {
                    a.setArrive(rs1.getString("sanbay"));
                }
                rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("depart_id"));
                while (rs1.next()) {
                    a.setDepart(rs1.getString("sanbay"));
                }
                stm1.close();
                a.setDaytime(rscb.getString("daytime"));
                a.setTimeflight(rscb.getString("timeflight"));
            }
            Statement stkh = conn.createStatement();
            ResultSet rskh = stkh.executeQuery("SELECT name FROM khachhang WHERE id_khachhang = " + rs.getString("khachhang_id"));
            while(rskh.next()) {
                a.setName(rskh.getString(1));
            }
            Statement stg = conn.createStatement();
            ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + rs.getString("ghe_id"));
            while(rsg.next()) {
                a.setGia(String.format("%,d", Integer.parseInt(rsg.getString("gia"))) + " VND");
                if("1".equals(rsg.getString("loai"))) {
                    a.setLoai("Primary Class");
                } else {
                    a.setLoai("Secondary Class"); 
                }
            }
            lvcb.add(a);      
        }
        return lvcb;
    }
    
    public static void datVe(Vechuyenbay vcb) throws SQLException, SQLException, SQLException, SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement dv = conn.createStatement();
        dv.executeUpdate("INSERT INTO vechuyenbay (chuyenbay_id, khachhang_id, ghe_id) VALUES (" + vcb.getChuyenbay_id() + ", " + vcb.getKhachhang_id() + ", " + vcb.getGhe_id() + ")");
        conn.close();
    }
    
    public static void addGhe(Ghe g1, Ghe g2) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st1 = conn.createStatement();
        st1.executeUpdate("INSERT INTO ghe (loai, gia, soluong, chuyenbay_id) VALUES (1, " + 
                g1.getGia() + ", " + g1.getSoluong() + ", 8)");
                
        Statement st2 = conn.createStatement();
        st2.executeUpdate("INSERT INTO ghe (loai, gia, soluong, chuyenbay_id) VALUES (2, " + 
                g2.getGia() + ", " + g2.getSoluong() + ", 8)");
    }
    
    public static List<Chuyenbay> searchCbByArrive(String kw) throws SQLException {
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
        
        return lcb;
    }
    
    public static List<Chuyenbay> searchCbByDepart(String kw) throws SQLException {
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
        
        return lcb;
    }
    
    public static void huyVechuyenbay(Vechuyenbay vcb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stvcb = conn.createStatement();
        stvcb.executeUpdate("DELETE FROM vechuyenbay WHERE khachhang_id = " + vcb.getKhachhang_id() + " AND chuyenbay_id = " + vcb.getChuyenbay_id());
        conn.close();   
    }
    
    public static void doiVechuyenbay(Vechuyenbay vcb) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stvcb = conn.createStatement();
        stvcb.executeUpdate("UPDATE vechuyenbay SET ghe_id = " + vcb.getKhachhang_id() + " WHERE chuyenbay_id = " + vcb.getChuyenbay_id());            
        conn.close();
    }
    
    public static void doiGhe(Ghe g) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement scsl1 = conn.createStatement();
        scsl1.executeUpdate("UPDATE ghe SET soluong = " + g.getSl() + " WHERE id_ghe = " + g.getSoluong());
        conn.close();
    }
    
    public static List<Ghe> checkDoighe(int sl, String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + kw + " AND soluong = " + sl);
        List<Ghe> lg = new ArrayList<>();
        while(rs.next()) {
            Ghe g = new Ghe();
            g.setGia(rs.getString("gia"));
            g.setSoluong(rs.getString("soluong"));
            lg.add(g);
        }
        return lg;
    }
}
