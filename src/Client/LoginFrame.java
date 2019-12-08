package Client;

import Entity.UserInfo;
import Service.UserInfoService;
import Utils.Client;
import com.mysql.cj.util.StringUtils;

import javax.swing.*;
import java.net.Socket;

public class LoginFrame extends JFrame {

    private JButton okButton=new JButton("登录");
    private JButton cancelButton=new JButton("取消");
    private JButton registerButton=new JButton("注册");
    private JLabel title=new JLabel("聊天室登入");
    private JLabel userNameLabel=new JLabel("用户名:");
    private JLabel passwordLabel=new JLabel("密    码:");
    private JTextField userNameText=new JTextField(16);
    private JPasswordField passwordText=new JPasswordField(16);

    private JFrame current=this;
    private UserInfoService userInfoService=new UserInfoService();

    public LoginFrame(){
        //整体界面设置
        setBounds(450,150,450,300);
        setLayout(null);

        //标题设置
        title.setBounds(170,20,130,40);
        add(title);

        //输入标签设置
        userNameLabel.setBounds(80,80,60,30);
        add(userNameLabel);
        passwordLabel.setBounds(80,120,60,30);
        add(passwordLabel);
        //输入框设置
        userNameText.setBounds(150,85,150,25);
        add(userNameText);
        passwordText.setBounds(150,125,150,25);
        add(passwordText);
        //按钮组设置
        registerButton.setBounds(80,190,80,25);
        okButton.setBounds(170,190,80,25);
        cancelButton.setBounds(260,190,80,25);
        setButtonListeners();
        add(registerButton);
        add(okButton);
        add(cancelButton);

        setVisible(true);
    }

    private void setButtonListeners(){
        //注册按钮响应事件
        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        //ok按钮响应事件
        okButton.addActionListener(e -> {
            String userName=userNameText.getText();
            String password=new String(passwordText.getPassword());
            if(StringUtils.isNullOrEmpty(userName)||StringUtils.isNullOrEmpty(password)){
                JOptionPane.showMessageDialog(current,"用户名或密码不能为空！");
            }
            UserInfo userInfo=userInfoService.login(userName,password);
            if(userInfo==null){
                JOptionPane.showMessageDialog(current,"用户名或密码不正确!");
            }else if(userInfo.getStatus().equals("1")){
                JOptionPane.showMessageDialog(current, "该用户已经登录！");
            } else{

                userInfo.setStatus("1");
                userInfoService.updateUserStatus(userInfo);

                ChatFrame cf=new ChatFrame();
                try{
                    Socket socket=new Socket("127.0.0.1",4321);
                    Client client=new Client(socket);
                    client.setUserInfo(userInfo);
                    cf.setClient(client);
                    FramesHolder.addChatFrame(cf);
                }catch (Exception exception){
                    exception.printStackTrace();
                }

                dispose();
            }
        });

        //cancel按钮响应事件
        cancelButton.addActionListener(e -> dispose());
    }
}
