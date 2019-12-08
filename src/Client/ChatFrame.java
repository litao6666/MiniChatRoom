package Client;

import Entity.UserInfo;
import Service.UserInfoService;
import Utils.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatFrame extends JFrame {

    private JLabel title=new JLabel("微型聊天室");
    private JLabel userStatus=new JLabel();
    private JTextArea messageArea=new JTextArea();
    private JTextArea editArea=new JTextArea();
    private JTextArea usersArea=new JTextArea();
    private JButton send=new JButton("发送");

    private UserInfoService userInfoService=new UserInfoService();
    private Client client=null;
    private UserInfo userInfo=null;
    private ChatFrame current=this;

    public ChatFrame(){
        //整体界面设置
        setBounds(450,150,600,400);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                userInfo.setStatus("0");
                userInfoService.updateUserStatus(userInfo);
                FramesHolder.remove(current);
            }
        });
        addButtonListener();

        //标题设置
        title.setBounds(245,5,160,30);
        add(title);

        //用户状态文本设置
        userStatus.setBounds(25,45,200,30);
        add(userStatus);

        //发送按钮设置
        send.setBounds(320,280,80,25);
        add(send);

        //文本域设置
        messageArea.setBounds(25,85,375,150);
        //messageArea.setText("aaaaaa");
        messageArea.setEditable(false);
        //消息域设置滚动条
        JScrollPane scrollPane1=new JScrollPane(messageArea);
        scrollPane1.setBounds(25,85,375,150);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setViewportView(messageArea);
        add(scrollPane1);

        editArea.setBounds(25,245,375,60);
        JScrollPane scrollPane2=new JScrollPane(editArea);
        scrollPane2.setBounds(25,245,375,60);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setViewportView(editArea);
        add(scrollPane2);

        usersArea.setBounds(415,85,150,220);
        usersArea.setEditable(false);
        JScrollPane scrollPane3=new JScrollPane(usersArea);
        scrollPane3.setBounds(415,85,150,220);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setViewportView(usersArea);
        add(scrollPane3);

        setVisible(true);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void addButtonListener(){
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sendText=editArea.getText();
                client.sendMessageToServer(sendText);
                editArea.setText("");
            }
        });
    }

    public void setClient(Client client){
        this.client=client;
        userInfo=client.getUserInfo();
        String myName=userInfo.getUserName();
        String status="1".equals(userInfo.getStatus())?"在线":"离线";
        userStatus.setText("用户: "+myName+"  "+status);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String rm;
                while ((rm=client.receiveMessage())!=null){
                    messageArea.setText(messageArea.getText()+"\n"+rm);
                }
            }
        }).start();
    }

    public void setUsersList(String s){
        usersArea.setText(s);
    }

}
