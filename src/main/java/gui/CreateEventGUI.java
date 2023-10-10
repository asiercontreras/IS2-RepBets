package gui;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import exceptions.ObjectAlreadyExistException;
import exceptions.EventFinished;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.*;

public class CreateEventGUI extends JFrame {
	private JTextField txtEventDescription;
	private JButton btnCreateEvent;
	private JCalendar jCalendar;
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<>();
	
	// Crear logger
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public CreateEventGUI() {

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
				logger.addHandler(fileHandler);
			}

			jbInit();

			// El mensaje que queremos poner cuando el programa se ejecute correctamente
			logger.log(Level.INFO, ">>>>>>> " + this.getClass().getSimpleName() + " ejecutando correctamente\n");
		} catch (Exception e) {
			// El mensaje que queremos poner cuando el programa salte un error
			logger.log(Level.INFO, ">>>>>>> ERROR al ejecutar en " + this.getClass().getSimpleName()+"\n");
		}
	}

	private void jbInit() {
		setResizable(false);
		this.setSize(new Dimension(700, 500));
		getContentPane().setLayout(null);
		this.setTitle("Create Event");
		BLFacade facade = ApplicationLauncher.getBusinessLogic();

		JLabel title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		title.setFont(new Font("Tahoma", Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(227, 48, 251, 67);
		getContentPane().add(title);

		JLabel lblError = new JLabel();
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblError.setBounds(200, 370, 500, 15);
		getContentPane().add(lblError);

		JLabel lblDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDescription"));
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescription.setBounds(56, 168, 137, 29);
		getContentPane().add(lblDescription);

		txtEventDescription = new JTextField();
		txtEventDescription.setBounds(202, 169, 263, 28);
		txtEventDescription.setBorder(BorderFactory.createEmptyBorder());
		getContentPane().add(txtEventDescription);
		txtEventDescription.setColumns(10);

		jCalendar = new JCalendar();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);
		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		jCalendar.setBounds(202, 221, 225, 150);
		getContentPane().add(jCalendar);
		jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
				CreateQuestionGUI.paintDaysWithEvents(jCalendar, datesWithEventsCurrentMonth);
			}
		});

		JLabel lblCalendar = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Date"));
		lblCalendar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCalendar.setBounds(56, 221, 115, 30);
		getContentPane().add(lblCalendar);

		btnCreateEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnCreateEvent.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnCreateEvent.setBounds(221, 400, 245, 50);
		getContentPane().add(btnCreateEvent);
		btnCreateEvent.setBorder(BorderFactory.createEmptyBorder());
		btnCreateEvent.setBackground(new Color(180, 180, 180));
		btnCreateEvent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCreateEvent.setBackground(new Color(70, 130, 180));
				btnCreateEvent.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCreateEvent.setBackground(new Color(180, 180, 180));
			}
		});

		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblError.setForeground(new Color(255, 0, 0));
					String description = txtEventDescription.getText();
					if (description.length() == 0)
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DescriptionField"));
					else {
						Date eventDate = UtilDate.trim(jCalendar.getDate());
						facade.createEvent(description, eventDate);
						lblError.setForeground(new Color(0, 0, 0));
						lblError.setText("The event has been created.");
					}
				} catch (NumberFormatException e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NumberField"));
				} catch (EventFinished e2) {
					lblError.setText(e2.getMessage());
				} catch (ObjectAlreadyExistException e3) {
					lblError.setText(e3.getMessage());
				}
			}
		});
	}
}
