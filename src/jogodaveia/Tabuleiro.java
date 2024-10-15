package jogodaveia;

public class Tabuleiro {

	private char matriz[][] = new char[3][3];
	private char verificar;
	
	

	public char getVerificar() {
		return verificar;
	}

	public void setVerificar(char verificar) {
		this.verificar = verificar;
	}

	public Tabuleiro() {
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				matriz[i][j] = '-';
			}
		}
	}

	public Tabuleiro(Tabuleiro t) {
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				this.matriz[i][j] = t.matriz[i][j];
			}
		}
	}

	public boolean verificarPosicao(int l, int c) {
		if (matriz[l][c] == '-') {
			return true;
		}
		return false;
	}

	public void jogar(int l, int c, char x) {
		this.matriz[l][c] = x;
	}

	public void desenhar() {
		System.out.println("TABULEIRO");
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public boolean empate() {
		int cont = 0;
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				if (this.matriz[i][j] != '-') {
					cont++;
				}
			}
		}
		if (cont == 9 && !verificarGanhador()) {
			System.out.println("Empate!");
			return true;
		}
		return false;
	}

	public boolean verificarGanhador() {
		if ((matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2] && matriz[0][0] != '-')
				|| (matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0] && matriz[0][2] != '-')) {
			verificar = this.matriz[1][1];

			return true;
		}
		for (int i = 0; i < 3; i++) {
			if (matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2] && matriz[i][0] != '-') {
				verificar = this.matriz[i][1];

				return true;
			}
			if (matriz[0][i] == matriz[1][i] && matriz[1][i] == matriz[2][i] && matriz[0][i] != '-') {
				verificar = this.matriz[1][i];

				return true;
			}
		}
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
