package com.chuanzhi.health.view;

import com.chuanzhi.health.dao.impl.UserDaoImpl;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;

    public LoginView() {
        userService = new UserService(new UserDaoImpl());
        setTitle("用户登录");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 用户名
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(50, 40, 80, 25);
        add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(130, 40, 165, 25);
        add(usernameField);

        // 密码
        JLabel passwordLabel = new JLabel("密  码:");
        passwordLabel.setBounds(50, 80, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(130, 80, 165, 25);
        add(passwordField);

        // 登录按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(50, 140, 100, 30);
        add(loginButton);

        // 前往注册按钮
        registerButton = new JButton("前往注册");
        registerButton.setBounds(195, 140, 100, 30);
        add(registerButton);

        // 登录按钮事件监听
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginView.this, "用户名和密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = userService.login(username, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginView.this, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 登录成功后，关闭当前登录窗口并打开主框架
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        new MainFrame(user).setVisible(true);
                    });
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 前往注册按钮事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前登录窗口
                new RegisterView().setVisible(true); // 打开注册窗口
            }
        });
    }
}