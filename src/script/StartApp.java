package script;
import javax.swing.JTable;

import client.Client;
import form.*;

/**
 * Класс управления формами на стороне клиента
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
	
	//id_client, x, y, id_sKem,
	/**смс для отправки на сервер */
	private static String messageForSever = "-1";
	
	/**Тип игры пользователя */
	static int typeGame;
		
	/**Имя пользователя */
	static String name = "";
	/** Главная форма */
	static Main mainForm = new Main();
	/** Форма игры с пользователем\пк */
	static Game gameForm;
	/**Строка состояния подключения к серверу */
	static String Status = "Не подключено";
	
	/**
	 * Метод получения текущего статуса
	 * @return статус
	 */
	public static String getStatus() {
		return Status;
	}
	/**
	 * Метод изменения текущего статуса
	 * @param status новый текущий статус
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
	 * Метод получения имени пользователя
	 * @return имя пользователя
	 */
	public static String getName() {
		return name;
	}
	/**
	 * Метод обновления имени пользователя
	 * @param name новое имя пользователя
	 */
	public static void setName(String name) {
		StartApp.name = name;
	}	
	
	/**
	 * Метод вывода главной формы на экран
	 */
	public static void showMainForm() {mainForm.show();}
	/**
	 * Метод скрытия главной формы 
	 */
	public static void hideMainForm() {mainForm.hide();}
	/**
	 * Метод вывода формы с игрой на экран
	 */
	public static void showGameForm() {gameForm.show();}
	/**
	 * Метод скрытия игровой формы
	 */
	public static void hideGameForm() {gameForm.hide();}
	/**
	 * Метод, реализующий создание новой игровой формы
	 */
	public static void newGameForm() {gameForm = new Game(typeGame); setMessageForSever("newArr");}
	
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
	            if (table.getModel().getValueAt(i,j) == "") {
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
	        // Проверка по горизонтали
	        for (int i = 0; i < 3; i++) {
	            if ( table.getModel().getValueAt(i, 0) ==  table.getModel().getValueAt(i,1 ) && table.getModel().getValueAt(i,1 ) == table.getModel().getValueAt(i,2 )
	                    &&  table.getModel().getValueAt(i,0 ) != "") {
	                winner = ""+table.getModel().getValueAt(i,0);
	                break;
	            }
	        }
	 
	        // Проверка по вертикали если победитель пока не найден
	        if (winner == " ") {
	            for (int i = 0; i < 3; i++) {
	                if (table.getModel().getValueAt(0,i) ==  table.getModel().getValueAt(1,i ) && table.getModel().getValueAt(1,i) == table.getModel().getValueAt(2,i )
		                    &&  table.getModel().getValueAt(0,i ) != "") {
	                    winner = ""+table.getModel().getValueAt(0,i );
	                    break;
	                }
	            }
	        }
	 
	        // Проверка главной диагонали если победитель пока не найден
	        if (winner == " ") {
	            if (table.getModel().getValueAt(0,0) ==  table.getModel().getValueAt(1,1 ) && table.getModel().getValueAt(1,1) == table.getModel().getValueAt(2,2 )
	                    &&  table.getModel().getValueAt(1,1) != "") {
	            	winner = ""+table.getModel().getValueAt(1,1);
	            }
	        }
	 
	        // Проверка побочной диагонали если победитель пока не найден
	        if (winner == " ") {
	        	 if (table.getModel().getValueAt(0,2) ==  table.getModel().getValueAt(1,1 ) && table.getModel().getValueAt(1,1) == table.getModel().getValueAt(2,0 )
		                    &&  table.getModel().getValueAt(1,1 ) != "") {
		            	winner = ""+table.getModel().getValueAt(1,1);
		            }
	        }
	        // Возвращаем победителя или пробел, если такового пока нет
	        //System.out.println(winner);
			
	        return winner;
	    }

}
