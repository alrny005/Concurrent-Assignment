import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLobby implements KeyListener, WindowListener {

    private int height = 600;
    private int width = 600;
    private Frame frame;

    public GameLobby(){
        frame = new Frame();
        this.init();
    }

    public void init() {
        frame.setSize(width + 7, height + 27);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.addWindowListener(this);
        frame.dispose();

        TextField userField = new TextField("Username");
        userField.setBounds(150,150,200,30);
        TextField passField = new TextField("Password");

        passField.setBounds(150,180,200,30);

        Button login = new Button("Login");
        login.setBounds(165,220,70,30);
        login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("Username:\t" + userField.getText() + "\nPassword:\t" + passField.getText());
                frame.setVisible(false);
                //FIX HERE, game window opens but is unresponsive
                Game.start();
            }
        });

        userField.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { }
            public void mousePressed(MouseEvent e) {
                if (userField.getText().equals("Username")) {
                    return;
                }
                userField.setText("");

            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        passField.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                if (passField.getText().equals("Password")) {
                    passField.setText("");
                }
            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        passField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                passField.setEchoChar('*');
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        frame.setLayout(null);
        frame.add(userField);
        frame.add(passField);
        frame.add(login);
        frame.setTitle("Login");
        frame.setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
