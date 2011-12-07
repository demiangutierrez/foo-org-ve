package chat.client;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Clase con el main de un cliente del chat. Establece la conexion y crea la
 * ventana y la clase de control.
 */

public class ClientChat {
	/* Socket con el servidor del chat */
	private Socket socket;

	/* Panel con la ventana del cliente */
	private ClientPanel clientPnl;

	/* Arranca el Cliente de chat */

	public static void main(String[] args) {
		new ClientChat();
	}

	/* Crea la ventana, establece la conexion e instancia al controlador */

	public ClientChat() {
		try {
			createWindows();
			socket = new Socket("localhost", 5557);
			ClientControl control = new ClientControl(socket, clientPnl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Crea una ventana, le asigna dentro el panel para el cliente y la
	 * visualiza
	 */

	private void createWindows() {
		JFrame frame = new JFrame();
		clientPnl = new ClientPanel(frame.getContentPane());
		frame.setSize(670, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
