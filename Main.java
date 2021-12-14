import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {


			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {

						FramePrincipal frame = new FramePrincipal();
						frame.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			try {
				ChatServidor servidor = new  ChatServidor();
			} catch (Exception e) {
				System.out.println(e);
			}
			
	
		
		
	}
}
