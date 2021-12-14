import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ChatCliente extends JFrame {

	BufferedReader in;
	PrintWriter out;

	private JTextField campoTexto;
	private JTextArea areaMensaje;

	
	public static void main(String[] args) throws IOException {

		ChatCliente frame = new ChatCliente();

	}

	public ChatCliente() throws IOException {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 302);
		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelContenido.setLayout(new BorderLayout(10, 10));
		setContentPane(panelContenido);

		campoTexto = new JTextField();
		panelContenido.add(campoTexto, BorderLayout.NORTH);
		campoTexto.setColumns(10);

		areaMensaje = new JTextArea();
		panelContenido.add(areaMensaje, BorderLayout.CENTER);

		campoTexto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				out.println(campoTexto.getText());
				campoTexto.setText("");
			}
		});
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
               out.println("exit");
            }
        });

		campoTexto.setEditable(false);
		areaMensaje.setEditable(false);

		setVisible(true);

		Socket socket = new Socket("localhost", 3000);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {

						String line = in.readLine();
						if (line == null) {
							dispose();
							break;
						}
						if (line.startsWith("Insertar Cedula")) {
							String cedula = inicioSesion();
							setTitle(cedula);
							out.println(cedula);
						} else if (line.startsWith("Cedula aceptada")) {
							line = in.readLine();
							while (!line.equals("end")) {
								areaMensaje.append(line + "\n");
								line = in.readLine();
							}

							campoTexto.setEditable(true);

						} else if (line.startsWith("Mensaje")) {
							areaMensaje.append(line.substring(8) + "\n");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private String inicioSesion() {
		return JOptionPane.showInputDialog(null, "Cedula:", "Inicio de sesion", JOptionPane.PLAIN_MESSAGE);
	}

}
