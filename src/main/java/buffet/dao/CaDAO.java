package buffet.dao;

import buffet.model.Ca;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class CaDAO extends BuffetDAO<Ca, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO Ca (NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE Ca SET NgayLam=?,CaLam=?,BatDau=?,KetThuc=?,MaNV=? WHERE MaCa=?";
    public static String DELETE_SQL = "DELETE FROM Ca WHERE MaCa=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM Ca";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM Ca WHERE MaCa=?";

    @Override
    public void insert(Ca entity) {
        XJDBC.update(INSERT_SQL,
                entity.getNgayLam(),
                entity.getCaLam(),
                entity.getBatDau(),
                entity.getKetThuc(),
                entity.getMaNV());
    }

    @Override
    public void update(Ca entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getNgayLam(),
                entity.getCaLam(),
                entity.getBatDau(),
                entity.getKetThuc(),
                entity.getMaNV(),
                entity.getMaCa());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<Ca> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Ca selectById(String key) {
        List<Ca> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<Ca> selectBySql(String sql, Object... args) {
        List<Ca> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    Ca entity = new Ca();
                    entity.setMaCa(rs.getInt("MaCa"));
                    entity.setNgayLam(rs.getDate("NgayLam"));
                    entity.setCaLam(rs.getString("CaLam"));
                    entity.setBatDau(rs.getTime("BatDau"));
                    entity.setKetThuc(rs.getTime("KetThuc"));
                    entity.setMaNV(rs.getString("MaNV"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

}
