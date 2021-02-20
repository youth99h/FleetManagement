package com.fleetManagement.gui;

import javax.swing.*;

import com.fleetManagement.util.PathUtils;
import com.fleetManagement.util.ScreenUtils;

//����������

public class MainFrame extends JFrame {

    static MainFrame self;

    public MainFrame(int w, int h) {
        this.setSize(w, h);

        this.setTitle("������˾��������ϵͳ");

        self = this;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(ScreenUtils.getMarginWidth(w), ScreenUtils.getMarginHeight(h), w, h);

        this.setIconImage(new ImageIcon(PathUtils.getIconImgPath()).getImage());

        //�������
        JPanel panel = new JPanel();
        // ������
        this.add(panel);

        LoginPannel.placeComponents(panel);

    }

    public void doShow() {
        // ���ý���ɼ���
        this.setVisible(true);
    }

    public static void setT(String t) {
        self.setTitle(String.format("%s-%s", self.getTitle().split("-")[0], t));
    }
}
