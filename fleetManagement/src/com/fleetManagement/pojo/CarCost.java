package com.fleetManagement.pojo;

import com.fleetManagement.pojo.anno.TableFiled;

//�����ճ�����

public class CarCost {

  @TableFiled(index = 0, name = "���ƺ�")
  private String no;
  @TableFiled(index = 1, name = "ά�޷���(Ԫ)")
  private double fixCost;
  @TableFiled(index = 2, name = "ά�޵�λ")
  private String fixCompany;
  @TableFiled(index = 3, name = "ά������")
  private String fixContext;
  @TableFiled(index = 4, name = "ά��ʱ��")
  private String fixTime;
  @TableFiled(index = 5, name = "ά�����")
  private String fixParts;
  @TableFiled(index = 6, name = "��ע")
  private String memo;

  public CarCost(String no, double fixCost, String fixCompany, String fixContext, String fixTime, String fixParts, String memo) {
      this.no = no;
      this.fixCost = fixCost;
      this.fixCompany = fixCompany;
      this.fixContext = fixContext;
      this.fixTime = fixTime;
      this.fixParts = fixParts;
      this.memo = memo;
  }

  public CarCost() {
	// TODO Auto-generated constructor stub
}

public String getNo() {
      return no;
  }

  public void setNo(String no) {
      this.no = no;
  }

  public double getFixCost() {
      return fixCost;
  }

  public void setFixCost(double fixCost) {
      this.fixCost = fixCost;
  }

  public String getFixCompany() {
      return fixCompany;
  }

  public void setFixCompany(String fixCompany) {
      this.fixCompany = fixCompany;
  }

  public String getFixContext() {
      return fixContext;
  }

  public void setFixContext(String fixContext) {
      this.fixContext = fixContext;
  }

  public String getFixTime() {
      return fixTime;
  }

  public void setFixTime(String fixTime) {
      this.fixTime = fixTime;
  }

  public String getFixParts() {
      return fixParts;
  }

  public void setFixParts(String fixParts) {
      this.fixParts = fixParts;
  }

  public String getMemo() {
      return memo;
  }

  public void setMemo(String memo) {
      this.memo = memo;
  }

  @Override
  public String toString() {
      return "{\r\n"  +
              "no='" + no + '\'' +
              ", \r\n fixCost=" + fixCost +
              ", \r\n fixCompany='" + fixCompany + '\'' +
              ", \r\n fixContext='" + fixContext + '\'' +
              ", \r\n fixTime='" + fixTime + '\'' +
              ", \r\n fixParts='" + fixParts + '\'' +
              ", \r\n memo='" + memo + '\'' +
              "\r\n}";
  }
}
