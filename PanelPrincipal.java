import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelPrincipal extends JPanel {


	public PanelPrincipal(FramePrincipal mainFrame) {
		
		setSize(1045, 405);
		setBackground(Color.GRAY);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblMenuPrincipal = new JLabel("Menu Principal");
		lblMenuPrincipal.setFont(new Font("Arial", Font.BOLD, 30));
		lblMenuPrincipal.setForeground(Color.WHITE);
		lblMenuPrincipal.setBounds(420, 40, 275, 45);
		add(lblMenuPrincipal);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mainFrame.mostrarVista2();
				
			}
		});
		
		btnEntrar.setFont(new Font("Arial", Font.PLAIN, 20));
		btnEntrar.setBackground(Color.BLACK);
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setBounds(420, 185, 175, 35);
		add(btnEntrar);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Arial", Font.PLAIN, 20));
		btnSalir.setBackground(Color.BLACK);
		btnSalir.setBounds(420, 320, 175, 35);
		add(btnSalir);
		
		
		JButton btnChat = new JButton("Chat");
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chatVentana();
			}
		});
		btnChat.setForeground(Color.WHITE);
		btnChat.setFont(new Font("Arial", Font.PLAIN, 20));
		btnChat.setBackground(Color.BLACK);
		btnChat.setBounds(420, 250, 175, 35);
		add(btnChat);

	}
}

