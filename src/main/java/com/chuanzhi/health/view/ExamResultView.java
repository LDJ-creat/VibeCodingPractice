package com.chuanzhi.health.view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chuanzhi.health.model.ExamResult;

public class ExamResultView extends JDialog {
    public ExamResultView(Window owner, List<ExamResult> results) {
        super(owner, "体检结果详情", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        String[] columnNames = {"检查项", "检查结果", "参考值", "是否异常"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultTable = new JTable(tableModel);
        resultTable.setFillsViewportHeight(true); // 让表格填充整个视口高度
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // 填充数据
        for (ExamResult result : results) {
            Object[] rowData = {
                    result.getCheckitemName(),
                    result.getExamValue(),
                    result.getReferenceValue(),
                    result.getIsAbnormal() == 1 ? "是" : "否"
            };
            tableModel.addRow(rowData);
        }

        // 关闭按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}