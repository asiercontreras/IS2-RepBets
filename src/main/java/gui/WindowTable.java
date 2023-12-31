package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import domain.*;

public class WindowTable extends JFrame{
	private JTable	tabla;
	
	public WindowTable(User	user){
		super("Apuestas	realizadas	por	"+	user.getName()+":");
		this.setBounds(100,	100,	700,	200);
		UserAdapter	adapt	=	new UserAdapter(user);
		tabla =	new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
		//Creamos un JscrollPane	y	le agregamos la JTable
		JScrollPane	scrollPane =	new JScrollPane(tabla);
		//Agregamos el	JScrollPane	al contenedor
		getContentPane().add(scrollPane,	BorderLayout.CENTER);
	}
}
