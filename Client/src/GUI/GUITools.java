package GUI;

import javax.swing.*;
import java.awt.*;

public class GUITools
{
	/**
	 * Метод определения отступа компонентов от границ слева и справа
	 * @param buttons список кнопок
	 */
	public static void createRecommendedMargin(JButton[] buttons)
	{
		for (int i = 0; i < buttons.length; i++) {
			Insets margin = buttons[i].getMargin();
			margin.left = 12;
			margin.right = 12;
			buttons[i].setMargin(margin);
		}
	}
	/**
	 * Определение компонентам размера самого большого (по ширине) компонента в группе
	 * Метод придания группе компонентов одинаковых размеров (минимальных,
	 * предпочтительных и максимальных).
	 * @param components список компонентов
	 */
	public static void makeSameSize(JComponent[] components)
	{
		// Массив компонентов
		int[] array = new int[components.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = components[i].getPreferredSize().width;
		}
		int maxSizePos = maximumElementPosition(array);
		Dimension maxSize = components[maxSizePos].getPreferredSize();
		// Set same length for components
		for (int i=0; i<components.length; i++) {
			components[i].setPreferredSize(maxSize);
			components[i].setMinimumSize(maxSize);
			components[i].setMaximumSize(maxSize);
		}
	}

	/**
	 * Correct Text Field Size
	 * @param field Text Field witch will be corrected
	 */
	public static void fixTextFieldSize(JTextField field)
	{
		Dimension size = field.getPreferredSize();
		size.width = field.getMaximumSize().width;
		field.setMaximumSize(size);
	}

	private static void TextFieldSettings(JTextField tf){
		tf.setFont(new Font("Dialog", Font.PLAIN, 20));
		tf.setPreferredSize(new Dimension(100, 25));
		tf.setMaximumSize(new Dimension(200, 30));
		tf.setMinimumSize(new Dimension(60, 25));
	}
	/**
	 * Create and set some settings of Text Field
	 * @param len Length
	 * @return created TextField
	 */
	public static JTextField createTField(int len){
		JTextField tf = new JTextField(len);
		TextFieldSettings(tf);
		return tf;
	}

	/**
	 * Create Password Field and use some settings
	 * @param len length
	 * @return created Password Field
	 */
	public static JPasswordField createPField(int len){
		JPasswordField pf = new JPasswordField(len);
		TextFieldSettings(pf);
		return pf;
	}

	/**
	 * Create JButton and use some settings
	 * @param caption
	 * @return
	 */
	public static JButton createButton(String caption){
		JButton singButton      = new JButton(caption);
		singButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		singButton.setFont(new Font("Impact", Font.PLAIN, 18));
		singButton.setMaximumSize(new Dimension(200, 30));
		singButton.setMinimumSize(new Dimension(100, 30));
		singButton.setPreferredSize(new Dimension(100, 30));
		singButton.setBackground(new Color(153,153,153));
//        singButton.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		singButton.setBorderPainted(true);
		return singButton;
	}

	public static void setBackground(JComponent[] components, Color color){
		for (JComponent c: components){
			c.setBackground(color);
		}
	}
	/**
	 * Get max elem position from array
	 * @param array
	 * @return
	 */
	private static int maximumElementPosition(int[] array)
	{
		int maxPos = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array [maxPos])
				maxPos = i;
		}
		return maxPos;
	}
}