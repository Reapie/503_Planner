/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.gui;

import at.htlkaindorf.ahif18.data.Event;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author mjuri
 */
public class EventItemRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list,
            Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, 
                isSelected, cellHasFocus);
        
        if (((Event) value).isDone()) {
            setText("\u2713 " + value.toString());
        } else {
            setText("\u2715 " + value.toString());
        }
        
        if (((Event) value).isOverdue()) {
            setForeground(Color.red);
        } else {
            setForeground(Color.black);
        }
        
        if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
        }

        return this;
    }
}
