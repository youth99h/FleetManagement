package com.fleetManagement.gui;

import javax.swing.*;

import com.fleetManagement.gui.extend.VFlowLayout;

import java.awt.*;

//�����

public class DriverLoginPanel {

    public static void placeComponents(JPanel panel) {
        panel.removeAll();

        MainFrame.setT("��ҳ");

        panel.setLayout(new BorderLayout(10,5));

        //�����ϱ��У����
        JPanel nav = new JPanel(new VFlowLayout());
        nav.setBackground(Color.white);
        JPanel center = new JPanel(null);
        JPanel jp3 = new JPanel(new GridLayout(10,2));
        JPanel jp4 = new JPanel(new FlowLayout());
        JPanel jp5 = new JPanel(new FlowLayout());

        //���������
        panel.add(BorderLayout.NORTH,jp3);
        panel.add(BorderLayout.SOUTH,jp3);
        panel.add(BorderLayout.CENTER,center);
        panel.add(BorderLayout.EAST,jp3);
        panel.add(BorderLayout.WEST,nav);


        //��ߵ���
        JButton DITtn = new JButton("������Ϣ");
        DITtn.addActionListener(e -> {
            MainFrame.setT("������Ϣ");
            DriverInfoManagePanel.placeComponents(center, null);
        });

        
        JButton dRTtn = new JButton("�鿴����");
        dRTtn.addActionListener(e -> {
            MainFrame.setT("�鿴����");
            DriverTaskPanel.placeComponents(center, null);
        });

        JButton exit = new JButton("�˳���¼");
        exit.addActionListener(e -> {
            panel.removeAll();
            LoginPannel.placeComponents(panel);
            panel.repaint();
        });
        
        nav.add(DITtn);
        nav.add(dRTtn);
        nav.add(exit);
        panel.updateUI();

        panel.repaint();
    }
}

