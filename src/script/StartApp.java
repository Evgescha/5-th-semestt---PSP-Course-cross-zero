package script;

import javax.swing.JTable;

import client.Client;
import form.*;

/**
 * ����� ���������� ������� �� ������� �������
 * 
 * @author Yauhenii
 *
 */
public final class StartApp {
	public static final int PC = 1;
	public static final int USER = 2;
	public static Client client = new Client();
	static String ipAdress;
	static String ansX = "-1";
	static String ansY = "-1";
	static int idEnemy = -1;
	static String isGame = "one";
	static int ID;
	static int[][] arr = new int[3][3];
	static String enemyName = "";
	public static String firstGo = "";
	// id_client, x, y, id_sKem,
	/** ��� ��� �������� �� ������ */
	private static String messageForSever = "-1";

	/** ��� ���� ������������ */
	static int typeGame;

	/** ��� ������������ */
	static String name = "";
	/** ������� ����� */
	static Main mainForm = new Main();
	/** ����� ���� � �������������\�� */
	static Game gameForm;
	/** ������ ��������� ����������� � ������� */
	static String Status = "�� ����������";

	/**
	 * ����� ��������� �������� �������
	 * 
	 * @return ������
	 */
	public static String getStatus() {
		return Status;
	}

	public static String getEnemyName() {
		return enemyName;
	}

	public static void setEnemyName(String enemyName) {
		StartApp.enemyName = enemyName;
	}

	/**
	 * ����� ��������� �������� �������
	 * 
	 * @param status
	 *            ����� ������� ������
	 */
	public static void setStatus(String status) {
		Status = status;
	}

	public static int getTypeGame() {
		return typeGame;
	}

	public static void setTypeGame(int typeGame) {
		StartApp.typeGame = typeGame;
	}

	public static String getMessageForSever() {
		return messageForSever;
	}

	public static void setMessageForSever(String messageForSever) {
		StartApp.messageForSever = messageForSever;
	}

	/**
	 * ����� ��������� ����� ������������
	 * 
	 * @return ��� ������������
	 */
	public static String getName() {
		return name;
	}

	/**
	 * ����� ���������� ����� ������������
	 * 
	 * @param name
	 *            ����� ��� ������������
	 */
	public static void setName(String name) {
		StartApp.name = name;
	}

	/**
	 * ����� ������ ������� ����� �� �����
	 */
	@SuppressWarnings("deprecation")
	public static void showMainForm() {
		mainForm.show();
	}

	/**
	 * ����� ������� ������� �����
	 */
	@SuppressWarnings("deprecation")
	public static void hideMainForm() {
		mainForm.hide();
	}

	/**
	 * ����� ������ ����� � ����� �� �����
	 */
	@SuppressWarnings("deprecation")
	public static void showGameForm() {
		gameForm.show();
	}

	/**
	 * ����� ������� ������� �����
	 */
	@SuppressWarnings("deprecation")
	public static void hideGameForm() {
		gameForm.hide();
	}

	/**
	 * �����, ����������� �������� ����� ������� �����
	 */
	public static void newGameForm() {
		gameForm = null;
		gameForm = new Game(typeGame);
	}

	public static String getAnsX() {
		return ansX;
	}

	public static void setAnsX(String ansX) {
		StartApp.ansX = ansX;
	}

	public static String getAnsY() {
		return ansY;
	}

	public static void setAnsY(String ansY) {
		StartApp.ansY = ansY;
	}

	public static String getIpAdress() {
		return ipAdress;
	}

	public static void setIpAdress(String iaAdress) {
		StartApp.ipAdress = iaAdress;
	}

	public static int getIdEnemy() {
		return idEnemy;
	}

	public static void setIdEnemy(int idEnemy) {
		StartApp.idEnemy = idEnemy;
	}

	public static int[][] getArr() {
		return arr;
	}

	public static void setArr(int[][] arr) {
		StartApp.arr = arr;
	}

	public static int getID() {
		return ID;
	}

	public static void setID(int iD) {
		ID = iD;
	}

	public static String getIsGame() {
		return isGame;
	}

	public static void setIsGame(String isGame) {
		StartApp.isGame = isGame;
	}

	public static boolean CanMove(JTable table) {
		boolean p = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (table.getModel().getValueAt(i, j) == "") {
					p = true;
					break;
				}
			}
			if (p = true) {
				break;
			}
		}
		return p;
	}

	public static String CheckGame(JTable table) {

		String winner = " ";
		// �������� �� �����������
		for (int i = 0; i < 3; i++) {
			if (table.getModel().getValueAt(i, 0) == table.getModel().getValueAt(i, 1)
					&& table.getModel().getValueAt(i, 1) == table.getModel().getValueAt(i, 2)
					&& table.getModel().getValueAt(i, 0) != "") {
				winner = "" + table.getModel().getValueAt(i, 0);
				break;
			}
		}

		// �������� �� ��������� ���� ���������� ���� �� ������
		if (winner == " ") {
			for (int i = 0; i < 3; i++) {
				if (table.getModel().getValueAt(0, i) == table.getModel().getValueAt(1, i)
						&& table.getModel().getValueAt(1, i) == table.getModel().getValueAt(2, i)
						&& table.getModel().getValueAt(0, i) != "") {
					winner = "" + table.getModel().getValueAt(0, i);
					break;
				}
			}
		}

		// �������� ������� ��������� ���� ���������� ���� �� ������
		if (winner == " ") {
			if (table.getModel().getValueAt(0, 0) == table.getModel().getValueAt(1, 1)
					&& table.getModel().getValueAt(1, 1) == table.getModel().getValueAt(2, 2)
					&& table.getModel().getValueAt(1, 1) != "") {
				winner = "" + table.getModel().getValueAt(1, 1);
			}
		}

		// �������� �������� ��������� ���� ���������� ���� �� ������
		if (winner == " ") {
			if (table.getModel().getValueAt(0, 2) == table.getModel().getValueAt(1, 1)
					&& table.getModel().getValueAt(1, 1) == table.getModel().getValueAt(2, 0)
					&& table.getModel().getValueAt(1, 1) != "") {
				winner = "" + table.getModel().getValueAt(1, 1);
			}
		}
		// ���������� ���������� ��� ������, ���� �������� ���� ���
		// System.out.println(winner);

		return winner;
	}

	public static void doGo(int x, int y) {
		arr[x][y] = 1;
	}

	public static int[] answer() {

		int x, y;
		do {
			x = (int) (Math.random() * 3);
			y = (int) (Math.random() * 3);
		} while (arr[x][y] != 0);
		arr[x][y] = 5;

		int[] ans = new int[2];
		ans[0] = x;
		ans[1] = y;
		return ans;
	}

}
