import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;


public class FramePrincipal extends JFrame {

	private PanelPrincipal primerPanel;
	private Vista2 segundoPanel;

	
	public FramePrincipal() {
		
		primerPanel = new PanelPrincipal(this);
		segundoPanel = new Vista2(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1045, 445);
		setContentPane(primerPanel);
		setLocationRelativeTo(null);

	}

	public void mostrarPanelPrincipal() {
		setContentPane(new PanelPrincipal(this));
	}

	public void mostrarVista2() {
		setContentPane(segundoPanel);
	}

	public void chatVentana() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {

					ChatCliente frame = new ChatCliente();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).run();
		
	}

}