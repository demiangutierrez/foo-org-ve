package chat.server;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;

/**
 * Servidor de chat.
 * Acepta conexiones de clientes, crea un hilo para atenderlos, y espera la
 * siguiente conexion.
 * @author Chuidiang
 *
 */
public class ServerChat
{
    /** Lista en la que se guaradara toda la conversacion */
    private DefaultListModel charla = new DefaultListModel();

    /**
     * Instancia esta clase.
     * @param args
     */
    public static void main(String[] args)
    {
        new ServerChat();
    }

    /**
     * Se mete en un bucle infinito para ateder clientes, lanzando un hilo
     * para cada uno de ellos.
     */
    public ServerChat()
    {
        try
        {
            ServerSocket socketServidor = new ServerSocket(5557);
            while (true)
            {
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new ClientThread(charla, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
