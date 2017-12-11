
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class MainController<boardView> extends JFrame implements ActionListener,
        KeyListener {
    public static final String ACTION_NEW_GAME1 = "newGame1";
    public static final String ACTION_NEW_GAME2 = "newGame2";
    public static final String ACTION_NEW_GAME3 = "newGame3";
    public static final String ACTION_NEW_GAME4 = "newGame4";
    public static final String ACTION_NEW_GAME5 = "newGame5";
    public static final String ACTION_EXIT_GAME = "exitGame";
    public static final String ACTION_ABOUT = "helpAbout";
	private static final String BoxView = null;

    private Mouse mouse;
    private BoardView boardView;
    private java.util.List<Cat> cats;
    private JPanel mainPanel;

    public MainController() {
        initUI();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainController mainController = new MainController();
                mainController.setVisible(true);
            }
        });
    }

 void initUI() {

        setTitle("Cat vs Mouse - Team IMA");
        setSize(380, 460);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(getRootPane());

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

       
        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);

      
        initMenu(menuBar);

       
        addKeyListener(this);

        newGame(1);
    }

   
    private void initMenu(JMenuBar menuBar) {

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        
        JMenuItem newGame1 = new JMenuItem("new game");
        newGame1.setToolTipText("Start level 1");
        newGame1.setActionCommand(ACTION_NEW_GAME1);

        
        JMenuItem newGame2 = new JMenuItem("Level 2");
        newGame2.setToolTipText("Start level 2");
        newGame2.setActionCommand(ACTION_NEW_GAME2);

  
        JMenuItem newGame3 = new JMenuItem("Level 3");
        newGame3.setToolTipText("Start level 3");
        newGame3.setActionCommand(ACTION_NEW_GAME3);

     
        JMenuItem newGame4 = new JMenuItem("Level 4");
        newGame4.setToolTipText("Start level 4");
        newGame4.setActionCommand(ACTION_NEW_GAME4);

      
        JMenuItem newGame5 = new JMenuItem("Level 5");
        newGame5.setToolTipText("Start level 5");
        newGame5.setActionCommand(ACTION_NEW_GAME5);

   
        JMenuItem exitGame = new JMenuItem("Exit (Esc)");
        exitGame.setToolTipText("Exit the game");
        exitGame.setActionCommand(ACTION_EXIT_GAME);

       
        JMenuItem about = new JMenuItem("About");
        about.setToolTipText("About the developers");
        about.setActionCommand(ACTION_ABOUT);

     
        newGame1.addActionListener(this);
        newGame2.addActionListener(this);
        newGame3.addActionListener(this);
        newGame4.addActionListener(this);
        newGame5.addActionListener(this);
        exitGame.addActionListener(this);
        about.addActionListener(this);

       
        fileMenu.add(newGame1);
        fileMenu.add(newGame2);
        fileMenu.add(newGame3);
        fileMenu.add(newGame4);
        fileMenu.add(newGame5);
        fileMenu.add(exitGame);
        helpMenu.add(about);

    }

    private void newGame(int level) {
        if (BoxView != null) {
            mainPanel.remove(boardView);
        }

       
        URL configFileUrl = Utils.class.getResource("levels/" + level);
        Config config = null;
        try {
            config = Utils.getElementsFromFile(configFileUrl.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mouse = config.getMouse();
        cats = config.getCats();

        boardView = new BoardView(config);
        boardView.notifyLivesChanged(mouse.getLives());
        mainPanel.add(boardView, BorderLayout.CENTER);

        for (Cat c : cats) {
            Thread t = new Thread(c);
            t.start();
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(ACTION_NEW_GAME1)) {
            System.out.println("New Game");
            newGame(1);
        } else if (action.equals(ACTION_NEW_GAME2)) {
            System.out.println("Start level 2");
            newGame(2);
        } else if (action.equals(ACTION_NEW_GAME3)) {
            System.out.println("Start level 3");
            newGame(3);
        } else if (action.equals(ACTION_NEW_GAME4)) {
            System.out.println("Start level 4");
            newGame(4);
        } else if (action.equals(ACTION_NEW_GAME5)) {
            System.out.println("Start level 5");
            newGame(5);
        } else if (action.equals(ACTION_EXIT_GAME)) {
            System.out.println("Exit the game");
            System.exit(0);
        } else if (action.equals(ACTION_ABOUT)) {
            System.out.println("Show about dialog");
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
                mouse.move(DIRECTION.NORTH, null);
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
                mouse.move(DIRECTION.SOUTH, null);
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
                mouse.move(DIRECTION.WEST, null);
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
                mouse.move(DIRECTION.EAST, null);
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }

        boardView.notifyDataChanged();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
    }
}