package dataAccess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.logging.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;

import javax.swing.JTextArea;



import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

/**
 * It runs the database server as a separate process.
 */
public class ObjectdbManagerServer extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea textArea;
	ConfigXML c;
	
	//For windows
    private String objectDbpath="src\\main\\resources\\objectdb.jar";
    
    //For mac 
    //private String objectDbpath="src//main//resources//objectdb.jar";

    

	public static void main(String[] args) {
		
		//Crear logger
		Logger logger = Logger.getLogger(ObjectdbManagerServer.class.getSimpleName());
		
		try {
        	
			//Crear el nombre del que va a tener el archivo
			String nombreArchivo = ObjectdbManagerServer.class.getSimpleName() + "LOGGER.txt";
			
			//Crear un fichero para saber si se ha creado o no
			File archivo = new File(nombreArchivo);
			
			//Comprobar si ya existe el archivo para no crear otro
			if(!archivo.exists()) {
				// Crear el fichero
				FileHandler fileHandler = new FileHandler(nombreArchivo);

				// El formato que vayamos a querer darle al logger
				fileHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(fileHandler);
			}
		    
			ObjectdbManagerServer dialog = new ObjectdbManagerServer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			//El mensaje que queremos poner cuando el programa se ejecute correctamente
			logger.log(Level.INFO, ">>>>>>> " + ObjectdbManagerServer.class.getSimpleName() + " ejecutando correctamente\n");
		} catch (Exception e) {
			//El mensaje que queremos poner cuando el programa salte un error
			logger.log(Level.INFO, ">>>>>>> ERROR al ejecutar en" + ObjectdbManagerServer.class.getSimpleName()+"\n");
		}
	}


	public ObjectdbManagerServer() {
	    
		setTitle("objectDBManagerServer: running the database server");
		setBounds(100, 100, 486, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			textArea = new JTextArea();
			contentPanel.add(textArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.append("\n\n\nClosing the database... ");
					    try {
					    	System.out.println("Server close");
					    	 try {
					    		    
					    		    
							    	Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" stop");
							    	
							    } catch (Exception ioe) {
							    	System.out.println (ioe);
							    }

								System.exit(1);
							
						} catch (Exception e1) {
						}
						System.exit(1);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.isDatabaseLocal()) {
			textArea.append("\nERROR, the database is configured as local");
		}
		else {
		try{
			System.out.println("Lauching objectdb server");

			
			try {
		    	Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" start");
		    } catch (Exception ioe) {
		    	System.out.println (ioe);
		    }

		    textArea.append("\nAccess granted to: "+c.getUser());
		    
			textArea.append("\nPress button to exit this database server... ");
			
		} catch (Exception e) {
			textArea.append("Something has happened in ObjectDbManagerServer: "+e.toString());

		}
		
		}
	}

}
	
