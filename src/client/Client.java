package client;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

import script.StartApp;

/**
 * ����� ������ � �������� �� ������� �������
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

	

	// ������ ��� � ����
	private class ReadMsg extends Thread {
		@Override
		public void run() {
			try {
				StartApp.setID(Integer.parseInt(in.readLine()));
				while (true) {
					msgIn = in.readLine(); // ���� ��������� � �������
					System.out.println("� ������� ������ "+msgIn); // ����� ��������� � ������� �� �������
					String[] ans = msgIn.split(",");
					if(ans[0].contains("findYes")) {
						StartApp.firstGo = ans[1];
						StartApp.setEnemyName(ans[2]);
					}else if(ans[0].contains("play")){
						StartApp.setAnsX(ans[1]);
						StartApp.setAnsY(ans[2]);	
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ����������� ��� �� ������
	public class WriteMsg extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);

					if (StartApp.getMessageForSever() != "-1") {
						out.println(StartApp.getMessageForSever());
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
		System.out.println("����� ���������� � �������");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("��� IP ����� (�� �������) - " + IP.getHostAddress());

		Socket fromserver = null;
	

		System.out.println("�����������... ");
		try {
			fromserver = new Socket(addres, portServ);
			in = new BufferedReader(new InputStreamReader(fromserver.getInputStream()));
			out = new PrintWriter(fromserver.getOutputStream(), true);

			new WriteMsg().start();
			new ReadMsg().start();
			System.out.println("���������� �������");
			StartApp.setStatus("����������");
		} catch (Exception ex) {
			System.out.println("�������� �����... ");
			StartApp.setStatus("������");
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