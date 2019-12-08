package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TaskThread implements Runnable{

    private Socket socket;

    public TaskThread(Socket socket){
        this.socket=socket;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run(){
        System.out.println("port:"+socket.getPort()+" in run");
        BufferedReader br=null;
        try{
            br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            String temp;
            StringBuilder sb=new StringBuilder();
            while((temp=br.readLine())!=null){
                int index;
                if((index=temp.indexOf("eof"))!=-1){
                    sb.append(temp.substring(0,index));
                    sb.append('\n');
                    String receivedMessage=sb.toString();
                    System.out.println("Message From Client[port:" + socket.getPort()
                            + "] 消息内容:" + receivedMessage);
                    ThreadsHolder.threadList.broadcastMessage(receivedMessage);
                    sb.delete(0,sb.length());
                }else{
                    System.out.println("temp:"+temp);
                    sb.append(temp);
                    sb.append('\n');
                }
                temp=br.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "socket port:"+socket.getPort();
    }
}
