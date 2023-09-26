package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher {
	private static InitGUI i;

	public static void launchMainGUI(BLFacade blf) {
		MainGUI a = new MainGUI();
		a.setVisible(true);
	}

	public static void closeInit(ActionEvent e) {
		i.close(e);
	}

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public static void launchInit() {
		i.setVisible(true);
	}

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: " + Locale.getDefault());

		try {

			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {
				// In this option the DataAccess is created by FacadeImplementationWS
				// appFacadeInterface=new BLFacadeImplementation();
				// In this option, you can parameterize the DataAccess (e.g. a Mock DataAccess
				// object)
				DataAccess da = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				appFacadeInterface = new BLFacadeImplementation(da);
				char[] passwd = { '1', '1' };
				appFacadeInterface.createUser("asier", "contreras", "11", new Date(2000, 5, 1), passwd, false);	
				appFacadeInterface.createUser("Adminitrador", "Administrador", "00", new Date(2000, 5, 1), passwd, true);
			} else { // If remote
				String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/"
						+ c.getBusinessLogicName() + "?wsdl";
				// URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = new URL(serviceName);
				// 1st argument refers to wsdl document above
				// 2nd argument is service name, refer to wsdl document above
				// QName qname = new QName("http://businessLogic/",
				// "FacadeImplementationWSService");
				QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
				Service service = Service.create(url, qname);
				appFacadeInterface = service.getPort(BLFacade.class);
			}
			/*
			 * if (c.getDataBaseOpenMode().equals("initialize"))
			 * appFacadeInterface.initializeBD();
			 */

		} catch (Exception e) {
			i.jLabelSelectOption.setText("Error: " + e.toString());
			i.jLabelSelectOption.setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}
		// a.pack();

		i = new InitGUI();
		i.setVisible(true);
	}
}
