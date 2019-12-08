package Client;

import javax.swing.*;

public class ThreadHelper extends JFrame {
    private JButton start=new JButton("启动一个客户端！");
    private JLabel label=new JLabel("启动助手");

    public ThreadHelper(){
        setBounds(100,100,300,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label.setBounds(115,40,120,30);
        add(label);

        start.setBounds(66,120,150,30);
        start.setFocusPainted(false);
        start.addActionListener(e-> new Thread(LoginFrame::new).start());
        add(start);

        setVisible(true);
    }
}
