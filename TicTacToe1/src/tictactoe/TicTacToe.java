package tictactoe;
import gamingplatformclient.peer.MessageRecipient;
import gamingplatformclient.peer.Peers;
import gamingplatformclient.peer.TeamPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TicTacToe extends JFrame implements ActionListener, MessageRecipient, WindowListener
{
    JLabel lblTitle, lblStatus;
    JButton allButtons[];
    Game g;
    String myName;
    int teamSize;
    LinkedList<TeamPlayer> team;
    TicTacToe(String myName, int teamSize)
    {
        this.myName = myName;
        this.teamSize = teamSize;
        
        initComponents();
        team = Peers.getInstance().getTeam();
        Peers.getInstance().setMessageRecipient(this);
        int otherPlayerNumber = team.get(0).getPlayerNumber();
        int myPlayerNumber;
        if(otherPlayerNumber == 0)
            myPlayerNumber = 1;
        else
            myPlayerNumber = 0;
        g =new Game(myName, myPlayerNumber, team.get(0).getName(), otherPlayerNumber);
        refreshUI();
    }
    
    
    private void initComponents()
    {
        Font f = new Font("Tahoma", 0, 36);
        setLayout(null);
        
        lblTitle = new JLabel("TicTacToe");
        lblTitle.setFont(f);
        lblTitle.setForeground(new Color(255, 102, 102));
        lblTitle.setBounds(120,20, 200,50);
        add(lblTitle);
        
        lblStatus = new JLabel("");
        lblStatus.setBounds(100,250, 200,50);
        add(lblStatus);
        
        
        allButtons = new JButton[9];
        int i;
        int x[] = {100,170,240, 100,170,240, 100,170,240};
        int y[] = {100,100,100, 150,150,150, 200,200,200};
                
        for(i =0 ; i <allButtons.length; i++)
        {
            allButtons[i] = new JButton();
            allButtons[i].setFont(f);
            allButtons[i].setFocusable(false);
            add(allButtons[i]);
            allButtons[i].setBounds(x[i], y[i], 60, 40);
            allButtons[i].addActionListener(this);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420,350);
        setResizable(false);
        setVisible(true);
    }
    
    private void setTheme(JButton bttn, String val)
    {
        if(val.equals("X"))
        {
            bttn.setText(val);
            bttn.setForeground(Color.red);
            bttn.setContentAreaFilled(false);
            bttn.removeActionListener(this);
        }
        else if(val.equals("O"))
        {
            bttn.setText(val);
            bttn.setForeground(Color.blue);
            bttn.setContentAreaFilled(false);
            bttn.removeActionListener(this);
        }
    }
    
    public void response(String resp)
    {
        g.fromJSONString(resp);
        refreshUI();
    }
    
    void play(int x, int y)
    {
        if(g.move(x, y))
        {
            Peers.getInstance().addUpdate(g.toJSONString());
            refreshUI();
            
        }
        else
            JOptionPane.showMessageDialog(this, "Move Not Allowed");
        
    }
    
    void refreshUI()
    {
        String updates = g.toJSONString();
        
        JSONObject jobj = new JSONObject(updates);
        lblStatus.setText(g.getCurrentPlayerName() + " PLAYS");
        //update GUI as per board
        JSONArray boardArr = jobj.getJSONArray("board");
        JSONArray row;
        int i, j;
        String val;
        for(i =0 ; i < boardArr.length(); i++)
        {
            row = boardArr.getJSONArray(i);
            for (j =0 ; j < row.length(); j++)
            {
                val = String.valueOf((char)row.getInt(j));
                setTheme(allButtons[i*boardArr.length()+j], val);
            }
        }
        if(g.checkWin())
        {
            JOptionPane.showMessageDialog(this, g.getCurrentPlayerName() + " WINS");
            System.exit(0);
        }
        else if(g.checkDraw())
        {
            JOptionPane.showMessageDialog(this, "DRAW");
            System.exit(0);
        }
        g.changePlayer();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        int i ;
        for(i =0 ; i < allButtons.length;i++)
        {
            if(allButtons[i].equals(e.getSource()))
            {
                play(i/3, i %3);
            }
        }
    }

    public void windowOpened(WindowEvent we) {
    }

    public void windowClosing(WindowEvent we) {
        Peers.getInstance().disconnect();
    }

    public void windowClosed(WindowEvent we) {
    }

    public void windowIconified(WindowEvent we) {
    }

    public void windowDeiconified(WindowEvent we) {
    }

    public void windowActivated(WindowEvent we) {
    }

    public void windowDeactivated(WindowEvent we) {
    }
    
    
}
