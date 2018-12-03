package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import script.StartApp;

/**
 * Класс, реализующий логику сервера
 * 
 * @author Yauhenii
 *
 */
class Server {
	static int id = 0;
	static int portServ = 8080;
	public static LinkedList<Serverss> serverList = new LinkedList<>(); // список всех нитей - экземпляров
	// сервера, слушающих каждый своего клиента

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Добро пожаловать на сервер.");
		System.out.println("");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("IP адрес системы - " + IP.getHostAddress());

		// System.out.println("Введите порт для работы с клиентами (8080 по
		// умолчанию)");
		// int portServ = sc.nextInt();

		ServerSocket server = new ServerSocket(portServ);
		System.out.println("Server Started");
		try {
			while (true) {
				// Блокируется до возникновения нового соединения:
				Socket socket = server.accept();
				try {
					serverList.add(new Serverss(socket, id)); // добавить новое соединенние в список
					System.out.println("одключился пользователь с ид " + id);
					id++;
				} catch (IOException e) {
					// Если завершится неудачей, закрывается сокет,
					// в противном случае, нить закроет его:
					socket.close();
				}
			}
		} finally {
			server.close();
		}
	}
}

class Serverss extends Thread {

	String input, output;// строки что приняли и что отправляем
	BufferedReader in = null;
	PrintWriter out = null;
	ArrayList<String> list = new ArrayList<String>();
	Socket fromclient = null;
	String myName = "";
	int ID = -1;
	int idEnemy = -1;
	boolean isSearh = false;
	Serverss thiss = null;

	public Serverss(Socket fromclient, int id) throws IOException {
		thiss = this;
		this.fromclient = fromclient;
		ID = id;
		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		out.println(ID);
		start();
	}

	public void writeMsg(String sms) {
		out.println(sms);
	}

	@Override
	public void run() {
		// new WriteMsg().start();
		try {
			while (true) {
				input = in.readLine();
				System.out.println("В чате пишут: " + input);
				String[] ans = input.split(",");
				// принимаем: поиск, имя, кто ходит
				if (ans[0].contains("find")) {
					boolean findYes = false;
					myName = ans[1];
					for (Serverss serv : Server.serverList) {
						if (serv.isSearh) {
							
							this.idEnemy = Server.serverList.indexOf(serv);
							serv.idEnemy = Server.serverList.indexOf(this);
							serv.isSearh = false;						
							boolean firstHod = false;
							int iGo = Math.random() > 0.5 ? 1 : 0;
							if (iGo == 1)
								firstHod = true;
							else
								firstHod = false;
							out.println("findYes," + firstHod + "," + serv.myName);
							serv.writeMsg("findYes," + !firstHod + "," + myName);
							findYes = true;
							System.out.println("Нашли врагов и направили их друг на друга");
							break;
						}
					}
					if (!findYes) {
						isSearh = true;
						out.println("Ожидаем");
					}
				} else if (ans[0].contains("out")) {
//					if(idEnemy!=-1) {
					//if(Server.serverList.remove(this))System.out.print("Удален из списка ид " + ID);
					idEnemy = -1;					
					//Server.serverList.add(this);
//					}
				} else if (ans[0].contains("play")) {
					System.out.println("Пришла ответка от " + ID);
					if(idEnemy != -1)
					Server.serverList.get(idEnemy).writeMsg(input);

				}

			}
		} catch (IOException e) {
			// System.out.println("Общение скончалось(((");
			System.out.println("Пользователь с ID " + this.ID + " вышел");
			Server.serverList.remove(this);
			// e.printStackTrace();
		}

	}

}
