package domain;

import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import businessLogic.BLFacade;
import gui.ApplicationLauncher;

public class UserAdapter implements TableModel {

	private User user;
	private int columnas = 4;
	private String[] columnBets = { ResourceBundle.getBundle("Etiquetas").getString("Event") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Question") + ":",
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
			ResourceBundle.getBundle("Etiquetas").getString("Bet") + ":" };
	private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private Vector<Bet> bets;
	
	
	public UserAdapter(User user) {
		this.user=user;
		bets = user.getBets(); //No creo que haya una mejor manera. Se podría hacer otro método.
		
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return user.getBets().size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return columnBets[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch(columnIndex) {
		case 0:
			return bets.get(rowIndex).getFr().getQuestion().getEvent().getDescription();
		case 1:
			return bets.get(rowIndex).getFr().getQuestion().getQuestion();
		case 2:
			return bets.get(rowIndex).getFr().getQuestion().getEvent().getEventDate();
		case 3:
			return bets.get(rowIndex).getBet();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return columnBets[columnIndex].getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
	
}
