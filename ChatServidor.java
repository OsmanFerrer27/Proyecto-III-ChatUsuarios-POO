import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class ChatServidor {

	private static String driverDB = "org.postgresql.Driver";
	private static String nombreDB = "usuario_db";
	private static String urlDB = "jdbc:postgresql://localhost:5432/" + nombreDB;
	private static String usuarioDB = "postgres";
	private static String passDB = "osman271201";
	private static Connection conn;
	private static final int PORT = 3000;
	private static List<String> cedula = new ArrayList<String>();
	private static List<PrintWriter> writers = new ArrayList<PrintWriter>();
	private static List<String> mensajes = new ArrayList<>();

	public ChatServidor() throws Exception {
		System.out.println("El servidor del chat esta activo.");

		cargarMensajes();

		ServerSocket listener = new ServerSocket(PORT);
		try {
			while (true) {
				new Handler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}

	private void cargarMensajes() {
		mensajes.clear();
		try {
			Class.forName(driverDB);
			conn = DriverManager.getConnection(urlDB, usuarioDB, passDB);
			if (conn != null) {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from mensajes");
				while (rs.next()) {
					mensajes.add(rs.getString("texto"));
				}
				conn.close();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private static class Handler extends Thread {

		private String cedula2;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public Handler(Socket socket) {
			this.socket = socket;
			conectar();
		}

		public void run() {
			try {

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				while (true) {
					out.println("Insertar Cedula");
					cedula2 = in.readLine();
					if (cedula2 == null) {
						return;
					}
					synchronized (cedula) {
						if (!cedula.contains(cedula2)) {

							if (cedulaExiste(cedula2)) {
								cedula.add(cedula2);
								break;
							} else {
								return;
							}

						}
					}
				}

				out.println("Cedula aceptada");
				for (String msg : mensajes) {
					out.println(msg);
				}
				out.println("end");
				writers.add(out);

				while (true) {
					String input = in.readLine();
					if (input == null || input.equals("exit")) {
						break;
					}

					mensajes.add(cedula2 + ": " + input);
					subirDB(cedula2 + ": " + input);

					for (PrintWriter writer : writers) {
						writer.println("Mensaje " + cedula2 + ": " + input);
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				if (cedula2 != null) {
					cedula.remove(cedula2);
				}
				if (out != null) {
					writers.remove(out);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}

		private void subirDB(String texto) {
			try {
				String query = "Insert into mensajes values('" + texto + "')";
				Statement statement = conn.createStatement();
				statement.executeUpdate(query);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		private boolean cedulaExiste(String cedula) {
			try {

				String query = "Select cedula from usuarios Where cedula = '" + cedula + "'";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);

				return rs.next();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		public void conectar() {
			
			try {
				Class.forName(driverDB);
				conn = DriverManager.getConnection(urlDB, usuarioDB, passDB);
				if (conn != null) {
					System.out.println("Conexion exitosa.");
				}

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
