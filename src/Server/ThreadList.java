package Server;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadList {
    private List<TaskThread> list=new ArrayList<>();

    public void add(TaskThread thread){
        list.add(thread);
    }

    public void broadcastMessage(String message) throws Exception{
        for (TaskThread taskThread:list){
            Socket socket=taskThread.getSocket();
            Writer writer=new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
            writer.write(message);
            writer.write("eof\n");
            writer.flush();
            System.out.println("广播消息:"+message+"至Client[port:"+socket.getPort()+"]");
            //socket.close();
        }
    }

    public boolean remove(TaskThread thread){
        return list.remove(thread);
    }

    public void print(){
        System.out.println(list);
    }
}
