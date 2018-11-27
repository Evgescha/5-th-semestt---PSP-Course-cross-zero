package form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import script.StartApp;

import java.awt.Color;
import javax.swing.border.CompoundBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class Game extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static int typeGame = 0;
	static int whyGO = 0;
	static JTextPane textPane;
	int a = (int) Math.ceil((Math.random()));
	ReadWin RW;
	ReadAnswer RA ;
	JButton button;
	JButton button_2;
	
	private class ReadAnswer extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(500);
					if (StartApp.getAnsX() != "-1" && StartApp.getAnsY() != "-1") {
						if (textPane.getText().equals("!")) {
							
							StartApp.setAnsX("-1");
							StartApp.setAnsY("-1");
							return;
						}
							
						table.getModel().setValueAt("O", Integer.parseInt(StartApp.getAnsX()),
								Integer.parseInt(StartApp.getAnsY()));
						textPane.setText("Противник ходит: " + StartApp.getAnsX() + "," + StartApp.getAnsY() + "\n"
								+ textPane.getText());

						StartApp.setAnsX("-1");
						StartApp.setAnsY("-1");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class ReadWin extends Thread {
		@Override
		public void run() {
			try {
				// JOptionPane.showMessageDialog(null, "Поток работает");
				while (true) {
					Thread.sleep(400);
					String check = StartApp.CheckGame(table);
					if (check.equals("X") || check.equals("O") || !StartApp.CanMove(table)) {
						if (check.equals("X"))
							textPane.setText("Поздравляем, вы выйграли!" + "\n" + textPane.getText());
						if (check.equals("O"))
							textPane.setText("Жаль, но вы проиграли. Не отчаивайтесь, повезет в следующий раз!" + "\n"
									+ textPane.getText());
						if (!StartApp.CanMove(table))
							textPane.setText("Интересно вышло, ходы кончились... Еще раз!" + "\n" + textPane.getText());
						RA.stop();
						
						button.setEnabled(false);
						table.removeAll();
						// RA.stop();

						this.stop();

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Game(int typeGame) {

		// if (typeGame == 1) JOptionPane.showMessageDialog(null, "Вы играете с ПК");
		// if (typeGame == 2) JOptionPane.showMessageDialog(null, "Вы играете с Другим
		// пользователем");
		this.typeGame = typeGame;
		setFont(new Font("Dialog", Font.PLAIN, 30));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 30));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] { { "", "", "" }, { "", "", "" }, { "", "", "" }, },
				new String[] { "New column", "New column", "New column" }));
		table.getColumnModel().getColumn(0).setMinWidth(10);
		table.getColumnModel().getColumn(1).setMinWidth(10);
		table.getColumnModel().getColumn(2).setMinWidth(10);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(67);
		table.setCellSelectionEnabled(false);
		// table.
		table.setBounds(10, 35, 200, 200);

		// выравнивание строк по центру
		DefaultTableCellRenderer centerRender = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
		centerRender.setHorizontalAlignment(JLabel.CENTER);

		contentPane.add(table);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(270, 35, 162, 200);
		contentPane.add(textPane);

		JLabel label = new JLabel("Ходит: ");
		label.setBounds(10, 11, 61, 14);
		contentPane.add(label);

		JLabel label_1 = new JLabel("Имя того, кто ходит");
		label_1.setBounds(91, 10, 119, 14);
		contentPane.add(label_1);

		// поиск игрока или игра с ним
		JLabel label_2 = new JLabel("Статус");
		label_2.setBounds(269, 10, 163, 14);
		contentPane.add(label_2);

		button = new JButton("Ходить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) == "") {

					table.getModel().setValueAt("X", table.getSelectedRow(), table.getSelectedColumn());
					
					System.out.println("Ходим по координатам: " + StartApp.getMessageForSever());
					textPane.setText("Ходим по координатам: " + StartApp.getMessageForSever() + "\n" + textPane.getText());
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(StartApp.CheckGame(table)==" ")
					StartApp.setMessageForSever(table.getSelectedRow() + "," + table.getSelectedColumn());
					
					
					
					
					
					
					
					
					
					
				} else
					JOptionPane.showMessageDialog(null, "ячейка заполнена");

				
			}
		});
		button.setBounds(10, 246, 91, 23);
		contentPane.add(button);

		button_2 = new JButton("Назад");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartApp.showMainForm();
				StartApp.hideGameForm();
				
			}
		});
		button_2.setBounds(341, 246, 91, 23);
		contentPane.add(button_2);

		RW = new ReadWin();RW.start();
		RA = new ReadAnswer();RA.start();
	}
}
