package GUI;

import sun.java2d.windows.GDIRenderer;

import javax.swing.*;
import java.awt.*;
import static GUI.GUITools.*;
import static javax.swing.SpringLayout.*;

/**
 * Class for creation some panels
 * @author Evgeny Gurin
 */
public class PanelsCreator {
    public static JPanel createSingInPanel(JTextField       tfLogin,
                                           JTextField       tfPassword,
                                           JButton          btnOk,
                                           JButton          btnCancel,
                                           String           nLabel,
                                           String           pLabel){

        JLabel          nameLabel       = new JLabel(nLabel);
        nameLabel.setFont(new Font("Impact", Font.PLAIN, 18));

        JLabel          passwrdLabel    = new JLabel(pLabel);
        passwrdLabel.setFont(new Font("Impact", Font.PLAIN, 18));

        JPanel          panel           = new JPanel();
        SpringLayout    springLayout    = new SpringLayout();
        panel.setLayout(springLayout);
        panel.setBorder (BorderFactory.createEmptyBorder(25,25,25,25));

        JPanel      name        = BoxLayoutUtils.createHorizontalPanel();
        name.add(nameLabel);
        name.add(Box.createHorizontalStrut(12));
        name.add(tfLogin);
        // Создание панели для размещения метки и текстового поля пароля
        JPanel      password    = BoxLayoutUtils.createHorizontalPanel();
        password.add(passwrdLabel);
        password.add(Box.createHorizontalStrut(12));
        password.add(tfPassword);
        // Создание панели для размещения кнопок управления
        JPanel      flow                = new JPanel( new FlowLayout( FlowLayout.RIGHT, 0, 0) );
        JPanel      grid                = new JPanel( new GridLayout( 1,2,5,0) );

        GUITools.setBackground(new JComponent[]{flow, grid, password, name, panel}, new Color(0x0084DB));
        grid.add(btnOk);
        grid.add(btnCancel);
        flow.add(grid);
        // Выравнивание вложенных панелей по горизонтали
        BoxLayoutUtils.setGroupAlignmentX(new JComponent[] { name, password, panel, flow },
                Component.CENTER_ALIGNMENT);
        // Выравнивание вложенных панелей по вертикали
        BoxLayoutUtils.setGroupAlignmentY(new JComponent[] { tfLogin, tfPassword, nameLabel, passwrdLabel},
                Component.CENTER_ALIGNMENT);
        // Определение размеров надписей к текстовым полям
        GUITools.makeSameSize(new JComponent[] { nameLabel, passwrdLabel } );
        // Определение стандартного вида для кнопок
        GUITools.createRecommendedMargin(new JButton[] { btnOk, btnCancel } );
        // Устранение "бесконечной" высоты текстовых полей
        GUITools.fixTextFieldSize(tfLogin   );
        GUITools.fixTextFieldSize(tfPassword);

        panel.add(name);
        panel.add(password);
        panel.add(flow);
        //Spring Layout settings
        springLayout.putConstraint(
                NORTH, name, 90,
                NORTH, panel);
        springLayout.putConstraint(
                WEST, name, 40,
                WEST, panel);
        springLayout.putConstraint(
                NORTH, password, 20,
                SOUTH, name);
        springLayout.putConstraint(
                WEST, password, 40,
                WEST, panel);
        springLayout.putConstraint(
                NORTH, flow, 20,
                SOUTH, password);
        springLayout.putConstraint(
                EAST, flow, 0,
                EAST, password);
        return panel;
    }

    public static JPanel createSingInPanel(JTextField       tfLogin,
                                           JPasswordField   tfPassword,
                                           JButton          btnOk,
                                           JButton          btnCancel){
        return createSingInPanel(tfLogin, tfPassword, btnOk, btnCancel,
                "LOGIN:" , "PASSWORD:");
    }

    public static JPanel createRegPanel(JTextField       tfLogin,
                                           JTextField   tfPassword,
                                           JButton          btnOk,
                                           JButton          btnCancel){
        return createSingInPanel(tfLogin, tfPassword, btnOk, btnCancel,
                "LOGIN:", "E-MAIL:");
    }

    public static JPanel createChoosePanel(JComponent[] buttons, JComboBox comboBox){
        JPanel          choosePanel         = new JPanel();
        JLabel          projectLabel        = new JLabel("CARLSON-PROJECT");

        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectLabel.setFont(new Font("Impact", Font.BOLD, 22));

        choosePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets      = new Insets(5, 0, 0, 0);
        constraints.fill        = GridBagConstraints.NONE;
        constraints.ipadx       = 80;
        constraints.gridx       = GridBagConstraints.RELATIVE;      // вторая ячейка таблицы по горизонтали
        constraints.gridy       = GridBagConstraints.RELATIVE;
        constraints.gridwidth   = GridBagConstraints.REMAINDER;
        choosePanel.add(projectLabel, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        for (JComponent button: buttons){
            choosePanel.add(button, constraints);

        }
        GUITools.setBackground(new JComponent[]{buttonPanel, choosePanel}, new Color(0x0084DB));
        comboBox.setBackground(new Color(153, 153, 153));
        constraints.fill = GridBagConstraints.NONE;
        constraints.ipadx = 0;
//        constraints.anchor    = GridBagConstraints.PAGE_END;
        constraints.insets    = new Insets(80, 0, 0, 0);  // граница ячейки по Y
        constraints.gridwidth = 1;    // размер кнопки в 2 ячейки
        choosePanel.add(comboBox, constraints);
        return choosePanel;
    }
}
