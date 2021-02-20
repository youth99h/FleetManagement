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
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//˾�������������

public class DriverManagePannel {
	
	public static int lastRow = 0;

    private static String[] DrivingLicenseSelect = {"A3", "B1", "B2", "C1", "C2"};

    /**
     * @param panel
     * @param predicate ɸѡ����
     */
    public static void placeComponents(JPanel panel, Predicate<? super Driver> predicate) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));

        String[] columns={"����","�Ա�","��������","������֤ʱ��","��Ч��ʼʱ��","��Ч����ʱ��","��ַ","ִ�պ���","׼�ݳ���"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> dri = null;
		try {
			dri = DriverDao.get();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:dri)
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
       //ɾ��ѡ�����ٳ־û�ʣ�µ�˾����Ϣ
           try {
			Driver de = DriverDao.getAll().get(selectedRow);
			DriverDao.delete(de);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
            //ˢ��ҳ��
            DriverManagePannel.placeComponents(panel, null);
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
    			dri2 = DriverDao.get();
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
        	 
            DriverManagePannel.placeComponents(panel, null);
           
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
				buildForm(panel, eP, DriverDao.getAll(), selectedRow);
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
        PrintPanel.print(dri, panel, pP);

        panel.updateUI();
        panel.repaint();

    }


    private static void buildForm(JPanel mainPanel, JPanel panel, List<Driver> drivers, int rows) {
        int lw = 200, tw = 200, lh = 25, th = 25, x = 100, y = 20, tx = lw + x;

        JLabel lTitle = new JLabel("˾������¼��", JLabel.RIGHT);
        lTitle.setBounds(x, y, lw, lh);
        panel.add(lTitle);

        y += 30;
        JLabel lName = new JLabel("������", JLabel.RIGHT);
        lName.setBounds(x, y, lw, lh);
        JTextField tName = new JTextField(20);
        tName.setBounds(tx, y, tw, th);
        panel.add(lName);
        panel.add(tName);

        y += 30;
        JLabel lSex = new JLabel("�Ա�", JLabel.RIGHT);
        lSex.setBounds(x, y, lw, lh);
        JRadioButton manRbtn = new JRadioButton("��", true);
        manRbtn.setBounds(tx, y, tw, th);
        JRadioButton wowenRbtn = new JRadioButton("Ů");
        y += 25;
        wowenRbtn.setBounds(tx, y, tw, th);
        ButtonGroup sexGroup = new ButtonGroup();
        sexGroup.add(manRbtn);
        sexGroup.add(wowenRbtn);
        panel.add(lSex);
        panel.add(manRbtn);
        panel.add(wowenRbtn);

        y += 30;
        JLabel lBirthday = new JLabel("�������ڣ�", JLabel.RIGHT);
        lBirthday.setBounds(x, y, lw, lh);
        JTextField tBirthday = new JTextField(20);
        tBirthday.setBounds(tx, y, tw, th);
        CalendarPanel p = new CalendarPanel(tBirthday, "yyyy-MM-dd");
        p.initCalendarPanel();
        panel.add(p);
        panel.add(lBirthday);
        panel.add(tBirthday);

        y += 30;
        JLabel lCertificateTime = new JLabel("������֤ʱ�䣺", JLabel.RIGHT);
        lCertificateTime.setBounds(x, y, lw, lh);
        JTextField tCertificateTime = new JTextField(20);
        tCertificateTime.setBounds(tx, y, tw, th);
        CalendarPanel p2 = new CalendarPanel(tCertificateTime, "yyyy-MM-dd");
        p2.initCalendarPanel();
        panel.add(p2);
        panel.add(lCertificateTime);
        panel.add(tCertificateTime);
        y += 30;
        JLabel lStartTime = new JLabel("��Ч��ʼʱ�䣺", JLabel.RIGHT);
        lStartTime.setBounds(x, y, lw, lh);
        JTextField tStartTime = new JTextField(20);
        tStartTime.setBounds(tx, y, tw, th);
        CalendarPanel p3 = new CalendarPanel(tStartTime, "yyyy-MM-dd");
        p3.initCalendarPanel();
        panel.add(p3);
        panel.add(lStartTime);
        panel.add(tStartTime);

        y += 30;
        JLabel lEndTime = new JLabel("��Ч����ʱ�䣺", JLabel.RIGHT);
        lEndTime.setBounds(x, y, lw, lh);
        JTextField tEndTime = new JTextField(20);
        tEndTime.setBounds(tx, y, tw, th);
        CalendarPanel p4 = new CalendarPanel(tEndTime, "yyyy-MM-dd");
        p4.initCalendarPanel();
        panel.add(p4);
        panel.add(lEndTime);
        panel.add(tEndTime);

        y += 30;
        JLabel lAddress = new JLabel("��ַ��", JLabel.RIGHT);
        lAddress.setBounds(x, y, lw, lh);
        JTextField tAddress = new JTextField(20);
        tAddress.setBounds(tx, y, tw, th);
        panel.add(lAddress);
        panel.add(tAddress);

        y += 30;
        JLabel lLicenseNumber = new JLabel("ִ�պ��룺", JLabel.RIGHT);
        lLicenseNumber.setBounds(x, y, lw, lh);
        JTextField tLicenseNumber = new JTextField(20);
        tLicenseNumber.setBounds(tx, y, tw, th);
        panel.add(lLicenseNumber);
        panel.add(tLicenseNumber);

        y += 30;
        JLabel lDrivingLicense = new JLabel("׼�ݳ��ͣ�", JLabel.RIGHT);
        lDrivingLicense.setBounds(x, y, lw, lh);
        JComboBox tDrivingLicense = new JComboBox<>();
        tDrivingLicense.setModel(new DefaultComboBoxModel(DrivingLicenseSelect));
        tDrivingLicense.setBounds(tx, y, tw, th);
        panel.add(lDrivingLicense);
        panel.add(tDrivingLicense);

        y += 50;
        JButton submitBtn = new JButton("ȷ��");
        submitBtn.setBounds(x + 250, y, 100, th);
        panel.add(submitBtn);

        if (Objects.nonNull(drivers) && drivers.size() > 0 && rows >= 0) {
            Driver temp = drivers.get(rows);
            tName.setText(temp.getName());
            tAddress.setText(temp.getAddress());
            tBirthday.setText(temp.getBirthday().toString());
            tCertificateTime.setText(temp.getCertificateTime().toString());
            tDrivingLicense.setSelectedItem(temp.getDrivingLicense());
            tLicenseNumber.setText(temp.getLicenseNumber());
            tStartTime.setText(temp.getStartTime().toString());
            tEndTime.setText(temp.getEndTime().toString());
        }

        submitBtn.addActionListener(e -> {
            String name = tName.getText();
            if (Objects.isNull(name) || name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "�û�������Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            char sex;
            if (manRbtn.isSelected()) {
                sex = '��';
            } else {
                sex = 'Ů';
            }
            String db = tBirthday.getText();
            if (Objects.isNull(db) || db.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "�������ڲ���Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String addr = tAddress.getText();
            if (Objects.isNull(addr) || addr.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "��ַ����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ct = tCertificateTime.getText();
            if (Objects.isNull(ct) || ct.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "������֤ʱ�䲻��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ln = tLicenseNumber.getText();
            if (Objects.isNull(ln) || ln.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "ִ�պ��벻��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String st = tStartTime.getText();
            if (Objects.isNull(st) || st.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "��Ч��ʼʱ�䲻��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String et = tEndTime.getText();
            if (Objects.isNull(et) || et.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "��Ч����ʱ�䲻��Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Driver driver = new Driver(name, sex, TimeUtils.str2Date(db), addr, TimeUtils.str2Date(ct), ln, (String) tDrivingLicense.getSelectedItem(), TimeUtils.str2Date(st), TimeUtils.str2Date(et));
            // �¼�
            if (rows < 0) {
                try {
					DriverDao.save(driver);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            // �༭
            else {
            	Driver info = drivers.get(rows);
            	
                try {
					DriverDao.remove(driver, info);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                
            }
            //ˢ��
            DriverManagePannel.placeComponents(mainPanel, null);

        });

    }
}
