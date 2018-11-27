package form;

import java.awt.Button;
import java.awt.EventQueue;
import client.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import script.StartApp;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * Класс главной формы
 * 
 * @author Yauhenii
 *
 */
public class Main extends JFrame {

	private JTextField textField;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private JTextField textField_1;

	/**
	 * Метод обновления статуса подлючения
	 * 
	 * @author Yauhenii
	 *
	 */
	private class ReadStatus extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);
					if(StartApp.getStatus()!=label_1.getText())
					label_1.setText(StartApp.getStatus());
				}
			} catch (Exception e) {
				label_1.setText("Ошибка");
				e.printStackTrace();
			}
		}
	}

	public Main() {
		initialize();
		new ReadStatus().start();
	}

	/**
	 * Initialize the contents of the this.
	 */
	private void initialize() {
		// установка панели, размеров, действия при закрытии
		this.setBounds(100, 100, 311, 249);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		// кнопка и слушатель
		button = new JButton("Подключится к серверу");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// работать лишь при условии что имя введено
				if (!textField.getText().isEmpty()) {
					// Сохранении имени и запуск подключения
					StartApp.setName(textField.getText());
					textField.setEnabled(false);
					button.setEnabled(false);
					button_2.setEnabled(true);
					button_1.setEnabled(true);
					try {
						StartApp.client.main(null);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Ошибка подключения к серверу");
						StartApp.setStatus("Ошибка");
						e.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(null, "Введите ваше имя для подключения");
			}
		});
		button.setBounds(10, 28, 293, 23);
		this.getContentPane().add(button);

		label = new JLabel("Статус: ");
		label.setBounds(10, 51, 65, 34);
		this.getContentPane().add(label);

		label_1 = new JLabel("Не подключено");
		label_1.setBounds(172, 51, 131, 34);
		this.getContentPane().add(label_1);

		label_2 = new JLabel("Ваш ник");
		label_2.setBounds(10, 87, 107, 23);
		this.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setBounds(127, 88, 176, 20);
		this.getContentPane().add(textField);
		textField.setColumns(10);

		label_3 = new JLabel("Выберите вариант игры");
		label_3.setBounds(10, 119, 278, 23);
		this.getContentPane().add(label_3);

		button_1 = new JButton("Игра с компьютером");
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if (label_1.getText() == "Подключено") {
					StartApp.setTypeGame(StartApp.PC);
					StartApp.newGameForm();
					StartApp.showGameForm();
					StartApp.hideMainForm();
					StartApp.setIsGame("one");
				//}
			}
		});
		button_1.setBounds(10, 153, 293, 23);
		this.getContentPane().add(button_1);

		button_2 = new JButton("Игра с человеком");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartApp.setTypeGame(StartApp.USER);
				StartApp.newGameForm();
				StartApp.setIdEnemy(-2);
				StartApp.setIsGame("find");
				StartApp.showGameForm();
				StartApp.hideMainForm();
			}
		});
		button_2.setBounds(10, 187, 293, 23);
		this.getContentPane().add(button_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(127, 4, 176, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblIp = new JLabel("Введите IP:");
		lblIp.setBounds(10, 3, 107, 23);
		getContentPane().add(lblIp);

		// button.setVisible(false);

	}
}
