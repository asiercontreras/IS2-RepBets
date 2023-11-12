package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

public class InitGUI extends JFrame {
	private JButton jButtonSignIn;
	private JButton jButtonSignUp;
	private JButton jButtonEvent;
	private JPanel panel = null;
	private JPanel JContentPane = null;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	protected JLabel jLabelSelectOption;
	
	public JLabel getIJlabelSelectOption() {
		return this.jLabelSelectOption;
	}

	public InitGUI() {

		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		init();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void init() {
		this.setSize(510, 320);
		this.getContentPane().setLayout(null);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private JPanel getJContentPane() {
		if (JContentPane == null) {
			JContentPane = new JPanel();
			JContentPane.setLayout(null);
			JContentPane.add(getLblNewLabel());
			JContentPane.add(getPanel());
			JContentPane.add(this.getJButtonSignUp());
			JContentPane.add(this.getJButtonSignIn());
			JContentPane.add(this.getJButtonEvent());
		}
		return JContentPane;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 222, 483, 31);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		jButtonSignIn.setText(ResourceBundle.getBundle("Etiquetas").getString("SignIn"));
		jButtonSignUp.setText(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		jButtonEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeEvents"));

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

	private JButton getJButtonSignIn() {

		ImageIcon icono = new ImageIcon(this.getClass().getResource("/in.png"));
		Image image = icono.getImage(); // transform it
		Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		icono = new ImageIcon(newimg); // transform it back

		jButtonSignIn = new JButton();
		jButtonSignIn.setText("SIGN UP");
		jButtonSignIn.setIcon(icono);
		jButtonSignIn.setForeground(Color.BLACK);
		jButtonSignIn.setBackground(new Color(180, 180, 180));
		jButtonSignIn.setText(ResourceBundle.getBundle("Etiquetas").getString("SignIn"));
		jButtonSignIn.setBounds(2, 105, 243, 42);
		jButtonSignIn.setBorder(BorderFactory.createEmptyBorder());

		this.getContentPane().add(jButtonSignIn);

		jButtonSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButtonSignIn.setBackground(new Color(70, 130, 180));
				jButtonSignIn.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButtonSignIn.setBackground(new Color(180, 180, 180));
			}
		});

		jButtonSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
			}
		});

		return jButtonSignIn;
	}

	private JButton getJButtonSignUp() {
		ImageIcon icono = new ImageIcon(this.getClass().getResource("/register-14-512.png"));
		Image image = icono.getImage(); // transform it
		Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		icono = new ImageIcon(newimg); // transform it back

		jButtonSignUp = new JButton();
		jButtonSignUp.setText(ResourceBundle.getBundle("Etiquetas").getString("SignUp"));
		jButtonSignUp.setBounds(250, 105, 243, 42);
		jButtonSignUp.setIcon(icono);

		jButtonSignUp.setForeground(Color.BLACK);
		jButtonSignUp.setBackground(new Color(180, 180, 180));
		jButtonSignUp.setFocusPainted(false);
		jButtonSignUp.setOpaque(true);
		jButtonSignUp.setBorder(BorderFactory.createEmptyBorder());

		jButtonSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButtonSignUp.setBackground(new Color(70, 130, 180));
				jButtonSignUp.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButtonSignUp.setBackground(new Color(180, 180, 180));
			}
		});
		jButtonSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SignUpGUI();
				a.setVisible(true);
			}
		});
		this.getContentPane().add(jButtonSignUp);

		return jButtonSignUp;
	}

	private JButton getJButtonEvent() {
		ImageIcon icono = new ImageIcon(this.getClass().getResource("/sports.png"));
		Image image = icono.getImage(); // transform it
		Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		icono = new ImageIcon(newimg); // transform it back

		jButtonEvent = new JButton();
		jButtonEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeEvents"));
		jButtonEvent.setBounds(248 - 243 / 2, 160, 243, 42);
		jButtonEvent.setIcon(icono);
		jButtonEvent.setForeground(Color.BLACK);
		jButtonEvent.setBackground(new Color(180, 180, 180));
		jButtonEvent.setFocusPainted(false);
		jButtonEvent.setOpaque(true);
		jButtonEvent.setBorder(BorderFactory.createEmptyBorder());

		this.getContentPane().add(jButtonEvent);
		jButtonEvent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButtonEvent.setBackground(new Color(70, 130, 180));
				jButtonEvent.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButtonEvent.setBackground(new Color(180, 180, 180));
			}
		});

		jButtonEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateBetGUI();
				a.setVisible(true);
			}
		});

		return jButtonEvent;
	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 483, 63);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 20));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}

	protected void close(ActionEvent e) {
		this.setVisible(false);
	}
}
