package com.example.JavaChatApplication.client;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.PropertyPermission;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public class Utilities {
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    public static final Color PRIMARY_COLOR = Color.decode("#1f3635");
    public static final Color SECONDARY_COLOR = Color.decode("#1f3f3e");
    public static final Color TEXT_COLOR = Color.WHITE.brighter();

    public static EmptyBorder addPadding(int top, int left, int bottom, int right){
        return new EmptyBorder(top, left, bottom, right);
    }
}
