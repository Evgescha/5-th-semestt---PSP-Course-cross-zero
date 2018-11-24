package script;
import client.Client;
import form.*;

/**
 * ����� ���������� ������� �� ������� �������
 * @author Yauhenii
 *
 */
public final class StartApp {
	public static final int PC = 1;
	public static final int USER = 2;
	public static Client client = new Client();
	static String ipAdress;
	
	public static String getIpAdress() {
		return ipAdress;
	}
	public static void setIpAdress(String iaAdress) {
		StartApp.ipAdress = iaAdress;
	}
	/**��� ��� �������� �� ������ */
	private static String messageForSever = "-1";
	
	/**��� ���� ������������ */
	static int typeGame;
		
	/**��� ������������ */
	static String name = "";
	/** ������� ����� */
	static Main mainForm = new Main();
	/** ����� ���� � �������������\�� */
	static Game gameForm;
	/**������ ��������� ����������� � ������� */
	static String Status = "�� ����������";
	
	/**
	 * ����� ��������� �������� �������
	 * @return ������
	 */
	public static String getStatus() {
		return Status;
	}
	/**
	 * ����� ��������� �������� �������
	 * @param status ����� ������� ������
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
	 * @return ��� ������������
	 */
	public static String getName() {
		return name;
	}
	/**
	 * ����� ���������� ����� ������������
	 * @param name ����� ��� ������������
	 */
	public static void setName(String name) {
		StartApp.name = name;
	}	
	
	/**
	 * ����� ������ ������� ����� �� �����
	 */
	public static void showMainForm() {mainForm.show();}
	/**
	 * ����� ������� ������� ����� 
	 */
	public static void hideMainForm() {mainForm.hide();}
	/**
	 * ����� ������ ����� � ����� �� �����
	 */
	public static void showGameForm() {gameForm.show();}
	/**
	 * ����� ������� ������� �����
	 */
	public static void hideGameForm() {gameForm.hide();}
	/**
	 * �����, ����������� �������� ����� ������� �����
	 */
	public static void newGameForm() {gameForm = new Game(getTypeGame());}

}
