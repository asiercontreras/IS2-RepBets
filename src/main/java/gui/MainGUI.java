package gui;

/**
 * @author Software Engineering teachers
 */

import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private BLFacade appFacadeInterface;

	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonCreateEvent;
	private JButton jButtonCreateForecast;
	private JButton btnMakeBet;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddMoney;
	private JButton btnLogOut;
	private JButton jButtonCloseEvent;
    private JButton btnChangeProfile;
    private JButton lookBets;

	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();

		this.appFacadeInterface = ApplicationLauncher.getBusinessLogic();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
				} catch (Exception e1) {
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(498, 384);
		this.setContentPane(getJContentPane());
		if (appFacadeInterface.getCurrentUser().isAdmin()) {
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin") + " "
					+ appFacadeInterface.getCurrentUser().getName() + " "
					+ appFacadeInterface.getCurrentUser().getSurnames());

		} else {

			setTitle(ResourceBundle.getBundle("Etiquetas").getString("User") + " "
					+ appFacadeInterface.getCurrentUser().getName() + " "
					+ appFacadeInterface.getCurrentUser().getSurnames());


		}

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getPanel());
			jContentPane.add(getBtnLogOut());
            jContentPane.add(this.getBtnChangeProfile());
			if (appFacadeInterface.getCurrentUser().isAdmin()) {
				jContentPane.add(this.getJButtonCreateQuery());
				jContentPane.add(this.getJButtonCreateEvent());
                jContentPane.add(this.getjButtonCloseEvent());
				jContentPane.add(this.getJButtonCreateForecast());
			} else {
				jContentPane.add(this.getJButtonQueryQueries());
				jContentPane.add(this.getJButtonMakeBet());
				jContentPane.add(this.getJButtonAddMoney());
                jContentPane.add(this.getJButtonLookBets());
			}
		}

		return jContentPane;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 288, 483, 31);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCreateQuery() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(126, 52, 243, 42);
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
			jButtonCreateQuery.setBorder(BorderFactory.createEmptyBorder());
			jButtonCreateQuery.setBackground(new Color(180, 180, 180));
			jButtonCreateQuery.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonCreateQuery.setBackground(new Color(70, 130, 180));
					jButtonCreateQuery.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonCreateQuery.setBackground(new Color(180, 180, 180));
				}
			});

			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonQueryQueries() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(126, 54, 243, 42);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries")); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonQueryQueries.setBorder(BorderFactory.createEmptyBorder());
			jButtonQueryQueries.setBackground(new Color(180, 180, 180));
			jButtonQueryQueries.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonQueryQueries.setBackground(new Color(70, 130, 180));
					jButtonQueryQueries.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonQueryQueries.setBackground(new Color(180, 180, 180));
				}
			});

			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 1, 483, 63);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
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

	/**
	 * 
	 */
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		btnLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));

		if (appFacadeInterface.getCurrentUser().isAdmin()) {
			//For Admin
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
			jButtonCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));

			jButtonCreateForecast.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateForecast"));

			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin") + " "
					+ appFacadeInterface.getCurrentUser().getName() + " "
					+ appFacadeInterface.getCurrentUser().getSurnames());
			jButtonCloseEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));

		} else {
			//For User
			lookBets.setText(ResourceBundle.getBundle("Etiquetas").getString("LookBets"));
			btnMakeBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
			jButtonAddMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("Wallet"));
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("User") + " "
					+ appFacadeInterface.getCurrentUser().getName() + " "
					+ appFacadeInterface.getCurrentUser().getSurnames());

		}

	}

	/**
	 * @return
	 */
	private JButton getJButtonCreateEvent() {
		if (jButtonCreateEvent == null) {
			jButtonCreateEvent = new JButton();
			jButtonCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
			jButtonCreateEvent.setBounds(126, 97, 243, 42);
			jButtonCreateEvent.setBorder(BorderFactory.createEmptyBorder());
			jButtonCreateEvent.setBackground(new Color(180, 180, 180));
			jButtonCreateEvent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonCreateEvent.setBackground(new Color(70, 130, 180));
					jButtonCreateEvent.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonCreateEvent.setBackground(new Color(180, 180, 180));
				}
			});

			jButtonCreateEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateEventGUI();

					a.setVisible(true);
				}
			});
		}
		return jButtonCreateEvent;
	}

	/**
	 * @return
	 */
	private JButton getJButtonCreateForecast() {
		if (jButtonCreateForecast == null) {
			jButtonCreateForecast = new JButton();
			jButtonCreateForecast.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateForecast"));
			jButtonCreateForecast.setBounds(126, 142, 243, 42);

			jButtonCreateForecast.setBorder(BorderFactory.createEmptyBorder());
			jButtonCreateForecast.setBackground(new Color(180, 180, 180));
			jButtonCreateForecast.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonCreateForecast.setBackground(new Color(70, 130, 180));
					jButtonCreateForecast.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonCreateForecast.setBackground(new Color(180, 180, 180));
				}
			});
			jButtonCreateForecast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateForecastGUI();

					a.setVisible(true);
				}
			});
		}
		return jButtonCreateForecast;
	}

	private JButton getjButtonCloseEvent() {
		if (jButtonCloseEvent == null) {
			jButtonCloseEvent = new JButton();

			jButtonCloseEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
			jButtonCloseEvent.setBounds(126, 187, 243, 42);

			jButtonCloseEvent.setBorder(BorderFactory.createEmptyBorder());
			jButtonCloseEvent.setBackground(new Color(180, 180, 180));
			jButtonCloseEvent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonCloseEvent.setBackground(new Color(70, 130, 180));
					jButtonCloseEvent.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonCloseEvent.setBackground(new Color(180, 180, 180));
				}
			});

			jButtonCloseEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CloseEventGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonCloseEvent;
	}

	private JButton getJButtonMakeBet() {
		if (btnMakeBet == null) {
			btnMakeBet = new JButton();
			btnMakeBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
			btnMakeBet.setBounds(126, 99, 243, 42);
			btnMakeBet.setBorder(BorderFactory.createEmptyBorder());
			btnMakeBet.setBackground(new Color(180, 180, 180));
			btnMakeBet.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btnMakeBet.setBackground(new Color(70, 130, 180));
					btnMakeBet.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					btnMakeBet.setBackground(new Color(180, 180, 180));
				}
			});

			btnMakeBet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateBetGUI();

					a.setVisible(true);
				}
			});
		}
		return btnMakeBet;
	}

	private JButton getJButtonAddMoney() {
		if (jButtonAddMoney == null) {
			jButtonAddMoney = new JButton();
			jButtonAddMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("Wallet"));
			jButtonAddMoney.setBounds(126, 144, 243, 42);
			jButtonAddMoney.setBorder(BorderFactory.createEmptyBorder());
			jButtonAddMoney.setBackground(new Color(180, 180, 180));
			jButtonAddMoney.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					jButtonAddMoney.setBackground(new Color(70, 130, 180));
					jButtonAddMoney.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					jButtonAddMoney.setBackground(new Color(180, 180, 180));
				}
			});

			jButtonAddMoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new WalletGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonAddMoney;
	}

	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton();
			btnLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
			btnLogOut.setFont(new Font("Dialog", Font.BOLD, 14));
			btnLogOut.setBounds(167, 240, 146, 35);
			btnLogOut.setBorder(BorderFactory.createEmptyBorder());
			btnLogOut.setBackground(new Color(180, 180, 180));
			btnLogOut.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btnLogOut.setBackground(new Color(70, 130, 180));
					btnLogOut.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					btnLogOut.setBackground(new Color(180, 180, 180));
				}
			});

			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					appFacadeInterface.setCurrentUser(null);
					setVisible(false);
					ApplicationLauncher.launchInit();
				}
			});
		}
		return btnLogOut;
	}

	private JButton getBtnChangeProfile() {
        if (btnChangeProfile == null) {
            Image resized_img;
            Image img;
            byte[] img_bytes = ApplicationLauncher.getBusinessLogic().getCurrentUser().getProfile_img();
            btnChangeProfile = new JButton();

            if(img_bytes == null){
                img = new ImageIcon(this.getClass().getResource("/default_user_img.png")).getImage();
                resized_img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
            }else{
                img = new ImageIcon(img_bytes).getImage();
                resized_img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
            }
            btnChangeProfile.setIcon(new ImageIcon(resized_img));

            btnChangeProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnChangeProfile.setFont(new Font("Dialog", Font.BOLD, 14));
            btnChangeProfile.setBounds(369-20, 10, 40, 35);
            btnChangeProfile.setBorder(BorderFactory.createEmptyBorder());
            btnChangeProfile.setContentAreaFilled(false);
            btnChangeProfile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EditarPerfilGUI a = new EditarPerfilGUI();
                    a.setVisible(true);
                }
            });
        }
		return btnChangeProfile;
	}

	private JButton getJButtonLookBets() {
		if (lookBets == null) {
			lookBets = new JButton();
			lookBets.setText(ResourceBundle.getBundle("Etiquetas").getString("LookBets"));
			lookBets.setBounds(126, 189, 243, 42);
			lookBets.setBorder(BorderFactory.createEmptyBorder());
			lookBets.setBackground(new Color(180, 180, 180));
			lookBets.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lookBets.setBackground(new Color(70, 130, 180));
					lookBets.setFocusPainted(false);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lookBets.setBackground(new Color(180, 180, 180));
				}
			});

			lookBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new LookBetsGUI();

					a.setVisible(true);
				}
			});
		}
		return lookBets;
	}
}
