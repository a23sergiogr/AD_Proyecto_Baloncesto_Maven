package baloncesto;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ClasificacionVentata extends JFrame {

    public ClasificacionVentata(String clasificacion) throws HeadlessException {
        super("Clasificaciones");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        jPanel.setBackground(Color.WHITE);


        Arrays.stream(clasificacion.split("\n")).forEach(s -> {
            JLabel jLabel = new JLabel(s);
            jPanel.add(jLabel);
        });

        add(jPanel);

        setSize(600,400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
