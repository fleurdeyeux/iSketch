import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;


@SuppressWarnings("serial")
public class MainWindow extends JFrame implements WindowListener{

	private static int wHeight = 600;
	private static int wWidth = 1100;
	
	private DrawPanel drawPanel;
	private ChatPanel chatPanel;
	private JPanel westPanel = new JPanel(null);
	private JPanel eastPanel = new JPanel(null);
	private JPanel globalPanel = new JPanel(null);
	
	private TextPanel textP = new TextPanel(wWidth, wHeight, this);
	private MessagesPanel messP = new MessagesPanel(wWidth, wHeight);
	
	private Messenger msn;

	public MainWindow(Messenger msn)
	{
		this.setTitle("Joue !");
		this.setSize(wWidth, wHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		
		drawPanel = new DrawPanel(wWidth / 2 - 10, (wHeight * 3) / 4 - 10, this);
		drawPanel.setBackground(Color.white);
		drawPanel.setPreferredSize(new Dimension(wWidth / 2 - 10, (wHeight * 3) / 4 - 10));
		
		chatPanel = new ChatPanel(this, wWidth / 2 - 10, wHeight / 4 - 10);
		
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
		westPanel.setPreferredSize(new Dimension(wWidth / 2, wHeight));
		westPanel.setBackground(Color.DARK_GRAY);
		westPanel.add(messP);
		westPanel.add(textP, BorderLayout.SOUTH);
		
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		eastPanel.setPreferredSize(new Dimension(wWidth / 2, wHeight));
		eastPanel.setBackground(Color.DARK_GRAY);
		eastPanel.add(drawPanel);
		eastPanel.add(chatPanel);
		
		globalPanel.setLayout(new BoxLayout(globalPanel, BoxLayout.LINE_AXIS));
		globalPanel.add(westPanel);
		globalPanel.add(eastPanel);
		
		this.getContentPane().add(globalPanel);
		this.setVisible(true);
		this.setResizable(false);
		
		this.msn = msn;
	}

	/* TEXT FIELD */
	
	public void setAvailableButton() { this.textP.setAvailableButton(); }
	
	public void setDisableButton() { this.textP.setDisableButton(); }
	
	public void setAvailableChatZone() { this.chatPanel.availableZone(); }
	
	public void setDisableChatZone() { this.chatPanel.disableZone(); };
	
	
	/* PLAYERS */
	
	public void addPlayer(String name, String score)
	{
		this.messP.addPlayer(name, score);
	}

	
	/* COMMAND TO READ */
	
	public void guessed(String[] tab)
	{
		if (tab.length < 3)
		{
			System.out.println("Erreur dans MainWindow : guessed");
			return;
		}
		System.out.println("Le mot " + tab[1] + " a été proposé par " + tab[2]);
		this.messP.newProp(tab[2], tab[1]);	
	}
	
	public void wordFound(String[] tab)
	{
		if (tab.length < 2)
		{
			System.out.println("Erreur dans MainWindow : wordFound");
			return;
		}
		System.out.println("Le mot a été trouvé par " + tab[1]);
		messP.wordFound(tab[1]);
	}
	
	public void wordFoundTimeOut(String[] tab)
	{
		if (tab.length < 2)
		{
			System.out.println("Erreur dans MainWindow : wordFoundTimeOut");
			return;
		}
		System.out.println("Il vous reste " + tab[1] + " secondes pour trouver le mot");
		messP.wordFoundTimeOut(tab[1]);
	}
	
	public void scoreOut(ArrayList<Player> list)
	{
		System.out.println("Voici les scores");
		messP.scoreout(list);
	}
	
	public void endRound(String[] tab)
	{
		if (tab.length < 3)
		{
			System.out.println("Erreur dans MainWindow : endRound");
			return;
		}
		System.out.println("Le round est terminé");
		System.out.println("Le gagnant est " + tab[1]);
		System.out.println("Le mot à trouver était " + tab[2]);
		this.messP.endRound(tab);
	}
	
	public void line(String[] tab) { drawPanel.line(tab); }
	
	public void broadcast(String[] tab) { this.messP.broadcast(tab); }
	
	public void exitFinder(String[] tab) { this.messP.exitFinder(tab); }
	
	public void exitDrawer(String[] tab)
	{
		this.messP.exitDrawer(tab);
		this.drawPanel.cleanBoard();
	}
	
	public void listen(String[] tab) { this.chatPanel.chat(tab); }
	
	
	/* GRAPHICS */
	
	public void setPassiveMode()
	{
		this.drawPanel.setPassiveMode();
		this.messP.setMode(false, "");
	}
	
	public void setActifMode(String word)
	{ 
		this.drawPanel.setActifMode();
		this.messP.setMode(true, word);
	}
	
	public void setPassiveModeSilence()
	{
		this.drawPanel.setPassiveMode();
	}
	
	public void cleanBoard() { this.drawPanel.cleanBoard(); }
	
	
	/* COMMAND */
	
	public void sendProposition(String prop) { msn.wordProposition(prop);}
	
	public void talk(String chat) { msn.sendCommandTalk(chat); }
	
	public void sendCommandSetColor(int r, int g, int b)
	{
		msn.sendCommandSetColor(r, g, b);
	}

	public void sendCommandSetSize(int size)
	{
		msn.sendCommandSetSize(size);
	}
	
	public void sendCommandSetLine(int x1, int y1, int x2, int y2)
	{
		msn.sendCommandSetLine(x1, y1, x2, y2);
	}
	
	public void sendCommandPass() { msn.sendCommandPass(); }
	
	public void sendCommandCheat() { msn.sendCommandCheat(); }

	
	/* WINDOWS LISTENER */

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		msn.sendCommandExit();
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	
}
