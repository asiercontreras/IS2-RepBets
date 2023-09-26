package gui;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;

public class LookBetsGUI extends JFrame {
	private int WIDTH = 750;
	private int HEIGHT = 500;
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private JTable tableBets = new JTable();
	private DefaultTableModel tableModelBets;
	private JScrollPane scrollBets = new JScrollPane();

	private String[] columnBets = { ResourceBundle.getBundle("Etiquetas").getString("Event") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Question") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Forecast") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("Bet") + ":" };

	public LookBetsGUI() {
		this.setResizable(false);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LookBets"));
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tableModelBets = new DefaultTableModel(null, columnBets);
		Vector<Bet> bets = facade.getCurrentUser().getBets();
		for (Bet b : bets) {
			Vector<Object> row = new Vector<>();
			Forecast fr = b.getFr();
			Question q = fr.getQuestion();
			Event ev = q.getEvent();
			if (ev.getEventDate().compareTo(new Date()) < 0)
				continue;
			row.add(ev.getDescription());
			row.add(q.getQuestion());
			row.add(fr.getDescription());
			row.add(ev.getEventDate());
			row.add(b.getBet());
			tableModelBets.addRow(row);
		}

		scrollBets.setBounds(0, 0, WIDTH, HEIGHT);
		scrollBets.setViewportView(tableBets);
		tableBets.setModel(tableModelBets);
		this.getContentPane().add(scrollBets);
	}
}
