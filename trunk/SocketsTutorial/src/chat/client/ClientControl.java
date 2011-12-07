package chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Clase que atiende el socket y las peticiones de usuario. Lo que llega por el
 * socket lo muestra en el textArea del panel, lo que escribe el usuario en el
 * panel lo envia por el socket.
 */

public class ClientControl implements ActionListener, Runnable {
	/* Para lectura de datos del socket */
	private DataInputStream dataInput;

	/* Para escritura de datos en el socket */
	private DataOutputStream dataOutput;

	/* Panel con los controles para el usuario */
	private ClientPanel panel;

	/**
	 * Contruye una instancia de esta clase, lanzando un hilo para atender al
	 * socket.
	 */
	public ClientControl(Socket socket, ClientPanel panel) {
		this.panel = panel;
		try {
			dataInput = new DataInputStream(socket.getInputStream());
			dataOutput = new DataOutputStream(socket.getOutputStream());
			panel.addActionListener(this);
			Thread thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recoge el texto del panel y lo envia por el socket. El panel llamara a
	 * este metodo cuando el usuario escriba algo y pulse el boton de "enviar"
	 * sobre el textfield.
	 */

	public void actionPerformed(ActionEvent evento) {
		try {
			dataOutput.writeUTF(panel.getText());
		} catch (Exception excepcion) {
			excepcion.printStackTrace();
		}
	}

	/**
	 * Metodo run para antender al socket. Todo lo que llega por el socket se
	 * escribe en el panel.
	 */
	public void run() {
		try {
			while (true) {
				String texto = dataInput.readUTF();
				panel.addText(texto);
				panel.addText("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
