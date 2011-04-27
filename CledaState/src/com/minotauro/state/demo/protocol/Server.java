package com.minotauro.state.demo.protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) throws IOException {

    int playerId = 0;

//    GlobalThread globalThread = new GlobalThread();
    GlobalImpl global = new GlobalImpl();
    //    globalThread.doBeg();

    ServerSocket serverSocket = null;
    Socket/*   */clientSocket = null;

    try {
      serverSocket = new ServerSocket(8080);

      while (true) {
        clientSocket = serverSocket.accept();
        System.out.println("Accepted Connection");

        playerId++;

        if (playerId > 99) {
          throw new IllegalStateException( //
              "id > 99: Sorry folks, only up to 99 right now");
        }

        new ClientThread(global, clientSocket, playerId);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // TODO: Clean up where???
    //    pw.close();
    //    br.close();
    //    clientSocket.close();

    serverSocket.close();
  }
}
