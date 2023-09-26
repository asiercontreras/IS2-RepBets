package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import domain.User;

public class EditarPerfilGUI extends JFrame {

    private JTextField nameField;
    private JTextField sur1Field;
    private JTextField sur2Field;
    private JPasswordField prevPassField;
    private JPasswordField newPasswdField;
    private JPasswordField confirmPasswdField;
    private JCalendar birthDateCalendar;
    private JButton btnEnter;
    private BLFacade facade = ApplicationLauncher.getBusinessLogic();
    private int WIDTH = 725;
    private int HEIGHT = 600;
    private byte[] img_bytes = null;

    public EditarPerfilGUI() {
        //Configurar el marco de la ventana
        this.setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        setShowProfile();
    }

    private void jButtonClose_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    private ImageIcon getUserImg(){
        Image resized_img;
        if(facade.getCurrentUser().getProfile_img() == null){
            Image img = new ImageIcon(this.getClass().getResource("/default_user_img.png")).getImage();
            resized_img = img.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
        }else{
            Image img = new ImageIcon(facade.getCurrentUser().getProfile_img()).getImage();
            resized_img = img.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
        }
        return new ImageIcon(resized_img);
    }

    private void setShowProfile(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Profile"));

        Date birthdate = facade.getCurrentUser().getBirthdate();
        JLabel namelbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name")+" " + facade.getCurrentUser().getName());
        JLabel surnameslbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surnames")+" " + facade.getCurrentUser().getSurnames());
        JLabel birthdatelbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate")+" " + birthdate.getYear() + "/" + birthdate.getMonth() + "/" + birthdate.getDay());
        JLabel moneylbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money")+" " + facade.getCurrentUser().getWallet());
        JButton editProfilebtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EditProfile"));
        JButton exitBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
        JLabel profileImg = new JLabel();
        
        profileImg.setIcon(getUserImg());

        profileImg.setBounds(285, 74, 95, 90);
        namelbl.setBounds(193 , 193, 363, 35);
        surnameslbl.setBounds(193, 226, 290, 35);
        birthdatelbl.setBounds(193, 259, 262, 35);
        moneylbl.setBounds(193, 294, 230, 35);

        panel.add(namelbl);
        panel.add(surnameslbl);
        panel.add(birthdatelbl);
        panel.add(moneylbl);
        panel.add(profileImg);

        exitBtn.setBounds(349, 355, 200, 44);
        exitBtn.setBackground(new Color(180, 180, 180));
        exitBtn.setBorder(BorderFactory.createEmptyBorder());
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitBtn.setBackground(new Color(70, 130, 180));
                exitBtn.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitBtn.setBackground(new Color(180, 180, 180));
            }
        });
        exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                jButtonClose_actionPerformed(arg0);
			}
            
        });
        panel.add(exitBtn);

        editProfilebtn.setBounds(139, 355, 200, 44);
        editProfilebtn.setBackground(new Color(180, 180, 180));
        editProfilebtn.setBorder(BorderFactory.createEmptyBorder());
        editProfilebtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editProfilebtn.setBackground(new Color(70, 130, 180));
                editProfilebtn.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editProfilebtn.setBackground(new Color(180, 180, 180));
            }
        });

        editProfilebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                setEditProfile();
			}
        });
        panel.add(editProfilebtn);
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void setEditProfile(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("EditProfile"));

        JLabel lblerror = new JLabel();
        lblerror.setHorizontalAlignment(SwingConstants.CENTER);
        lblerror.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblerror.setForeground(new Color(255, 0, 0));
        lblerror.setBounds(210, HEIGHT - 60, 300, 14);
        panel.add(lblerror);

        birthDateCalendar = new JCalendar();
        birthDateCalendar.setBounds(new Rectangle(40, 50, 225, 150));
        birthDateCalendar.setBounds(420, 205, 225, 150);
        panel.add(birthDateCalendar);

        JLabel profileImg = new JLabel();
        profileImg.setIcon(getUserImg());
        profileImg.setBounds(420, 80, 64, 64);
        panel.add(profileImg);

        JButton changeImg = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Change"));
        changeImg.setFont(new Font("Tahoma", Font.PLAIN, 8));
        changeImg.setBounds(420, 159, 64, 15);
        changeImg.setBorder(BorderFactory.createEmptyBorder());

        changeImg.setBackground(new Color(180, 180, 180));
        panel.add(changeImg);

        changeImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeImg.setBackground(new Color(70, 130, 180));
                changeImg.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeImg.setBackground(new Color(180, 180, 180));
            }
        });

        changeImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                j.setDialogTitle(ResourceBundle.getBundle("Etiquetas").getString("Selectanimage"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg, *.jpeg, *.png", "jpg", "jpeg", "png");
                j.setFileFilter(filter);
                int r = j.showOpenDialog(null);
                if(r == JFileChooser.APPROVE_OPTION){
                    Image img = new ImageIcon(j.getSelectedFile().getAbsolutePath()).getImage();
                    Image resized_img = img.getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH);
                    profileImg.setIcon(new ImageIcon(resized_img));
                    try{
                        img_bytes = Files.readAllBytes(j.getSelectedFile().toPath());
                    }catch(IOException e){
                        lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("UnexpectedError"));
                    }
                }
			}
            
        });

        JLabel lblName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name")+" ");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(30, 116, 141, 14);
        panel.add(lblName);
        
        JLabel lblSur1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname1"));
        lblSur1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSur1.setBounds(30, 156, 141, 14);
        panel.add(lblSur1);

        JLabel lblSur2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname2"));
        lblSur2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSur2.setBounds(30, 198, 141, 14);
        panel.add(lblSur2);

        JLabel lblPrevPass = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PreviousPass"));
        lblPrevPass.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPrevPass.setBounds(30, 234, 141, 14);
        panel.add(lblPrevPass);

        JLabel lblDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate"));
        lblDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDate.setBounds(420, 185, 200, 14);
        panel.add(lblDate);

        JLabel lblpasswd = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NewPass")+":");
        lblpasswd.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblpasswd.setBounds(30, 271, 141, 14);
        panel.add(lblpasswd);

        JLabel lblConfirmPass = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ConfirmPass")+":");
        lblConfirmPass.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblConfirmPass.setBounds(30, 308, 160, 14);
        panel.add(lblConfirmPass);

        nameField = new JTextField();
        nameField.setBounds(210, 110, 166, 30);
        panel.add(nameField);
        nameField.setBorder(BorderFactory.createEmptyBorder());
        nameField.setColumns(10);

        sur1Field = new JTextField();
        sur1Field.setBounds(210, 150, 166, 30);
        sur1Field.setBorder(BorderFactory.createEmptyBorder());
        panel.add(sur1Field);
        sur1Field.setColumns(10);

        sur2Field = new JTextField();
        sur2Field.setBounds(210, 190, 166, 30);
        sur2Field.setBorder(BorderFactory.createEmptyBorder());
        panel.add(sur2Field);
        sur2Field.setColumns(10);

        prevPassField = new JPasswordField();
        prevPassField.setBounds(210, 228, 166, 30);
        prevPassField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(prevPassField);
        prevPassField.setColumns(10);


        newPasswdField = new JPasswordField();
        newPasswdField.setBounds(210, 265, 166, 30);
        newPasswdField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(newPasswdField);

        confirmPasswdField = new JPasswordField();
        confirmPasswdField.setBounds(210, 302, 166, 30);
        confirmPasswdField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(confirmPasswdField);

        btnEnter = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EditProfile"));
        btnEnter.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnEnter.setBounds(WIDTH / 2 - 185, 420, 180, 45);
        btnEnter.setBackground(new Color(180, 180, 180));
        btnEnter.setBorder(BorderFactory.createEmptyBorder());

        btnEnter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEnter.setBackground(new Color(70, 130, 180));
                btnEnter.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEnter.setBackground(new Color(180, 180, 180));
            }
        });

        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User usr = facade.getCurrentUser();

                String name = nameField.getText();
                String sur1 = sur1Field.getText();
                String sur2 = sur2Field.getText();
                // String dni = dniField.getText();

                char[] newPasswd = newPasswdField.getPassword();
                char[] confirmPasswd = confirmPasswdField.getPassword();
                char[] prevPasswd = prevPassField.getPassword();

                Date birthdate = birthDateCalendar.getDate();
                Date now = new Date();
                birthdate = configuration.UtilDate.trim(birthdate);
                now = configuration.UtilDate.trim(now);

                if(prevPasswd.length != 0 && !facade.isHash(prevPasswd, usr.getPasswd())) {
                    lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongPrevPass"));
                    return;
                }

                if(!Arrays.equals(newPasswd, confirmPasswd)) {
                    lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("PassMatch"));
                    return;
                }

                if(now.getTime() != birthdate.getTime()) {
                    System.out.println(now.getTime());
                    System.out.println(birthdate.getTime());
                    long diffInMillies = now.getTime() - birthdate.getTime();
                    if(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) <= 18*365){
                        lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("NotAdult"));
                        return;
                    }
                }else birthdate = usr.getBirthdate();

                if(sur1.length() == 0 && sur2.length() != 0 || sur1.length() != 0 && sur2.length() == 0){
                    lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("BothSurnames"));
                    return;
                }
                String surnames = sur1 + " " + sur2;

                if(name.length() == 0) name = usr.getName();
                if(surnames.equals(" ")) surnames = usr.getSurnames();
                try{
                    usr = facade.guardarCambios(name, surnames, birthdate, usr.getDni(), newPasswd, img_bytes);
                    facade.setCurrentUser(usr);
                }catch(NoSuchAlgorithmException e1){
                    lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("UnexpectedError"));
                }
                setShowProfile();
            }
        });
        panel.add(btnEnter);

        JButton backbtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
        backbtn.setBounds(WIDTH / 2 + 5, 420, 180, 45);
        panel.add(backbtn);
        backbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
        backbtn.setBackground(new Color(180, 180, 180));
        backbtn.setBorder(BorderFactory.createEmptyBorder());
        backbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backbtn.setBackground(new Color(70, 130, 180));
                backbtn.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backbtn.setBackground(new Color(180, 180, 180));
            }
        });
        backbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
                setShowProfile();
			}
            
        });

        JLabel lblNewLabel_6 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EditProfile"));
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel_6.setBounds(214, 30, 181, 52);
        panel.add(lblNewLabel_6);

        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
}
