package form;


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
	private static JTable table;
	boolean isExit = false;
	static boolean isWin = false;
	static int typeGame = 0;
	static int whyGO = 0;
	static JTextPane textPane;
	static JLabel label_1;
	int a = (int) Math.ceil((Math.random()));
	static int iGo = Math.random() > 0.5 ? 1 : 0;
	Hod hod;
	ReadAnswer RA;
	static JButton button;
	JButton button_2;

	// считать ход врага и поставить нолик
	private class ReadAnswer extends Thread {
		@Override
		public void run() {
			try {
				while (!isExit) {
					Thread.sleep(500);

					if (StartApp.firstGo != "") {
						iGo = Boolean.valueOf(StartApp.firstGo) ? 1 : 0;
						StartApp.firstGo = "";
						// whoGo();
					}
					if (StartApp.getAnsX() != "-1" && StartApp.getAnsY() != "-1") {
						if (textPane.getText().equals("!")) {

							StartApp.setAnsX("-1");
							StartApp.setAnsY("-1");
							return;
						}

						table.getModel().setValueAt("O", Integer.parseInt(StartApp.getAnsX()),
								Integer.parseInt(StartApp.getAnsY()));
						textPane.setText("Противник ходит: " + (Integer.parseInt(StartApp.getAnsX()) + 1) + ","
								+ (Integer.parseInt(StartApp.getAnsY()) + 1) + "\n" + textPane.getText());

						StartApp.setAnsX("-1");
						StartApp.setAnsY("-1");
						iGo = 1;
						// whoGo();
						chechWin();

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class Hod extends Thread {
		@Override
		public void run() {
			try {
				while (!isExit && !isWin) {
					Thread.sleep(500);
					System.out.println("iG0 " + iGo);
					if (StartApp.getTypeGame() == StartApp.PC) {
						if (iGo == 1) {
							label_1.setText("Ходит: " + StartApp.getName());
						} else {
							label_1.setText("Ходит: PC");
						}

					} else if (StartApp.getTypeGame() == StartApp.USER) {
						if (iGo == 1) {
							label_1.setText("Ходит: " + StartApp.getName());
							button.setEnabled(true);
						} else {
							label_1.setText("Ходит: " + StartApp.getEnemyName());
							button.setEnabled(false);
						}
						if (StartApp.getEnemyName() == "") {
							label_1.setText("Ожидание соперника");
							button.setEnabled(false);
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Game(int typeGame) {
		isExit = false;
		isWin = false;
		hod = new Hod();
		RA = new ReadAnswer();
		this.typeGame = typeGame;
		setFont(new Font("Dialog", Font.PLAIN, 30));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				doGo();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 30));

		table.setModel(new DefaultTableModel(new Object[][] { { "", "", "" }, { "", "", "" }, { "", "", "" }, },
				new String[] { "New column", "New column", "New column" }));
		table.getColumnModel().getColumn(0).setMinWidth(10);
		table.getColumnModel().getColumn(1).setMinWidth(10);
		table.getColumnModel().getColumn(2).setMinWidth(10);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(67);
		table.setCellSelectionEnabled(false);
		table.setBounds(10, 35, 200, 200);

		// выравнивание строк по центру
		DefaultTableCellRenderer centerRender = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
		centerRender.setHorizontalAlignment(JLabel.CENTER);

		contentPane.add(table);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(270, 35, 162, 200);
		contentPane.add(textPane);

		JLabel label = new JLabel("Cтатус: ");
		label.setBounds(10, 11, 61, 14);
		contentPane.add(label);

		label_1 = new JLabel("Имя того, кто ходит");
		label_1.setBounds(91, 10, 168, 14);
		contentPane.add(label_1);

		// поиск игрока или игра с ним
		JLabel label_2 = new JLabel("История");
		label_2.setBounds(269, 10, 163, 14);
		contentPane.add(label_2);

		button = new JButton("Ходить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// не забыть выбрать ячейку по умолчанию
				if (table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) == ""
						&& table.getSelectedRow() != -1) {
					doGo();
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
				StartApp.setMessageForSever("out");
				StartApp.setEnemyName("");
				isExit = true;

				table.setModel(new DefaultTableModel(new Object[][] { { "", "", "" }, { "", "", "" }, { "", "", "" }, },
						new String[] { "New column", "New column", "New column" }));
			}
		});
		button_2.setBounds(341, 246, 91, 23);
		contentPane.add(button_2);

		if (StartApp.getTypeGame() == StartApp.USER) {
			label_1.setText("Ожидаем подключения");
			StartApp.setMessageForSever("find," + StartApp.getName());
			button.setEnabled(false);
		} else {
			// whoGo();
			if (iGo == 0)
				ansGo();
		}
		hod.start();
		RA.start();
		// RW = new ReadWin();RW.start();

	}

	// ход-ответ ИИ
	public static void ansGo() {
		chechWin();
		if (StartApp.CheckGame(table) == " ") {
			int[] ans = StartApp.answer();
			table.getModel().setValueAt("O", ans[0], ans[1]);
			textPane.setText("PC ходит: " + (ans[0] + 1) + "," + (ans[1] + 1) + "\n" + textPane.getText());
			chechWin();
			iGo = 1;
			// whoGo();
		}
	}

	public static void doGo() {
		if (label_1.getText().equals("Ходит: " + StartApp.getName())&&table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) == ""&&!textPane.getText().contains("!")) {
			table.getModel().setValueAt("X", table.getSelectedRow(), table.getSelectedColumn());
			StartApp.doGo(table.getSelectedRow(), table.getSelectedColumn());
			System.out.println("Ходим по координатам: " + table.getSelectedRow() + "," + table.getSelectedColumn()
					+ "\n" + textPane.getText());
			textPane.setText("Ходим по координатам: " + (table.getSelectedRow() + 1) + ","
					+ (table.getSelectedColumn() + 1) + "\n" + textPane.getText());

			if (StartApp.getTypeGame() == StartApp.PC) {
				ansGo();
			} else if (StartApp.getTypeGame() == StartApp.USER) {
				StartApp.setMessageForSever("play," + table.getSelectedRow() + "," + table.getSelectedColumn());
			}

			iGo = 0;
			// whoGo();
			chechWin();
		}
	}

	/*
	 * // говорим пользователю чей сейчас ход public void whoGo() { //
	 * System.out.println(iGo); if (StartApp.getTypeGame() == StartApp.PC) { if (iGo
	 * == 1) { label_1.setText("Ходит: " + StartApp.getName()); } else {
	 * label_1.setText("Ходит: PC"); }
	 * 
	 * } else if (StartApp.getTypeGame() == StartApp.USER) {
	 * 
	 * if (iGo == 1) { label_1.setText("Ходит: " + StartApp.getName());
	 * button.setEnabled(true); } else { label_1.setText("Ходит: " +
	 * StartApp.getEnemyName()); button.setEnabled(false); }
	 * 
	 * } }
	 */
	public static void chechWin() {
		String check = StartApp.CheckGame(table);
		if (check.equals("X") || check.equals("O") || !StartApp.CanMove(table)) {
			if (check.equals("X"))
				textPane.setText("Поздравляем, вы выйграли!" + "\n" + textPane.getText());
			if (check.equals("O"))
				textPane.setText(
						"Жаль, но вы проиграли. Не отчаивайтесь, повезет в следующий раз!" + "\n" + textPane.getText());
			if (!StartApp.CanMove(table))
				textPane.setText("Интересно вышло, ходы кончились... Еще раз!" + "\n" + textPane.getText());
			// RA.stop();
			isWin = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			button.setEnabled(false);

			// StartApp.setMessageForSever("out");
		}
	}
}
