package com.fleetManagement.gui;

import com.fleetManagement.dao.DriverLoginDao;
import com.fleetManagement.dao.UserDao;
import com.fleetManagement.pojo.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//��¼���
 
public class LoginPannel {
	public static String[] users = new String[2];

    public static void placeComponents(JPanel panel) {

        MainFrame.setT("��¼");

        /*
         * ���ò���null
         */
        panel.setLayout(null);

        int labelX = 80;
        int inputX = 165;
        int marginlabelX = 50;
        int x = (Config.getWidth() - labelX - inputX) / 2;
        int y = Config.getHeight() / 4;

        // ���� JLabel
        JLabel userLabel = new JLabel("�˻�:");
        /* ������������������λ�á�
         * setBounds(x, y, width, height)
         * x �� y ָ�����Ͻǵ���λ�ã��� width �� height ָ���µĴ�С��
         */
        userLabel.setBounds(x, y, labelX, 25);
        panel.add(userLabel);

        /*
         * �����ı��������û�����
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(x + marginlabelX, y, inputX, 25);
        panel.add(userText);
       

        // ����������ı���
        JLabel passwordLabel = new JLabel("����:");
        passwordLabel.setBounds(x, y + 50, labelX, 25);
        panel.add(passwordLabel);

        /*
         *�����������������ı���
         * �����������Ϣ���Ե�Ŵ��棬���ڰ�������İ�ȫ��
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(x + marginlabelX, y + 50, inputX, 25);
        panel.add(passwordText);

        // ������¼��ť
        JButton loginButton = new JButton("��¼");
        loginButton.setBounds(x + 80, y + 100, labelX, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					if (passwordText.getText().equals(UserDao.queryUserByName(userText.getText()))) {
						users[1] = userText.getText();
						MainPannel.placeComponents(panel);
					} else if(passwordText.getText().equals("123456") && userText.getText().equals("root")) {
						RootPanel.placeComponents(panel);
					} else if(passwordText.getText().equals(DriverLoginDao.queryUserByName(userText.getText()))) {
						users[0] = userText.getText();
						DriverLoginPanel.placeComponents(panel);
					} else {
					    JOptionPane.showMessageDialog(panel, "�˻��������������!!!", "��¼�쳣", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        
        
    }

	public static String[] getUsers() {
		return users;
	}

	public static void setUsers(String[] users) {
		LoginPannel.users = users;
	}
    
    

}