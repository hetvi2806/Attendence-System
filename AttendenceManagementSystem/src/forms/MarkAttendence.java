/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.sql.Connection;
import java.lang.String;
import dao.ConnectionProvider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.String.format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import utility.Myutility;

/**
 *
 * @author admin
 */
public class MarkAttendence extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;

    private Webcam webcam = null;
    private ExecutorService executor = Executors.newSingleThreadExecutor(this);
    private volatile boolean running = true;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MarkAttendence.class.getName());

    /**
     * Creates new form MarkAttendence
     */
    public MarkAttendence() {
        initComponents();

        // JFrame layout set to null to allow absolute positioning
        this.setLayout(null);

        // Set background image
        Myutility.setImage(this, "images/abc1.jpg", 1366, 768);

        // JFrame border
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));

        // Initialize webcam
        initWebcam();

        // Timer for updating time
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

        lblImage.setSize(300, 300);
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        lblImage.setVerticalAlignment(JLabel.CENTER);

        // Dimensions for bottom-right placement
        int lblWidth = 350;
        int lblHeight = 50;
        int marginRight = 20;
        int marginBottom = 40;
        int frameWidth = 1366;
        int frameHeight = 768;

        // Initialize lblName (display user name)
        lblName = new JLabel();
        lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblName.setHorizontalAlignment(JLabel.CENTER);
        lblName.setText("");
        // Place above lblCheckInCheckOut
        lblName.setBounds(frameWidth - lblWidth - marginRight, frameHeight - lblHeight - marginBottom - 40, lblWidth, 30);
        this.add(lblName);

        // Initialize lblCheckInCheckOut (CheckIn/CheckOut status)
        lblCheckInCheckOut = new JLabel();
        lblCheckInCheckOut.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCheckInCheckOut.setHorizontalAlignment(JLabel.CENTER);
        lblCheckInCheckOut.setForeground(Color.BLACK);
        lblCheckInCheckOut.setBackground(Color.LIGHT_GRAY);
        lblCheckInCheckOut.setOpaque(true);
        lblCheckInCheckOut.setBounds(frameWidth - lblWidth - marginRight, frameHeight - lblHeight - marginBottom, lblWidth, lblHeight);
        this.add(lblCheckInCheckOut);

        // Window closing listener
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                running = false;
                stopWebcam();

                if (executor != null && !executor.isShutdown()) {
                    executor.shutdownNow();
                }
            }
        });

        // Set JFrame visible after adding components
        this.setVisible(true);
    }

    private void updateTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        lblTime.setText(simpleDateFormat.format(new Date()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        btnExit2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnExit3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame1.setMinimumSize(new java.awt.Dimension(1087, 491));
        jFrame1.setUndecorated(true);
        jFrame1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jFrame1formComponentShown(evt);
            }
        });

        btnExit2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExit2.setText("X");
        btnExit2.addActionListener(this::btnExit2ActionPerformed);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Delete User");

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Gender", "Email", "Contact", "Address", "State", "Country", "Registration Id", "Image Name"
            }
        ));
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userTable);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Search");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Delete User");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Delete User");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExit2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrame1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrame1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrame1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrame1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setUndecorated(true);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Mark Attendence");

        btnExit3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExit3.setText("X");
        btnExit3.addActionListener(this::btnExit3ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Date");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Time");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Time");

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblTime.setText("Time");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(183, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(511, 511, 511)
                        .addComponent(btnExit3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(972, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(357, 357, 357)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(855, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(269, 269, 269)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExit3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(32, 32, 32)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(185, 185, 185)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(611, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(694, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(65, 65, 65)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExit2ActionPerformed
        running = false;  // QR code scanning thread રોકો
        stopWebcam();     // Webcam બંધ કરો

        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); // Executor thread બંધ કરો
        }

        this.dispose();
    }//GEN-LAST:event_btnExit2ActionPerformed

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked

    }//GEN-LAST:event_userTableMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased


    }//GEN-LAST:event_txtSearchKeyReleased

    private void jFrame1formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jFrame1formComponentShown

    }//GEN-LAST:event_jFrame1formComponentShown

    private void btnExit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExit3ActionPerformed
        running = false;  // QR code scanning thread રોકો
        stopWebcam();     // Webcam બંધ કરો

        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow(); // Executor thread બંધ કરો
        }

        this.dispose();
    }//GEN-LAST:event_btnExit3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MarkAttendence().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit2;
    private javax.swing.JButton btnExit3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
private javax.swing.JLabel lblName;

    private javax.swing.JLabel lblCheckInCheckOut;

    Map<String, String> resultMap = new HashMap<String, String>();

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100); // Small delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            try {
                BufferedImage image = null;
                if (webcam != null && webcam.isOpen()) {
                    image = webcam.getImage();
                }

                if (image == null) {
                    continue; // Skip this iteration
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                Result result = null;
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException ex) {
                    // No QR code found in this frame, ignore
                }

                if (result != null) {
                    String jsonString = result.getText();
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<Map<String, String>>() {
                    }.getType();
                    resultMap = gson.fromJson(jsonString, type);

                    String finalPath = Myutility.getPath(
                            "images" + File.separator + resultMap.get("email") + ".jpg"
                    );

                    CircluarImageFrame(finalPath);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (running);
    }

    private void initWebcam() {

        webcam = Webcam.getDefault();

        if (webcam == null) {
            JOptionPane.showMessageDialog(this, "No Webcam Detected");
            return;
        }

        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open(); // ✅ VERY IMPORTANT

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(new Dimension(689, 518));
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        jPanel2.removeAll();
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(panel, BorderLayout.CENTER);
        jPanel2.revalidate();
        jPanel2.repaint();

        executor.execute(this); // QR thread
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    private void stopWebcam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }

    }
    private BufferedImage imagee = null;

    private void CircluarImageFrame(String imagePath) {

    try {
        File imageFile = new File(imagePath);
        System.out.println("Checking image path: " + imageFile.getAbsolutePath());

        SwingUtilities.invokeLater(() -> {
            try {
                if (imageFile.exists()) {

                    BufferedImage original = ImageIO.read(imageFile);

                    if (original != null) {
                        BufferedImage circular = createCircularImage(original);
                        lblImage.setIcon(new ImageIcon(circular));
                    } else {
                        lblImage.setIcon(
                            new ImageIcon(createLetterAvatar(resultMap.get("name")))
                        );
                    }

                } else {
                    lblImage.setIcon(
                        new ImageIcon(createLetterAvatar(resultMap.get("name")))
                    );
                }

                lblImage.revalidate();
                lblImage.repaint();
                lblName.setText(resultMap.get("name"));

                checkInCheckOut();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void showPopUpForCertainDuration(String popUpMessage, String popUpHeader, Integer iconId) throws HeadlessException {
        final JOptionPane optionPane = new JOptionPane(popUpMessage, iconId);
        final JDialog dialog = optionPane.createDialog(popUpHeader);
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                clearUserDetails();
            }
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    private boolean clearUserDetails() {
        lblCheckInCheckOut.setText("");
        lblCheckInCheckOut.setBackground(null);
        lblCheckInCheckOut.setForeground(null);
        lblCheckInCheckOut.setOpaque(false);
        lblCheckInCheckOut.setText("");
        lblCheckInCheckOut.setIcon(null);
        // showPopUpForCertainDuration(popUpMessage,popUpHeader,JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private BufferedImage createLetterAvatar(String name) {

        int size = 285;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Black Circle
        g2.setColor(Color.BLACK);
        g2.fillOval(0, 0, size, size);

        // White Letter
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Serif", Font.BOLD, 200));

        String letter = name.substring(0, 1).toUpperCase();
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(letter)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(letter, x, y);
        g2.dispose();

        return img;
    }

    private BufferedImage createCircularImage(BufferedImage image) {

        int size = 285;

        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create circular clip
        g2.setClip(new Ellipse2D.Float(0, 0, size, size));

        // Draw image inside circle
        g2.drawImage(image, 0, 0, size, size, null);

        g2.dispose();
        return output;
    }

    private boolean checkInCheckOut() throws HeadlessException, SQLException {
        String popUpHeader = null;
        String popUpMessage = null;
        Color color = null;

        Connection con = ConnectionProvider.getCon();
        Statement st = con.createStatement();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ResultSet rs = st.executeQuery("SELECT * FROM userattendence WHERE date='" + currentDate.format(dateFormatter)
                + "' AND userid=" + Integer.valueOf(resultMap.get("id")) + ";");

        if (rs.next()) {
            // User has already checked in
            String checkOutDateTime = rs.getString("checkout");
            if (checkOutDateTime != null) {
                popUpMessage = "Already checked out for the day";
                popUpHeader = "Invalid Checkout";
                showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.ERROR_MESSAGE);
                lblCheckInCheckOut.setText("Already Checked Out");
                lblCheckInCheckOut.setForeground(Color.RED);
                lblCheckInCheckOut.setBackground(Color.DARK_GRAY);
                lblCheckInCheckOut.setOpaque(true);
                return false;
            }

            String checkInDateTime = rs.getString("checkin");
            LocalDateTime checkInLocalDateTime = LocalDateTime.parse(checkInDateTime, dateTimeFormatter);
            Duration duration = Duration.between(checkInLocalDateTime, currentDateTime);
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

            if (!(hours > 0 || (hours == 0 && minutes >= 3))) {
                long remainingMinutes = 3 - minutes;
                long remainingSeconds = 60 - seconds;

                popUpMessage = String.format("Your work duration is less than 5 minutes.\nYou can checkout after: %d minutes and %d seconds", remainingMinutes, remainingSeconds);
                popUpHeader = "Duration Warning";
                showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.WARNING_MESSAGE);

                lblCheckInCheckOut.setText("Checked In at " + checkInLocalDateTime.format(dateTimeFormatter));
                lblCheckInCheckOut.setForeground(Color.GREEN);
                lblCheckInCheckOut.setBackground(Color.DARK_GRAY);
                lblCheckInCheckOut.setOpaque(true);
                return false;
            }

            // Perform checkout
            String updateQuery = "UPDATE userattendence SET checkout=?, workduration=? WHERE date=? AND userid=?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, currentDateTime.format(dateTimeFormatter));
            preparedStatement.setString(2, hours + " Hours and " + minutes + " Minutes");
            preparedStatement.setString(3, currentDate.format(dateFormatter));
            preparedStatement.setString(4, resultMap.get("id"));
            preparedStatement.executeUpdate();

            popUpHeader = "CheckOut";
            popUpMessage = "Checked out at " + currentDateTime.format(dateTimeFormatter) + "\nWork Duration: " + hours + " Hours and " + minutes + " Minutes";
            color = Color.RED;

            lblCheckInCheckOut.setText("Checked Out at " + currentDateTime.format(dateTimeFormatter));
            lblCheckInCheckOut.setForeground(color);
            lblCheckInCheckOut.setBackground(Color.DARK_GRAY);
            lblCheckInCheckOut.setOpaque(true);

        } else {
            // Perform checkin
            String insertQuery = "INSERT INTO userattendence(userid, date, checkin) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, resultMap.get("id"));
            preparedStatement.setString(2, currentDate.format(dateFormatter));
            preparedStatement.setString(3, currentDateTime.format(dateTimeFormatter));
            preparedStatement.executeUpdate();

            popUpHeader = "CheckIn";
            popUpMessage = "Checked in at " + currentDateTime.format(dateTimeFormatter);
            color = Color.GREEN;

            lblCheckInCheckOut.setText("Checked In at " + currentDateTime.format(dateTimeFormatter));
            lblCheckInCheckOut.setForeground(color);
            lblCheckInCheckOut.setBackground(Color.DARK_GRAY);
            lblCheckInCheckOut.setOpaque(true);
        }

        showPopUpForCertainDuration(popUpMessage, popUpHeader, JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    //@Override
    //public void paint(Graphics g) {
    //   super.paint(g);
    // if (imagee != null) {
    //   g.drawImage(imagee, 0, 0, null);
    //}
    //}
}
