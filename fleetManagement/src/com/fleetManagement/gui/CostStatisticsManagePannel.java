package com.fleetManagement.gui;

import com.fleetManagement.dao.DriverDao;
import com.fleetManagement.dao.StatisticsDao;
import com.fleetManagement.gui.extend.ExtTable;
import com.fleetManagement.pojo.CarCost;
import com.fleetManagement.pojo.CarCostStatistics;
import com.fleetManagement.pojo.OilCard;
import com.fleetManagement.pojo.OilCardStatistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

//����ͳ�����

public class CostStatisticsManagePannel {


    public static void placeComponents(JPanel panel, Predicate<? super OilCard> oilCardPredicate , Predicate<? super CarCost> carCostPredicate ) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));

        String[] columns={"�Ϳ����","���","��ע"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> dri = null;
		try {
			dri = StatisticsDao.getOil();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:dri)
        {
            String[] args=info.split(",");
            tableModel.addRow(args);
        }
        
        String[] columns1={"���ƺ�","ά���ܷ��ã�Ԫ��","��ע"};
        DefaultTableModel tableModel1 = new DefaultTableModel(columns1, 0);
        List<String> dri1 = null;
		try {
			dri1 = StatisticsDao.getCar();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:dri1)
        {
            String[] args=info.split(",");
            tableModel1.addRow(args);
        }

        //���
        JPanel oP = new JPanel(new BorderLayout(10, 0));
        JPanel cP = new JPanel(new BorderLayout(10, 0));
        JPanel oPP = new JPanel(null);
        JPanel cPP = new JPanel(null);

        Map<String, Component> panelsMap = new HashMap<>();
        panelsMap.put("�Ϳ���֧ͳ��", oP);
        panelsMap.put("����ά�޷���ͳ��", cP);

        panelsMap.put("��ӡ�Ϳ���֧", oPP);
        panelsMap.put("��ӡ����ά�޷���", cPP);

        JTabbedPane jTabbedPane = ExtTable.buildJTabbedPane(panelsMap);
        panel.add(jTabbedPane, BorderLayout.CENTER);

        panel.updateUI();
        panel.repaint();


        JTable oilTable = ExtTable.getDefaultTable(null);
        oilTable.setModel(tableModel);
        oP.add(oilTable.getTableHeader(), BorderLayout.NORTH);
        oP.add(new JScrollPane(oilTable), BorderLayout.CENTER);

        JTable carTable = ExtTable.getDefaultTable(null);
        carTable.setModel(tableModel1);
        cP.add(carTable.getTableHeader(), BorderLayout.NORTH);
        cP.add(new JScrollPane(carTable), BorderLayout.CENTER);

      
        PrintPanel.print(dri, panel, oPP);
        PrintPanel.print(dri1, panel, cPP);
    }
}
