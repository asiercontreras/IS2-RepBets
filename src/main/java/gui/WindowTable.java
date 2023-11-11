package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import domain.*;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;

public class WindowTable extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable	tabla;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private String[] columnBets = { ResourceBundle.getBundle("Etiquetas").getString("Event") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Question") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("Bet") + ":" };
	
		public WindowTable(User	user){
			super("Apuestas	realizadas	por	"+	user.getName()+":");
			this.setBounds(100,	100,	700,	200);
			UserAdapter	adapt	=	new UserAdapter(user);
			tabla =	new JTable();
			DefaultTableModel tableModelBets = new DefaultTableModel(null, columnBets);
			Vector<Bet> bets = adapt.getBets();
			for (Bet b : bets) {
				Vector<Object> row = new Vector<>();
				Forecast fr = b.getFr();
				Question q = fr.getQuestion();
				Event ev = q.getEvent();
				if (ev.getEventDate().compareTo(new Date()) < 0)
					continue;
				row.add(ev.getDescription());
				row.add(q.getQuestion());
				row.add(ev.getEventDate());
				row.add(b.getBet());
				tableModelBets.addRow(row);
			}
			tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
			//Creamos un JscrollPane	y	le agregamos la JTable
			JScrollPane	scrollPane =	new JScrollPane(tabla);
			//Agregamos el	JScrollPane	al contenedor
			scrollPane.setBounds(0, 0, WIDTH, HEIGHT);
			scrollPane.setViewportView(tabla);
			tabla.setModel(tableModelBets);
			getContentPane().add(scrollPane,	BorderLayout.CENTER);
		}
}