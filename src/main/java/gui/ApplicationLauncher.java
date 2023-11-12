package gui;

import java.awt.event.ActionEvent;
import java.util.Date;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import configuration.ConfigXML;
import domain.User;

public class ApplicationLauncher {
	private static InitGUI i;
	private static BLFacade appFacadeInterface;

	public static void launchMainGUI(BLFacade blf) {
		MainGUI a = new MainGUI();
		a.setVisible(true);
	}

	public static void closeInit(ActionEvent e) {
		i.close(e);
	}

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	public static void launchInit() {
		i.setVisible(true);
	}

	private static BLFactory blfact = new BLFactory();

	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();
		appFacadeInterface = blfact.crearBFFacade(c);

		char[] passwd = { '1', '1' };
		String passS = "";
		for (char ch : passwd) {
			passS += ch;
		}
		try {
			appFacadeInterface.createUser(new User("asier", "contreras", "11", new Date(2000, 5, 1), passS, false));
			appFacadeInterface
					.createUser(new User("Adminitrador", "Administrador", "00", new Date(2000, 5, 1), passS, true));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// a.pack();

		i = new InitGUI();
		i.setVisible(true);
	}
}
