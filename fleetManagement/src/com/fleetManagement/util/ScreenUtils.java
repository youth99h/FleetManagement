package com.fleetManagement.util;

import java.awt.*;

//������Ļ������

public class ScreenUtils {

    private static int width;
    private static int height;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getHalfOfWidth() {
        return width / 2;
    }

    public static int getHalfOfHeight() {
        return height / 2;
    }

    /**
     * ������ ���ڽ��������Ļˮƽ����
     *
     * @param w
     * @return
     */
    public static int getMarginWidth(int w) {
        return (width - w) / 2;
    }

     /**
     * ������ ���ڽ��������Ļ��ֱ����
     *
     * @param h
     * @return
     */
    public static int getMarginHeight(int h) {
        return (height - h) / 2;
    }

    public static int getMarginX(int w, int cw) {
        return (w - cw) / 2;
    }

    public static int getMarginY(int h, int ch) {
        return (h - ch) / 2;
    }
}
