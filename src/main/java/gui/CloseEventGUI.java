package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Forecast;
import domain.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel lblForecast = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Forecasts"));
	private final JButton btnChooseWinner = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("ChooseWinner"));
	private JLabel lblError;
	private JButton btnCloseEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
	private final Object[] options = { ResourceBundle.getBundle("Etiquetas").getString("Yes"),
			ResourceBundle.getBundle("Etiquetas").getString("No") };

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
	private final JButton btnCloseWindow = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CloseWindow"));

	public CloseEventGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setResizable(false);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CloseEvent"));
		jLabelEventDate.setFont(new Font("Tahoma", Font.PLAIN, 14));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabelQueries.setBounds(40, 230, 598, 14);
		jLabelEvents.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = ApplicationLauncher.getBusinessLogic();
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
					// jCalendar1.setCalendar(calendarAct);
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
						queryBox.removeAllItems();
						forecastBox.removeAllItems();

						if (events.isEmpty()) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
							btnCloseEvent.setEnabled(false);
						} else
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
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2); // obtain
																												// ev
																												// object
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
				btnCloseEvent.setEnabled(true);
				btnCloseEvent.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnCloseEvent.setBackground(new Color(70, 130, 180));
						btnCloseEvent.setFocusPainted(false);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						btnCloseEvent.setBackground(new Color(180, 180, 180));
					}
				});
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

		queryBox.setBounds(40, 254, 514, 21);
		getContentPane().add(queryBox);

		lblError = new JLabel();
		lblError.setBounds(new Rectangle(215, 450, 250, 15));
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblError.setForeground(new Color(255, 0, 0));
		getContentPane().add(lblError);

		lblForecast.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblForecast.setBounds(40, 286, 598, 13);
		getContentPane().add(lblForecast);

		forecastBox.setBounds(40, 310, 514, 22);
		getContentPane().add(forecastBox);
		forecastBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (forecastBox.getItemCount() != 0) {
					btnChooseWinner.setEnabled(true);
					btnChooseWinner.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							btnChooseWinner.setBackground(new Color(70, 130, 180));
							btnChooseWinner.setFocusPainted(false);
						}

						@Override
						public void mouseExited(MouseEvent e) {
							btnChooseWinner.setBackground(new Color(180, 180, 180));
						}
					});

				} else {
					btnChooseWinner.setEnabled(false);
				}
			}
		});

		btnCloseEvent.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCloseEvent.setBounds(250, 364, 180, 39);
		btnCloseEvent.setEnabled(false);
		getContentPane().add(btnCloseEvent);
		btnCloseEvent.setBorder(BorderFactory.createEmptyBorder());
		btnCloseEvent.setBackground(new Color(180, 180, 180));

		btnCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2);
				int n;
				if (ev.getEventDate().compareTo(new Date()) > 0) {
					n = JOptionPane.showOptionDialog(btnCloseEvent,
							ResourceBundle.getBundle("Etiquetas").getString("EventNotFinished"), "unresolved",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (n == 1)
						return;
				}
				Boolean unresolved = false;
				for (Question q : ev.getQuestions()) {
					if (q.getResult() == null) {
						unresolved = true;
					}
				}
				if (unresolved) {
					n = JOptionPane.showOptionDialog(btnCloseEvent,
							ResourceBundle.getBundle("Etiquetas").getString("UnresolvedQuestions"), "unresolved",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if (n == 1)
						return;
				}
				facade.closeEvent(ev);
				setVisible(false);
			}
		});

		btnChooseWinner.setFont(new Font("Dialog", Font.BOLD, 14));
		btnChooseWinner.setBounds(40, 364, 180, 39);
		btnChooseWinner.setEnabled(false);
		getContentPane().add(btnChooseWinner);

		btnChooseWinner.setBorder(BorderFactory.createEmptyBorder());
		btnChooseWinner.setBackground(new Color(180, 180, 180));

		btnChooseWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2);
				Question q = (Question) queryBox.getSelectedItem();
				Forecast fr = (Forecast) forecastBox.getSelectedItem();

				int n = JOptionPane.showOptionDialog(btnCloseEvent,
						ResourceBundle.getBundle("Etiquetas").getString("YouSure") + " " + fr.getDescription() + " "
								+ ResourceBundle.getBundle("Etiquetas").getString("WinnerOf") + " " + q.getQuestion()
								+ "? \n" + ResourceBundle.getBundle("Etiquetas").getString("NoRollback"),
						"Select winner forecast", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[1]);
				if (n == 1)
					return;

				facade.setResult(q, fr);
				forecastBox.removeAllItems();
				queryBox.removeItemAt(queryBox.getSelectedIndex());
				for (Question qry : ev.getQuestions()) {
					if (qry.equals(q)) {
						qry.setResult(fr);
					}
				}
				btnChooseWinner.setEnabled(false);
			}
		});

		btnCloseWindow.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCloseWindow.setBounds(458, 364, 180, 39);
		btnCloseWindow.setForeground(Color.BLACK);
		btnCloseWindow.setFocusPainted(false);
		btnCloseWindow.setOpaque(true);
		getContentPane().add(btnCloseWindow);

		btnCloseWindow.setBorder(BorderFactory.createEmptyBorder());
		btnCloseWindow.setBackground(new Color(180, 180, 180));
		btnCloseWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCloseWindow.setBackground(new Color(70, 130, 180));
				btnCloseWindow.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCloseWindow.setBackground(new Color(180, 180, 180));
			}
		});

		btnCloseWindow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCloseWindow.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCloseWindow.setBackground(new Color(30, 144, 255));
			}
		});
		btnCloseWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
}
