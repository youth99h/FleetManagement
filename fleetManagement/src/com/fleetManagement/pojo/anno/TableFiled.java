package com.fleetManagement.pojo.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**����ע��
 * @Target��

��ʾ��ע���������ʲô�ط������ܵ�ElementType�����У�
CONSTRUCTOR��������������
FIELD��������������enumʵ����
LOCAL_VARIABLE���ֲ���������
METHOD����������
PACKAGE��������
PARAMETER����������
TYPE���ࡢ�ӿڣ�����ע�����ͣ���enum����
 @Retention

 ��ʾ��Ҫ��ʲô���𱣴��ע����Ϣ����ѡ��RetentionPolicy����������
 SOURCE��ע�⽫������������
 CLASS��ע����class�ļ��п��ã����ᱻVM����
 RUNTIME��VM���������ڼ䱣��ע�⣬��˿���ͨ��������ƶ�ȡע�����Ϣ
  *
  * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableFiled {
     int index();
     String name() default "";
}
