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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.model.ExamResult;

public class ExamResultView extends JDialog {
    private static final Logger logger = LoggerFactory.getLogger(ExamResultView.class);
    
    public ExamResultView(Window owner, List<ExamResult> results) {
        super(owner, "体检结果详情", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        
        logger.debug("显示体检结果详情，结果数量: {}", results != null ? results.size() : 0);

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
        if (results != null && !results.isEmpty()) {
            for (ExamResult result : results) {
                // 记录调试信息
                logger.debug("处理体检结果 - 检查项: {}, 结果值: {}, 参考值: {}, 是否异常: {}", 
                           result.getCheckitemName(), result.getExamValue(), 
                           result.getReferenceValue(), result.getIsAbnormal());
                
                Object[] rowData = {
                        result.getCheckitemName() != null ? result.getCheckitemName() : "未知检查项",
                        result.getExamValue() != null ? result.getExamValue() : "无结果",
                        result.getReferenceValue() != null ? result.getReferenceValue() : "无参考值",
                        result.getIsAbnormal() != null ? (result.getIsAbnormal() == 1 ? "是" : "否") : "未知"
                };
                tableModel.addRow(rowData);
            }
        } else {
            logger.warn("体检结果为空或null");
            // 如果没有数据，显示提示信息
            Object[] rowData = {"暂无数据", "", "", ""};
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