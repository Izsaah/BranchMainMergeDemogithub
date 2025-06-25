package dao;

import dto.PromotionsDTO;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionsDAO {

    public boolean addPromotion(PromotionsDTO promo) throws Exception {
        String sql = "INSERT INTO tblPromotions(name, discountPercent, startDate, endDate, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, promo.getName());
            ps.setFloat(2, promo.getDiscountPercent());
            ps.setDate(3, promo.getStartDate());
            ps.setDate(4, promo.getEndDate());
            ps.setString(5, promo.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePromotion(PromotionsDTO promo) throws Exception {
        String sql = "UPDATE tblPromotions SET name = ?, discountPercent = ?, startDate = ?, endDate = ?, status = ? WHERE promoID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, promo.getName());
            ps.setFloat(2, promo.getDiscountPercent());
            ps.setDate(3, promo.getStartDate());
            ps.setDate(4, promo.getEndDate());
            ps.setString(5, promo.getStatus());
            ps.setInt(6, promo.getPromoID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletePromotion(int promoID) throws Exception {
        String sql = "DELETE FROM tblPromotions WHERE promoID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            return ps.executeUpdate() > 0;
        }
    }

    public PromotionsDTO getPromotionByID(int promoID) throws Exception {
        String sql = "SELECT * FROM tblPromotions WHERE promoID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, promoID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<PromotionsDTO> getAllPromotions() throws Exception {
        List<PromotionsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions ORDER BY promoID DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<PromotionsDTO> searchPromotions(String keyword) throws Exception {
        List<PromotionsDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions WHERE name LIKE ? ORDER BY promoID DESC";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private PromotionsDTO map(ResultSet rs) throws SQLException {
        PromotionsDTO dto = new PromotionsDTO();
        dto.setPromoID(rs.getInt("promoID"));
        dto.setName(rs.getString("name"));
        dto.setDiscountPercent(rs.getFloat("discountPercent"));
        dto.setStartDate(rs.getDate("startDate"));
        dto.setEndDate(rs.getDate("endDate"));
        dto.setStatus(rs.getString("status"));
        return dto;
    }
}
