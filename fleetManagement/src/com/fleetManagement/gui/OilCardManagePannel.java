package com.fleetManagement.gui;

import com.fleetManagement.dao.CarCostDao;
import com.fleetManagement.dao.CarDao;
import com.fleetManagement.dao.OilCardDao;
import com.fleetManagement.gui.extend.CalendarPanel;
import com.fleetManagement.gui.extend.ExtTable;
import com.fleetManagement.pojo.CarCost;
import com.fleetManagement.pojo.OilCard;
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

// �Ϳ�����

public class OilCardManagePannel {
	
	public static int lastRow = 0;


    public static void placeComponents(JPanel panel, Predicate<? super OilCard> predicate) {
        panel.removeAll();

        panel.setLayout(new BorderLayout(10, 5));

        //List<OilCard> oilCard = OilCardDao.getInstance().get(predicate);
        //���ر������
       // DefaultTableModel tableModel = ExtTable.buildTableModel4Objects(oilCard, OilCard.class);
        
        String[] columns={"�Ϳ����","ʯ��Ʒ��","���ѱ��","���ѳ���","���ѽ�Ԫ��","��ֵ���","��ֵ��Ԫ��","��¼ʱ��","��ע"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        List<String> o = null;
		try {
			o = OilCardDao.get();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for(String info:o)
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
    			OilCard oid = OilCardDao.getAll().get(selectedRow);
    			OilCardDao.delete(oid);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            //ˢ��ҳ��
            OilCardManagePannel.placeComponents(panel, null);
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
    			dri2 = OilCardDao.get();
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
            OilCardManagePannel.placeComponents(panel, null);
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
				buildForm(panel, eP, OilCardDao.getAll(), selectedRow);
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

        //��
        buildForm(panel, aP, null, -1);

        //��ӡ
        PrintPanel.print(o, panel, pP);

        panel.updateUI();
        panel.repaint();

    }


    private static void buildForm(JPanel mainPanel, JPanel panel, List<OilCard> oilCard, int rows) {
        int lw = 200, tw = 200, lh = 25, th = 25, x = 100, y = 20, tx = lw + x;

        JLabel lTitle = new JLabel("��������¼��", JLabel.RIGHT);
        lTitle.setBounds(x, y, lw, lh);
        panel.add(lTitle);

        y += 30;
        JLabel lNo = new JLabel("�Ϳ���ţ�", JLabel.RIGHT);
        lNo.setBounds(x, y, lw, lh);
        JTextField tNo = new JTextField(20);
        tNo.setBounds(tx, y, tw, th);
        panel.add(lNo);
        panel.add(tNo);

        y += 30;
        JLabel lBand = new JLabel("ʯ��Ʒ�ƣ�", JLabel.RIGHT);
        lBand.setBounds(x, y, lw, lh);
        JTextField tBand = new JTextField(20);
        tBand.setBounds(tx, y, tw, th);
        panel.add(lBand);
        panel.add(tBand);

        y += 30;
        JLabel lUpdateTime = new JLabel("¼��ʱ�䣺", JLabel.RIGHT);
        lUpdateTime.setBounds(x, y, lw, lh);
        JTextField tUpdateTime = new JTextField(20);
        tUpdateTime.setBounds(tx, y, tw, th);
        CalendarPanel p = new CalendarPanel(tUpdateTime, "yyyy-MM-dd");
        p.initCalendarPanel();
        panel.add(p);
        panel.add(lUpdateTime);
        panel.add(tUpdateTime);


        y += 30;
        JLabel lType = new JLabel("��֧���ͣ�", JLabel.RIGHT);
        lType.setBounds(x, y, lw, lh);
        JRadioButton iRbtn = new JRadioButton("��ֵ");
        iRbtn.setBounds(tx, y, tw, th);
        JRadioButton oRbtn = new JRadioButton("����");
        y += 25;
        oRbtn.setBounds(tx, y, tw, th);
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(iRbtn);
        typeGroup.add(oRbtn);
        panel.add(lType);
        panel.add(iRbtn);
        panel.add(oRbtn);

        y += 30;
        JLabel lRecharge = new JLabel("��ֵ��Ԫ����", JLabel.RIGHT);
        lRecharge.setBounds(x, y, lw, lh);
        JTextField tRecharge = new JTextField(20);
        tRecharge.setBounds(tx, y, tw, th);

        y += 30;
        JLabel lConsume = new JLabel("���ѽ�Ԫ����", JLabel.RIGHT);
        lConsume.setBounds(x, y, lw, lh);
        JTextField tConsume = new JTextField(20);
        tConsume.setBounds(tx, y, tw, th);

        y += 30;
        JLabel lCarNo = new JLabel("���ѳ��ţ�", JLabel.RIGHT);
        lCarNo.setBounds(x, y, lw, lh);
        JComboBox tCarNo = new JComboBox<>();
        try {
			tCarNo.setModel(new DefaultComboBoxModel(CarDao.getCarNos()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        tCarNo.setBounds(tx, y, tw, th);

        iRbtn.addActionListener(e -> {
            panel.add(lRecharge);
            panel.add(tRecharge);

            panel.remove(lConsume);
            panel.remove(tConsume);
            panel.remove(lCarNo);
            panel.remove(tCarNo);

            panel.repaint();
        });

        oRbtn.addActionListener(e -> {
            panel.remove(lRecharge);
            panel.remove(tRecharge);

            panel.add(lConsume);
            panel.add(tConsume);
            panel.add(lCarNo);
            panel.add(tCarNo);

            panel.repaint();
        });

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

        if (Objects.nonNull(oilCard) && oilCard.size() > 0 && rows >= 0) {
            OilCard temp = oilCard.get(rows);
            tNo.setText(temp.getNo());
            tBand.setText(temp.getBand());
            tUpdateTime.setText(temp.getUpdateTime().toString());
            if (temp.getConsume()>0) {
                tConsume.setText(temp.getConsume()+"");
                tCarNo.setSelectedItem(temp.getConsumeCarNo());
                panel.add(lConsume);
                panel.add(tConsume);
                panel.add(lCarNo);
                panel.add(tCarNo);

                panel.repaint();
            }else{
                tRecharge.setText(temp.getRecharge()+"");
                panel.add(lRecharge);
                panel.add(tRecharge);

                panel.repaint();
            }
            tMemo.setText(temp.getMemo());
        }

        submitBtn.addActionListener(e -> {
            if (Objects.isNull(typeGroup.getSelection())) {
                JOptionPane.showMessageDialog(panel, "û��ѡ����֧����!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tNo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "�Ϳ����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double consume = 0,recharge = 0;
            String consumeCarNo = "";
            if (iRbtn.isSelected()) {
                if (tRecharge.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "��ֵ����Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 recharge = Double.parseDouble(tRecharge.getText());
            }else{
                consume  = Double.parseDouble(tConsume.getText());
                if (tConsume.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "���ѽ���Ϊ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                consumeCarNo =(String) tCarNo.getSelectedItem();
            }


            OilCard car = new OilCard(tNo.getText(), tBand.getText(), consume,consumeCarNo,  recharge, TimeUtils.str2Date(tUpdateTime.getText()), tMemo.getText()); // �¼�
            if (rows < 0) {
            	try {
					OilCardDao.save(car);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            else {
            	OilCard info = oilCard.get(rows);
            	
                try {
					OilCardDao.remove(car, info);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
                
            }
            //ˢ��
            OilCardManagePannel.placeComponents(mainPanel, null);

        });

    }
}
