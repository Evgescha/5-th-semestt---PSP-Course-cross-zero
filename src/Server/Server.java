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
	int[][] arr = new int[3][3];
	static int ID = -1;
	static int idEnemy = -1;

	public Serverss(Socket fromclient, int id) throws IOException {
		this.fromclient = fromclient;
		ID = id;
		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		out.println(ID);
		start();
	}

	// void sendMessage(String msg) {
	// for (Serverss vr : Server.serverList) {
	// vr.out.println(msg);
	// }
	// }
	//

	public void printArr() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void run() {
		// new WriteMsg().start();
		try {
			while (true) {
				input = in.readLine();
				System.out.println("В чате пишут: " + input);
				String[] ans = input.split(",");
				if (!ans[1].equals("newArr")) {
					
					int x_, x = Integer.parseInt(ans[1]);
					int y_, y = Integer.parseInt(ans[2]);
					arr[x][y] = 1;
					printArr();

					if (Integer.parseInt(ans[3]) == -1) {

						do {
							x_ = (int) (Math.random() * 3);
							y_ = (int) (Math.random() * 3);
						} while (arr[x_][y_] != 0);
						arr[x_][y_] = 5;
						out.println("-1," + x_ + "," + y_);
					} else {
					}
				} else {
					arr = new int[3][3];
				}
			}
		} catch (IOException e) {
			// System.out.println("Общение скончалось(((");
			System.out.println("Пользователь с ID " + ID + " вышел");
			Server.serverList.remove(this);
			// e.printStackTrace();
		}

	}

}
