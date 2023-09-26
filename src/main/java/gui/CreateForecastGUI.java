package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Forecast;
import domain.Question;
import exceptions.ObjectAlreadyExistException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class CreateForecastGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel lblDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description"));
	private final JLabel lblWinrate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Winrate"));
	private JLabel lblError;

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton btnCreateForecast = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateForecast"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelForecast;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesForecast = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Description"), ResourceBundle.getBundle("Etiquetas").getString("Winrate"), };

	private JComboBox<Question> queryBox = new JComboBox<>();
	private JTextField descriptionField;
	private JTextField winrateField;
	private final JLabel lblForecasts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Forecasts")); //$NON-NLS-1$ //$NON-NLS-2$
	private JScrollPane scrollPaneForecast = new JScrollPane();
	private JTable tableForecast = new JTable();

	public CreateForecastGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setResizable(false);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateForecast"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 230, 350, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(347, 410, 130, 30));

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

						if (events.isEmpty()) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
							queryBox.removeAllItems();
						} else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
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
					queryBox.addItem(q);
				}
			}
		});
		btnCreateForecast.setBorder(BorderFactory.createEmptyBorder());
		btnCreateForecast.setBackground(new Color(180, 180, 180));
		queryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableModelForecast.setDataVector(null, columnNamesForecast);
					tableModelForecast.setColumnCount(2);

					if (queryBox.getItemCount() == 0) {
						btnCreateForecast.setEnabled(false);

						return;
					}
					btnCreateForecast.setEnabled(true);

					btnCreateForecast.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							btnCreateForecast.setBackground(new Color(70, 130, 180));
							btnCreateForecast.setFocusPainted(false);
						}

						@Override
						public void mouseExited(MouseEvent e) {
							btnCreateForecast.setBackground(new Color(180, 180, 180));
						}
					});
					Question q = (Question) queryBox.getSelectedItem();

					for (Forecast i : facade.getQuestionForecasts(q)) {
						Vector<Object> row = new Vector<Object>();

						System.out.println("Forecast" + i.getDescription());

						row.add(i.getDescription());
						row.add(i.getWinrate());
						tableModelForecast.addRow(row);
					}
					tableForecast.getColumnModel().getColumn(0).setPreferredWidth(170);
					tableForecast.getColumnModel().getColumn(1).setPreferredWidth(25);
				} catch (Exception e1) {
					lblForecasts.setText(e1.getMessage());
				}
			}
		});

		btnCreateForecast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblError.setForeground(new Color(255, 0, 0));
					String description = descriptionField.getText();
					if (description.length() <= 0) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DescriptionField"));
						return;
					}
					Question q = (Question) queryBox.getSelectedItem();
					float winrate = Float.parseFloat(winrateField.getText());

					facade.createForecast(description, winrate, q);

					Vector<Object> row = new Vector<>();
					row.add(description);
					row.add(winrate);
					tableModelForecast.addRow(row);

					winrateField.setText("");
					descriptionField.setText("");
					lblError.setForeground(new Color(0, 0, 0));
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ForecastCreated"));
				} catch (NumberFormatException e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("WinrateField"));
				} catch (ObjectAlreadyExistException e1) {
					lblError.setText(e1.getMessage());
				}
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);

		btnCreateForecast.setBounds(207, 410, 130, 30);
		btnCreateForecast.setEnabled(false);
		getContentPane().add(btnCreateForecast);

		queryBox.setBounds(40, 254, 374, 21);
		getContentPane().add(queryBox);

		lblError = new JLabel();
		lblError.setBounds(new Rectangle(215, 390, 250, 15));
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblError);

		descriptionField = new JTextField();
		descriptionField.setBounds(143, 285, 271, 25);
		descriptionField.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(descriptionField);
		descriptionField.setColumns(10);

		winrateField = new JTextField();
		winrateField.setBounds(143, 319, 271, 25);
		winrateField.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(winrateField);
		winrateField.setColumns(10);

		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescription.setBounds(40, 291, 93, 13);
		getContentPane().add(lblDescription);

		lblWinrate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWinrate.setBounds(40, 325, 93, 13);
		getContentPane().add(lblWinrate);

		lblForecasts.setBounds(443, 231, 200, 13);
		getContentPane().add(lblForecasts);

		scrollPaneForecast.setViewportView(tableForecast);
		tableModelForecast = new DefaultTableModel(null, columnNamesForecast);

		tableForecast.setModel(tableModelForecast);
		tableForecast.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableForecast.getColumnModel().getColumn(1).setPreferredWidth(25);

		scrollPaneForecast.setBounds(443, 256, 195, 88);
		getContentPane().add(scrollPaneForecast, null);
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
