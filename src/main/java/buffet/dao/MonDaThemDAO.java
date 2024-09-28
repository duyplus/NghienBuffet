package buffet.dao;

import buffet.model.MonDaThem;
import buffet.model.TienMT;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MonDaThemDAO extends BuffetDAO<MonDaThem, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO MonDaThem(MaBan,MaMT,SoLuong) VALUES(?,?,?)";
    public static String UPDATE_SQL = "UPDATE MonDaThem SET SoLuong=? WHERE MaMT=? AND MaBan=?";
    public static String DELETE_SQL = "DELETE FROM MonDaThem WHERE MaMT=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM MonDaThem";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM MonDaThem WHERE MaBan=?";
    public static String SELECTGIA = "SELECT SoLuong,Gia FROM MonDaThem\n"
            + "LEFT JOIN MonThem ON MonDaThem.MaMT = MonThem.MaMT\n"
            + "WHERE MaBan=?;";

    @Override
    public void insert(MonDaThem entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaBan(),
                entity.getMaMT(),
                entity.getSoLuong());
    }

    @Override
    public void update(MonDaThem entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getSoLuong(),
                entity.getMaMT(),
                entity.getMaBan());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<MonDaThem> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public MonDaThem selectById(String key) {
        List<MonDaThem> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<MonDaThem> selectBySql(String sql, Object... args) {
        List<MonDaThem> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    MonDaThem entity = new MonDaThem();
                    entity.setMaMT(rs.getString("MaMT"));
                    entity.setMaBan(rs.getString("MaBan"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
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

    public List<MonDaThem> selectByMonThem(String key) {
        return this.selectBySql(SELECT_BY_ID_SQL, key);
    }

    public List selectgia(String key) {
        List<TienMT> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(SELECTGIA, key);
                while (rs.next()) {
                    TienMT entity = new TienMT();
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setGia(rs.getFloat("Gia"));
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
}
