package buffet.utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author duyplus
 */
public class XHelper {

    /*
     * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
     */
    public static Image getAppIcon() {
        String file = "/icons/logo.png";
        return new ImageIcon(XValidate.class.getResource(file)).getImage();
    }

    /*
     * Lưu ảnh vào thư mục avatars
     */
    public static void saveIcon(File src) {
        File file = new File("avatars", src.getName());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(file.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Đọc ảnh trong thư mục avatars
     */
    public static ImageIcon readIcon(String fileName) {
        File path = new File("avatars", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
    }

    /*
     * Xuất table sang excel
     */
    public static void writeToExcel(JTable table, String title) {
        XSSFWorkbook wb = null;
        FileOutputStream fos = null;
        try {
            wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(title);

            JFileChooser fc = new JFileChooser("D:\\");
            fc.setDialogTitle("Save as...");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Choose Files", "xls", "xlsx", "xlsm");
            fc.setFileFilter(fnef);
            int chooser = fc.showSaveDialog(null);
            if (chooser == JFileChooser.APPROVE_OPTION) {
                TableModel tm = table.getModel();
                TableColumnModel tcm = table.getTableHeader().getColumnModel();
                XSSFRow rowtitle = sheet.createRow((short) 0);
                for (int i = 0; i < tcm.getColumnCount(); i++) {
                    // Set font header
                    XSSFFont font = wb.createFont();
                    font.setFontHeightInPoints((short) 12);
                    font.setColor(IndexedColors.RED.getIndex());
                    font.setBold(true);
                    // Set style header
                    XSSFCellStyle cs = wb.createCellStyle();
                    cs.setAlignment(HorizontalAlignment.CENTER);
                    cs.setVerticalAlignment(VerticalAlignment.CENTER);
                    cs.setFont(font);
                    // Create header cell
                    XSSFCell cell = rowtitle.createCell((short) i);
                    cell.setCellValue(tcm.getColumn(i).getHeaderValue().toString());
                    cell.setCellStyle(cs);
                }
                for (int i = 0; i < tm.getRowCount(); i++) {
                    XSSFFont font1 = wb.createFont();
                    // Set font body
                    font1.setFontHeightInPoints((short) 10);
                    // Set style
                    XSSFCellStyle cs1 = wb.createCellStyle();
                    cs1.setFont(font1);
                    XSSFRow row = sheet.createRow((short) i + 1);
                    for (int j = 0; j < tm.getColumnCount(); j++) {
                        // Create body cell
                        XSSFCell cell = row.createCell((short) j);
                        cell.setCellValue(Objects.toString(tm.getValueAt(i, j), ""));
                        cell.setCellStyle(cs1);
                        // Set fit column
                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            wb.getSheetAt(0).autoSizeColumn(k);
                        }
                    }
                }
                fos = new FileOutputStream(fc.getSelectedFile() + ".xlsx");
                XDialog.alert(null, "Xuất dữ liệu thành công!");
                Desktop.getDesktop().open(new File(fc.getSelectedFile() + ".xlsx"));
                wb.write(fos);
                fos.close();
            }
        } catch (HeadlessException | IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void senMail(String username, String password, String email, String sub, String vc, int sld) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "false");

        Session ss = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(ss);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
            message.setSubject(sub, "utf-8");
            String msg = "<div style='background-color:cyan;padding:25px;border-radius:10px;margin:0 auto;width:70%;line-height:30px;'>"
                    + "<center><img src='https://i.imgur.com/jjxWo0t.png' width='200'></center><br/>"
                    + "<div style='font-size:18px;'>Nhà hàng <b>Nghiện Buffet</b> muốn gửi cho bạn một voucher giảm giá<br/><br/>"
                    + "Bạn đã ghé ăn tại nhà hàng của chúng tôi được <b>" + sld + "</b> lần.<br/>"
                    + "Và lần ghé thăm nhà hàng lần tới bạn sẽ được tự động áp dụng <b>voucher giảm giá " + vc + "</b><br/><br/>"
                    + "Cám ơn quý khách, chúc quý khách có một bữa ăn thật ngon miệng.<br/>"
                    + "----------------------------------<br/>"
                    + "CTY CP NGHIỆN BUFFET CO.</div>";
            message.setContent(msg, "text/html;charset=utf-8");
            Transport.send(message);
            XDialog.alert(null, "Gửi e-mail thành công!");
        } catch (Exception e) {
            XDialog.alert(null, "Gửi e-mail thất bại!");
            e.printStackTrace();
        }

    }

    /*
     * Tìm kiếm theo ngày tháng năm
     */
    public static void searchMultiple(JTable table, JTextField txt) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        table.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(txt.getText().trim()));
    }

    /*
     * Thêm dấy phẩy sau 3 số
     */
    public static String commafy(String num) {
        String regex = "(\\d)(?=(\\d{3})+$)";
        String[] split = num.split("\\.");
        if (split.length == 2) {
            return split[0].replaceAll(regex, "$1,") + "." + split[1];
        } else {
            return num.replaceAll(regex, "$1,");
        }
    }

    /*
     * Âm thanh chào
     */
    public static void welcome() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    // Supported on all JDK
                    URL url = getClass().getResource("/welcome.wav");
                    AudioClip clip = Applet.newAudioClip(url);
                    clip.play();
                    // Any supported on JDK 8
//                    InputStream in = getClass().getResourceAsStream("/welcome.wav");
//                    AudioStream out = new AudioStream(in);
//                    AudioPlayer.player.start(out);
                } catch (Exception e) {
                }
                ses.shutdown();
            }
        };
        ses.scheduleAtFixedRate(run, 0, 1, TimeUnit.SECONDS);
    }

    /*
     * Âm thanh sau khi thêm bàn mới
     */
    public static void alert(String soban) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable run = new Runnable() {
            int i = 110;

            @Override
            public void run() {
                i--;
                if (i < 0) {
                    try {
                        // Supported on all JDK
                        URL url = getClass().getResource("/alert.wav");
                        AudioClip clip = Applet.newAudioClip(url);
                        clip.play();
                    } catch (Exception e) {
                    }
                    XDialog.alert(null, "Bàn số " + soban + " còn 10 phút hết giờ!\nVui lòng ra nhắc nhở khách.");
                    ses.shutdown();
                }
            }
        };
        ses.scheduleAtFixedRate(run, 0, 1, TimeUnit.MINUTES);
    }
}
