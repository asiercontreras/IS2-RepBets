package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bet;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.NotEnoughMoneyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.File;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.*;

import javax.swing.table.DefaultTableModel;

public class CreateBetGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel lblForecast = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Forecasts"));
	private final JLabel labelBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
	private JLabel lblcurrentBet;
	private JLabel lblMoney;
	private JLabel lblError;

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton btnMakeBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();

	private DefaultTableModel tableModelEvents;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"), };
	private JComboBox<Question> queryBox = new JComboBox<>();
	private JComboBox<Forecast> forecastBox = new JComboBox<>();
	private JTextField betField = new JTextField();
	private JButton btnQuestion;

	// Crear logger
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public CreateBetGUI() {

		try {

			// Crear el nombre del que va a tener el archivo
			String nombreArchivo = this.getClass().getSimpleName() + "LOGGER.txt";

			// Crear un fichero para saber si se ha creado o no
			File archivo = new File(nombreArchivo);

			// Comprobar si ya existe el archivo para no crear otro
			if (!archivo.exists()) {
				// Crear el fichero
				FileHandler fileHandler = new FileHandler(nombreArchivo);

				// El formato que vayamos a querer darle al logger
				fileHandler.setFormatter(new SimpleFormatter());
				
				fileHandler.setLevel(Level.INFO);
				
				logger.addHandler(fileHandler);
			}

			jbInit();

			// El mensaje que queremos poner cuando el programa se ejecute correctamente
			logger.log(Level.INFO, ">>>>>>> " + this.getClass().getSimpleName() + " ejecutando correctamente\n");
		} catch (SecurityException e) {
			// El mensaje que queremos poner cuando el programa salte un error
			logger.log(Level.INFO, ">>>>>>> ERROR en el logger\n");
		} catch (Exception e) {
			// El mensaje que queremos poner cuando el programa salte un error
			logger.log(Level.INFO, ">>>>>>> ERROR al ejecutar en " + this.getClass().getSimpleName() + "\n");
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setResizable(false);
		BLFacade facade = ApplicationLauncher.getBusinessLogic();
		User usr = facade.getCurrentUser();
		if (usr == null) {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeNextEvent"));
		} else {
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateBet"));

		}
		jLabelEventDate.setFont(new Font("Tahoma", Font.PLAIN, 14));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabelQueries.setBounds(40, 230, 409, 14);
		jLabelEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(264, 410, 130, 30));
		jButtonClose.setBorder(BorderFactory.createEmptyBorder());
		jButtonClose.setBackground(new Color(180, 180, 180));
		jButtonClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButtonClose.setBackground(new Color(70, 130, 180));
				jButtonClose.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButtonClose.setBackground(new Color(180, 180, 180));
			}
		});
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = ApplicationLauncher.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = ApplicationLauncher.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							if (ev.isClosed())
								continue;
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {
						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();
				queryBox.removeAllItems();

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					if (q.getResult() == null)
						queryBox.addItem(q);
				}
			}
		});

		queryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forecastBox.removeAllItems();
				Question q = (Question) queryBox.getSelectedItem();

				if (q == null)
					return;

				Vector<Forecast> frs = facade.getQuestionForecasts(q);
				for (Forecast fr : frs) {
					forecastBox.addItem(fr);
				}
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);

		queryBox.setBounds(40, 254, 354, 21);
		getContentPane().add(queryBox);

		lblError = new JLabel();
		lblError.setBounds(new Rectangle(215, 450, 250, 15));
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblError);

		lblForecast.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblForecast.setBounds(40, 286, 409, 13);
		getContentPane().add(lblForecast);

		forecastBox.setBounds(40, 310, 354, 22);
		getContentPane().add(forecastBox);

		forecastBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usr == null)
					return;
				if (forecastBox.getItemCount() == 0) {
					lblcurrentBet.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBet") + " ");
					btnMakeBet.setEnabled(false);
					betField.setText("");
					return;
				}
				btnMakeBet.setEnabled(true);
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
				Bet bet = usr.findBet((Forecast) forecastBox.getSelectedItem());
				if (bet != null)
					lblcurrentBet.setText(
							ResourceBundle.getBundle("Etiquetas").getString("CurrentBet") + " " + bet.getBet());
			}
		});
		/* Condicion para que no aparezcan los datos del wallet */
		if (usr != null) {

			/* Boton make bet */
			btnMakeBet.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnMakeBet.setEnabled(false);
			btnMakeBet.setBounds(229, 360, 204, 39);
			btnMakeBet.setBorder(BorderFactory.createEmptyBorder());

			this.getContentPane().add(btnMakeBet);
			btnMakeBet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblError.setForeground(new Color(255, 0, 0));
					try {
						float bet = Float.parseFloat(betField.getText());
						Forecast fr = (Forecast) forecastBox.getSelectedItem();
						User usr = facade.getCurrentUser();
						Question q = (Question) queryBox.getSelectedItem();

						if (usr == null) {
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginToBet"));
							return;
						}

						if (bet < q.getBetMinimum()) {
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginToBet") + " "
									+ q.getBetMinimum());
							return;
						}

						if (q == null || fr == null) {
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("QandF"));
							return;
						}

						usr.setWallet(facade.createBet(bet, usr, fr));
						usr.addBet(new Bet(bet, usr, fr));
						lblError.setForeground(new Color(0, 0, 0));
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BetUpdated"));
						lblcurrentBet.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBet") + " "
								+ String.valueOf(bet));
						lblMoney.setText(
								ResourceBundle.getBundle("Etiquetas").getString("YourMoney") + " " + usr.getWallet());
					} catch (IllegalArgumentException e1) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NumberField"));
					} catch (NotEnoughMoneyException e1) {
						lblError.setText(e1.getMessage());
					}
				}
			});

			/* End boton make bet */

			lblcurrentBet = new JLabel();
			lblcurrentBet.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblcurrentBet.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBet") + " ");
			lblcurrentBet.setBounds(470, 254, 200, 16);
			lblcurrentBet.setBorder(BorderFactory.createEmptyBorder());
			getContentPane().add(lblcurrentBet);

			lblMoney = new JLabel();
			lblMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBet") + " "
					+ facade.getCurrentUser().getWallet());
			lblMoney.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMoney.setBounds(470, 230, 200, 16);
			getContentPane().add(lblMoney);

			labelBet.setFont(new Font("Tahoma", Font.PLAIN, 14));
			labelBet.setBounds(470, 286, 216, 14);
			getContentPane().add(labelBet);

			btnQuestion = new JButton();
			ImageIcon icono = new ImageIcon(this.getClass().getResource("/question.png"));
			Image image = icono.getImage(); // transform it
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
			icono = new ImageIcon(newimg); // transform it back
			btnQuestion.setIcon(icono);
			btnQuestion.setBounds(365, 310, 140, 35);
			btnQuestion.setVisible(true);
			btnQuestion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnQuestion.setFont(new Font("Dialog", Font.BOLD, 14));
			btnQuestion.setBorder(BorderFactory.createEmptyBorder());
			btnQuestion.setContentAreaFilled(false);
			getContentPane().add(btnQuestion);
			btnQuestion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,
							ResourceBundle.getBundle("Etiquetas").getString("CuotaGanancia"));

				}
			});
			betField.setBounds(470, 310, 140, 35);
			betField.setBorder(BorderFactory.createEmptyBorder());
			betField.setText(ResourceBundle.getBundle("Etiquetas").getString("QuantityInEuros"));
			betField.setForeground(new Color(190, 190, 190));
			betField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					betField.setText("");
					betField.setForeground(new Color(0, 0, 0));

				}
			});
			betField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (betField.getText().isEmpty()) {
						betField.setForeground(new Color(190, 190, 190));
						betField.setText(ResourceBundle.getBundle("Etiquetas").getString("QuantityInEuros"));
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (betField.getText().isEmpty()) {
						betField.setForeground(new Color(190, 190, 190));
						betField.setText(ResourceBundle.getBundle("Etiquetas").getString("QuantityInEuros"));
					}
				}
			});
			getContentPane().add(betField);
			betField.setColumns(10);
		}
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
