package buffet.dao;

import buffet.model.HoaDon;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducdu
 */
public class HoaDonDAO extends BuffetDAO<HoaDon, String> {

    public static ResultSet rs = null;
    public static String INSERT_SQL = "INSERT INTO HoaDon (MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (?,?,?,?,?,?,?,?,?)";
    public static String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM HoaDon WHERE MaHD LIKE ? OR SDT LIKE ?";
    public static String SELECT_SOLANDEN = "SELECT COUNT(MaHD) FROM HoaDon WHERE SDT=?";

    @Override
    public void insert(HoaDon entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getSoBan(),
                entity.getGioVao(),
                entity.getNgayXuat(),
                entity.getNguoiLon(),
                entity.getTreEm(),
                entity.getTongVe(),
                entity.getTongTien(),
                entity.getSDT()
        );
    }

    @Override
    public void update(HoaDon entity) {
    }

    @Override

    public void delete(String key) {
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(String key) {
        List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    HoaDon entity = new HoaDon();
                    entity.setMaHD(rs.getString("MaHD"));
                    entity.setSoBan(rs.getString("SoBan"));
                    entity.setGioVao(rs.getTime("GioVao"));
                    entity.setNgayXuat(rs.getDate("NgayXuat"));
                    entity.setNguoiLon(rs.getInt("NguoiLon"));
                    entity.setTreEm(rs.getInt("TreEm"));
                    entity.setTongVe(rs.getInt("TongVe"));
                    entity.setTongTien(rs.getInt("TongTien"));
                    entity.setSDT(rs.getString("SDT"));
                    entity.setMaNV(rs.getString("MaNV"));
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

    public List<HoaDon> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%");
    }

    public int selectSoLanDen(String key) {
        int sld = 0;
        rs = XJDBC.query(SELECT_SOLANDEN, key);
        try {
            try {
                while (rs.next()) {
                    sld = rs.getInt("");
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return sld;
    }
}
