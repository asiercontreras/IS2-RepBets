package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import exceptions.ObjectAlreadyExistException;

public class SignUpGUI extends JFrame {
	private JTextField nameField;
	private JTextField sur1Field;
	private JTextField sur2Field;
	private JTextField dniField;
	private JPasswordField passwdField;
    private JCalendar birthDateCalendar;
    private JButton btnEnter;

	public SignUpGUI() {
        BLFacade facade = ApplicationLauncher.getBusinessLogic();
		this.setResizable(false);
		this.setSize(new Dimension(700, 450));
        setTitle("SIGN UP");

		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name")+" ");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(44, 116, 141, 14);
		getContentPane().add(lblName);
		
        birthDateCalendar = new JCalendar();
		birthDateCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		birthDateCalendar.setBounds(366, 141, 225, 150);
		getContentPane().add(birthDateCalendar);
		
		JLabel lblSur1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname1")+" ");
		lblSur1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSur1.setBounds(44, 156, 141, 14);
		getContentPane().add(lblSur1);
		
		JLabel lblSur2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname2")+" ");
		lblSur2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSur2.setBounds(44, 198, 141, 14);
		getContentPane().add(lblSur2);
		
		JLabel lblDNI = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DNI")+" ");
		lblDNI.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDNI.setBounds(44, 234, 141, 14);
		getContentPane().add(lblDNI);
		
		JLabel lblDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate")+" ");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDate.setBounds(366, 116, 196, 14);
		getContentPane().add(lblDate);
		
		JLabel lblpasswd = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password")+" ");
		lblpasswd.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblpasswd.setBounds(44, 271, 141, 14);
		getContentPane().add(lblpasswd);
		
		nameField = new JTextField();
		nameField.setBounds(176, 110, 166, 30);
		getContentPane().add(nameField);
		nameField.setBorder(BorderFactory.createEmptyBorder());
		nameField.setColumns(10);
		
		sur1Field = new JTextField();
		sur1Field.setBounds(176, 150, 166, 30);
		sur1Field.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(sur1Field);
		sur1Field.setColumns(10);
		
		sur2Field = new JTextField();
		sur2Field.setBounds(176, 190, 166, 30);
		sur2Field.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(sur2Field);
		sur2Field.setColumns(10);
		
		dniField = new JTextField();
		dniField.setBounds(176, 228, 166, 30);
		dniField.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(dniField);
		dniField.setColumns(10);

        JLabel lblerror = new JLabel();
        lblerror.setHorizontalAlignment(SwingConstants.CENTER);
        lblerror.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblerror.setForeground(new Color(255, 0, 0));
        lblerror.setBounds(176, 297, 300, 14);
        getContentPane().add(lblerror);
		
		passwdField = new JPasswordField();
		passwdField.setBounds(176, 265, 166, 30);
		passwdField.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(passwdField);
		
        btnEnter = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnEnter.setBounds(214, 320, 180, 45);
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
                String name = nameField.getText();
                String sur1 = sur1Field.getText();
                String sur2 = sur2Field.getText();
                String dni = dniField.getText();
                char[] passwd = passwdField.getPassword();
                String surnames = sur1 + " " + sur2;
                Date birthdate = birthDateCalendar.getDate();
                Date now = new Date();
                birthdate = configuration.UtilDate.trim(birthdate);
                now = configuration.UtilDate.trim(now);
                long diffInMillies = now.getTime() - birthdate.getTime();
                if(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) <= 18*365){
                    lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("NotAdult"));
                    return;
                }
                if(name.length() > 0 && sur1.length() > 0 && sur2.length() > 0 && 
                        dni.length() > 0 && passwd.length > 0){
                    try {
                        facade.createUser(name, surnames, dni, birthdate, passwd, false);
                        jButtonClose_actionPerformed(e);
                    } catch (ObjectAlreadyExistException e1) {
                        lblerror.setText(e1.getMessage());
                    } catch (NoSuchAlgorithmException e1) {
                        lblerror.setText(e1.getMessage());
                    }
                }else lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("Empty"));
            }
        });
		getContentPane().add(btnEnter);
		
        this.getRootPane().setDefaultButton(btnEnter);

		JLabel lblNewLabel_6 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_6.setBounds(138, 30, 363, 52);
		getContentPane().add(lblNewLabel_6);
		
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
