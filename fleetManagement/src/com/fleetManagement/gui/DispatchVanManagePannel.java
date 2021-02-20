package com.fleetManagement.gui;

import com.fleetManagement.dao.CarDao;
import com.fleetManagement.dao.DispatchVanDao;
import com.fleetManagement.dao.DriverDao;
import com.fleetManagement.gui.extend.CalendarPanel;
import com.fleetManagement.gui.extend.ExtTable;
import com.fleetManagement.pojo.DispatchVan;
import com.fleetManagement.util.TimeUtils;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

//�����ɣ����������
 
public class DispatchVanManagePannel {
	public static int lastRow = 0;

    public static void placeComponents(JPanel panel, Predicate<? super DispatchVan> predicate) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));

        //List<DispatchVan> dispatchVans = DispatchVanDao.getInstance().get(predicate);
        //���ر������
        //DefaultTableModel tableModel = ExtTable.buildTableModel4Objects(dispatchVans, DispatchVan.class);

        String[] columns={"����","˾������","����ʱ��","������","Ŀ�ĵ�"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> dis = null;
		try {
			dis = DispatchVanDao.get();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:dis)
        {
            String[] args=info.split(",");
            tableModel.addRow(args);
        }
        
        //���
        JPanel qP = new JPanel(new BorderLayout(10, 0));
        //��¼����ɳ�����Ϣ
        JPanel aP = new JPanel(null);
        JPanel pP = new JPanel(null);
        JPanel eP = new JPanel(null);
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
            	DispatchVan dvd = DispatchVanDao.getAll().get(selectedRow);
    			DispatchVanDao.delete(dvd);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            //ˢ��ҳ��
            DispatchVanManagePannel.placeComponents(panel, null);
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
    			dri2 = DispatchVanDao.get();
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
            DispatchVanManagePannel.placeComponents(panel, null);
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
				buildForm(panel, eP, DispatchVanDao.getAll(), selectedRow);
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

        //��¼��
        buildForm(panel, aP, null, -1);
        //��ӡ
        PrintPanel.print(dis, panel, pP);
        panel.updateUI();
        panel.repaint();

    }


    private static void buildForm(JPanel mainPanel, JPanel panel, List<DispatchVan> dispatchVans, int rows) {
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
        JLabel lNames = new JLabel("˾��������", JLabel.RIGHT);
        lNames.setBounds(x, y, lw, lh);
        JComboBox tNames = new JComboBox<>();
        try {
			tNames.setModel(new DefaultComboBoxModel(DriverDao.getDriverNames()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        tNames.setBounds(tx, y, tw, th);
        panel.add(lNames);
        panel.add(tNames);

        y += 30;
        JLabel lStartTime = new JLabel("����ʱ�䣺", JLabel.RIGHT);
        lStartTime.setBounds(x, y, lw, lh);
        JTextField tStartTime = new JTextField(20);
        tStartTime.setBounds(tx, y, tw, th);
        CalendarPanel p = new CalendarPanel(tStartTime, "yyyy-MM-dd");
        p.initCalendarPanel();
        panel.add(p);
        panel.add(lStartTime);
        panel.add(tStartTime);


        y += 30;
        JLabel lTo = new JLabel("�����أ�", JLabel.RIGHT);
        lTo.setBounds(x, y, lw, lh);
        JTextField tTo = new JTextField(20);
        tTo.setBounds(tx, y, tw, th);
        panel.add(lTo);
        panel.add(tTo);

        y += 30;
        JLabel lFrom = new JLabel("Ŀ�ĵأ�", JLabel.RIGHT);
        lFrom.setBounds(x, y, lw, lh);
        JTextField tFrom = new JTextField(20);
        tFrom.setBounds(tx, y, tw, th);
        panel.add(lFrom);
        panel.add(tFrom);


        y += 50;
        JButton submitBtn = new JButton("ȷ��");
        submitBtn.setBounds(x + 250, y, 100, th);
        panel.add(submitBtn);

        if (Objects.nonNull(dispatchVans) && dispatchVans.size() > 0 && rows >= 0) {
            DispatchVan temp = dispatchVans.get(rows);
            tNo.setSelectedItem(temp.getNo());
            tTo.setText(temp.getTo());
            tFrom.setText(temp.getFrom());
            tStartTime.setText(temp.getStartTime().toString());
            tNames.setSelectedItem(temp.getDriverName());
        }

        submitBtn.addActionListener(e -> {
            if (tTo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "������Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tFrom.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Ŀ�ĵ�Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tStartTime.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "����ʱ��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DispatchVan car = new DispatchVan((String)tNo.getSelectedItem(), tTo.getText(), tFrom.getText(), TimeUtils.str2Date(tStartTime.getText()), (String)tNames.getSelectedItem());// �¼�
            if (rows < 0) {
                try {
					DispatchVanDao.save(car);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else {
            	DispatchVan info = dispatchVans.get(rows);
            	
                try {
					DispatchVanDao.remove(car, info);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                
            }
            //ˢ��
            DispatchVanManagePannel.placeComponents(mainPanel, null);

        });

    }
}
