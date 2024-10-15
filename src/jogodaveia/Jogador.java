package jogodaveia;

public class Jogador {

	private String nome;
	private char icone;
	protected Tabuleiro t;

	public Jogador(Tabuleiro t, String nome, char icone) {
		this.t = t;
		this.nome = nome;
		this.icone = icone;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getIcone() {
		return icone;
	}

	public void setIcone(char icone) {
		this.icone = icone;
	}

	public Jogador() {
	}

	public void setTabuleiro(Tabuleiro t) {
		this.t = t;
	}

	public void jogada(int l, int c, char icone) {
		System.out.println(this.nome + " está jogando na posição [" + l + ", " + c + "]");
		if (t.verificarPosicao(l, c)) {
			t.jogar(l, c, icone);
		}
	}

}
