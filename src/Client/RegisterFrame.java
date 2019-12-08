package Client;

import Entity.UserInfo;
import Service.UserInfoService;
import com.mysql.cj.util.StringUtils;

import javax.swing.*;

public class RegisterFrame extends JFrame{

    private JLabel title=new JLabel("用户注册");
    private JTextField userNameText=new JTextField(16);
    private JPasswordField passwordText=new JPasswordField(16);
    private JPasswordField passwordAckText=new JPasswordField(16);
    private JLabel userNameLabel=new JLabel("用户名:");
    private JLabel passwordLabel=new JLabel("密    码:");
    private JLabel passwordAckLabel=new JLabel("确认密码");
    private JButton register=new JButton("注册");
    private JButton ret=new JButton("返回");

    private JFrame current=this;
    private UserInfoService userInfoService=new UserInfoService();

    public RegisterFrame(){
        //整体界面设置
        setBounds(450,150,450,340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //标题设置
        title.setBounds(195,20,130,40);
        add(title);

        //输入标签设置
        userNameLabel.setBounds(80,80,60,30);
        add(userNameLabel);
        passwordLabel.setBounds(80,120,60,30);
        add(passwordLabel);
        passwordAckLabel.setBounds(80,160,60,30);
        add(passwordAckLabel);

        //输入框设置
        userNameText.setBounds(150,85,150,25);
        add(userNameText);
        passwordText.setBounds(150,125,150,25);
        add(passwordText);
        passwordAckText.setBounds(150,165,150,25);
        add(passwordAckText);

        //按钮组设置
        setButtonListeners();
        register.setBounds(120,230,80,25);
        add(register);
        ret.setBounds(250,230,80,25);
        add(ret);

        setVisible(true);
    }

    private void setButtonListeners(){
        //注册按钮响应事件
        register.addActionListener(e -> {
            String userName=userNameText.getText();
            String password=new String(passwordText.getPassword());
            String ackPassword=new String(passwordAckText.getPassword());
            if(StringUtils.isNullOrEmpty(userName)||StringUtils.isNullOrEmpty(password)||
                    StringUtils.isNullOrEmpty(ackPassword)){
                JOptionPane.showMessageDialog(current,"必填项不能为空！");
            }else if(!password.equals(ackPassword)){
                JOptionPane.showMessageDialog(current,"两次输入密码不一致！");
            }else{
                UserInfo newUser=userInfoService.register(userName,password);
                JOptionPane.showMessageDialog(current,"注册成功！");
                new LoginFrame();
                dispose();
            }
        });

        //返回按钮响应事件
        ret.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
    }
}
