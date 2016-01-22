// Ruta Joshi
// 4/23/12
// Game.java
// This game, Chicken Little Life, asks the main player, a chicken, to
// cross the screen without being hit by pieces of falling sky. If hit,
// the player has to answer biology related questions.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Game extends JApplet
{
     // Variables
     private Image littlechicken, egg, raindrop, acorn, bigchicken, allstuff, greenallstuff, clouds;
     private Container cont;
     private CardLayout cards;
     private JButton play, anotherplay, instructions, helpbutton, newgame;
     private MainScreen main;
     private HelpScreen help;
     private Question question;
     private WonScreen wonscreen;
     private ToolBar1 toolbar1;
     private ToolBar2 toolbar2;
     private ToolBar3 toolbar3;
     private Level1 lev1;
     private Level2 lev2;
     private Level3 lev3;
     private JPanel playscreen1, playscreen2, playscreen3;
     private boolean won, gotitright, feet, play1, play2, play3;
     private Timer timer1, timer2, timer3;
     private int xchic, ychic;
     private JRadioButton ans1, ans2, ans3, ans4;
     //private Chickens chicken;

     // Constructor
     public Game()
     {
             won = gotitright = false;
             feet = true;
             play1 = true;
             play2 = play3 = false;
     }

     // init method
     public void init()
     {
    	     // Initializing all images
             littlechicken = getImage(getDocumentBase(), "littlechicken.jpg");
             WaitForImage (this, littlechicken);
             raindrop = getImage(getDocumentBase(), "raindrop.jpg");
             WaitForImage (this, raindrop);
             acorn = getImage(getDocumentBase(), "acorn.jpg");
             WaitForImage (this, acorn);
             egg = getImage(getDocumentBase(), "egg.jpg");
             WaitForImage (this, egg);
             bigchicken = getImage(getDocumentBase(), "bigchicken.jpg");
             WaitForImage (this, bigchicken);
             allstuff = getImage(getDocumentBase(), "allstuff.jpg");
             WaitForImage (this, allstuff);
             greenallstuff = getImage(getDocumentBase(), "greenallstuff.jpg");
             WaitForImage (this, greenallstuff);
             clouds = getImage(getDocumentBase(), "clouds.jpg");
             WaitForImage (this, clouds);
             
             // Initializing all panels
             cont = this.getContentPane();
             cards = new CardLayout();
             cont.setLayout(cards);

             play = new JButton("Play");
             instructions = new JButton("Instructions");
             helpbutton = new JButton("Help");
             newgame = new JButton("New Game");
             anotherplay = new JButton("Play");

             main = new MainScreen();
             main.setBackground(Color.white);
             cont.add(main, "main");
             cards.show(cont, "main");

             help = new HelpScreen();
             help.setBackground(Color.yellow);
             cont.add(help, "help");

             question = new Question();
             question.setBackground(Color.white);
             cont.add(question, "question");

             wonscreen = new WonScreen(greenallstuff);
             wonscreen.setBackground(Color.green);
             cont.add(wonscreen, "wonscreen");

             toolbar1 = new ToolBar1();
             toolbar1.setBackground(new Color(190,90,130));

             toolbar2 = new ToolBar2();
             toolbar2.setBackground(new Color(190,90,130));

             toolbar3 = new ToolBar3();
             toolbar3.setBackground(new Color(190,90,130));

             lev1 = new Level1(littlechicken, raindrop, clouds);
             lev1.setBackground(Color.cyan);

             lev2 = new Level2(littlechicken, acorn, clouds);
             lev2.setBackground(Color.cyan);

             lev3 = new Level3(littlechicken, egg, clouds);
             lev3.setBackground(Color.cyan);

             playscreen1 = new JPanel();
             playscreen1.setLayout(new BorderLayout());
             playscreen1.add(lev1, BorderLayout.CENTER);
             playscreen1.add(toolbar1, BorderLayout.SOUTH);
             cont.add(playscreen1, "playscreen1");

             playscreen2 = new JPanel();
             playscreen2.setLayout(new BorderLayout());
             playscreen2.add(lev2, BorderLayout.CENTER);
             playscreen2.add(toolbar2, BorderLayout.SOUTH);
             cont.add(playscreen2, "playscreen2");

             playscreen3 = new JPanel();
             playscreen3.setLayout(new BorderLayout());
             playscreen3.add(lev3, BorderLayout.CENTER);
             playscreen3.add(toolbar3, BorderLayout.SOUTH);
             cont.add(playscreen3, "playscreen3");
     }

     public void WaitForImage (JApplet component, Image image)   {
             MediaTracker tracker = new MediaTracker(component);
             try  {
                     tracker.addImage(image, 0);
                     tracker.waitForID(0);
             }
             catch ( InterruptedException e )   {
                     e.printStackTrace();
             }
     }
     // This panel is called whenever the chicken is hit by a piece of falling sky
     class Question extends JPanel implements ActionListener
     {
              //private JRadioButton ans1, ans2, ans3, ans4;
              private JButton done;
              private String q, a, b, c, d, right;
              private ButtonGroup buttons;
              File quests, answers;
              Scanner reader;
              private String [] array;
              private int random;
              private JTextArea asker;
              private boolean yes;
              private JPanel bottom;

              public Question()
              {
                      yes = false;
                      this.setLayout(new GridLayout(5,1));
                      //random = (int)(Math.random()*17);
                      //random = 1;
                      buttons = new ButtonGroup();
                      array = new String[18];
                      /*bottom = new JPanel();
                      bottom.setLayout(new GridLayout(4,1));
                      oldline = 1+random*5;
                      line = oldline; */
                      //loadQuestion();
                      done = new JButton("Done! Check if I'm right.");
                      done.addActionListener(this);

                      asker = new JTextArea();

                      ans1 = new JRadioButton(a);
                      ans1.addActionListener(this);
                      buttons.add(ans1);
                      ans2 = new JRadioButton(b);
                      ans2.addActionListener(this);
                      buttons.add(ans2);
                      ans3 = new JRadioButton(c);
                      ans3.addActionListener(this);
                      buttons.add(ans3);
                      ans4 = new JRadioButton(d);
                      ans4.addActionListener(this);
                      buttons.add(ans4);
                      loadQuestion();
                      ans1.setSelected(false);
                      ans2.setSelected(false);
                      ans3.setSelected(false);
                      ans4.setSelected(false);
                      this.add(asker);
                      this.add(ans1);
                      this.add(ans2);
                      this.add(ans3);
                      this.add(ans4);

              }
              public void loadQuestion()
              {
                      random = (int)(Math.random()*17 +1);
                      System.out.println("random = " + random);
                      ans1.setSelected(false);
                      ans2.setSelected(false);
                      ans3.setSelected(false);
                      ans4.setSelected(false);
                      quests = null;
                      //play1 = true;
                                               quests = new File("justquestions.txt");
                       /*else if(play2)
                               quests = new File("lev2_questions.txt");
                       else if(play3)
                               quests = new File("lev3_questions.txt"); */
                       Scanner reader = null;
                              try
                              {
                                      reader = new Scanner(quests);
                              }
                              catch(FileNotFoundException e)
                              {
                                      System.out.println("File NOT Found!");
                                      String curDir = System.getProperty("user.dir");
                                      System.out.println("\nThe current working directory is: ");
                                      System.out.println(" - " + curDir);
                                      System.exit(1);
                              }
                              while(reader.hasNext())
                              {
                                      for(int i = 1; i < 18; i++)
                                      {
                                          array[i] = reader.nextLine();
                                      }
                              }
                              // Switches the answers that appear as JRadioButtons
                              switch(random)
                              {
                                      case 1:
                                              q = array[1];
                                              a = "The buildup of oxygen";
                                              b = "The breakdown of water";
                                              c = "The breakdown of biomolecules ";
                                              d = "The buildup of water molecules";
                                              right = c;
                                              break;
                                      case 2:
                                              q = array[2];
                                              a = "An ocean";
                                              b = "An estuary";
                                              c = "A river";
                                              d = "A lake";
                                              right = b;
                                              break;
                                      case 3:
                                              q = array[3];
                                              a = "A shark";
                                              b = "A minnow";
                                              c = "A manta ray";
                                              d = "A puffer fish";
                                              right = a;
                                              break;
                                      case 4:
                                          q = array[4];
                                          a = "Septic tank";
                                          b = "Percolation pond";
                                          c = "Reservoir";
                                          d = "Greywater facility";
                                          right = d;
                                              break;
                                      case 5:
                                          q = array[5];
                                          a = "Keeps in greenhouse gases";
                                          b = "Blocks UV radiation";
                                          c = "Creates smog";
                                          d = "Prevents solar panels from functioning properly";
                                          right = b;
                                              break;
                                      case 6:
                                          q = array[6];
                                          a = "They are generally easy to construct.";
                                          b = "They can represent reality precisely.";
                                          c = "They are used when no observations have been made.";
                                          d = "They can help predict outcomes.";
                                          right = d;
                                              break;
                                      case 7:
                                          q = array[7];
                                          a = "lichens, grasses, shrubs, trees";
                                          b = "mosses, grasses, lichens, trees";
                                          c = "grasses, trees, mosses, lichens";
                                          d = "shrubs, grasses, trees, lichens";
                                          right = a;
                                              break;
                                      case 8:
                                          q = array[8];
                                          a = "climatic variation.";
                                          b = "episodic speciation.";
                                          c = "biological diversity.";
                                          d = "geographic isolation.";
                                          right = c;
                                              break;
                                      case 9:
                                          q = array[9];
                                          a = "water intake.";
                                          b = "muscle contractions.";
                                          c = "waste removal.";
                                          d = "nervous impulses.";
                                          right = c;
                                              break;
                                      case 10:
                                          q = array[10];
                                          a = "endocrine and circulatory";
                                          b = "circulatory and respiratory";
                                          c = "respiratory and endocrine";
                                          d = "reproductive and excretory";
                                          right = b;
                                              break;
                                      case 11:
                                          q = array[11];
                                          a = "genetic drift.";
                                          b = "mutation rate.";
                                          c = "natural selection.";
                                          d = "genetic variation.";
                                          right = d;
                                              break;
                                      case 12:
                                          q = array[12];
                                          a = "natural processes that produce genetic diversity.";
                                          b = "natural processes that always affect the phenotype.";
                                          c = "unnatural processes that always affect the phenotype.";
                                          d = "unnatural processes that are harmful to genetic diversity.";
                                          right = a;
                                              break;
                                      case 13:
                                          q = array[13];
                                          a = "herbivores.";
                                          b = "water.";
                                          c = "vegetation. ";
                                          d = "atmosphere.";
                                          right = d;
                                              break;
                                      case 14:
                                          q = array[14];
                                          a = "herbivores";
                                          b = "producers";
                                          c = "parasites and viruses";
                                          d = "fungi and bacteria";
                                          right = d;
                                              break;
                                      case 15:
                                          q = array[15];
                                          a = "TGTCACG";
                                          b = "GUGACAU";
                                          c = "UGUCACG";
                                          d = "CACUGUA";
                                          right = c;
                                              break;
                                      case 16:
                                          q = array[16];
                                          a = "mutates the DNA.";
                                          b = "turns the protein into carbohydrates.";
                                          c = "stops protein formation.";
                                          d = "changes the protein structure.";
                                          right = d;
                                              break;
                                      case 17:
                                          q = array[17];
                                          a = "size of a given amino acid can vary.";
                                          b = "chemical composition of a given amino acid can vary.";
                                          c = "sequence and number of amino acids is different.";
                                          d = "same amino acid can have many different properties.";
                                          right = c;
                                              break;
                                      default: break;
                              }

                              asker.setText(q);
                              ans1.setText(a);
                              ans2.setText(b);
                              ans3.setText(c);
                              ans4.setText(d);
                      }
              // Responds to answering a question - if right, goes back to the playscreen. if wrong, new question appears.
                      public void actionPerformed(ActionEvent e)
                      {
                              if (ans1.isSelected() && (right == a))
                              {
                                      yes = true;
                              }
                              else if(ans2.isSelected() && right == b)
                              {
                                      yes = true;
                              }
                              else if(ans3.isSelected() && right == c)
                              {
                                      yes = true;
                              }
                              else if(ans4.isSelected() && right == d)
                              {
                                      yes = true;
                              }
                              else
                              {
                                      yes = false;
                              }

                              if(yes == false)
                              {
                                  loadQuestion();
                                  repaint();
                                  System.out.println("Yes is false, coming up with new question.");
                              }
                              else // if(yes == true)
                              {
                                  loadQuestion();
                                  if(play1)
                                  {
                                      //lev1.setFalls1();
                                      //timer1.start();
                                      cards.show(cont, "playscreen1");
                                      System.out.println("Going back to playscreen1");
                                      //play1 = true;
                                      lev1.setFalls1();
                                      timer1.start();
                                  }
                                  else if(play2)
                                  {
                                      lev2.setFalls2();
                                      cards.show(cont, "playscreen2");
                                      System.out.println("Going back to playscreen2");
                                      //play2 = true;
                                      //lev2.setFalls2();
                                      timer2.start();
                                  }
                                  else if(play3)
                                  {
                                      lev3.setFalls3();
                                      cards.show(cont, "playscreen3");
                                      System.out.println("Going back to playscreen3");
                                      //play3 = true;
                                      //lev3.setFalls3();
                                      timer3.start();
                                  }
                              }
                      }
                      public void paintComponent(Graphics g)
                      {
                              super.paintComponent(g);
                      }
     }
     // Main home screen panel
     class MainScreen extends JPanel implements ActionListener
     {
             private JLabel nameofgame;
             private JPanel toppart, bottompart;
             private JPanel middleofbottom;
             private JPanel leftofbottom, rightofbottom;
             
             // Constructor
             public MainScreen()
             {
                     this.setLayout(new GridLayout(2,1));
                     play.addActionListener(this);
                     play.setPreferredSize(new Dimension(300,130));
                     play.setFont(new Font("Arial", Font.PLAIN, 48));
                     instructions.addActionListener(this);
                     instructions.setPreferredSize(new Dimension(300,130));
                     instructions.setFont(new Font("Arial", Font.PLAIN, 36));
                     toppart = new Toppart();
                     toppart.setBackground(Color.cyan);
                     toppart.setLayout(new FlowLayout(FlowLayout.CENTER, 5,90));
                     nameofgame = new JLabel("Chicken Little Life");
                     nameofgame.setFont(new Font("Helvetica", Font.BOLD, 105));
                     nameofgame.setForeground(Color.red);
                     toppart.add(nameofgame);
                     this.add(toppart);
                     bottompart = new Bottompart();
                     bottompart.setBackground(Color.red);
                     bottompart.setLayout(new GridLayout(1,3));
                     leftofbottom = new LeftOfBottom(bigchicken);
                     middleofbottom = new JPanel();
                     middleofbottom.setBackground(Color.green);
                     middleofbottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
                     middleofbottom.add(play);
                     middleofbottom.add(instructions);
                     rightofbottom = new RightOfBottom(allstuff);
                     bottompart.add(leftofbottom);
                     bottompart.add(middleofbottom);
                     bottompart.add(rightofbottom);
                     this.add(bottompart);
             }
             // minipanel for the top half of the mainscreen panel
             private class Toppart extends JPanel
             {
                      public void paintComponent(Graphics g)
                      {
                              super.paintComponent(g);
                              Confetti(g);
                      }
                      public void Confetti(Graphics g)
                      {
                              g.setColor(Color.yellow);
                              for(int i = 0; i < 100; i++)
                              {
                              int randx = (int)(Math.random()*1000);
                              int randy = (int)(Math.random()*300);
                              g.fillOval(randx,randy,10,10);
                              }
                       }
             }
             // minipanel for the bottom half of the mainscreen panel
             private class Bottompart extends JPanel
                         {
                               public void paintComponent(Graphics g)
                               {
                                      super.paintComponent(g);
                               }
                         }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
             }
             public void actionPerformed(ActionEvent e)
             {
                     Object source = e.getSource();
                     if(source == play)
                     {
                             xchic = 0;
                             ychic = 415;
                             lev1.setFalls1();
                             cards.show(cont, "playscreen1");
                             play1 = true;
                             timer1.start();
                     }
                     else if(source == instructions)
                             cards.show(cont, "help");
             }
     }
     // minipanel for left side of bottom panel
     class LeftOfBottom extends JPanel
     {
             private Image bigchick;
             public LeftOfBottom(Image bigchicken)
             {
                      bigchick = bigchicken;
                  this.setBackground(Color.magenta);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
                     g.drawImage(bigchicken, 80,30, this);
             }
     }
     // minipanel for right side of bottom panel
     class RightOfBottom extends JPanel
     {
             private Image allchick;
             public RightOfBottom(Image allstuff)
             {
                      allchick = allstuff;
                  this.setBackground(Color.red);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
                     g.drawImage(allstuff, 10,25, this);
             }
     }
     // this panel contains a JTextArea with the instructions for how to avoid the pieces of falling sky.
     class HelpScreen extends JPanel implements ActionListener
     {
             private JLabel hereishelp;
             private JTextArea helpbox;
             private String helptext= "To play Chicken Little Life, get Chicken Little to the other side of the screen/world. The chicken will " +
                                             "have to avoid pieces of the falling sky. In level one, the chicken faces rain, an element of abiotic " +
                                             "nature. If you don't know what that is, you're the chicken. Imagine the shame you bring to chickens " +
                                             "everywhere. Being the natural chicken you are, you will surely be able to answer a few questions about " +
                                             "natural disasters and the environment. In level 2, the chicken shall face falling acorns, elements of " +
                                             "plant life that have the unimaginable power of being able to crush stupid little chickens. If you can't " +
                                             "face the acorns, you need to get a life. Or answer questions about plants and evolution. Getting a life " +
                                             "is usually the better choice. In level three, the chicken faces the greatest fear of all birds - falling " +
                                             "eggs. If you can't face the eggs, the eggs will face you. In other words, your face will become a very " +
                                             "ugly type of plate. You will become a scrambled, messed-up omelet and spend the rest of java class " +
                                             "wondering why you didn't study for the questions about genetics and animal life. If you make it past level " +
                                             "3, you win the game of life. The game of the life of a crazy little chicken who should really listen " +
                                             "in science class. Use the left and right keys to move the chicken. If you get hit, you have to face your " +
                                             "fate through biology. Keep in mind that as soon as you answer these very important questions about life, " +
                                             "the sky will start falling again. Be careful! Good luck, and may your tiny wings someday grow big enough " +
                                             "for you to fly. ";
             private JScrollPane scroller;
             // Constructor
             public HelpScreen()
             {
                     this.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
                     hereishelp = new JLabel("The Sky Is Falling!");
                     hereishelp.setFont(new Font("Arial", Font.BOLD, 42));
                     hereishelp.setForeground(Color.red);
                     this.add(hereishelp);
                     helpbox = new JTextArea(helptext);
                     helpbox.setMargin(new Insets(5,5,5,5));
                     helpbox.setEditable(false);
                     helpbox.setPreferredSize(new Dimension(700,400));
                     helpbox.setFont(new Font("Century Gothic", Font.PLAIN, 16));
                     helpbox.setLineWrap(true);
                     helpbox.setWrapStyleWord(true);
                     scroller = new JScrollPane(helpbox);
                     scroller.setBounds(150, 200, 300, 200);
                     add(scroller);
                     anotherplay.setPreferredSize(new Dimension(80,40));
                     anotherplay.addActionListener(this);
                     this.add(anotherplay);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
             }
             public void actionPerformed(ActionEvent e)
             {
                     xchic = 0;
                     ychic = 415;
                     lev1.setFalls1();
                     cards.show(cont, "playscreen1");
                     play1 = true;
                     timer1.start();
             }
     }
     // Level 1 panel
     class Level1 extends JPanel implements KeyListener
     {
             private Image litchick, raindrops, cloud1;
             //private int xchic, ychic;
             private int xdrop, ydrop1, ydrop3, ydrop5, ydrop7, ydrop9;
             
             // Constructor initializes variables and makes sure that question appears if chicken is hit
             public Level1(Image littlechicken, Image raindrop, Image clouds)
             {
                     xchic = 0;
                     ychic = 415;

                     setFalls1();

                     this.addKeyListener(this);
                     litchick = littlechicken;
                     raindrops = raindrop;
                     cloud1 = clouds;
                     ActionListener action = new ActionListener()
                     {
                         public void actionPerformed(ActionEvent e)
                         {
                             ydrop1+=10; ydrop3+=10; ydrop5+=10; ydrop7+=10; ydrop9+=10;
                             ans1.setSelected(false);
                                 ans2.setSelected(false);
                                 ans3.setSelected(false);
                                 ans4.setSelected(false);
                             if((xchic == 100) && (ydrop1 >= 350))
                                      cards.show(cont, "question");
                             else if((xchic == 300) && (ydrop3 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 500) && (ydrop5 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 700) && (ydrop7 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 900) && (ydrop9 >= 350))
                                                                       cards.show(cont, "question");
                             lev1.repaint();
                         }
                     };
                     timer1 = new Timer(100, action);
             }
             // Sets initial positions of raindrops in sky
             public void setFalls1()
             {
                 xdrop = 100;
                 ydrop1 = 0; ydrop3 = 150;
                 ydrop5 = 50; ydrop7 = 300; ydrop9 = 200;
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
                     g.drawImage(cloud1, 0, 0, this);
                     g.drawImage(litchick, xchic, ychic, this);
                     //drawChick(g);
                     drawRain(g);
                     this.requestFocus();
             }
             // Draws raindrops
             public void drawRain(Graphics g)
             {
                 if(ydrop1 == 540)
                     ydrop1 = 0;
                 g.drawImage(raindrops, xdrop, ydrop1, this);
                 if(ydrop3 == 540)
                     ydrop3 = 0;
                 g.drawImage(raindrops, xdrop+200, ydrop3, this);
                 if(ydrop5 == 540)
                     ydrop5 = 0;
                 g.drawImage(raindrops, xdrop+400, ydrop5, this);
                 if(ydrop7 == 540)
                     ydrop7 = 0;
                 g.drawImage(raindrops, xdrop+600, ydrop7, this);
                 if(ydrop9 == 540)
                     ydrop9 = 0;
                 g.drawImage(raindrops, xdrop+800, ydrop9, this);
             }
             // Moves chicken when left and right keys are pressed
             public void keyPressed(KeyEvent e)
             {
                     int value = e.getKeyCode();
                     if(value == KeyEvent.VK_LEFT && xchic>=100)
                     {
                             xchic-=100;
                     }
                     else if(value == KeyEvent.VK_RIGHT && xchic<=900)
                     {
                             xchic+=100;
                     }
                     if(xchic == 1000)
                     {
                              xchic = 0;
                              ychic = 415;
                              timer1.stop();
                              play1 = false;
                              lev2.setFalls2();
                              cards.show(cont, "playscreen2");
                              play2 = true;
                              timer2.start();
                                         }
                                         else
                              lev1.repaint();
             }
             public void keyReleased(KeyEvent e) {}
             public void keyTyped(KeyEvent e) {}
     }
     
     // Level 2 panel
     class Level2 extends JPanel implements KeyListener
     {
             private Image litchick, acorns, cloud2;
            // private int xchic, ychic;
             private int xdrop, ydrop1, ydrop3, ydrop5, ydrop7, ydrop9;
             
             
             // Constructor initializes variables and makes sure that question appears if chicken is hit
             public Level2(Image littlechicken, Image acorn, Image clouds)
             {
                     xchic = 0;
                     ychic = 415;

                     setFalls2();

                     this.addKeyListener(this);
                     litchick = littlechicken;
                     acorns = acorn;
                     cloud2 = clouds;
                     ActionListener action = new ActionListener()
                     {
                         public void actionPerformed(ActionEvent e)
                         {
                             ydrop1+=10; ydrop3+=10; ydrop5+=10; ydrop7+=10; ydrop9+=10;
                             ans1.setSelected(false);
                                 ans2.setSelected(false);
                             ans3.setSelected(false);
                                 ans4.setSelected(false);
                             if((xchic == 100) && (ydrop1 >= 350))
                                      cards.show(cont, "question");
                             else if((xchic == 300) && (ydrop3 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 500) && (ydrop5 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 700) && (ydrop7 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 900) && (ydrop9 >= 350))
                                                                       cards.show(cont, "question");
                             lev2.repaint();
                         }
                     };
                     timer2 = new Timer(50, action);
                     //timer1.start();
             }
             // Sets initial positions of acorns in the sky
             public void setFalls2()
             {
                 xdrop = 100;
                 ydrop1 = 250; ydrop3 = 50;
                 ydrop5 = 200; ydrop7 = 150; ydrop9 = 250;
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
                     g.drawImage(cloud2, 0, 0, this);
                     g.drawImage(litchick, xchic, ychic, this);
                     //drawChick(g);
                     drawAcorn(g);
                     this.requestFocus();
             }
             // Draws acorns 
             public void drawAcorn(Graphics g)
             {
                 if(ydrop1 == 540)
                     ydrop1 = 0;
                 g.drawImage(acorns, xdrop, ydrop1, this);
                 if(ydrop3 == 540)
                     ydrop3 = 0;
                 g.drawImage(acorns, xdrop+200, ydrop3, this);
                 if(ydrop5 == 540)
                     ydrop5 = 0;
                 g.drawImage(acorns, xdrop+400, ydrop5, this);
                 if(ydrop7 == 540)
                     ydrop7 = 0;
                 g.drawImage(acorns, xdrop+600, ydrop7, this);
                 if(ydrop9 == 540)
                     ydrop9 = 0;
                 g.drawImage(acorns, xdrop+800, ydrop9, this);
             }
              
             // Moves chicken when left and right keys are pressed
             public void keyPressed(KeyEvent e)
             {
                     int value = e.getKeyCode();
                     if(value == KeyEvent.VK_LEFT && xchic>=100)
                     {
                             xchic-=100;
                     }
                     else if(value == KeyEvent.VK_RIGHT && xchic<=900)
                     {
                             xchic+=100;
                     }
                     if(xchic == 1000)
                     {
                              xchic = 0;
                          ychic = 415;
                          timer2.stop();
                          play2 = false;
                                  lev3.setFalls3();
                              cards.show(cont, "playscreen3");
                              play3 = true;
                              timer3.start();
                                         }
                                         else
                              lev2.repaint();
             }
             public void keyReleased(KeyEvent e) {}
             public void keyTyped(KeyEvent e) {}
     }
     
     // Level 3 panel
     class Level3 extends JPanel implements KeyListener
     {
             private Image litchick, eggs, cloud3;
             //private int xchic, ychic;
             private int xdrop, ydrop1, ydrop3, ydrop5, ydrop7, ydrop9;
             
          // Constructor initializes variables and makes sure that question appears if chicken is hit
             public Level3(Image littlechicken, Image egg, Image clouds)
             {
                     xchic = 0;
                     ychic = 415;

                     setFalls3();

                     this.addKeyListener(this);
                     litchick = littlechicken;
                     eggs = egg;
                     cloud3 = clouds;
                     ActionListener action = new ActionListener()
                     {
                         public void actionPerformed(ActionEvent e)
                         {
                             ydrop1+=10; ydrop3+=10; ydrop5+=10; ydrop7+=10; ydrop9+=10;
                             ans1.setSelected(false);
                                 ans2.setSelected(false);
                                 ans3.setSelected(false);
                                 ans4.setSelected(false);
                             if((xchic == 100) && (ydrop1 >= 350))
                                   cards.show(cont, "question");
                             else if((xchic == 300) && (ydrop3 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 500) && (ydrop5 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 700) && (ydrop7 >= 350))
                                                                       cards.show(cont, "question");
                             else if((xchic == 900) && (ydrop9 >= 350))
                                                                       cards.show(cont, "question");
                             lev3.repaint();
                         }
                     };
                     timer3 = new Timer(25, action);
                     //timer1.start();
             }
             // Sets initial positions of eggs
             public void setFalls3()
             {
                 xdrop = 100;
                 ydrop1 = 150; ydrop3 = 250;
                 ydrop5 = 50; ydrop7 = 200; ydrop9 = 100;
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
                     g.drawImage(cloud3, 0, 0, this);
                     g.drawImage(litchick, xchic, ychic, this);
                     //drawChick(g);
                     drawEgg(g);
                     g.setColor(Color.red);
                     g.fillRect(960, 480, 40, 60);
                     g.setColor(Color.black);
                     g.setFont(new Font("Helvetica", Font.BOLD, 12));
                     g.getFont();
                     String end = new String("END");
                     g.drawString(end, 964, 515);
                     this.requestFocus();
             }
             // Draws eggs
             public void drawEgg(Graphics g)
             {
                 if(ydrop1 == 540)
                     ydrop1 = 0;
                 g.drawImage(eggs, xdrop, ydrop1, this);
                 if(ydrop3 == 540)
                     ydrop3 = 0;
                 g.drawImage(eggs, xdrop+200, ydrop3, this);
                 if(ydrop5 == 540)
                     ydrop5 = 0;
                 g.drawImage(eggs, xdrop+400, ydrop5, this);
                 if(ydrop7 == 540)
                     ydrop7 = 0;
                 g.drawImage(eggs, xdrop+600, ydrop7, this);
                 if(ydrop9 == 540)
                     ydrop9 = 0;
                 g.drawImage(eggs, xdrop+800, ydrop9, this);

             }
             
             // Moves chicken when left and right keys are pressed
             public void keyPressed(KeyEvent e)
             {
                     int value = e.getKeyCode();
                     if(value == KeyEvent.VK_LEFT && xchic>=100)
                     {
                             xchic-=100;
                     }
                     else if(value == KeyEvent.VK_RIGHT && xchic<=900)
                     {
                             xchic+=100;
                     }
                     if(xchic == 1000)
                     {
                              timer3.stop();
                              play3 = false;
                              cards.show(cont, "wonscreen");
                     }
                     else
                              lev3.repaint();
             }
             public void keyReleased(KeyEvent e) {}
             public void keyTyped(KeyEvent e) {}
     }

     // This is the panel that appears when the user comes to the end of level 3. From here, the user can play again or check the instructions.
     class WonScreen extends JPanel implements ActionListener
     {
              private JButton playagain, instructions;
              private Image greenstuff;
              private JLabel youwon;
              
                      public WonScreen(Image greenallstuff)
                      {
                    	  	  greenstuff = greenallstuff;  
                              this.setLayout(null);
                              playagain = new JButton("PLAY AGAIN");
                              playagain.setBounds(600, 250, 350, 90);
                              playagain.addActionListener(this);
                              this.add(playagain);
                              instructions = new JButton("INSTRUCTIONS");
                              instructions.setBounds(600, 350, 350, 90);
                              instructions.addActionListener(this);
                              this.add(instructions);
                              youwon = new JLabel("You Won!!!");
                              youwon.setFont(new Font("Arial", Font.BOLD, 100));
                              youwon.setForeground(Color.white);
                              youwon.setBounds(250, 50, 600, 100);
                              this.add(youwon);
                      }
                      public void paintComponent(Graphics g)
                      {
                              super.paintComponent(g);
                              g.drawImage(greenstuff, 150, 200, this);
                      }
                      public void actionPerformed(ActionEvent e)
                      {
                              Object source = e.getSource();
                              if(source == playagain)
                              {
                                      xchic = 0;
                                      ychic = 415;
                                      lev1.setFalls1();
                                      cards.show(cont, "main");
                                      timer1.start();
                                      play1 = true;
                              }
                              else if(source == instructions)
                              {
                                      cards.show(cont, "help");
                              }
                      }
     }

     // This ToolBar, which actually appears at the bottom of the screen, corresponds to Level 1
     class ToolBar1 extends JPanel implements ActionListener
     {
             public ToolBar1()
             {
                     this.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
                     helpbutton.addActionListener(this);
                     this.add(helpbutton);
                     newgame.addActionListener(this);
                     this.add(newgame);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
             }
             public void actionPerformed(ActionEvent e)
             {
                     Object source = e.getSource();
                     if(source == helpbutton)
                     {
                                 System.out.println("Showing help screen now");
                             cards.show(cont, "help");
                     }
                     else if(source == newgame)
                     {
                             System.out.println("Starting new game now");
                             xchic = 0;
                                 ychic = 415;
                                 lev1.setFalls1();
                             cards.show(cont, "playscreen1");
                             play1 = true;
                             timer1.start();
                     }
             }
     }
  // This ToolBar, which actually appears at the bottom of the screen, corresponds to Level 2
     class ToolBar2 extends JPanel implements ActionListener
     {
                 private JButton helpbutton2, newgame2;
             public ToolBar2()
             {
                         helpbutton2 = new JButton("Help");
                         newgame2 = new JButton("New Game");
                     this.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
                     helpbutton2.addActionListener(this);
                     this.add(helpbutton2);
                     newgame2.addActionListener(this);
                     this.add(newgame2);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
             }
             public void actionPerformed(ActionEvent e)
             {
                     Object source = e.getSource();
                     if(source == helpbutton2)
                     {
                             cards.show(cont, "help");
                     }
                     else if(source == newgame2)
                     {
                    	 System.out.println("Starting new game now");
                             xchic = 0;
                                 ychic = 415;
                                 lev1.setFalls1();
                             cards.show(cont, "playscreen1");
                             play1 = true;
                             timer1.start();
                     }
             }
     }
  // This ToolBar, which actually appears at the bottom of the screen, corresponds to Level 3
     class ToolBar3 extends JPanel implements ActionListener
     {
             private JButton helpbutton3, newgame3;
             public ToolBar3()
             {
                         helpbutton3 = new JButton("Help");
                         newgame3 = new JButton("New Game");
                     this.setLayout(new FlowLayout(FlowLayout.CENTER, 5,5));
                     helpbutton3.addActionListener(this);
                     this.add(helpbutton3);
                     newgame3.addActionListener(this);
                     this.add(newgame3);
             }
             public void paintComponent(Graphics g)
             {
                     super.paintComponent(g);
             }
             public void actionPerformed(ActionEvent e)
             {
                     Object source = e.getSource();
                     if(source == helpbutton3)
                     {
                             cards.show(cont, "help");
                     }
                     else if(source == newgame3)
                     {
                    	 System.out.println("Starting new game now");
                             xchic = 0;
                                 ychic = 415;
                                 lev1.setFalls1();
                             cards.show(cont, "playscreen1");
                             play1 = true;
                             timer1.start();
                     }
             }
     }
}