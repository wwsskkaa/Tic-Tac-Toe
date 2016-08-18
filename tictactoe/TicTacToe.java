
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Random;
/*
 * Last Revised:April 17th 2014
 */

public class TicTacToe extends JFrame{
  private Game game;
  static final char space=' ', O='O', X='X';
  private char position[]={  // Game position (space, O, or X)
    space, space, space,
    space, space, space,
    space, space, space};
  private int wins=0, losses=0, draws=0;  // game count by user
  private String choice="";
  private static int count=0;
  private static JProgressBar progress = new JProgressBar();
  private static Timer time;

  // Start the game
  public static void main(String args[]) {
    SplashScreen splash = new SplashScreen(7000);
    splash.showSplashAndExit();
    new TicTacToe();
  }

public static class SplashScreen extends JWindow {
  private int showtime;
  public SplashScreen(int d) {
    showtime = d;
  }
  public void showSplash() {
    JPanel Screen = (JPanel)getContentPane();
    Screen.setBackground(Color.white);
    int width = 600;
    int height =400;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);
    JLabel welcome=new JLabel("Welcome to Tic-Tac-Toe Game!");
    welcome.setForeground(Color.BLUE);
    welcome.setFont(new Font("Sans-Serif",Font.BOLD, 30));
    JPanel instruc=new JPanel(new GridLayout(6,1));
    instruc.setBackground(Color.YELLOW);
    JLabel intro=new JLabel(" This game is created by Shuang Wu:)");
    intro.setFont(new Font("Serif",Font.PLAIN, 19));
    intro.setForeground(Color.GRAY);
    JLabel labelpng = new JLabel(new ImageIcon("R.png"));
    JLabel label1 = new JLabel("   Instructions:");
    JLabel label2 = new JLabel("   Choose X or O first.X goes first.");
    JLabel label3 = new JLabel("   Then try to win the game by getting three in a row on a 3*3 grid.");
    JLabel label4 = new JLabel("   Good Luck!");
    label1.setFont(new Font("Serif",Font.ITALIC, 20));
    label1.setForeground(Color.BLUE);
    label2.setFont(new Font("Sans-Serif",Font.BOLD, 15));
    label3.setFont(new Font("Sans-Serif",Font.BOLD, 15));
    label4.setFont(new Font("Sans-Serif",Font.BOLD, 15));
    instruc.add(intro);
    //instruc.add(labelpng);
    instruc.add(label1);
    instruc.add(label2);
    instruc.add(label3);
    instruc.add(label4);
    progress.setMaximum(50);
    Screen.add(progress,BorderLayout.SOUTH);
    Loading();
    Screen.add(welcome, BorderLayout.NORTH);
    Screen.add(instruc, BorderLayout.CENTER);
    Screen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
    setVisible(true);
    try { Thread.sleep(showtime); } catch (Exception e) {}

   setVisible(false);
  }

  public void Loading() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                count++;
                progress.setValue(count);
                if (count == 800) {
                    time.stop();
                    setVisible(false);
                    return;
                }
            }
        };
        time = new Timer(100, al);
        time.start();
    }
    
  public void showSplashAndExit() {
    showSplash();
}
}
  
  public TicTacToe() {
    super("Bonnie's TicTacToe!");
    
    JPanel biggie= new JPanel();//create the big Panel
        biggie.setLayout(new BorderLayout());//biggie is using BorderLayout
        JPanel north=new JPanel();//create the north Panel which uses GridLayout
        north.setLayout(new GridLayout(2,1));
        JPanel welcome= new JPanel();//create the welcome Panel inside the north Panel
        welcome.setBackground(Color.YELLOW);
        JLabel one=new JLabel("Welcome to the Tic-Tac-Toe game! \nMy name is Bonnie, I am going to play with you!");
        welcome.add(one);
        north.add(welcome);
        JPanel choose =new JPanel();//create the choice Panel inside the north Panel
        choose.setBackground(Color.WHITE);
        choose.setLayout(new FlowLayout());
        JLabel choose1=new JLabel("O or X?   ");
        JButton b1=new JButton("X");
        JButton b2=new JButton("O");
        JOptionPane.showMessageDialog(null,"Please choose X or O before you play the game!");
        Listener listen=new Listener();
        b1.addActionListener(listen);
        b2.addActionListener(listen);
        choose.add(choose1);
        choose.add(b1);
        choose.add(b2);
        north.add(choose);
        this.setFocusable(true);
        biggie.add(north,BorderLayout.NORTH);//add north to the biggie
        add(biggie, BorderLayout.NORTH);
        JPanel bottom=new JPanel();//create the bottom panel which contains copyright info as a lable
        JLabel copyright=new JLabel("Copyright.2014. Shuang Wu. All rights reserved");
        bottom.add(copyright);
        add(bottom,BorderLayout.SOUTH);//add bottom to the biggie to the south.
        Game game=new Game();
        add(game, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setVisible(true);
  }
    
  private class Listener implements ActionListener{
      public void actionPerformed(ActionEvent e)
      {
          Game a=new Game();
          String namebutton=e.getActionCommand();
          if(namebutton.equals("X"))
          {
           choice="X";   
           JOptionPane.showMessageDialog(null,"You chose X, you go first.");
            }
            else if(namebutton.equals("O"))
            {
                choice="O";
                JOptionPane.showMessageDialog(null,"You chose O, I go first.");
               Random rand=new Random();
                
               int r=rand.nextInt(9);
             
              position[r]=X;
                repaint();
            }
        }
    }
    
  // Game is what actually plays and displays the game
  private class Game extends JPanel implements MouseListener {
    private Random random=new Random();
    private int rows[][]={{0,2},{3,5},{6,8},{0,6},{1,7},{2,8},{0,8},{2,6}};
      // Endpoints of the 8 rows in position[] (across, down, diagonally)

    public Game() {
      addMouseListener(this);
    }

    // Redraw the game
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int w=getWidth();
      int h=getHeight();
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0,0,w,h);
      g.setColor(Color.BLACK);
      g.drawLine(0, h/3, w, h/3);
      g.drawLine(0, h*2/3, w, h*2/3);
      g.drawLine(w/3, 0, w/3, h);
      g.drawLine(w*2/3, 0, w*2/3, h);

      // Draw the Os and Xs
      for (int j=0; j<9; j++) {
        double xpos=w*(j%3+0.5)/3.0;
        double ypos=(j/3+0.5)*h/3.0;
        int x=w/8;
        int y=h/8;
        if (position[j]==X) {
          g.setColor(Color.YELLOW);
          g.setFont(new Font("TimesRoman", Font.PLAIN, 200));
          g.drawString("X",(int)(xpos-x), (int)(ypos+y));
        }
        else if(position[j]==O) {
          g.setColor(Color.BLUE);
          g.setFont(new Font("TimesRoman", Font.PLAIN, 250));
          g.drawString("o",(int)(xpos-x), (int)(ypos+y));
        }
      }
    }

    public void mousePressed(MouseEvent e) 
    {}
    public void mouseEntered(MouseEvent e) 
    {}
    public void mouseClicked(MouseEvent e) {
      int getx=e.getX()*3/getWidth();
      int gety=e.getY()*3/getHeight();
      int positionclicked=getx+3*gety;
      if (position[positionclicked]==space) {
        if(choice.equals("O")){
        position[positionclicked]=O;
        repaint();
        ComputerX();
        repaint();
        
        
        }
        else if(choice.equals("X"))
        {
        position[positionclicked]=X;
        repaint();
        ComputerO();
        repaint();
        }
     }
    }
    public void mouseReleased(MouseEvent e) 
    {}
    public void mouseExited(MouseEvent e) 
    {}
    // Computer plays X
    public void ComputerX() {
      // Check if game is over
      if (won(O)){
        newGamewinner0(O);
        }
      else if (isDraw()){
        newGamewinner0(space);
        }
      // Play X, possibly ending the game
      else {
        nextMoveX();
        if (won(X)){
          newGamewinner0(X);
         }
        else if (isDraw()){
          newGamewinnerX(space);
        }
        }}
    
    public void ComputerO() {
      if (won(X))
        newGamewinner0(O);
      else if (isDraw())
        newGamewinner0(space);

      else {
        nextMoveO();
        if (won(O))
          newGamewinner0(X);
        else if (isDraw())
          newGamewinner0(space);
      }
    }
    // Return true if player has won
    public boolean won(char player) {
      for (int i=0; i<8; ++i)
        if (testRow(player, rows[i][0], rows[i][1]))
          return true;
      return false;
    }

    // Has player won in the row from position[a] to position[b]?
    public boolean testRow(char player, int a, int b) {
      return position[a]==player && position[b]==player && position[(a+b)/2]==player;
    }
    // Play X in the best spot
    public void nextMoveX() {
      int r=Row(X);  // complete a row of X and win if possible
      if (r<0)
        r=Row(O);  // or try to prevent O from winning
      if (r<0) {  // move randomly
        do
          r=random.nextInt(9);
        while (position[r]!=space);
      }
      position[r]=X;
    }
    public void nextMoveO() {
      int r=Row(O);  // complete a row of O and win if possible
      if (r<0)
        r=Row(X);  // or try to block O from winning
      if (r<0) {  // otherwise move randomly
        do
          r=random.nextInt(9);
        while (position[r]!=space);
      }
      position[r]=O;
    }

    // Return 0-8 for the position of a blank spot in a row if the
    // other 2 spots are occupied by player, or -1 if no spot exists
    public int Row(char player) {
      for (int i=0; i<8; ++i) {
        int result=checkRow(player, rows[i][0], rows[i][1]);
        if (result>=0)
          return result;
      }
      return -1;
    }
    // If 2 of 3 spots in the row from position[a] to position[b]
    // are occupied by player and the third is blank, then return the
    // index of the blank spot, else return -1.
    public int checkRow(char player, int a, int b) {
      int c=(a+b)/2;  // middle spot
      if (position[a]==player && position[b]==player && position[c]==space)
        return c;
      if (position[a]==player && position[c]==player && position[b]==space)
        return b;
      if (position[b]==player && position[c]==player && position[a]==space)
        return a;
      return -1;
    }
    // Are all 9 spots filled?
   public  boolean isDraw() {
      for (int i=0; i<9; ++i)
        if (position[i]==space)
          return false;
      return true;
    }
    // Start a new game
   public void newGamewinner0(char winner) {
      repaint();
      // Announce result of last game. 
      String result="";
      if (winner==O) {
        ++wins;
        result = "You Win!";
      }
      else if (winner==X) {
        ++losses;
        result = "Oopsy,I won! You will never beat me:)";
      }
      else {
        result = "Tie. Try to beat me:)";
        ++draws;
      }
      JOptionPane.showMessageDialog(null,result);
       int computer=0;
    int user=0;
    int draw=0;
      try{
    Scanner input1=new Scanner(new File("Winningrecord.txt"));
    computer=input1.nextInt();
    user=input1.nextInt();
    draw=input1.nextInt();
    computer+=losses;
    user+=wins;
    draw+=draws;
    input1.close();
    }catch(FileNotFoundException e)
    {}
    JOptionPane.showMessageDialog(null,"This is the Highscore Table: \n\nYou have "+user+ " wins.\nComputer have "+computer+" wins. \nAnd there are "+draw+" draws.\n\nNice to play with you! Meliora! Bye!"
         , "High score Table!", JOptionPane.PLAIN_MESSAGE);
    try{
        Formatter output=new Formatter("Winningrecord.txt");
   output.format("%d%n%d%n%d",computer,user,draw);
    output.close();
    }catch(IOException e)
    {}
    System.exit(0);
    }
   public void newGamewinnerX(char winner){
      repaint();
      String result="";
      if (winner==X) {
        ++wins;
        result = "You Win!";
      }
      else if (winner==0) {
        ++losses;
        result = "Oopsy,I won! You will never beat me:)";
      }
      else {
        result = "Tie. Try to beat me:)";
        ++draws;
      }
    JOptionPane.showMessageDialog(null,result);
    int computer=0;
    int user=0;
    int draw=0;
      try{
    Scanner input1=new Scanner(new File("Winningrecord.txt"));
    computer=input1.nextInt();
    user=input1.nextInt();
    draw=input1.nextInt();
    computer+=losses;
    user+=wins;
    draw+=draws;
    input1.close();
    }catch(FileNotFoundException e)
    {}
    JOptionPane.showMessageDialog(null,"This is the Highscore Table: \n\nYou have "+user+ " wins.\nComputer have "+computer+" wins. \nAnd there are "+draw+" draws.\n\nNice to play with you! Meliora! Bye!"
         , "High score Table!", JOptionPane.PLAIN_MESSAGE);
    try{
    Formatter output=new Formatter("Winningrecord.txt");
    output.format("%d%n%d%n%d",computer,user,draw);
    output.close();
    }catch(IOException e)
    {}
    System.exit(0);
    }

 }  // end inner class Game
}// end class TicTacToe
