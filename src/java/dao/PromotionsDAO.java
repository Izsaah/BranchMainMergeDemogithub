/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.PromotionsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtil;

/**
 *
 * @author ACER
 */
public class PromotionsDAO {      
    public boolean addPromotion(PromotionsDTO promo) {
        String sql = "INSERT INTO tblPromotions(name, discountPercent, startDate, endDate, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, promo.getName());
            ps.setFloat(2, promo.getDiscountPercent());
            ps.setDate(3, promo.getStartDate());
            ps.setDate(4, promo.getEndDate());
            ps.setString(5, promo.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PromotionsDTO> getAllPromotions() {
        List<PromotionsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PromotionsDTO(
                    rs.getInt("promoID"),
                    rs.getString("name"),
                    rs.getFloat("discountPercent"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PromotionsDTO getPromotionById(int promoID) {
        String sql = "SELECT * FROM tblPromotions WHERE promoID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PromotionsDTO(
                    rs.getInt("promoID"),
                    rs.getString("name"),
                    rs.getFloat("discountPercent"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePromotion(PromotionsDTO promo) {
        String sql = "UPDATE tblPromotions SET name=?, discountPercent=?, startDate=?, endDate=?, status=? WHERE promoID=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, promo.getName());
            ps.setFloat(2, promo.getDiscountPercent());
            ps.setDate(3, promo.getStartDate());
            ps.setDate(4, promo.getEndDate());
            ps.setString(5, promo.getStatus());
            ps.setInt(6, promo.getPromoID());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePromotion(int promoID) {
        String sql = "DELETE FROM tblPromotions WHERE promoID = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
