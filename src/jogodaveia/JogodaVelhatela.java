package jogodaveia;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JogodaVelhatela extends JFrame {

	private JPanel contentPane;
	private JLabel l01;
	private JLabel l02;
	private JLabel l10;
	private JLabel l11;
	private JLabel l12;
	private JLabel l20;
	private JLabel l21;
	private JLabel l22;
	private JButton btvoltar;
	private JLabel lblNewLabel_9;
	private LinkedList<JLabel> lista;
	private JLabel l00;
	private ImageIcon x = new ImageIcon(getClass().getResource("x.png"));
	private ImageIcon o = new ImageIcon(getClass().getResource("o.png"));
	Tabuleiro t1 = new Tabuleiro();
	private Jogador j1 = new Jogador(t1, "Carolini", 'X');
	private Jogador pc = new Jogador(t1, "Pc", 'O');
	private JLabel ganhador;
	private Stack<Tabuleiro> tpilha = new Stack<Tabuleiro>();
	private Stack<Integer> intpilha = new Stack<Integer>();
	private Random gerador = new Random();
	private JButton btclear;
	private Image background;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					JogodaVelhatela frame = new JogodaVelhatela();
					//background = new ImageIcon()
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
		public JogodaVelhatela() {
		  
        setIconImage(Toolkit.getDefaultToolkit().getImage(JogodaVelhatela.class.getResource("/jogodaveia/jogoveia.png")));
        initialize();
        lista = new LinkedList<JLabel>();
        lista.add(l00);
        lista.add(l01);
        lista.add(l02);
        lista.add(l10);
        lista.add(l11);
        lista.add(l12);
        lista.add(l20);
        lista.add(l21);
        lista.add(l22);
        
        		btclear = new JButton("Limpar");
        		this.btclear.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				Limpar();
        			}
        		});
        		contentPane.add(btclear, "cell 3 7");
        jogada();
        t1.desenhar();
    }
	
	private void jogada() {
		for (JLabel lb : lista) {
			lb.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					jogar();
				}
			});
		}
	}

	private void jogar() {
		for (JLabel lb : lista) {
			if ((lb.getMousePosition(false) != null) && !t1.verificarGanhador() && !t1.empate()) {
				String vtposi[] = lb.getName().split(",");
				int linha = Integer.parseInt(vtposi[0]);
				int coluna = Integer.parseInt(vtposi[1]);
				if (t1.verificarPosicao(linha, coluna)) {
					j1.jogada(linha, coluna, 'X');
					lb.setIcon(x);
					tpilha.push(new Tabuleiro(t1));
					intpilha.push(linha);
					intpilha.push(coluna);
					if (!t1.verificarGanhador() && !t1.empate()) {
						jogarcomputador();
					} else {
						if (t1.getVerificar() == 'X') {
							System.out.println("Vencedor: " + j1.getNome());
							ganhador.setText("Vencedor: " + j1.getNome());
						} else {
							if (t1.getVerificar() == 'O') {
								System.out.println("Vencedor: " + pc.getNome());
								ganhador.setText("Vencedor: " + pc.getNome());
							} else {
								if (t1.empate()) {
									ganhador.setText("Jogo Empatou");
								}
							}
						}
					}
				}
			}
		}
	}

	private void jogarcomputador() {
		int linha, coluna;
		do {
			linha = gerador.nextInt(3);
			coluna = gerador.nextInt(3);
		} while (!t1.verificarPosicao(linha, coluna));
		t1.jogar(linha, coluna, 'O');
		String n = "";
		n += linha + ",";
		n += coluna;
		for (JLabel jb : lista) {
			if (n.equals(jb.getName())) {
				jb.setIcon(o);
				System.out.println("Entrou");
				tpilha.push(new Tabuleiro(t1));
				intpilha.push(linha);
				intpilha.push(coluna);
				t1.desenhar();
				if (t1.verificarGanhador()) {
					if (t1.getVerificar() == 'X') {
						System.out.println("Vencedor: " + j1.getNome());
						ganhador.setText("Vencedor: " + j1.getNome());
					} else {
						if (t1.getVerificar() == 'O') {
							System.out.println("Vencedor: " + pc.getNome());
							ganhador.setText("Vencedor: " + pc.getNome());
						}
					}
				} 
			}
		}
	}
	
	 private void Limpar() {
	        t1.reset();
	        for (JLabel lb : lista) {
	            lb.setIcon(null);
	        }
	        ganhador.setText("");
	        tpilha.clear();
	        intpilha.clear();
	    }

	 private void desfazer() {
	        if ((intpilha.size() >= 4 && (!t1.verificarGanhador()))) {
	            int coluna = intpilha.pop();
	            int linha = intpilha.pop();
	            t1.jogar(linha, coluna, '-');
	            String n = "";
	            n += linha + ",";
	            n += coluna;
	            for (JLabel lb : lista) {
	                if (n.equals(lb.getName())) {
	                    lb.setIcon(null);
	                }
	            }
	            coluna = intpilha.pop();
	            linha = intpilha.pop();
	            t1.jogar(linha, coluna, '-');
	            n = "";
	            n += linha + ",";
	            n += coluna;
	            for (JLabel lb : lista) {
	                if (n.equals(lb.getName())) {
	                    lb.setIcon(null);
	                }
	            }
	            t1.desenhar();
		}
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 537);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaptionText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(
				new MigLayout("", "[grow][:120:120px,fill][:120:120px,fill][:120:120px,fill][grow]", "[grow][][][:120:120px][:120:120px][:120:120px][][][][grow]"));

		lblNewLabel_9 = new JLabel("      JOGO DA V\u00C9IA\r\n");
		this.lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel_9.setForeground(Color.WHITE);
		contentPane.add(lblNewLabel_9, "cell 1 1 3 1,grow");

		ganhador = new JLabel("");
		contentPane.add(ganhador, "cell 1 2");

		l00 = new JLabel("");
		this.l00.setHorizontalAlignment(SwingConstants.CENTER);
		l00.setSize(new Dimension(40, 60));
		l00.setPreferredSize(new Dimension(40, 60));
		l00.setOpaque(true);
		l00.setBackground(new Color(255, 255, 204));
		l00.setForeground(Color.WHITE);
		l00.setName("0,0");
		contentPane.add(l00, "cell 1 3,alignx center,growy");

		l01 = new JLabel("");
		this.l01.setHorizontalAlignment(SwingConstants.CENTER);
		this.l01.setBackground(new Color(204, 153, 102));
		l01.setForeground(Color.WHITE);
		l01.setSize(new Dimension(40, 60));
		l01.setPreferredSize(new Dimension(40, 60));
		l01.setOpaque(true);
		l01.setName("0,1");
		contentPane.add(l01, "cell 2 3,alignx center,growy");

		l02 = new JLabel("");
		this.l02.setHorizontalAlignment(SwingConstants.CENTER);
		this.l02.setBackground(new Color(255, 255, 204));
		l02.setForeground(Color.WHITE);
		l02.setSize(new Dimension(40, 60));
		l02.setPreferredSize(new Dimension(40, 60));
		l02.setOpaque(true);
		l02.setName("0,2");
		contentPane.add(l02, "cell 3 3,grow");

		l10 = new JLabel("");
		this.l10.setHorizontalAlignment(SwingConstants.CENTER);
		this.l10.setBackground(new Color(204, 153, 102));
		l10.setSize(new Dimension(40, 60));
		l10.setPreferredSize(new Dimension(40, 60));
		l10.setOpaque(true);
		l10.setForeground(Color.WHITE);
		l10.setName("1,0");
		contentPane.add(l10, "cell 1 4,alignx center,growy");

		l11 = new JLabel("");
		this.l11.setHorizontalAlignment(SwingConstants.CENTER);
		this.l11.setBackground(new Color(255, 255, 204));
		l11.setSize(new Dimension(40, 60));
		l11.setPreferredSize(new Dimension(40, 60));
		l11.setOpaque(true);
		l11.setForeground(Color.WHITE);
		l11.setName("1,1");
		contentPane.add(l11, "cell 2 4,alignx center,growy");

		l12 = new JLabel("");
		this.l12.setHorizontalAlignment(SwingConstants.CENTER);
		this.l12.setBackground(new Color(204, 153, 102));
		l12.setSize(new Dimension(40, 60));
		l12.setPreferredSize(new Dimension(40, 60));
		l12.setOpaque(true);
		l12.setForeground(Color.WHITE);
		l12.setName("1,2");
		contentPane.add(l12, "cell 3 4,alignx center,growy");

		l20 = new JLabel("");
		this.l20.setHorizontalAlignment(SwingConstants.CENTER);
		l20.setSize(new Dimension(40, 60));
		l20.setPreferredSize(new Dimension(40, 60));
		l20.setOpaque(true);
		l20.setForeground(Color.WHITE);
		l20.setBackground(new Color(255, 255, 204));
		l20.setName("2,0");
		contentPane.add(l20, "cell 1 5,alignx center,growy");

		l21 = new JLabel("");
		this.l21.setHorizontalAlignment(SwingConstants.CENTER);
		l21.setSize(new Dimension(40, 60));
		l21.setPreferredSize(new Dimension(40, 60));
		l21.setOpaque(true);
		l21.setForeground(Color.WHITE);
		l21.setBackground(new Color(204, 153, 102));
		l21.setName("2,1");
		contentPane.add(l21, "cell 2 5,alignx center,growy");

		l22 = new JLabel("");
		this.l22.setHorizontalAlignment(SwingConstants.CENTER);
		l22.setSize(new Dimension(40, 60));
		l22.setPreferredSize(new Dimension(40, 60));
		l22.setOpaque(true);
		l22.setForeground(Color.WHITE);
		l22.setBackground(new Color(255, 255, 204));
		l22.setName("2,2");
		contentPane.add(l22, "cell 3 5,alignx center,growy");
		
		btvoltar = new JButton("Desfazer");
		btvoltar.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				desfazer();
			}
		});
		contentPane.add(btvoltar, "cell 1 7,grow");
	}

}
