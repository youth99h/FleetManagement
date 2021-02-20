package com.fleetManagement.gui;

import com.fleetManagement.dao.CarCostDao;
import com.fleetManagement.dao.CarDao;
import com.fleetManagement.dao.DriverDao;
import com.fleetManagement.gui.extend.CalendarPanel;
import com.fleetManagement.gui.extend.ExtTable;
import com.fleetManagement.pojo.Car;
import com.fleetManagement.pojo.CarCost;
import com.fleetManagement.pojo.Driver;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

//�����ճ����ù������
 
public class CarCostManagePannel {
	
	public static int lastRow = 0;

    public static void placeComponents(JPanel panel, Predicate<? super CarCost> predicate) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));

        //List<CarCost> carCosts = CarCostDao.getInstance().get(predicate);
        //���ر������
        //DefaultTableModel tableModel = ExtTable.buildTableModel4Objects(carCosts, CarCost.class);
        
        String[] columns={"���ƺ�","ά��ʱ��","ά�޷��ã�Ԫ��","ά�޵�λ","ά������","ά�����","��ע"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> cost = null;
		try {
			cost = CarCostDao.get();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:cost)
        {
            String[] args=info.split(",");
            tableModel.addRow(args);
        }

        //���
        JPanel qP = new JPanel(new BorderLayout(10, 0));
        //��¼��˾����Ϣ
        JPanel aP = new JPanel(null);
        JPanel eP = new JPanel(null);
        JPanel pP = new JPanel(null);
        Map<String, Component> panelsMap = new HashMap<>();
        panelsMap.put("�����", qP);
        panelsMap.put("¼��", aP);
        panelsMap.put("��ӡ", pP);

        JTabbedPane jTabbedPane = ExtTable.buildJTabbedPane(panelsMap);
        panel.add(jTabbedPane, BorderLayout.CENTER);

        jTabbedPane.addChangeListener(e -> {
            if (jTabbedPane.getSelectedIndex() != 3 && jTabbedPane.getTabCount() > 3) {
                jTabbedPane.remove(3);
            }
        });


        JTable table = ExtTable.getDefaultTable(null);
        table.setModel(tableModel);
        qP.add(table.getTableHeader(), BorderLayout.NORTH);
        qP.add(new JScrollPane(table), BorderLayout.CENTER);

        //ɾ��������
        HashMap<JMenuItem, Function> mMap = new HashMap<>();
        JMenuItem dMitem = new JMenuItem("ɾ��ѡ����");
        JMenuItem qMitem = new JMenuItem("ɸѡ");
        JMenuItem rMitem = new JMenuItem("ˢ��");
        JMenuItem eMitem = new JMenuItem("�༭");

        mMap.put(dMitem, (nil) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(panel, "û��ѡ��Ҫɾ������!!!", "��������", JOptionPane.WARNING_MESSAGE);
                return nil;
            }
            //ɾ��ѡ�����ٳ־û�ʣ�µĳ���������Ϣ
            try {
    			CarCost cor = CarCostDao.getAll().get(selectedRow);
    			CarCostDao.delete(cor);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            //ˢ��ҳ��
            CarCostManagePannel.placeComponents(panel, null);
            return nil;
        });
        
        mMap.put(qMitem, (nil) -> {
            String str = JOptionPane.showInputDialog(panel, "����", "ɸѡ", JOptionPane.PLAIN_MESSAGE);
            if (str.isEmpty()) {
                return nil;
            }
            
            
            int count=table.getRowCount();
            if (count<=0) {
             return 0;
            }else {
             for (int i = count -1 ; i >= 0; i--) {
              tableModel.removeRow(table.getRowCount() - 1);
             }
            }
            
            List<String> dri2 = null;
    		try {
    			dri2 = CarCostDao.get();
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            for(String info:dri2)
            {
            	if(info.contains(str)) {
            		String[] args2=info.split(",");
            		tableModel.addRow(args2);
            	}        
            }
            return nil;
        });
        mMap.put(rMitem, (nil) -> {
            CarCostManagePannel.placeComponents(panel, null);
            return nil;
        });
        //�༭
        mMap.put(eMitem, (nil) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(panel, "û�б༭����!!!", "��������", JOptionPane.WARNING_MESSAGE);
                return nil;
            }
            try {
				buildForm(panel, eP, CarCostDao.getAll(), selectedRow);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            jTabbedPane.add("�༭", eP);
            jTabbedPane.setSelectedIndex(3);
            return nil;
        });


        MouseInputListener mouseInputListener = ExtTable.buildMouseInputMenuListener(mMap);
        table.addMouseListener(mouseInputListener);
//      table.addMouseMotionListener(mouseInputListener);

        //¼���
        buildForm(panel, aP, null, -1);
        //��ӡ
        PrintPanel.print(cost, panel, pP);
        panel.updateUI();
        panel.repaint();

    }


    private static void buildForm(JPanel mainPanel, JPanel panel, List<CarCost> carCosts, int rows) {
        int lw = 200, tw = 200, lh = 25, th = 25, x = 100, y = 20, tx = lw + x;

        JLabel lTitle = new JLabel("��������¼��", JLabel.RIGHT);
        lTitle.setBounds(x, y, lw, lh);
        panel.add(lTitle);

        y += 30;
        JLabel lNo = new JLabel("���ţ�", JLabel.RIGHT);
        lNo.setBounds(x, y, lw, lh);
        JComboBox tNo = new JComboBox<>();
        try {
			tNo.setModel(new DefaultComboBoxModel(CarDao.getCarNos()));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        tNo.setBounds(tx, y, tw, th);
        panel.add(lNo);
        panel.add(tNo);

        y += 30;
        JLabel lFixTime = new JLabel("ά��ʱ�䣺", JLabel.RIGHT);
        lFixTime.setBounds(x, y, lw, lh);
        JTextField tFixTime = new JTextField(20);
        tFixTime.setBounds(tx, y, tw, th);
        CalendarPanel p = new CalendarPanel(tFixTime, "yyyy-MM-dd");
        p.initCalendarPanel();
        panel.add(p);
        panel.add(lFixTime);
        panel.add(tFixTime);

        y += 30;
        JLabel lFixCost = new JLabel("ά�޷��ã�Ԫ����", JLabel.RIGHT);
        lFixCost.setBounds(x, y, lw, lh);
        JTextField tFixCost = new JTextField(20);
        tFixCost.setBounds(tx, y, tw, th);
        panel.add(lFixCost);
        panel.add(tFixCost);

        y += 30;
        JLabel lFixCompany = new JLabel("ά�޵�λ��", JLabel.RIGHT);
        lFixCompany.setBounds(x, y, lw, lh);
        JTextField tFixCompany = new JTextField(20);
        tFixCompany.setBounds(tx, y, tw, th);
        panel.add(lFixCompany);
        panel.add(tFixCompany);

        y += 30;
        JLabel lFixContext = new JLabel("ά�����ݣ�", JLabel.RIGHT);
        lFixContext.setBounds(x, y, lw, lh);
        JTextField tFixContext = new JTextField(20);
        tFixContext.setBounds(tx, y, tw, th);
        panel.add(lFixContext);
        panel.add(tFixContext);

        y += 30;
        JLabel lFixParts = new JLabel("ά�������", JLabel.RIGHT);
        lFixParts.setBounds(x, y, lw, lh);
        JTextField tFixParts = new JTextField(20);
        tFixParts.setBounds(tx, y, tw, th);
        panel.add(lFixParts);
        panel.add(tFixParts);

        y += 30;
        JLabel lMemo = new JLabel("��ע��", JLabel.RIGHT);
        lMemo.setBounds(x, y, lw, lh);
        JTextField tMemo = new JTextField(20);
        tMemo.setBounds(tx, y, tw, th);
        panel.add(lMemo);
        panel.add(tMemo);

        y += 50;
        JButton submitBtn = new JButton("ȷ��");
        submitBtn.setBounds(x + 250, y, 100, th);
        panel.add(submitBtn);

        if (Objects.nonNull(carCosts) && carCosts.size() > 0 && rows >= 0) {
            CarCost temp = carCosts.get(rows);
            tNo.setSelectedItem(temp.getNo());
            tFixCompany.setText(temp.getFixCompany());
            tFixContext.setText(temp.getFixContext());
            tFixCost.setText(temp.getFixCost()+"");
            tFixParts.setText(temp.getFixParts());
            tMemo.setText(temp.getMemo());
            tFixTime.setText(temp.getFixTime());
        }

        submitBtn.addActionListener(e -> {
            if (tFixCompany.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ά�޷���Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tFixCompany.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ά�޵�λΪ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tFixContext.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ά������Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tFixTime.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ά��ʱ��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CarCost car = new CarCost((String)tNo.getSelectedItem(), Double.parseDouble(tFixCost.getText()), tFixCompany.getText(), tFixContext.getText(),tFixTime.getText(), tFixParts.getText(), tMemo.getText());
            // �¼�
            if (rows < 0) {
                try {
					CarCostDao.save(car);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else {
            	CarCost info = carCosts.get(rows);
            	
                try {
					CarCostDao.remove(car, info);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                
            }
            //ˢ��
            CarCostManagePannel.placeComponents(mainPanel, null);

        });

    }
}
