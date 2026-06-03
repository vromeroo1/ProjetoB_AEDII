import javax.swing.SwingUtilities;

public class Nome_program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal();
            }
        });
    }
}
