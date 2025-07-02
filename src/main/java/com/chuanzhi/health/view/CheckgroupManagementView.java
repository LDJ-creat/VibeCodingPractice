package com.chuanzhi.health.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.chuanzhi.health.model.Checkgroup;
import com.chuanzhi.health.model.Checkitem;
import com.chuanzhi.health.service.CheckgroupService;
import com.chuanzhi.health.service.CheckitemService;

/**
 * 检查组管理界面
 */
public class CheckgroupManagementView extends JPanel {

    private final CheckgroupService checkgroupService = new CheckgroupService();
    private final CheckitemService checkitemService = new CheckitemService();

    // --- UI Components ---
    private final JTextField queryField = new JTextField(15);
    private final JButton queryButton = new JButton("查询");
    private final JButton addButton = new JButton("新增");
    private final JButton editButton = new JButton("修改");
    private final JButton deleteButton = new JButton("删除");
    private final JButton refreshButton = new JButton("刷新");

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextArea associatedItemsArea = new JTextArea(5, 30);

    public CheckgroupManagementView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Top Panel for Query ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("查询条件:"));
        topPanel.add(queryField);
        topPanel.add(queryButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Center Panel for Table and Details ---
        String[] columnNames = {"ID", "代号", "名称", "性别", "助记码", "备注", "注意事项"};
        tableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(new TitledBorder("检查组列表"));

        associatedItemsArea.setEditable(false);
        associatedItemsArea.setLineWrap(true);
        associatedItemsArea.setWrapStyleWord(true);
        JScrollPane associatedItemsScrollPane = new JScrollPane(associatedItemsArea);
        associatedItemsScrollPane.setBorder(new TitledBorder("关联的检查项"));

        JSplitPane centerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, associatedItemsScrollPane);
        centerSplitPane.setResizeWeight(0.7);
        add(centerSplitPane, BorderLayout.CENTER);

        // --- Bottom Panel for Actions ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Add Listeners ---
        addListeners();

        // --- Initial Data Load ---
        loadTableData();
    }

    private void addListeners() {
        queryButton.addActionListener(e -> loadTableData());
        refreshButton.addActionListener(e -> {
            queryField.setText("");
            loadTableData();
        });
        addButton.addActionListener(e -> openDialog(null));
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请先选择一个要修改的检查组！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Integer checkgroupId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Checkgroup checkgroup = checkgroupService.findById(checkgroupId);
            openDialog(checkgroup);
        });
        deleteButton.addActionListener(e -> deleteCheckgroup());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayAssociatedCheckitems();
            }
        });
    }

    private void loadTableData() {
        String query = queryField.getText().trim();
        List<Checkgroup> checkgroups = checkgroupService.findByNameOrCode(query);
        tableModel.setRowCount(0); // Clear existing data
        for (Checkgroup cg : checkgroups) {
            tableModel.addRow(new Object[]{
                    cg.getId(),
                    cg.getCode(),
                    cg.getName(),
                    "0".equals(cg.getSex()) ? "不限" : ("1".equals(cg.getSex()) ? "男" : "女"),
                    cg.getHelpCode(),
                    cg.getRemark(),
                    cg.getAttention()
            });
        }
    }

    private void displayAssociatedCheckitems() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer checkgroupId = (Integer) tableModel.getValueAt(selectedRow, 0);
            List<Checkitem> associatedItems = checkitemService.findByCheckgroupId(checkgroupId);
            if (associatedItems == null || associatedItems.isEmpty()) {
                associatedItemsArea.setText("该检查组未关联任何检查项。");
            } else {
                String text = associatedItems.stream()
                        .map(item -> item.getName() + " (" + item.getCode() + ")")
                        .collect(Collectors.joining("\n"));
                associatedItemsArea.setText(text);
            }
        } else {
            associatedItemsArea.setText("");
        }
    }

    private void deleteCheckgroup() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个要删除的检查组！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "确认删除选中的检查组吗？这将解除其与所有检查项的关联。", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Integer checkgroupId = (Integer) tableModel.getValueAt(selectedRow, 0);
            try {
                checkgroupService.delete(checkgroupId);
                JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openDialog(Checkgroup checkgroup) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        CheckgroupDialog dialog = new CheckgroupDialog((Frame) owner, checkgroup);
        dialog.setVisible(true);
        // After dialog is closed, refresh data
        loadTableData();
    }

    /**
     * Inner class for Add/Edit Dialog
     */
    class CheckgroupDialog extends JDialog {
        private final boolean isEditMode;
        private final Checkgroup currentCheckgroup;

        private final JTextField codeField = new JTextField(20);
        private final JTextField nameField = new JTextField(20);
        private final JTextField helpCodeField = new JTextField(20);
        private final JComboBox<String> sexComboBox = new JComboBox<>(new String[]{"不限", "男", "女"});
        private final JTextArea remarkArea = new JTextArea(3, 20);
        private final JTextArea attentionArea = new JTextArea(3, 20);

        private final List<JCheckBox> checkitemCheckBoxes = new ArrayList<>();
        private final Map<Integer, JCheckBox> checkBoxMap = new HashMap<>();

        public CheckgroupDialog(Frame owner, Checkgroup checkgroup) {
            super(owner, true);
            this.isEditMode = (checkgroup != null);
            this.currentCheckgroup = checkgroup;

            setTitle(isEditMode ? "修改检查组" : "新增检查组");
            setLayout(new BorderLayout(10, 10));
            setSize(500, 600);
            setLocationRelativeTo(owner);

            // --- Form Panel ---
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("代号:"), gbc);
            gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(codeField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("名称:"), gbc);
            gbc.gridx = 1; gbc.gridy = 1; formPanel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("助记码:"), gbc);
            gbc.gridx = 1; gbc.gridy = 2; formPanel.add(helpCodeField, gbc);

            gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("适用性别:"), gbc);
            gbc.gridx = 1; gbc.gridy = 3; formPanel.add(sexComboBox, gbc);

            gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHWEST; formPanel.add(new JLabel("备注:"), gbc);
            gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.BOTH; formPanel.add(new JScrollPane(remarkArea), gbc);

            gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("注意事项:"), gbc);
            gbc.gridx = 1; gbc.gridy = 5; formPanel.add(new JScrollPane(attentionArea), gbc);

            // --- Checkitems Panel ---
            JPanel checkitemsPanel = new JPanel();
            checkitemsPanel.setLayout(new BoxLayout(checkitemsPanel, BoxLayout.Y_AXIS));
            checkitemsPanel.setBorder(new TitledBorder("选择检查项"));
            List<Checkitem> allCheckitems = checkitemService.findAll();
            for (Checkitem item : allCheckitems) {
                JCheckBox checkBox = new JCheckBox(item.getName() + " (" + item.getCode() + ")");
                checkitemCheckBoxes.add(checkBox);
                checkBoxMap.put(item.getId(), checkBox);
                checkitemsPanel.add(checkBox);
            }

            // --- Buttons Panel ---
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton okButton = new JButton("确认");
            JButton cancelButton = new JButton("取消");
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            // --- Add panels to dialog ---
            add(formPanel, BorderLayout.NORTH);
            add(new JScrollPane(checkitemsPanel), BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            // --- Populate data if in edit mode ---
            if (isEditMode) {
                populateFields();
            }

            // --- Add button listeners ---
            okButton.addActionListener(e -> saveCheckgroup());
            cancelButton.addActionListener(e -> dispose());
        }

        private void populateFields() {
            codeField.setText(currentCheckgroup.getCode());
            nameField.setText(currentCheckgroup.getName());
            helpCodeField.setText(currentCheckgroup.getHelpCode());
            sexComboBox.setSelectedIndex(Integer.parseInt(currentCheckgroup.getSex()));
            remarkArea.setText(currentCheckgroup.getRemark());
            attentionArea.setText(currentCheckgroup.getAttention());

            List<Integer> associatedItemIds = checkgroupService.findCheckitemIdsByCheckgroupId(currentCheckgroup.getId());
            for (Integer itemId : associatedItemIds) {
                if (checkBoxMap.containsKey(itemId)) {
                    checkBoxMap.get(itemId).setSelected(true);
                }
            }
        }

        private void saveCheckgroup() {
            // --- Validation (simple) ---
            if (codeField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "代号和名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Collect data ---
            Checkgroup group = isEditMode ? currentCheckgroup : new Checkgroup();
            group.setCode(codeField.getText().trim());
            group.setName(nameField.getText().trim());
            group.setHelpCode(helpCodeField.getText().trim());
            group.setSex(String.valueOf(sexComboBox.getSelectedIndex()));
            group.setRemark(remarkArea.getText().trim());
            group.setAttention(attentionArea.getText().trim());

            List<Integer> selectedCheckitemIds = new ArrayList<>();
            checkBoxMap.forEach((id, checkBox) -> {
                if (checkBox.isSelected()) {
                    selectedCheckitemIds.add(id);
                }
            });
            group.setCheckitemIds(selectedCheckitemIds);

            // --- Call service ---
            try {
                if (isEditMode) {
                    checkgroupService.update(group);
                    JOptionPane.showMessageDialog(this, "修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    checkgroupService.add(group);
                    JOptionPane.showMessageDialog(this, "新增成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                }
                dispose(); // Close dialog on success
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "操作失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}