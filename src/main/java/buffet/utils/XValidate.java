package buffet.utils;

import buffet.dao.DoiTacDAO;
import buffet.dao.KhachHangDAO;
import buffet.dao.NguoiDungDAO;
import buffet.dao.NhanVienDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author duyplus
 */
public class XValidate {

    public static NhanVienDAO nvdao = new NhanVienDAO();
    public static KhachHangDAO khdao = new KhachHangDAO();
    public static DoiTacDAO dtdao = new DoiTacDAO();
    public static NguoiDungDAO nddao = new NguoiDungDAO();

    /*
     * Kiểm thử mã
     */
    public static boolean checkMa(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{3,10}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-10 kí tự\n(chữ hoa, thường không dấu hoặc số).");
            return false;
        }
    }

    /*
     * Kiểm tra trùng mã Nhân viên
     */
    public static boolean checkTrungMaNV(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (nvdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Mã '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm tra trùng mã Đối tác
     */
    public static boolean checkTrungMaDT(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (dtdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Mã '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm tra trùng Số điện thoại nhân viên
     */
    public static boolean checkTrungSDT(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (nvdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm tra trùng Số điện thoại khách hàng
     */
    public static boolean checkTrungSDTKH(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (khdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm thử Số điện thoại
     */
    public static boolean checkSDT(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[0-9]{10}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải gồm 10 số\nĐúng đầu số của các nhà mạng Viettel, Vinaphone, Mobifone,..");
            return false;
        }
    }

    /*
     * Kiểm thử Số điện thoại
     */
    public static boolean checkSDTDoiTac(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[0-9]{10,11}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải gồm 10-11 số di động hoặc điện thoại bàn,..");
            return false;
        }
    }

    /*
     * Kiểm thử Tên
     */
    public static boolean checkName(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ 0-9]{3,50}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là tên có dấu hoặc không và từ 3-50 kí tự.");
            return false;
        }
    }

    /*
     * Kiểm thử giờ phút
     */
    public static boolean checkTime(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        int id1 = Integer.parseInt(txt.getText());
        String rgx = "^([0-1]?\\d|2[0-3])(?::([0-5]?\\d))?$";
        if (id.matches(rgx) && id1 > 0 && id1 < 25) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không đúng định dạng (HH:mm)"
                    + "HH phải là số và từ 1-24. mm phải là số và từ 0-60.");
            return false;
        }
    }

    /*
     * Kiểm thử Email
     */
    public static boolean checkEmail(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không đúng định dạng (ký tự có dấu)"
                    + "\nhoặc bị giới hạn kí tự cho phép.");
            return false;
        }
    }

    /*
     * Kiểm thử nhiều Email
     */
    public static boolean checkMultiEmail(JTextArea txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không đúng định dạng (ký tự có dấu)"
                    + "\nhoặc bị giới hạn kí tự cho phép.");
            return false;
        }
    }

    /*
     * Kiểm thử số lượng người
     */
    public static boolean checkNumber(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        int id = Integer.parseInt(txt.getText());
        if (id > 0 && id < 9) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số và từ 1-8.");
            return false;
        }
    }

    /*
     * Kiểm thử giá vé
     */
    public static boolean checkPrice(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[0-9]{6,7}$";
        if (id.matches(rgx) && txt.getText().length() > 5 && txt.getText().length() < 8) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số và từ 100,000 đến 1,000,000 VNĐ");
            return false;
        }
    }

    /*
     * Kiểm thử giá vé
     */
    public static boolean checkRate(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        String id = txt.getText();
        String rgx = "^[0-9]{1,4}$";
        if (id.matches(rgx) && txt.getText().length() > 0 && txt.getText().length() < 5) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số và từ 1 đến 1,000");
            return false;
        }
    }

    /*
     * Kiểm thử mật khẩu
     */
    public static boolean checkPass(JPasswordField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (txt.getPassword().length > 2 && txt.getPassword().length < 51) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-50 kí tự.");
            return false;
        }
    }

    /*
     * Kiểm thử in hoá đơn
     */
    public static boolean checkBill(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Hoá đơn không có sẵn, không thể in hoá đơn.");
            return false;
        }
    }

    /*
     * Kiểm thử nhập rỗng TextField
     */
    public static boolean checkNullText(JTextField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm thử nhập rỗng TextArea
     */
    public static boolean checkNullText(JTextArea txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm thử nhập ngày tháng năm
     */
    public static boolean checkNullDate(JDateChooser txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        Date date = txt.getDate();
        if (date != null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống hoặc không đúng định dạng (dd/MM/yyy).");
            return false;
        }
    }

    /*
     * Kiểm tra mật khẩu rỗng
     */
    public static boolean checkNullPass(JPasswordField txt) {
        txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 16));
        if (txt.getPassword().length > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm tra ảnh chưa được chọn
     */
    public static boolean checkNullHinh(JLabel lbl) {
        if (lbl.getToolTipText() != null) {
            return true;
        } else {
            XDialog.alert(lbl.getRootPane(), lbl.getToolTipText() + " không được để trống.");
            return false;
        }
    }
}
