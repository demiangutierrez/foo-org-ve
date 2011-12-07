package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Hilo encargado de atender a un cliente.
 */

public class ClientThread implements Runnable, ListDataListener {
	/* Lista en la que se guarda toda la charla */
	private DefaultListModel talks;

	/* Socket al que esta conectado el cliente */
	private Socket socket;

	/* Para lectura de datos en el socket */
	private DataInputStream dataInput;

	/* Para escritura de datos en el socket */
	private DataOutputStream dataOutput;

	/**
	 * Crea una instancia de esta clase y se suscribe a cambios en la charla.
	 */
	public ClientThread(DefaultListModel charla, Socket socket) {
		this.talks = charla;
		this.socket = socket;
		try {
			dataInput = new DataInputStream(socket.getInputStream());
			dataOutput = new DataOutputStream(socket.getOutputStream());
			charla.addListDataListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Atiende el socket. Todo lo que llega lo agrega en la charla.
	 */
	public void run() {
		try {
			while (true) {
				String texto = dataInput.readUTF();
				synchronized (talks) {
					talks.addElement(texto);
					System.out.println(texto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envia el ultimo texto de la charla por el socket. Se avisa a este metodo
	 * cada vez que se agrega algo en la charla, incluido cuando lo agrega este mismo
	 * hilo. De esta manera, lo que un cliente escriba, se le reenviara para que
	 * se muestre en el textArea.
	 */
	
	public void intervalAdded(ListDataEvent e) {
		String texto = (String) talks.getElementAt(e.getIndex0());
		try {
			dataOutput.writeUTF(texto);
		} catch (Exception excepcion) {
			excepcion.printStackTrace();
		}
	}

	public void intervalRemoved(ListDataEvent e) {
	}

	public void contentsChanged(ListDataEvent e) {
	}
}
