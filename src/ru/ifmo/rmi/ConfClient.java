package ru.ifmo.rmi;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;
import java.awt.*;

public class ConfClient {

    static JFrame frame;
    static JPanel panel;
    JLabel lbLastName;
    JLabel lbFirstName;
    JLabel lbOrganization;
    JLabel lbReportTheme;
    JLabel lbEmail;
    JTextField txtLastName;
    JTextField txtFirstName;
    JTextField txtOrganization;
    JTextField txtReportTheme;
    JTextField txtEmail;
    JButton submit;

    public ConfClient() {
        frame = new JFrame("Регистрация участника конференции");
        panel = new JPanel();

        panel.setLayout(new GridLayout(5, 2));
        frame.setBounds(100, 100, 400, 200);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lbLastName= new JLabel("Фамилия");
        lbFirstName= new JLabel("Имя");
        lbReportTheme= new JLabel("Темадоклада");
        lbOrganization= new JLabel("Организация");
        lbEmail= new JLabel("Емайл");
        txtLastName= new JTextField(15);
        txtFirstName= new JTextField(15);
        txtOrganization= new JTextField(70);
        txtReportTheme= new JTextField(100);
        txtEmail= new JTextField(15);
        submit= new JButton("Отправить");

        panel.add(lbLastName);
        panel.add(txtLastName);
        panel.add(lbFirstName);
        panel.add(txtFirstName);
        panel.add(lbOrganization);
        panel.add(txtOrganization);
        panel.add(lbReportTheme);
        panel.add(txtReportTheme);
        panel.add(lbEmail);
        panel.add(txtEmail);frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(submit, BorderLayout.SOUTH);
        frame.setVisible(true);
        submit.addActionListener(new ButtonListener());
    }
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                ConfServer server = (ConfServer) Naming.lookup("rmi://localhost/ConfServer");
                RegistrationInfo registrationInfo = new RegistrationInfo(txtFirstName.getText(), txtLastName.getText(),txtOrganization.getText(), txtReportTheme.getText(),txtEmail.getText());
                int count = server.registerConfParticipant(registrationInfo);
                JOptionPane.showMessageDialog(frame,"Регистрациявыполнена успешно"+ "\nКоличество зарегистрированных участников -"+ count + "\nСпасибо за участие");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Ошибка");
                System.out.println(e);

            }
        }
    }
    public static void main(String args[]) {
        new ConfClient();
    }

}
