/*
 * Author: Viktor Jurčić
 * 
 * You have my permission to use this source code for non
 * commercial purposes. All I ask is that You don't claim
 * this source as your own.
 *
 * Thank You!
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.io.*;

public class ConnectTheWords extends JApplet implements ActionListener {
    private static final int W = 480, H = 640, Wk = 80, Hk = 30;
    private static boolean errorHappend=false;
    private static String errorHappendModul;
    
    private static final Font titleF = new Font("SansSerif",Font.BOLD,20);
    private static final Font lableF = new Font("SansSerif",Font.BOLD,15);
    private static final Font wordF = new Font("SansSerif",Font.BOLD,12);
    private static final Font buttonF = new Font("Monospaced",Font.BOLD,17);
    private static final Font livesF = new Font("Serif",Font.BOLD,20);
    private static final Font messageF = new Font("Serif",Font.BOLD,20);
    private static final Font statusF = new Font("SansSerif",Font.BOLD,18);
    
    private static final Color backgroundM=new Color(255,200,160);
    private static final Color backgroundI=new Color(255,250,160);
    private static final Color backgroundK1=new Color(255,50,150);
    private static final Color backgroundK2=new Color(150,150,255);
    
    private Point p;
    
    // For filling the dictionary
    FileInputStream fin;
    BufferedReader br;
    int numOfWordsInDict;
    String line;
    String[] hrv;
    String[] eng;
    int[] wordIndex;
    
    // For status
    FileOutputStream fout;
    PrintStream ps;
    int[] status;
    String[] statusText;
    
    // Chosen words
    int[] wordsChosen;
    
    // Main panels
    Container content;
    JPanel start, difficulty, language, game, stats,stop;
    
    // Center, South, East , North, West panel & menus
    JPanel startC, startCI, startS, startN, startE, startW;
    JPanel difficultyC, difficultyCI, difficultyS, difficultyN, difficultyE, difficultyW;
    JPanel languageC, languageCI, languageS, languageN, languageE, languageW;
    JPanel gameC, gameCI, gameS, gameN, gameE, gameW;
    JPanel statsC, statsCI, statsS, statsN, statsE, statsW;

    // Aditional panels
    JPanel startMenu, difficultyMenu, languageMenu, gameMenu, statsMenu, gameDone, gameExit;
    game space;

    // Buttons by panels
    JButton gamej, settings, stat; //start
    JButton easy, normal, hard, returnP; //difficulty
    JButton languageEH, returnT; //language
    JButton exitP, next, giveup, newGame; //game
    JButton returnSP; //stats
    
    // Label strings
    String title = "Connect The Words";
    String victory = "Victory!!!";
    String loss = "You lost...";
    
    // Lables
    Label message;
    Label status1, status2, status3, status4, status5;
    
    // Help variables
    int window, chosenDifficulty, numOfWords, chosenLanguage;
    
    // Game window size
    int spaceHight, spaceWidth;
    
    // Array for saving the state of clicked words
    int[] inside;
    
    // Array for saving state of solved words
    int[] solved;
    int numOfSolved;
    
    // Lives
    int numOfLives;

    // Arrays for word container and lable coordinates
    int[] x1;
    int[] y1;
    int[] x2;
    int[] y2;

    // Space from upper left container corner and pointer possition
    int rx, ry;
    
    // Data of dragged word
    int iT, jT, xT, yT;

    @Override
    public void init() {
               
        setElFont("Button", buttonF);
        numOfWords=0;
        
        wordsChosen = new int[10];
        inside = new int[10];
        solved = new int[10];
        x1 = new int[10];
        x2 = new int[10];
        y1 = new int[10];
        y2 = new int[10];
        status = new int[10];
        statusText = new String[10];
        numOfSolved=0;
        
        message=new Label("->This is an ERROR<-");
        message.setFont(messageF);
        
        status1=new Label("->This is an ERROR<-");
        status1.setFont(statusF);
        status2=new Label("->This is an ERROR<-");
        status2.setFont(statusF);
        status3=new Label("->This is an ERROR<-");
        status3.setFont(statusF);
        status4=new Label("->This is an ERROR<-");
        status4.setFont(statusF);
        status5=new Label("->This is an ERROR<-");
        status5.setFont(statusF);
        
        setSize(new Dimension(W, H));
        
        content = new Container();
        content = this.getContentPane();
        content.setLayout(new FlowLayout());
        
        // Panel and layout init
        start = new JPanel();
        difficulty = new JPanel();
        language = new JPanel();
        game = new JPanel();
        space = new game();
        stats = new JPanel();
        stop = new JPanel();
        
        startC = new JPanel();
        startCI = new JPanel();
        startS = new JPanel();
        startN = new JPanel();
        startE = new JPanel();
        startW = new JPanel();
        
        difficultyC = new JPanel();
        difficultyCI = new JPanel();
        difficultyS = new JPanel();
        difficultyN = new JPanel();
        difficultyE = new JPanel();
        difficultyW = new JPanel();
        
        languageC = new JPanel();
        languageCI = new JPanel();
        languageS = new JPanel();
        languageN = new JPanel();
        languageE = new JPanel();
        languageW = new JPanel();
        
        gameC = new JPanel();
        gameCI = new JPanel();
        gameS = new JPanel();
        gameN = new JPanel();
        gameE = new JPanel();
        gameW = new JPanel();
        
        statsC = new JPanel();
        statsCI = new JPanel();
        statsS = new JPanel();
        statsN = new JPanel();
        statsE = new JPanel();
        statsW = new JPanel();
        
        start.setLayout(new BorderLayout());
        difficulty.setLayout(new BorderLayout());
        language.setLayout(new BorderLayout());
        game.setLayout(new BorderLayout());
        stats.setLayout(new BorderLayout());
        space.setLayout(null);
        
        startC.setLayout(new BoxLayout(startC,BoxLayout.Y_AXIS));
        startCI.setLayout(new FlowLayout());
        startS.setLayout(new FlowLayout());
        startN.setLayout(new FlowLayout());
        startE.setLayout(new FlowLayout());
        startW.setLayout(new FlowLayout());
                
        difficultyC.setLayout(new BoxLayout(difficultyC,BoxLayout.Y_AXIS));
        difficultyCI.setLayout(new FlowLayout());
        difficultyS.setLayout(new FlowLayout());
        difficultyN.setLayout(new FlowLayout());
        difficultyE.setLayout(new FlowLayout());
        difficultyW.setLayout(new FlowLayout());
        
        languageC.setLayout(new BoxLayout(languageC,BoxLayout.Y_AXIS));
        languageCI.setLayout(new FlowLayout());
        languageS.setLayout(new FlowLayout());
        languageN.setLayout(new FlowLayout());
        languageE.setLayout(new FlowLayout());
        languageW.setLayout(new FlowLayout());
        
        gameC.setLayout(new BoxLayout(gameC,BoxLayout.Y_AXIS));
        gameCI.setLayout(new FlowLayout());
        gameS.setLayout(new BoxLayout(gameS,BoxLayout.X_AXIS));
        gameN.setLayout(new FlowLayout());
        gameE.setLayout(new FlowLayout());
        gameW.setLayout(new FlowLayout());
        
        statsC.setLayout(new BoxLayout(statsC,BoxLayout.Y_AXIS));
        statsCI.setLayout(new FlowLayout());
        statsS.setLayout(new FlowLayout());
        statsN.setLayout(new FlowLayout());
        statsE.setLayout(new FlowLayout());
        statsW.setLayout(new FlowLayout());
        
        start.setPreferredSize(new Dimension(W, H));
        language.setPreferredSize(new Dimension(W, H));
        difficulty.setPreferredSize(new Dimension(W, H));
        game.setPreferredSize(new Dimension(W, H));
        stats.setPreferredSize(new Dimension(W, H));

        startMenu = new JPanel();
        difficultyMenu = new JPanel();
        languageMenu = new JPanel();
        gameMenu = new JPanel();
        statsMenu = new JPanel();
        gameDone = new JPanel();
        gameExit = new JPanel();
        
        startMenu.setLayout(new GridLayout(0,1,10,10));
        difficultyMenu.setLayout(new GridLayout(0,1,10,10));
        languageMenu.setLayout(new GridLayout(0,1,10,10));
        gameMenu.setLayout(new FlowLayout());
        statsMenu.setLayout(new GridLayout(0,1,10,10));
        gameDone.setLayout(new FlowLayout());
        gameDone.setPreferredSize(new Dimension(300,100));
        gameExit.setLayout(new BoxLayout(gameExit,BoxLayout.Y_AXIS));
        gameMenu.setPreferredSize(new Dimension(200,200));
        
        start.setBorder(BorderFactory.createLineBorder(Color.black));
        startC.setBorder(BorderFactory.createLineBorder(Color.black));
        startS.setBorder(BorderFactory.createLineBorder(Color.black));
        startN.setBorder(BorderFactory.createLineBorder(Color.black));
        startE.setBorder(BorderFactory.createLineBorder(Color.black));
        startW.setBorder(BorderFactory.createLineBorder(Color.black));
        
        difficulty.setBorder(BorderFactory.createLineBorder(Color.black));
        difficultyC.setBorder(BorderFactory.createLineBorder(Color.black));
        difficultyS.setBorder(BorderFactory.createLineBorder(Color.black));
        difficultyN.setBorder(BorderFactory.createLineBorder(Color.black));
        difficultyE.setBorder(BorderFactory.createLineBorder(Color.black));
        difficultyW.setBorder(BorderFactory.createLineBorder(Color.black));
        
        language.setBorder(BorderFactory.createLineBorder(Color.black));
        languageC.setBorder(BorderFactory.createLineBorder(Color.black));
        languageS.setBorder(BorderFactory.createLineBorder(Color.black));
        languageN.setBorder(BorderFactory.createLineBorder(Color.black));
        languageE.setBorder(BorderFactory.createLineBorder(Color.black));
        languageW.setBorder(BorderFactory.createLineBorder(Color.black));
        
        game.setBorder(BorderFactory.createLineBorder(Color.black));
        gameC.setBorder(BorderFactory.createLineBorder(Color.black));
        gameS.setBorder(BorderFactory.createLineBorder(Color.black));
        gameN.setBorder(BorderFactory.createLineBorder(Color.black));
        gameE.setBorder(BorderFactory.createLineBorder(Color.black));
        gameW.setBorder(BorderFactory.createLineBorder(Color.black));
        gameDone.setBorder(BorderFactory.createLineBorder(Color.black));
        gameExit.setBorder(BorderFactory.createLineBorder(Color.red));
        
        stats.setBorder(BorderFactory.createLineBorder(Color.black));
        statsC.setBorder(BorderFactory.createLineBorder(Color.black));
        statsS.setBorder(BorderFactory.createLineBorder(Color.black));
        statsN.setBorder(BorderFactory.createLineBorder(Color.black));
        statsE.setBorder(BorderFactory.createLineBorder(Color.black));
        statsW.setBorder(BorderFactory.createLineBorder(Color.black));
        
        start.setBackground(Color.BLACK);
        startCI.setBackground(backgroundM);
        startMenu.setBackground(backgroundM);
        startS.setBackground(Color.ORANGE);
        startN.setBackground(Color.ORANGE);
        startE.setBackground(Color.RED);
        startW.setBackground(Color.RED);
        
        difficulty.setBackground(Color.BLACK);
        difficultyCI.setBackground(backgroundM);
        difficultyMenu.setBackground(backgroundM);
        difficultyS.setBackground(Color.ORANGE);
        difficultyN.setBackground(Color.ORANGE);
        difficultyE.setBackground(Color.RED);
        difficultyW.setBackground(Color.RED);
        
        language.setBackground(Color.BLACK);
        languageCI.setBackground(backgroundM);
        languageMenu.setBackground(backgroundM);
        languageS.setBackground(Color.ORANGE);
        languageN.setBackground(Color.ORANGE);
        languageE.setBackground(Color.RED);
        languageW.setBackground(Color.RED);
        
        game.setBackground(Color.BLACK);
        gameCI.setBackground(backgroundM);
        gameMenu.setBackground(backgroundM);
        gameS.setBackground(Color.ORANGE);
        gameN.setBackground(Color.ORANGE);
        gameE.setBackground(Color.RED);
        gameW.setBackground(Color.RED);
        gameDone.setBackground(Color.LIGHT_GRAY);
        space.setBackground(backgroundI);
        
        stats.setBackground(Color.BLACK);
        statsCI.setBackground(new Color(255,200,160));
        statsMenu.setBackground(new Color(255,200,160));
        statsS.setBackground(Color.ORANGE);
        statsN.setBackground(Color.ORANGE);
        statsE.setBackground(Color.RED);
        statsW.setBackground(Color.RED);
        
        
        //Button and listener init
        gamej = new JButton("PLAY");
        settings = new JButton("SETTINGS");
        stat = new JButton("STATUS");
        settings.addActionListener(this);
        gamej.addActionListener(this);
        stat.addActionListener(this);
        gamej.setBackground(Color.GREEN);
        stat.setBackground(Color.YELLOW);

        easy = new JButton("EASY");
        normal = new JButton("NORMAL");
        hard = new JButton("HARD");
        returnP = new JButton("Back");
        easy.addActionListener(this);
        normal.addActionListener(this);
        hard.addActionListener(this);
        returnP.addActionListener(this);
        easy.setBackground(Color.GREEN);
        normal.setBackground(Color.YELLOW);
        hard.setBackground(Color.RED);
        returnP.setBackground(Color.LIGHT_GRAY);

        languageEH = new JButton("ENGLISH & CROATIAN");
        returnT = new JButton("Back");
        languageEH.addActionListener(this);
        returnT.addActionListener(this);
        languageEH.setBackground(Color.YELLOW);
        returnT.setBackground(Color.LIGHT_GRAY);

        next = new JButton("NEXT");
        giveup = new JButton("FORFEIT");
        newGame = new JButton("NEW GAME");
        exitP = new JButton("EXIT");
        next.addActionListener(this);
        giveup.addActionListener(this);
        newGame.addActionListener(this);
        exitP.addActionListener(this);
        next.setBackground(Color.GREEN);
        newGame.setBackground(Color.GREEN);
        giveup.setBackground(Color.ORANGE);
        exitP.setBackground(Color.RED);
        
        returnSP = new JButton("BACK");
        returnSP.addActionListener(this);
        returnSP.setBackground(Color.LIGHT_GRAY);
        
        //Adding buttons to panels
        startN.add(lable(title, titleF));
        startMenu.add(gamej);
        //startMenu.add(settings);
        startMenu.add(stat);
        startCI.add(startMenu);
        startC.add(startCI);
        start.add(startC);
        start.add(startS, BorderLayout.SOUTH);
        start.add(startN, BorderLayout.NORTH);
        start.add(startE, BorderLayout.EAST);
        start.add(startW, BorderLayout.WEST);
        
        difficultyN.add(lable(title, titleF));
        difficultyMenu.add(easy);
        difficultyMenu.add(normal);
        difficultyMenu.add(hard);
        difficultyMenu.add(returnP);
        difficultyCI.add(difficultyMenu);
        difficultyC.add(difficultyCI);
        difficulty.add(difficultyC);
        difficulty.add(difficultyS, BorderLayout.SOUTH);
        difficulty.add(difficultyN, BorderLayout.NORTH);
        difficulty.add(difficultyE, BorderLayout.EAST);
        difficulty.add(difficultyW, BorderLayout.WEST);
        
        languageN.add(lable(title, titleF));
        languageMenu.add(languageEH);
        languageMenu.add(returnT);
        languageCI.add(languageMenu);
        languageC.add(languageCI);
        language.add(languageC);
        language.add(languageS, BorderLayout.SOUTH);
        language.add(languageN, BorderLayout.NORTH);
        language.add(languageE, BorderLayout.EAST);
        language.add(languageW, BorderLayout.WEST);
        
        gameN.add(lable(title, titleF));
        gameMenu.add(message);
        gameMenu.add(next);
        gameMenu.add(newGame);
        gameMenu.add(giveup);
        gameExit.add(exitP);
        gameS.add(gameExit);
        gameS.add(gameDone);
        gameC.add(space);
        gameCI.add(gameMenu);
        gameC.add(gameCI);
        game.add(gameC);
        game.add(gameS,BorderLayout.SOUTH);
        game.add(gameN,BorderLayout.NORTH);
        game.add(gameE,BorderLayout.EAST);
        game.add(gameW,BorderLayout.WEST);
        
        statsN.add(lable(title, titleF));
        statsMenu.add(status1);
        statsMenu.add(status2);
        statsMenu.add(status3);
        statsMenu.add(status4);
        statsMenu.add(status5);
        statsMenu.add(returnSP);
        statsCI.add(statsMenu);
        statsC.add(statsCI);
        stats.add(statsC);
        stats.add(statsS, BorderLayout.SOUTH);
        stats.add(statsN, BorderLayout.NORTH);
        stats.add(statsE, BorderLayout.EAST);
        stats.add(statsW, BorderLayout.WEST);
        

        //Adding panels to aplets contentPane
        content.add(start);
        content.add(difficulty);
        content.add(language);
        content.add(game);
        content.add(stats);
        content.add(stop);
        
        //Setting panel visibility
        start.setVisible(false);
        difficulty.setVisible(false);
        language.setVisible(false);
        newGame.setVisible(false);
        next.setVisible(false);
        giveup.setVisible(false);
        gameCI.setVisible(false);
        game.setVisible(false);
        stats.setVisible(false);
        stop.setVisible(false);
        
        try {
            fillDictionary();
        } catch (IOException ex) {
            errorHappend=true;
            errorHappendModul="fillDictionary()";
        }
        try {
            stats(1);
        } catch (IOException ex) {
            try {            
                stats(0);
            } catch (IOException ex1) {
                errorHappend=true;
                errorHappendModul="stats(0)";
            }
        }
        statsInit();
        
    }

    @Override
    public void start() {
        space.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("Called mousePressed");
                p = e.getPoint();
                
                for (int i = 0; i < numOfWords; i++) {
                    if ((p.x >= x1[i]) && (p.x <= x1[i] + Wk) && (p.y >= y1[i]) && (p.y <= y1[i] + Hk)) {
                        inside[i] = 1; iT = i; jT = 1;
                        xT = x1[i]; yT = y1[i];
                        rx = p.x - x1[i]; ry = p.y - y1[i];
                        break;
                    } else {
                        inside[i] = 0;
                    }
                }
                if(inside[iT]==0){
                    for (int i = 0; i < numOfWords; i++) {
                        if ((p.x >= x2[i]) && (p.x <= x2[i] + Wk) && (p.y >= y2[i]) && (p.y <= y2[i] + Hk)) {
                            inside[i] = 1; iT = i; jT = 2;
                            xT = x2[i]; yT = y2[i];
                            rx = p.x - x2[i]; ry = p.y - y2[i];
                            break;
                        } else {
                            inside[i] = 0;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                //System.out.println("Called mouseReleased");
                if(inside[iT]==1)
                {
                    if((x1[iT]-x2[iT]>(-Wk) && x1[iT]-x2[iT]<Wk) && (y1[iT]-y2[iT]>-Hk && y1[iT]-y2[iT]<Hk)){
                        solved[iT]=1; numOfSolved++; space.printSolved(iT);
                        space.check();
                        
                    }else{
                        if(jT==1){x1[iT]=xT; y1[iT]=yT;}
                        else{x2[iT]=xT; y2[iT]=yT;}
                        space.lifeLost();
                    }
                    repaint();
                }
                
            }
        });
        space.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //System.out.println("Called mouseDragged");
                p = e.getPoint();
                if (inside[iT] == 1) {
                    int kx = p.x - rx;
                    int ky = p.y - ry;
                    spaceHight = space.getHeight();
                    spaceWidth = space.getWidth();
                    if(jT==1){
                        if(kx>0 && kx<spaceWidth-Wk){x1[iT] = kx;}
                        if(ky>0 && ky<spaceHight-Hk){y1[iT] = ky;}
                        //repaint(x1[iT],y1[iT],Wk,Hk);
                    }else{
                        if(kx>0 && kx<spaceWidth-Wk){x2[iT] = kx;}
                        if(ky>0 && ky<spaceHight-Hk){y2[iT] = ky;}
                        //repaint(x2[iT],y2[iT],Wk,Hk);
                    }
                    repaint();
                }
            }
        });
        
        if(!errorHappend){
            window = 1;start.setVisible(true);
        }else{
            stop.add(new Label("Error happened at: "+errorHappendModul));
            stop.setVisible(true);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
    }
        
    //*************************** DICTIONARY FILLING ***************************
    public void fillDictionary() throws FileNotFoundException, IOException{
            fin = new FileInputStream("hrv.txt");
            br = new BufferedReader(new InputStreamReader(fin));

            line=br.readLine();
            numOfWordsInDict=Integer.parseInt(line);
            System.out.println("Words in dictionary: "+numOfWordsInDict);
            
            hrv = new String[numOfWordsInDict];
            eng = new String[numOfWordsInDict];
            wordIndex = new int[numOfWordsInDict];

            int r=0;
            if (br.ready()==true){
                while ((line=br.readLine()) != null) {
                    hrv[r] = line;
                    wordIndex[r]=r;
                    r++;
                }
            }
            br.close();
            fin.close();
            r=0;
            fin = new FileInputStream("eng.txt");
            br = new BufferedReader(new InputStreamReader(fin));
            if (br.ready()==true){
                while ((line=br.readLine()) != null) {
                    eng[r] = line;
                    r++;
                }
            }
            br.close();
            fin.close();
    }
    //**************************************************************************
    
    //******************************* STATISTICS *******************************
    public void stats(int odabir) throws FileNotFoundException, IOException{
        switch (odabir){
            case 0:
                fout = new FileOutputStream("stat.txt");
                ps = new PrintStream (fout);
                
                ps.println("Played games:");
                ps.println(status[0]);
                ps.println("Games won with no life losses:");
                ps.println(status[1]);
                ps.println("Gained lives: ");
                ps.println(status[2]);
                ps.println("Lost lives: ");
                ps.println(status[3]);
                ps.println("Solved words: ");
                ps.println(status[4]);
                ps.close();
                fout.close();
                break;
            case 1:
                fin = new FileInputStream("stat.txt");
                
                br = new BufferedReader(new InputStreamReader(fin));
                int u=0;
                int b=0;
                int r=0;
                if (br.ready()==true){
                    while ((line=br.readLine()) != null) {
                        if(u%2==0){statusText[r]=line; r++;}
                        else{status[b]=Integer.parseInt(line); b++;}
                        u++;
                    }
                }
                br.close();
                fin.close();
                break;
            default:
                break;
        }
    }
    public void statsInit(){
        status1.setText(statusText[0]+status[0]);
        status2.setText(statusText[1]+status[1]);
        status3.setText(statusText[2]+status[2]);
        status4.setText(statusText[3]+status[3]);
        status5.setText(statusText[4]+status[4]);
    }
    
    public void statsU(int oI, int oIBGZ, int ukDZ, int ukIZ, int ukRP){
        if(oI>0){status[0]+=oI; status1.setText(statusText[0]+status[0]);}
        if(oIBGZ>0){status[1]+=oIBGZ; status2.setText(statusText[1]+status[1]);}
        if(ukDZ>0){status[2]+=ukDZ; status3.setText(statusText[2]+status[2]);}
        if(ukIZ>0){status[3]+=ukIZ; status4.setText(statusText[3]+status[3]);}
        if(ukRP>0){status[4]+=ukRP; status5.setText(statusText[4]+status[4]);}
        
    }
    //**************************************************************************
    
    /**************************************************************************/
    /***************************  MAIN GAME CLASS  ****************************/
    /**************************************************************************/
    public class game extends JPanel {
        boolean lostLives = false;
        int numOfGamesWithNoLivesLost;
        Label[] wordsA = new Label[10];
        Label[] wordsB = new Label[10];
        

        public void newGame(int numOfWords) {
            lostLives=false;
            gameCI.setVisible(false);
            space.setVisible(true);
            numOfSolved=0;
            System.out.println("Called newGame");
            gameDone.removeAll();
            for (int i = 0; i < numOfWords; i++) {
                inside[i] = 0;
                solved[i] = 0;
                x1[i] = 50;
                y1[i] = 40 + ((Hk+20) * i);
                x2[i] = 454-Wk-50;
                y2[i] = 40 + ((Hk+20) * i);
            }
            shuffle(numOfWords, y1);
            shuffle(numOfWords, y2);
            shuffle(numOfWordsInDict, wordIndex);
            choose(numOfWords, wordIndex, wordsChosen);
            repaint();
        }

        public void next() {
            gameDone.removeAll();
            gameCI.setVisible(false);
            
            if(!lostLives){
                numOfGamesWithNoLivesLost++;
                statsU(0,1,0,0,0);
                if(numOfGamesWithNoLivesLost>2){numOfLives++;statsU(0,0,1,0,0);}
                if(numOfWords<9){numOfWords += 1;}
            }else{
                numOfGamesWithNoLivesLost=0;}
            newGame(numOfWords);
        }

        public void initialize(int difficulty) {
            game.setVisible(true);
            numOfLives = 3;
            if (difficulty == 1) {numOfWords = 3;}
            else if (difficulty == 2) {numOfWords = 5;}
            else {numOfWords = 7;}
            numOfGamesWithNoLivesLost=0;
            newGame(numOfWords);
        }
        
        public void gameFinished(boolean pobj){
            if(pobj){
                statsU(1,0,0,0,numOfWords);
                message.setText(victory);
                //message.setBackground(Color.GREEN);
                newGame.setVisible(false);
                next.setVisible(true);
                giveup.setVisible(true);
                space.setVisible(false);
                gameCI.setVisible(true);
            }else{
                message.setText(loss);
                //message.setBackground(Color.RED);
                newGame.setVisible(true);
                next.setVisible(false);
                giveup.setVisible(true);
                space.setVisible(false);
                gameCI.setVisible(true);
            }
        }
        
        public void check(){
            if(numOfSolved==numOfWords){space.gameFinished(true);}
        }
        
        public void lifeLost(){
            numOfLives--;
            statsU(0,0,0,1,0);
            lostLives=true;
            if(numOfLives==0){gameFinished(false);}
        }
        
        public void shuffle(int broj, int[] array){
            Random rand= new Random();
            int randPoz;
            int tmp;
            for (int i=0; i < broj; i++) {
                randPoz = rand.nextInt(broj);
                tmp = array[i];
                array[i] = array[randPoz];
                array[randPoz] = tmp;
            }
        }
        
        public void choose(int broj, int[] arrayI, int[] arrayO){
            System.arraycopy(arrayI, 0, arrayO, 0, broj);
        }
        
        public void printSolved(int ind){
            gameDone.add(solved(ind));
            gameDone.revalidate();
        }
        
        public Label solved(int ind){
            Label prv = new Label(hrv[wordsChosen[ind]]+" = "+eng[wordsChosen[ind]]);
            prv.setFont(lableF);
            return prv;
        }

        @Override
        public void paint(Graphics g) {
            //System.out.println("Called paintComponent");
            super.paint(g);
            g.setColor(Color.BLACK);
            g.fillRect(290, 0, 100, 30);
            g.setFont(livesF);
            g.setColor(Color.GREEN);
            if(numOfLives==2){g.setColor(Color.YELLOW);}
            else if(numOfLives==1){g.setColor(Color.RED);}
            
            g.drawString("Lives: x"+numOfLives, 300,20);
            
            g.setFont(wordF);
            for (int i = 0; i < numOfWords; i++) {
                if(solved[i]==0){
                    //System.out.println("1: "+x1[i] + "-" + y1[i]);
                    g.setColor(backgroundK1);
                    g.fillRect(x1[i], y1[i], Wk, Hk);
                    g.setColor(Color.white);
                    g.drawRect(x1[i], y1[i], Wk, Hk);
                    g.drawString(hrv[wordsChosen[i]], x1[i]+10, y1[i]+20);
                    
                    //System.out.println("2: "+x2[i] + "-" + y2[i]);
                    g.setColor(backgroundK2);
                    g.fillRect(x2[i], y2[i], Wk, Hk);
                    g.setColor(Color.white);
                    g.drawRect(x2[i], y2[i], Wk, Hk);
                    g.drawString(eng[wordsChosen[i]], x2[i]+10, y2[i]+20);
                }
                if(solved[iT]==0){
                    if(jT==1){
                        g.setColor(backgroundK1);
                        g.fillRect(x1[iT], y1[iT], Wk, Hk);
                        g.setColor(Color.white);
                        g.drawRect(x1[iT], y1[iT], Wk, Hk);
                        g.drawString(hrv[wordsChosen[iT]], x1[iT]+10, y1[iT]+20);
                    }else{
                        g.setColor(backgroundK2);
                        g.fillRect(x2[iT], y2[iT], Wk, Hk);
                        g.setColor(Color.white);
                        g.drawRect(x2[iT], y2[iT], Wk, Hk);
                        g.drawString(eng[wordsChosen[iT]], x2[iT]+10, y2[iT]+20);
                    }
                }
                
                
            }
        }
    }
    /**************************************************************************/
    /**************************************************************************/
    
    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (window) {
            case 0:
                break;
            case 1:
                if (e.getSource() == gamej) {
                    window = 2;
                    start.setVisible(false);
                    difficulty.setVisible(true);
                    break;
                } else if (e.getSource() == stat) {
                    window = 5;
                    start.setVisible(false);
                    stats.setVisible(true);
                    break;
                } else {
                    break;
                }
            case 2:
                if (e.getSource() == normal) {
                    chosenDifficulty = 2;
                    window = 3;
                    difficulty.setVisible(false);
                    language.setVisible(true);
                    break;
                } else if (e.getSource() == easy) {
                    chosenDifficulty = 1;
                    window = 3;
                    difficulty.setVisible(false);
                    language.setVisible(true);
                    break;
                } else if (e.getSource() == hard) {
                    chosenDifficulty = 3;
                    window = 3;
                    difficulty.setVisible(false);
                    language.setVisible(true);
                    break;
                } else {
                    window = 1;
                    difficulty.setVisible(false);
                    start.setVisible(true);
                    break;
                }
            case 3:
                if (e.getSource() == languageEH) {
                    chosenLanguage = 1;
                    window = 4;
                    language.setVisible(false);
                    game.setVisible(true);
                    space.initialize(chosenDifficulty);
                    break;
                } else {
                    window = 2;
                    language.setVisible(false);
                    difficulty.setVisible(true);
                    break;
                }
            case 4:
                if (e.getSource() == exitP || e.getSource() == giveup) {
                    window = 1;
                    game.setVisible(false);
                    start.setVisible(true);
                    try {
                        stats(0);
                    } catch (IOException ex) {
                        errorHappend=true;
                        errorHappendModul="stats(0)";
                    }
                    break;
                } else if(e.getSource() == next){
                    space.next();
                } else if(e.getSource() == newGame){
                    space.initialize(chosenDifficulty);
                    break;
                }else {
                    break;
                }
            case 5:
                if (e.getSource() == returnSP){
                    window = 1;
                    stats.setVisible(false);
                    start.setVisible(true);
                    break;
                }else{
                    break;
                }
            default:
                break;
        }
    }
    public static void setElFont (String el, Font f){
        UIManager.put (el+".font", f);
    }
    
    public static Label lable(String word, Font f){
        Label tmp = new Label(word);
        tmp.setFont(f);
        return tmp;
    }
}
