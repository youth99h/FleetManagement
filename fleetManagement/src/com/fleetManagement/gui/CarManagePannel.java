package com.fleetManagement.gui;

import com.fleetManagement.dao.CarDao;
import com.fleetManagement.dao.DriverDao;
import com.fleetManagement.gui.extend.CalendarPanel;
import com.fleetManagement.gui.extend.ExtTable;
import com.fleetManagement.pojo.Car;
import com.fleetManagement.pojo.Driver;
import com.fleetManagement.util.TimeUtils;

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

//���������������
 
public class CarManagePannel {
	public static int lastRow = 0;

    /**
     * @param panel
     * @param predicate ɸѡ����
     */
    public static void placeComponents(JPanel panel, Predicate<? super Car> predicate) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));
        
        String[] columns={"����","�Ǽ�ʱ��","��֤ʱ��","����","����","��������","���ݺ�","����Ʒ��","�˶��ؿ���","��ɫ"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> c = null;
		try {
			c = CarDao.get();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:c)
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
            if (jTabbedPane.getSelectedIndex() != 3 && jTabbedPane.getTabCount() >3) {
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
            //ɾ��ѡ�����ٳ־û�ʣ�µ�˾����Ϣ
            try {
    			Car cr = CarDao.getAll().get(selectedRow);
    			CarDao.delete(cr);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            //ˢ��ҳ��
            CarManagePannel.placeComponents(panel, null);
            return nil;
        });
        mMap.put(qMitem, (nil) -> {
            String str = JOptionPane.showInputDialog(panel, "����", "ɸѡ", JOptionPane.PLAIN_MESSAGE);
            if (str.isEmpty()) {
                return nil;
            }
            //������������ɸѡ
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
    			dri2 = CarDao.get();
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
            CarManagePannel.placeComponents(panel, null);
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
				buildForm(panel, eP, CarDao.getAll(), selectedRow);
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

        //��
        buildForm(panel, aP, null, -1);

        //��ӡ
        PrintPanel.print(c, panel, pP);

        panel.updateUI();
        panel.repaint();

    }


    private static void buildForm(JPanel mainPanel, JPanel panel, List<Car> cars, int rows) {
        int lw = 200, tw = 200, lh = 25, th = 25, x = 100, y = 20, tx = lw + x;

        JLabel lTitle = new JLabel("��������¼��", JLabel.RIGHT);
        lTitle.setBounds(x, y, lw, lh);
        panel.add(lTitle);

        y += 30;
        JLabel lNo = new JLabel("���ţ�", JLabel.RIGHT);
        lNo.setBounds(x, y, lw, lh);
        JTextField tNo = new JTextField(20);
        tNo.setBounds(tx, y, tw, th);
        panel.add(lNo);
        panel.add(tNo);

        y += 30;
        JLabel lCheckTime = new JLabel("�Ǽ�ʱ�䣺", JLabel.RIGHT);
        lCheckTime.setBounds(x, y, lw, lh);
        JTextField tCheckTime = new JTextField(20);
        tCheckTime.setBounds(tx, y, tw, th);
        CalendarPanel p = new CalendarPanel(tCheckTime, "yyyy-MM-dd");
        p.initCalendarPanel();
        panel.add(p);
        panel.add(lCheckTime);
        panel.add(tCheckTime);

        y += 30;
        JLabel lSuccessTime = new JLabel("��֤ʱ�䣺", JLabel.RIGHT);
        lSuccessTime.setBounds(x, y, lw, lh);
        JTextField tSuccessTime = new JTextField(20);
        tSuccessTime.setBounds(tx, y, tw, th);
        CalendarPanel p1 = new CalendarPanel(tSuccessTime, "yyyy-MM-dd");
        p1.initCalendarPanel();
        panel.add(p1);
        panel.add(lSuccessTime);
        panel.add(tSuccessTime);


        y += 30;
        JLabel lType = new JLabel("���ͣ�", JLabel.RIGHT);
        lType.setBounds(x, y, lw, lh);
        JTextField tType = new JTextField(20);
        tType.setBounds(tx, y, tw, th);
        panel.add(lType);
        panel.add(tType);

        y += 30;
        JLabel lOwnerse = new JLabel("������", JLabel.RIGHT);
        lOwnerse.setBounds(x, y, lw, lh);
        JTextField tOwners = new JTextField(20);
        tOwners.setBounds(tx, y, tw, th);
        panel.add(lOwnerse);
        panel.add(tOwners);

        y += 30;
        JLabel lEngineNo = new JLabel("�������ţ�", JLabel.RIGHT);
        lEngineNo.setBounds(x, y, lw, lh);
        JTextField tEngineNo = new JTextField(20);
        tEngineNo.setBounds(tx, y, tw, th);
        panel.add(lEngineNo);
        panel.add(tEngineNo);

        y += 30;
        JLabel lDrivingNo = new JLabel("���ݺţ�", JLabel.RIGHT);
        lDrivingNo.setBounds(x, y, lw, lh);
        JTextField tDrivingNo = new JTextField(20);
        tDrivingNo.setBounds(tx, y, tw, th);
        panel.add(lDrivingNo);
        panel.add(tDrivingNo);

        y += 30;
        JLabel lBand = new JLabel("����Ʒ�ƣ�", JLabel.RIGHT);
        lBand.setBounds(x, y, lw, lh);
        JTextField tBand = new JTextField(20);
        tBand.setBounds(tx, y, tw, th);
        panel.add(lBand);
        panel.add(tBand);

        y += 30;
        JLabel lCarryLimit = new JLabel("�˶��ؿ�����", JLabel.RIGHT);
        lCarryLimit.setBounds(x, y, lw, lh);
        JTextField tCarryLimit = new JTextField(20);
        tCarryLimit.setBounds(tx, y, tw, th);
        panel.add(lCarryLimit);
        panel.add(tCarryLimit);

        y += 30;
        JLabel lColor = new JLabel("��ɫ��", JLabel.RIGHT);
        lColor.setBounds(x, y, lw, lh);
        JTextField tColor = new JTextField(20);
        tColor.setBounds(tx, y, tw, th);
        panel.add(lColor);
        panel.add(tColor);


        y += 50;
        JButton submitBtn = new JButton("ȷ��");
        submitBtn.setBounds(x + 250, y, 100, th);
        panel.add(submitBtn);

        if (Objects.nonNull(cars) && cars.size() > 0 && rows >= 0) {
            Car temp = cars.get(rows);
            tNo.setText(temp.getNo());
            tCheckTime.setText(temp.getCheckTime().toString());
            tSuccessTime.setText(temp.getSuccessTime().toString());
            tOwners.setText(temp.getOwners());
            tBand.setText(temp.getBand());
            tCarryLimit.setText(temp.getCarryLimit());
            tColor.setText(temp.getColor());
            tType.setText(temp.getType());
            tEngineNo.setText(temp.getEngineNo());
            tDrivingNo.setText(temp.getDrivingNo());
        }

        submitBtn.addActionListener(e -> {
            if (tNo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tCheckTime.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "�Ǽ�ʱ��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tSuccessTime.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "��֤ʱ��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tType.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tOwners.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tBand.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "����Ʒ��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tCarryLimit.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "�˶��ؿ���Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tColor.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "��ɫΪ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Car car = new Car(tNo.getText(), tType.getText(), tOwners.getText(), tEngineNo.getText(), tDrivingNo.getText(), tBand.getText(), tCarryLimit.getText(), tColor.getText(), TimeUtils.str2Date(tCheckTime.getText()), TimeUtils.str2Date(tSuccessTime.getText()));
            // �¼�
            if (rows < 0) {
                try {
					CarDao.save(car);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else {
            	Car info = cars.get(rows);
            	
                try {
					CarDao.remove(car, info);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                
            }
            //ˢ��
            CarManagePannel.placeComponents(mainPanel, null);

        });

    }
}
