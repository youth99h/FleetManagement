package com.fleetManagement.pojo;

import com.fleetManagement.pojo.anno.TableFiled;

//�Ϳ�ͳ��

public class OilCardStatistics {

  @TableFiled(index = 0, name = "�Ϳ����")
  private String no;
  @TableFiled(index =1, name = "���")
  private double quota;
  @TableFiled(index = 2, name = "��ע")
  private String memo;

  public OilCardStatistics(String no, double quota, String memo) {
      this.no = no;
      this.quota = quota;
      this.memo = memo;
  }

  public String getNo() {
      return no;
  }

  public void setNo(String no) {
      this.no = no;
  }

  public double getQuota() {
      return quota;
  }

  public void setQuota(double quota) {
      this.quota = quota;
  }

  public String getMemo() {
      return memo;
  }

  public void setMemo(String memo) {
      this.memo = memo;
  }

  @Override
  public String toString() {
      return "{\r\n" +
              "no='" + no + '\'' +
              ", \r\n quota=" + quota +
              ", \r\n memo='" + memo + '\'' +
              "\r\n}";
  }
}
