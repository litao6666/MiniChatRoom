import Server.TaskThread;
import Server.ThreadsHolder;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) throws Exception{
        int port=4321;
        ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("服务器等待连接...");
        while(true){
            Socket socket=serverSocket.accept();
            System.out.println("Accept a socket,port="+socket.getPort());
            TaskThread tt=new TaskThread(socket);
            ThreadsHolder.threadList.add(tt);
            Thread thread=new Thread(tt);
            thread.start();
        }
    }
}
