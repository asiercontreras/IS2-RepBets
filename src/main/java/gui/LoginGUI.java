package gui;

import javax.swing.*;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserNotFound;
import java.awt.event.FocusListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginGUI extends JFrame {
	private JTextField dniField;
	private JPasswordField passwdField;
	private JButton logButton;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private JLabel lblNewLabel_2 = new JLabel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblERROR;
	private JLabel lblDNI;
	private JLabel lblPasswd;
	private boolean error1 = false;
	private boolean error2 = false;

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(27, 321, 483, 31);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}

	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}

	/**
	 * @return
	 */
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	private void redibujar() {
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		lblDNI.setText(ResourceBundle.getBundle("Etiquetas").getString("UserDni") + " ");
		lblPasswd.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		logButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Enter"));
		if (error1 == false && error2 == false) {
			dniField.setText(ResourceBundle.getBundle("Etiquetas").getString("WriteDNI"));
	

		}
		if(error1) {
			lblERROR.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectPassword"));

		}
		if(error2) {
			lblERROR.setText(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));

		}

	}

	public LoginGUI() {
		try {
			getContentPane().add(getPanel());
			jbInit();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.setSize(new Dimension(550, 400));
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("LOGIN");

		BLFacade facade = ApplicationLauncher.getBusinessLogic();

		lblDNI = new JLabel();
		lblDNI.setText(ResourceBundle.getBundle("Etiquetas").getString("UserDni") + " ");
		lblDNI.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDNI.setBounds(92, 108, 125, 14);
		getContentPane().add(lblDNI);

		lblERROR = new JLabel();
		lblERROR.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblERROR.setBounds(199, 224, 300, 14);
		lblERROR.setForeground(new Color(255, 0, 0));
		getContentPane().add(lblERROR);

		dniField = new JTextField();
		dniField.setBounds(252, 97, 235, 40);
		getContentPane().add(dniField);
		dniField.setColumns(10);
		dniField.setText(ResourceBundle.getBundle("Etiquetas").getString("WriteDNI"));
		dniField.setBorder(BorderFactory.createEmptyBorder());
		Insets insets = new Insets(30, 50, 5, 10);
		dniField.setMargin(insets);
		dniField.setForeground(new Color(190, 190, 190));
		dniField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dniField.setText("");
				dniField.setForeground(new Color(0, 0, 0));

			}
		});
		dniField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (dniField.getText().isEmpty()) {
					dniField.setForeground(new Color(190, 190, 190));
					dniField.setText(ResourceBundle.getBundle("Etiquetas").getString("WriteDNI"));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (dniField.getText().isEmpty()) {
					dniField.setForeground(new Color(190, 190, 190));
					dniField.setText(ResourceBundle.getBundle("Etiquetas").getString("WriteDNI"));
				}
			}
		});

		lblPasswd = new JLabel();
		lblPasswd.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPasswd.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPasswd.setBounds(92, 172, 114, 14);
		getContentPane().add(lblPasswd);
		passwdField = new JPasswordField();
		passwdField.setBounds(252, 161, 235, 40);
		getContentPane().add(passwdField);
		passwdField.setForeground(new Color(0, 0, 0));
		passwdField.setBorder(BorderFactory.createEmptyBorder());

		logButton = new JButton();
		logButton.setText(ResourceBundle.getBundle("Etiquetas").getString("Enter"));
		logButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		logButton.setBounds(165, 249, 190, 45);
		this.getContentPane().add(logButton);
		logButton.setBorder(BorderFactory.createEmptyBorder());
		logButton.setBackground(new Color(180, 180, 180));
		logButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logButton.setBackground(new Color(70, 130, 180));
				logButton.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logButton.setBackground(new Color(180, 180, 180));
			}
		});

		logButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error1 = false;
				error2 = false;
				String dni = dniField.getText();
				char[] passwd = passwdField.getPassword();
				try {
					User usr = facade.getUser(dni);

					if (!facade.isHash(passwd, usr.getPasswd())) {
						error1 = true;
						lblERROR.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectPassword"));

					} else {
						facade.setCurrentUser(usr);
						ApplicationLauncher.launchMainGUI(facade);
						ApplicationLauncher.closeInit(e);
						jButtonClose_actionPerformed(e);
					}
				} catch (UserNotFound e1) {
					error2 = true;
					lblERROR.setText(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));
				}
			}
		});

		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel_2.setBounds(92, 32, 395, 54);
		getContentPane().add(lblNewLabel_2);
		this.getRootPane().setDefaultButton(logButton);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
