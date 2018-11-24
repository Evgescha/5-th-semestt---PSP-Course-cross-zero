package client;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import script.StartApp;

/**
 * Класс работы с клиентом на стороне клиента
 * 
 * @author Yauhenii
 */
public class Client {
	Scanner sc = new Scanner(System.in);
	BufferedReader in = null;
	PrintWriter out = null;
	BufferedReader inu;
	String addres = StartApp.getIpAdress();
	static String msgOut;
	static String msgIn;
	int portServ = 8080;
	static int ID = -1;

	public static int getID() {
		return ID;
	}

	public static void setID(int iD) {
		ID = iD;
	}

	// читаем смс с серв
	private class ReadMsg extends Thread {
		@Override
		public void run() {
			try {
				ID = Integer.parseInt(in.readLine());
				while (true) {
					msgIn = in.readLine(); // ждем сообщения с сервера
					System.out.println(msgIn); // пишем сообщение с сервера на консоль
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// отправлеяем мсм на сервер
	public class WriteMsg extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);

					if (StartApp.getMessageForSever() != "-1") {
						out.println(ID+ "," + StartApp.getMessageForSever());
						StartApp.setMessageForSever("-1");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void Go() throws IOException {
		inu = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Добро подаловать в клиенты");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("Ваш IP адрес (не сервера) - " + IP.getHostAddress());

		Socket fromserver = null;
		// System.out.println("Выберите порт для подключения(по умолчанию 8080)");
		// int portServ = sc.nextInt();
		// System.out.println("Введите IP подключения (127.0.0.1 по умолчанию)");
		// addres = inu.readLine();

		System.out.println("Подключение... ");
		try {
			fromserver = new Socket(addres, portServ);
			in = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
			out = new PrintWriter(fromserver.getOutputStream(), true);

			new WriteMsg().start();
			new ReadMsg().start();
			System.out.println("Подключено успешно ");
			StartApp.setStatus("Подключено");
		} catch (Exception ex) {
			System.out.println("Ошибочка вышла... ");
			fromserver.close();
			in.close();
			out.close();
			inu.close();
		}
	}

	public static void main(String[] args) throws IOException {
		Client cl = new Client();
		cl.Go();
	}
}