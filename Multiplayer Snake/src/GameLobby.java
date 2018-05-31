import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLobby implements WindowListener,KeyListener {

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

        Label title = new Label("Snake Game");
        title.setFont(new Font("SansSerif",Font.PLAIN, 30));

        Label userlb = new Label("Please Enter Username:");
        Label passlb = new Label("Please Enter Password:");
        TextField userField = new TextField(15);
        TextField passField = new TextField(15);

        Button login = new Button("Login");
        login.setBounds(165,220,70,30);

        Button btn4 = new Button("4 Players");
        btn4.setBounds(200,220,70,30);

        Button btn100 = new Button("100 Players");
        btn4.setBounds(250,220,70,30);

        login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("Username:\t" + userField.getText() + "\nPassword:\t" + passField.getText());
                frame.dispose();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Game.start();
                    }
                });
                thread.start();
            }
        });

        btn4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("Username:\t" + userField.getText() + "\nPassword:\t" + passField.getText());
                frame.dispose();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Game.start();
                    }
                });
                thread.start();
            }
        });

        btn100.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("Username:\t" + userField.getText() + "\nPassword:\t" + passField.getText());
                frame.dispose();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Game.start();
                    }
                });
                thread.start();
            }
        });

        // set panels
        JPanel labl = new JPanel(new FlowLayout());
        JPanel sel	= new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 50));
        // set panel colors
        labl.setBackground(Color.WHITE);
        sel.setBackground(Color.lightGray);

        // add label to label panel
        labl.add(title);

        // add to sel Panel
        sel.add(userlb);
        sel.add(userField);
        sel.add(passlb);
        sel.add(passField);
        sel.add(login);
        sel.add(btn4);
        sel.add(btn100);

        // add panels to frame
        frame.add(labl,BorderLayout.NORTH);
        frame.add(sel,BorderLayout.CENTER);

        frame.setTitle("Login");
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }
        });

    }


    @Override
    public void windowOpened(WindowEvent e) {
        frame.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosing(WindowEvent e) {

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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
