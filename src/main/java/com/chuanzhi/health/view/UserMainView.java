package com.chuanzhi.health.view;

import com.chuanzhi.health.model.Appointment;
import com.chuanzhi.health.model.Checkgroup;
import com.chuanzhi.health.model.ExamResult;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.service.AppointmentService;
import com.chuanzhi.health.service.CheckgroupService;
import com.chuanzhi.health.service.ExamResultService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class UserMainView extends JPanel {
    private User currentUser;
    private AppointmentService appointmentService = new AppointmentService();
    private CheckgroupService checkgroupService = new CheckgroupService();
    private ExamResultService examResultService = new ExamResultService();

    private JTable historyTable;
    private DefaultTableModel tableModel;

    public UserMainView(User user) {
        this.currentUser = user;
        // JPanel通常不需要设置大小，由父容器决定
        // setSize(800, 600);
        // setLocationRelativeTo(null);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 我要预约 Tab
        JPanel appointmentPanel = createAppointmentPanel();
        tabbedPane.addTab("我要预约", appointmentPanel);

        // 我的预约历史 Tab
        JPanel historyPanel = createHistoryPanel();
        tabbedPane.addTab("我的预约历史", historyPanel);

        this.add(tabbedPane);
        loadAppointmentHistory();
    }

    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // 套餐选择
//        panel.add(new JLabel("选择体检套餐:"), gbc);
//        gbc.gridx++;
//        JComboBox<Checkgroup> checkgroupComboBox = new JComboBox<>();
//        List<Checkgroup> checkgroups = checkgroupService.getAllCheckgroups();
//        for (Checkgroup group : checkgroups) {
//            checkgroupComboBox.addItem(group.getName());
//        }
//        panel.add(checkgroupComboBox, gbc);
// 套餐选择
        panel.add(new JLabel("选择体检套餐:"), gbc);
        gbc.gridx++;

// 创建JComboBox并自定义渲染器
        JComboBox<Checkgroup> checkgroupComboBox = new JComboBox<>();
        List<Checkgroup> checkgroups = checkgroupService.getAllCheckgroups();

// 添加所有套餐到下拉框
        for (Checkgroup group : checkgroups) {
            checkgroupComboBox.addItem(group);
        }

// 设置自定义渲染器，只显示套餐名称
        checkgroupComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Checkgroup) {
                    Checkgroup group = (Checkgroup) value;
                    setText(group.getName());  // 只显示套餐名称
                }
                return this;
            }
        });

        panel.add(checkgroupComboBox, gbc);

        // 日期选择
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("选择预约日期:"), gbc);
        gbc.gridx++;
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        panel.add(dateSpinner, gbc);

        // 提交按钮
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("提交预约");
        panel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            Checkgroup selectedGroup = (Checkgroup) checkgroupComboBox.getSelectedItem();
            Date selectedDate = (Date) dateSpinner.getValue();

            if (selectedGroup == null) {
                JOptionPane.showMessageDialog(this, "请选择一个体检套餐。", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                appointmentService.createAppointment(currentUser.getId(), selectedGroup.getId(), selectedDate);
                JOptionPane.showMessageDialog(this, "预约成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadAppointmentHistory(); // 刷新历史记录
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "预约失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // 表格
        String[] columnNames = {"ID", "预约日期", "套餐名称", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 不可编辑
            }
        };
        historyTable = new JTable(tableModel);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton viewDetailsButton = new JButton("查看详情");
        JButton cancelButton = new JButton("取消预约");
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 查看详情事件
        viewDetailsButton.addActionListener(e -> {
            int selectedRow = historyTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择一个预约记录。", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String status = (String) tableModel.getValueAt(selectedRow, 3);
            if (!"已完成".equals(status)) {
                JOptionPane.showMessageDialog(this, "只能查看“已完成”的预约详情。", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
            List<ExamResult> results = examResultService.findByAppointmentId(appointmentId);
            if (results == null || results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到该次预约的体检结果。", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Window owner = SwingUtilities.getWindowAncestor(this);
                ExamResultView resultView = new ExamResultView(owner, results);
                resultView.setVisible(true);
            }
        });

        // 取消预约事件
        cancelButton.addActionListener(e -> {
            int selectedRow = historyTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请选择一个预约记录。", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String status = (String) tableModel.getValueAt(selectedRow, 3);
            if (!"待检查".equals(status)) {
                JOptionPane.showMessageDialog(this, "只能取消“待检查”的预约。", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int appointmentId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "确定要取消这个预约吗？", "确认", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    appointmentService.cancelAppointment(appointmentId);
                    JOptionPane.showMessageDialog(this, "预约已取消。", "成功", JOptionPane.INFORMATION_MESSAGE);
                    loadAppointmentHistory(); // 刷新列表
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "取消失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private void loadAppointmentHistory() {
        tableModel.setRowCount(0); // 清空表格
        List<Appointment> appointments = appointmentService.findByUserId(currentUser.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Appointment app : appointments) {
            Vector<Object> row = new Vector<>();
            row.add(app.getId());
            row.add(sdf.format(app.getAppointmentDate()));
            row.add(app.getCheckgroupName());
            row.add(app.getStatus());
            tableModel.addRow(row);
        }
    }
}