package com.chuanzhi.health.view;

import com.chuanzhi.health.dao.impl.UserDaoImpl;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private UserService userService;

    public RegisterView() {
        userService = new UserService(new UserDaoImpl());
        setTitle("用户注册");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 用户名
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 40, 100, 25);
        add(userLabel);
        usernameField = new JTextField(20);
        usernameField.setBounds(150, 40, 165, 25);
        add(usernameField);

        // 密码
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 80, 100, 25);
        add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 80, 165, 25);
        add(passwordField);

        // 确认密码
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordLabel.setBounds(50, 120, 100, 25);
        add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(150, 120, 165, 25);
        add(confirmPasswordField);

        // 姓名
        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(50, 160, 100, 25);
        add(nameLabel);
        nameField = new JTextField(20);
        nameField.setBounds(150, 160, 165, 25);
        add(nameField);

        // 电话
        JLabel phoneLabel = new JLabel("电话:");
        phoneLabel.setBounds(50, 200, 100, 25);
        add(phoneLabel);
        phoneField = new JTextField(20);
        phoneField.setBounds(150, 200, 165, 25);
        add(phoneField);

        // 注册按钮
        registerButton = new JButton("注册");
        registerButton.setBounds(80, 260, 100, 30);
        add(registerButton);

        // 返回登录按钮
        backToLoginButton = new JButton("返回登录");
        backToLoginButton.setBounds(220, 260, 100, 30);
        add(backToLoginButton);

        // 注册按钮事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                char[] confirmPassword = confirmPasswordField.getPassword();
                String name = nameField.getText();
                String phone = phoneField.getText();

                if (username.isEmpty() || password.length == 0 || name.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterView.this, "所有字段均为必填项", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Arrays.equals(password, confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterView.this, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(new String(password));
                user.setUname(name);
                user.setTel(phone);

                boolean success = userService.register(user);

                if (success) {
                    JOptionPane.showMessageDialog(RegisterView.this, "注册成功！");
                    dispose();
                    new LoginView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(RegisterView.this, "注册失败，用户名可能已存在", "注册失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 返回登录按钮事件监听
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
    }
}