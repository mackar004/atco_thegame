package atco;

import static atco.ATCO.checkPlanes;
import static atco.ATCO.chkpt;
import static atco.ATCO.planes;
import static atco.ATCO.level;
import static atco.ATCO.levelCheckpoint;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author m
 */
public class GUI {

    JFrame frame;
    JPanel menu, steering;
    JTextField trackField = new JTextField("", 5);
    JLabel menuText;
    JButton up;
    JButton down;
    JButton left;
    JButton right;
    JButton send;
    JButton pauseButton;
    JButton play;
    JButton nextLevel;
    JButton exit;

    int delay = 700;
    int currentLevel = 1;
    static boolean pause = false;
    static boolean gameOver = false;

    DrawScreen screen;

    public void createGUI() {

        int counter = 0;

        frame = new JFrame("ATCO v0.9.6.alt");

        steering = new JPanel();
        JPanel trackPanel = new JPanel();
        JPanel altPanel = new JPanel();
        JPanel pausePanel = new JPanel();
        menu = new JPanel();
        screen = new DrawScreen();

        //czas na wygenereowanie wszystkich paneli
        waitHere(delay / 6);

        screen.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                super.mouseClicked(event);
                if (!pause) {
                    for (Plane planeClicked : level) {
                        if (planeClicked.isSelected()) {                 //jeśli wybrany
                            planeClicked.setSelected(false);                                                        //odznaczanie zaznaczonego samolotu w poprzednim kliknięciu
                            if (planeClicked.getNextCheckpoint() < levelCheckpoint.size()) {
                                levelCheckpoint.get(planeClicked.getNextCheckpoint()).setColor(Color.gray);   //odznaczanie checkpointów
                            }
                        }

                        if (planeClicked.getBounds().contains(event.getPoint()) //
                                && planeClicked.isPlayable()) { //pobieranie powierzchni samolotu o ile mona nim sterować
                            if (!planeClicked.isSelected()) {                       //sprawdzanie czy samolot jest wybrany
                                planeClicked.setSelected(true);                     //wybieranie jeśli nie był wybrany
                                if (planeClicked.getNextCheckpoint() < levelCheckpoint.size()) {
                                    levelCheckpoint.get(planeClicked.getNextCheckpoint()).setColor(Color.green);  //kolor dla aktualnego checkpointu
                                }
                            }
                        }
                    }
                    screen.repaint();
                }
            }
        });

        up = new JButton("CLIMB");
        up.addActionListener(new AltButtonListener());
        up.setBackground(Color.WHITE);

        down = new JButton("DESCEND");
        down.addActionListener(new AltButtonListener());
        down.setBackground(Color.WHITE);

        left = new JButton("LEFT");
        left.addActionListener(new TurnButtonListener());
        left.setBackground(Color.WHITE);

        right = new JButton("RIGHT");
        right.addActionListener(new TurnButtonListener());
        right.setBackground(Color.WHITE);

        send = new JButton("SEND");
        send.addActionListener(new SendButtonListener());

        pauseButton = new JButton("PAUSE");
        pauseButton.addActionListener(new PauseButtonListener());
        pauseButton.setBackground(Color.ORANGE);

        play = new JButton("PLAY");
        play.setBounds(25, 80, 250, 50);
        play.setFont(new Font("Arial", Font.BOLD, 18));
        play.addActionListener(new buttonListener());
        play.setBackground(Color.GREEN);

        nextLevel = new JButton("Next Level");
        nextLevel.setBounds(25, 135, 250, 50);
        nextLevel.setFont(new Font("Arial", Font.BOLD, 18));
        nextLevel.addActionListener(new buttonListener());
        nextLevel.setBackground(Color.GREEN);
        nextLevel.setEnabled(false);

        exit = new JButton("EXIT");
        exit.setBounds(25, 190, 250, 50);
        exit.setFont(new Font("Arial", Font.BOLD, 18));
        exit.addActionListener(new buttonListener());
        exit.setBackground(Color.RED);
        exit.setForeground(Color.WHITE);
        //exit.setAlignmentX(exit.CENTER_ALIGNMENT);

        trackPanel.add(left);
        trackPanel.add(trackField);
        trackPanel.add(right);
        altPanel.add(up);
        altPanel.add(down);

        pausePanel.add(pauseButton);

        send.setBackground(Color.YELLOW);
        send.setForeground(Color.BLACK);
        send.setContentAreaFilled(true);
        send.setBorderPainted(true);

        steering.setPreferredSize(new Dimension(800, 50));
        steering.setBackground(new Color(125, 0, 0, 255));
        steering.add(trackPanel);
        steering.add(send);
        steering.add(altPanel);
        steering.add(pausePanel);

        screen.setPreferredSize(new Dimension(800, 800));
        screen.setBackground(Color.BLACK);
        screen.setVisible(true);

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        frame.add(menu);
        frame.add(screen);
        frame.add(steering, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(806, 878);
        frame.setResizable(false);
        frame.setVisible(true);

        menuText = new JLabel("MENU", JLabel.CENTER);
        menuText.setFont(new Font("Arial", Font.BOLD, 30));
        menuText.setBounds(25, 25, 250, 25);

        menu.setPreferredSize(new Dimension(300, 300));
        menu.setLayout(null);
        menu.setBounds(frame.getWidth() / 2 - 150, frame.getHeight() / 2 - 125, 300, 250);
        menu.setBackground(Color.WHITE);
        menu.add(menuText);
        menu.add(play);
        menu.add(nextLevel);
        menu.add(exit);
        menu.setOpaque(true);
        menu.setVisible(false);

        loadLevel();
        while (true) {
            if (checkPlanes.endLevel()) {
                if (gameOver) {
                    menuText.setText("GAME OVER");
                    play.setText("PLAY AGAIN");
                    pauseButton.setEnabled(false);
                    pause(true);
                    currentLevel = 1;
                    loadLevel();
                }
                //przerwa w wyświetlaniu
                waitHere(delay);

                //=========
                //ATCO commands: 
                //plane 1, turn right, track 95
                //plane 3, altitude 100
                //plane 2, altitude 200
                //=========
//                            if (counter == 10) {
//                                planes.planeArray.get(1).setNewTrack(0, 2);
//                                planes.planeArray.get(1).setNewTrack(2, 95);
//                                planes.planeArray.get(3).setNewAlt(100);
//                                planes.planeArray.get(2).setNewAlt(200);
//                                planes.planeArray.get(4).setActive(true);
//                            }
                //plane 2, altitude 100
//            if (counter == 20) {
//                planes.planeArray.get(2).setNewAlt(100);
//            }
//
//            //plane 2, altitude 200
//            if (counter == 40) {
//                planes.planeArray.get(2).setNewAlt(200);
//            }
                if (!pause) {
                    pause(false);
                    counter++;

                    //wyliczenia dla samolotów
                    //Wyliczanie pozycji
                    for (Plane plane : level) {
                        if (plane.isActive()) {
                            plane.setInDanger(false);   //każdy krok to nowe wyliczenia
                            plane.updatePath();
                            plane.nextPosition(plane.getNewTrack(0), plane.getNewTrack(1));
                            plane.nextAltitude(plane.getNewAlt());
                        }
                    }

                    //Sekcja checkerów            
                    for (Plane plane : level) {

                        //Chechkopint checker
                        if (plane.isPlayable()) {
                            if (plane.getNextCheckpoint() < levelCheckpoint.size()) {
                                checkPlanes.checkCheckpoint(plane, levelCheckpoint.get(plane.getNextCheckpoint()));
                                //screen.repaint();
                            }
                        }

                        //Plane checker
                        //wyliczenie odległości do pozostałych samolotów
                        //separacja pionowa 1000ft (305m), pozioma 5Nm (9,25km)
                        for (Plane referencePlane : level) {
                            if (referencePlane.isActive() && !(plane.equals(referencePlane))) {
                                checkPlanes.checkDistance(plane, referencePlane);
                            }
                        }
                    }
                    //koniec wyliczeń dla samolotów

                    //wyświetlenie ekranu
                    screen.repaint();
                } else {
                    pause(true);
                    send.setBackground(Color.WHITE);
                }
            } else {
                pause(true);
                send.setBackground(Color.WHITE);
                nextLevel.setEnabled(true);
                play.setEnabled(false);
                menu.setVisible(true);
                //pause = false;
                //przerwa w wyświetlaniu
                waitHere(delay);
            }
//===============
//end of while!
//===============
        }
    }

    private void pause(boolean param) {
        pause = param;
        left.setEnabled(!param);
        right.setEnabled(!param);
        trackField.setEnabled(!param);
        up.setEnabled(!param);
        down.setEnabled(!param);
        send.setEnabled(!param);

        menu.setVisible(param);
        //nextLevel.setEnabled(false);

        if (param) {
            send.setBackground(Color.WHITE);
        } else {
            send.setBackground(Color.YELLOW);
        }
    }

    private void loadLevel() {
        //////////////
        //WCZYTYWANIE POZIOMU
        //////////////
        level = null;
        try {
            level = planes.loadLevel(currentLevel);
        } catch (IOException ex) {
            Logger.getLogger(DrawScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DrawScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        //////////////
        //WCZYTYWANIE CHECKPOINTÓW
        //////////////
        levelCheckpoint = null;
        try {
            levelCheckpoint = chkpt.loadLevelCheckpoint(currentLevel);
        } catch (IOException ex) {
            Logger.getLogger(DrawScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DrawScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void waitHere(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            System.out.println("--> Wyjątek w sekcji Thread.sleep!");
        }
    }

    private class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = trackField.getText();

            for (Plane planeClicked : level) {
                if (planeClicked.isSelected()) {            //wykonywane tylko dla wybranego samolotu
                    if (text.matches("\\d{1,3}")) {
                        if (Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 360) {
                            planeClicked.setNewTrack(2, Integer.parseInt(trackField.getText()));
                            if (left.getBackground() == Color.RED) {
                                planeClicked.setNewTrack(0, 1);
                            }
                            if (right.getBackground() == Color.RED) {
                                planeClicked.setNewTrack(0, 2);
                            }
                        }
                    }
                    if (down.getBackground() == Color.GREEN && planeClicked.getNewAlt() > 100) {
                        planeClicked.setNewAlt((int) (planeClicked.getNewAlt() - 100));
                    }

                    if (up.getBackground() == Color.GREEN && planeClicked.getNewAlt() < 300) {
                        planeClicked.setNewAlt((int) (planeClicked.getNewAlt() + 100));
                    }
                }
            }
            up.setBackground(Color.WHITE);
            down.setBackground(Color.WHITE);
            left.setBackground(Color.WHITE);
            right.setBackground(Color.WHITE);
            trackField.setText("");
        }
    }

    private class TurnButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == left) {
                if (right.getBackground() == Color.RED) {
                    right.setBackground(Color.WHITE);
                    left.setBackground(Color.RED);
                } else if (left.getBackground() == Color.RED) {
                    left.setBackground(Color.WHITE);
                } else {
                    left.setBackground(Color.RED);
                }
            }
            if (e.getSource() == right) {
                if (left.getBackground() == Color.RED) {
                    left.setBackground(Color.WHITE);
                    right.setBackground(Color.RED);
                } else if (right.getBackground() == Color.RED) {
                    right.setBackground(Color.WHITE);
                } else {
                    right.setBackground(Color.RED);
                }
            }
        }
    }

    private class AltButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == up) {
                if (down.getBackground() == Color.GREEN) {
                    down.setBackground(Color.WHITE);
                    up.setBackground(Color.GREEN);
                } else if (up.getBackground() == Color.GREEN) {
                    up.setBackground(Color.WHITE);
                } else {
                    up.setBackground(Color.GREEN);
                }
            }
            if (e.getSource() == down) {
                if (up.getBackground() == Color.GREEN) {
                    up.setBackground(Color.WHITE);
                    down.setBackground(Color.GREEN);
                } else if (down.getBackground() == Color.GREEN) {
                    down.setBackground(Color.WHITE);
                } else {
                    down.setBackground(Color.GREEN);
                }
            }
        }
    }

    private class PauseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pause = !pause;
            pause(pause);
        }
    }

    private class buttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play) {
                if (gameOver) {
                    menuText.setText("MENU");
                    play.setText("PLAY");
                    pauseButton.setEnabled(true);
                    gameOver = false;
                    waitHere(delay / 6);
                }
                pause(false);
                menu.setVisible(false);
            }
            if (e.getSource() == nextLevel) {
                currentLevel++;
                loadLevel();
                pause(false);
                menu.setVisible(false);
                play.setEnabled(true);
                nextLevel.setEnabled(false);
                screen.repaint();
            }
            if (e.getSource() == exit) {
                System.exit(0);
            }
        }

    }

//===============    
//END OF GUI
//===============
}
