package buffet.dao;

import buffet.model.KHTT;
import buffet.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducdu
 */
public class KHTTDAO {

    public static ResultSet rs = null;
    public static String SELECT_BY_KEYWORD_SQL = "SELECT KH.TenKH, KH.SDT, KH.Email, COUNT(HD.MaHD) AS SoLanDen "
            + "FROM HoaDon HD "
            + "LEFT JOIN KhachHang KH ON HD.SDT = KH.SDT "
            + "WHERE KH.TenKH LIKE ? OR KH.SDT LIKE ? OR KH.SDT LIKE ? GROUP BY KH.SDT, KH.TenKH, KH.Email";
    public static String SELECT_KHTT = "SELECT KH.TenKH, KH.SDT, KH.Email, COUNT(HD.MaHD) AS SoLanDen "
            + "FROM HoaDon HD "
            + "LEFT JOIN KhachHang KH ON HD.SDT = KH.SDT "
            + "GROUP BY KH.SDT, KH.TenKH, KH.Email";

    public List<KHTT> selectAll() {
        return this.selectBySql(SELECT_KHTT);
    }

    protected List<KHTT> selectBySql(String sql, Object... args) {
        List<KHTT> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    KHTT entity = new KHTT();
                    entity.setTenKH(rs.getString("TenKH"));
                    entity.setSDT(rs.getString("SDT"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setSoLanDen(rs.getInt("SoLanDen"));
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

    public List<KHTT> selectByKeyword(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }
}
