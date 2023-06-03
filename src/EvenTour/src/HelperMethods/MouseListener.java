package HelperMethods;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    public JTable evenTourDashboardTable;
    public JTextField tfId;

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = evenTourDashboardTable.getSelectedRow();
        tfId.setText(evenTourDashboardTable.getValueAt(row, 0).toString());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
