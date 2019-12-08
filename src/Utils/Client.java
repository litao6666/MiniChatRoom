package Utils;

import Entity.UserInfo;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client {
    private UserInfo userInfo;

    private Socket socket;

    private BufferedWriter writer;

    public Client(Socket socket){
        this.socket=socket;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void sendMessageToServer(String message){
        try{
            if(writer==null){
                writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            }
            writer.write(getUserNameAndTime());
            writer.newLine();
            writer.write(message+"\n");
            writer.newLine();
            writer.write("eof\n");
            writer.newLine();
            writer.flush();
            System.out.println("Cliect[port:" + socket.getPort() + "] 消息"+message+"发送成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String receiveMessage(){
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            StringBuilder sb=new StringBuilder();
            String temp;
            while ((temp=br.readLine())!=null){
                if("".equals(temp)||"\n".equals(temp)){
                    continue;
                }
                int index;
                System.out.println("temp:"+temp);
                if((index=temp.indexOf("eof"))!=-1){
                    break;
                }
                sb.append(temp);
                sb.append('\n');
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUserNameAndTime(){
        String userName=userInfo.getUserName();
        SimpleDateFormat sf=new SimpleDateFormat("hh:mm:ss");
        String time=sf.format(new Date());
        return "用户:"+userName+"  时间:"+time+"  \n";
    }
}
