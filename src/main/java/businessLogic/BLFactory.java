package businessLogic;

import java.awt.Color;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import gui.InitGUI;

public class BLFactory {

	private static InitGUI i;

	private static BLFacade appFacadeInterface;

	public BLFacade crearBLFacade(ConfigXML c) {

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
			return appFacadeInterface;
			/*
			 * if (c.getDataBaseOpenMode().equals("initialize"))
			 * appFacadeInterface.initializeBD();
			 */

		} catch (Exception e) {
			i.getIJlabelSelectOption().setText("Error: " + e.toString());
			i.getIJlabelSelectOption().setForeground(Color.RED);

			System.out.println("Error in ApplicationLauncher: " + e.toString());
		}

		return null;
	}
}