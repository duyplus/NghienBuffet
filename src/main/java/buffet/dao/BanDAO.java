package buffet.dao;

import buffet.model.Ban;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BanDAO extends BuffetDAO<Ban, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO Ban(MaBan,SDT,MaNV,SoBan,GioDat,SoLuong,NguoiLon,TreEm) VALUES(?,?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE Ban SET MaBan=?,MaNV=?,SoBan=?,GioDat=?,SoLuong=?,NguoiLon=?,TreEm=? WHERE SDT=?";
    public static String DELETE_SQL = "DELETE FROM Ban WHERE Maban=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM Ban";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM Ban WHERE MaBan=?";
    public static String SELECT_BY_ID2_SQL = "SELECT * FROM Ban WHERE SoBan=?";

    @Override
    public void insert(Ban entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaBan(),
                entity.getSDT(),
                entity.getMaNV(),
                entity.getSoBan(),
                entity.getGioDat(),
                entity.getSoLuong(),
                entity.getNguoiLon(),
                entity.getTreEm()
        );
    }

    @Override
    public void update(Ban entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getMaBan(),
                entity.getMaNV(),
                entity.getSoBan(),
                entity.getGioDat(),
                entity.getSoLuong(),
                entity.getNguoiLon(),
                entity.getTreEm(),
                entity.getSDT()
        );
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<Ban> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Ban selectById(String key) {
        List<Ban> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Ban entity = new Ban();
                    entity.setMaBan(rs.getString("MaBan"));
                    entity.setSDT(rs.getString("SDT"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setSoBan(rs.getString("SoBan"));
                    entity.setGioDat(rs.getTime("GioDat"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setNguoiLon(rs.getInt("NguoiLon"));
                    entity.setTreEm(rs.getInt("TreEm"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<Ban> selectByBan(String maban) {
        return selectBySql(SELECT_BY_ID_SQL, maban);
    }

    public Ban selectByMaBan(String soBan) {
        List<Ban> list = this.selectBySql(SELECT_BY_ID2_SQL, soBan);
        return list.size() > 0 ? list.get(0) : null;
    }
}
