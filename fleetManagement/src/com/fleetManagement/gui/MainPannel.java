package com.fleetManagement.gui;

import javax.swing.*;

import com.fleetManagement.gui.extend.VFlowLayout;

import java.awt.*;

//�����

public class MainPannel {

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
        JButton GIBtn = new JButton("������Ϣ����");
        GIBtn.addActionListener(e -> {
            MainFrame.setT("������Ϣ����");
            UserInfoManagePanel.placeComponents(center, null);
        });
        
        JButton dMBtn = new JButton("˾����������");
        dMBtn.addActionListener(e -> {
            MainFrame.setT("˾����������");
            DriverManagePannel.placeComponents(center, null);
        });

        JButton cMBtn = new JButton("������������");
        cMBtn.addActionListener(e -> {
            MainFrame.setT("������������");
            CarManagePannel.placeComponents(center, null);
        });

        //car cost
        JButton cCBtn = new JButton("�����ճ����ù���");
        cCBtn.addActionListener(e -> {
            MainFrame.setT("�����ճ����ù���");
            CarCostManagePannel.placeComponents(center, null);
        });

        //Oil card
        JButton ocBtn = new JButton("�Ϳ�����");
        ocBtn.addActionListener(e -> {
            MainFrame.setT("�Ϳ�����");
            OilCardManagePannel.placeComponents(center, null);
        });

        //Dispatch van
        JButton dvBtn = new JButton("�����ɣ�������");
        dvBtn.addActionListener(e -> {
            MainFrame.setT("�����ɣ�������");
            DispatchVanManagePannel.placeComponents(center, null);
        });

        //Cost statistics
        JButton csBtn = new JButton("����ͳ��");
        csBtn.addActionListener(e -> {
            MainFrame.setT("����ͳ��");
            CostStatisticsManagePannel.placeComponents(center, null, null);
        });
        
        JButton dbBtn = new JButton("����˾���˻�");
        dbBtn.addActionListener(e -> {
            MainFrame.setT("����˾���˻�");
            DriverLoginManagePanel.placeComponents(center, null);
        });

        JButton exit = new JButton("�˳���¼");
        exit.addActionListener(e -> {
            panel.removeAll();
            LoginPannel.placeComponents(panel);
            panel.repaint();
        });
        
        nav.add(GIBtn);
        nav.add(dMBtn);
        nav.add(cMBtn);
        nav.add(cCBtn);
        nav.add(ocBtn);
        nav.add(dvBtn);
        nav.add(csBtn);
        nav.add(dbBtn);
        nav.add(exit);
        panel.updateUI();

        panel.repaint();
    }
}
