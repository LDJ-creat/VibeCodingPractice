package com.chuanzhi.health.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.chuanzhi.health.model.Checkitem;
import com.chuanzhi.health.service.CheckitemService;

public class CheckitemManagementView extends JPanel {

    private final CheckitemService checkitemService = new CheckitemService();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField searchField;
    private final JButton editButton;
    private final JButton deleteButton;

    public CheckitemManagementView() {
        setSize(800, 600); // JPanel通常不需要设置大小，由父容器决定

        // 创建组件
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("查询");
        topPanel.add(new JLabel("编号/名称:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("新增");
        editButton = new JButton("修改");
        deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(refreshButton);

        // 表格模型和表格
        String[] columnNames = {"ID", "项目代号", "项目名称", "参考值", "单位"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 表格内容不可编辑
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
        JScrollPane scrollPane = new JScrollPane(table);

        // 布局
        Container contentPane = this;
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // 添加事件监听器
        searchButton.addActionListener(e -> searchCheckitems());
        refreshButton.addActionListener(e -> loadCheckitems());
        addButton.addActionListener(e -> addCheckitem());
        editButton.addActionListener(e -> editCheckitem());
        deleteButton.addActionListener(e -> deleteCheckitem());

        // 根据表格选择状态更新按钮可用性
        table.getSelectionModel().addListSelectionListener(e -> updateButtonStates());

        // 初始化
        loadCheckitems();
        updateButtonStates();
    }

    private void updateButtonStates() {
        boolean isSelected = table.getSelectedRow() != -1;
        editButton.setEnabled(isSelected);
        deleteButton.setEnabled(isSelected);
    }

    private void loadCheckitems() {
        SwingUtilities.invokeLater(() -> {
            List<Checkitem> checkitems = checkitemService.findAll();
            updateTable(checkitems);
        });
    }

    private void searchCheckitems() {
        String keyword = searchField.getText().trim();
        SwingUtilities.invokeLater(() -> {
            List<Checkitem> checkitems = checkitemService.findByCondition(keyword);
            updateTable(checkitems);
        });
    }

    private void updateTable(List<Checkitem> checkitems) {
        tableModel.setRowCount(0); // 清空表格
        if (checkitems != null) {
            for (Checkitem item : checkitems) {
                Vector<Object> row = new Vector<>();
                row.add(item.getCid());
                row.add(item.getCcode());
                row.add(item.getCname());
                row.add(item.getReferVal());
                row.add(item.getUnit());
                tableModel.addRow(row);
            }
        }
    }

    private void addCheckitem() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        CheckitemDialog dialog = new CheckitemDialog(owner, "新增检查项", null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            Checkitem newItem = dialog.getCheckitem();
            checkitemService.add(newItem);
            loadCheckitems();
        }
    }

    private void editCheckitem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要修改的检查项！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Checkitem selectedItem = getCheckitemFromSelectedRow();
        if (selectedItem == null) return;

        Window owner = SwingUtilities.getWindowAncestor(this);
        CheckitemDialog dialog = new CheckitemDialog(owner, "修改检查项", selectedItem);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Checkitem updatedItem = dialog.getCheckitem();
            checkitemService.update(updatedItem);
            loadCheckitems();
        }
    }

    private void deleteCheckitem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的检查项！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除选中的检查项吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Checkitem selectedItem = getCheckitemFromSelectedRow();
            if (selectedItem != null) {
                checkitemService.deleteById(selectedItem.getCid());
                loadCheckitems();
            }
        }
    }

    private Checkitem getCheckitemFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return null;

        Checkitem item = new Checkitem();
        item.setCid((Integer) tableModel.getValueAt(selectedRow, 0));
        item.setCcode((String) tableModel.getValueAt(selectedRow, 1));
        item.setCname((String) tableModel.getValueAt(selectedRow, 2));
        item.setReferVal((String) tableModel.getValueAt(selectedRow, 3));
        item.setUnit((String) tableModel.getValueAt(selectedRow, 4));
        return item;
    }

}

// 用于新增和修改的对话框
class CheckitemDialog extends JDialog {
    private JTextField codeField, nameField, referValField, unitField;
    private boolean succeeded = false;
    private Checkitem checkitem;

    public CheckitemDialog(Window owner, String title, Checkitem item) {
        super(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
        this.checkitem = (item == null) ? new Checkitem() : item;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 初始化表单字段
        codeField = new JTextField(20);
        nameField = new JTextField(20);
        referValField = new JTextField(20);
        unitField = new JTextField(20);

        // 布局
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("项目代号:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(codeField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("项目名称:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("参考值:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(referValField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("单位:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(unitField, gbc);

        // 如果是修改，则填充数据
        if (item != null) {
            populateFields();
        }

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        codeField.setText(checkitem.getCcode());
        nameField.setText(checkitem.getCname());
        referValField.setText(checkitem.getReferVal());
        unitField.setText(checkitem.getUnit());
    }

    private void onOK() {
        // 数据验证
        if (nameField.getText().trim().isEmpty() || codeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "项目代号和名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 收集数据
        checkitem.setCcode(codeField.getText().trim());
        checkitem.setCname(nameField.getText().trim());
        checkitem.setReferVal(referValField.getText().trim());
        checkitem.setUnit(unitField.getText().trim());

        succeeded = true;
        dispose();
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Checkitem getCheckitem() {
        return checkitem;
    }
}