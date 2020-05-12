package br.ufpb.dcx.tcc.pl;

import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * 
 * Classe concreta para maximizar uma função obejetivo Z, com três restrição  
 * 
 * @author Wellerson Alex
 *
 */

public class Maximizar3R extends ProgramacaoLinear {

	/** Propriedade de contagem*/
	private int cont;
	
	/** Propriedade que guarda os valores iniciais de Z */
	private double[] guardarInicialZ;
		
	/** Propriedade que guarda os valores iniciais da restrição 1 */
	private double[] guardarInicialR1;

	/** Propriedade que guarda os valores iniciais da restrição 2 */
	private double[] guardarInicialR2;

	/** Propriedade que guarda os valores iniciais da restrição 3 */
	private double[] guardarInicialR3;
	
	/**
	 * Encotra a solução ótima para a função Z de forma recursiva.
	 * 
	 *@return Tabela inicial, tabelas atualizadas e solução ótima.
	 */
	public double[] max3R(double[] Z, double[] r1, double[] r2, double[] r3) {

		if (cont == 0) {
			guardarInicialR1 = new double[r1.length];
			guardarInicialR2 = new double[r2.length];
			guardarInicialR3 = new double[r3.length];
			guardarInicialZ = new double[Z.length];

			guardarInicialR1 = r1;
			guardarInicialR2 = r2;
			guardarInicialR3 = r3;
			guardarInicialZ = Z;
		}

		verificarLoopTableau(Z, r1, r2, r3, guardarInicialR1, guardarInicialR2, guardarInicialR3, guardarInicialZ);

		
		if (chegouNoOtimo(Z)) {
			return Z;
		}

		mostrarQuadroInicial(Z, r1, r2, r3);

		cont += 1;

		int posicaoColuna = colunaMaiorValorPositivo(Z);

		double valorRestritivo1 = linhaMaisRestrtitiva(r1, posicaoColuna - 1);
		double valorRestritivo2 = linhaMaisRestrtitiva(r2, posicaoColuna - 1);
		double valorRestritivo3 = linhaMaisRestrtitiva(r3, posicaoColuna - 1);

		double[] valoresRestritivos = inserir(valorRestritivo1, valorRestritivo2, valorRestritivo3);
		int posicaoLinha = linhaDoMenorValorRestritivoPositivo(valoresRestritivos);
		double[] linhaFixa = linhaEscolhida(posicaoLinha, r1, r2, r3);

		double interseccao = linhaFixa[posicaoColuna - 1];

		double valorZerarColuna1 = calcularValorEscalonamento(interseccao, r1[posicaoColuna - 1]);
		double valorZerarColuna2 = calcularValorEscalonamento(interseccao, r2[posicaoColuna - 1]);
		double valorZerarColuna3 = calcularValorEscalonamento(interseccao, r3[posicaoColuna - 1]);
		double valorZerarColunaZ = calcularValorEscalonamento(interseccao, Z[posicaoColuna - 1]);

		r1 = zerarColuna(linhaFixa, r1, valorZerarColuna1);
		r2 = zerarColuna(linhaFixa, r2, valorZerarColuna2);
		r3 = zerarColuna(linhaFixa, r3, valorZerarColuna3);
		Z = zerarColuna(linhaFixa, Z, valorZerarColunaZ);

		System.out.println();
		System.out.println("A coluna escolhida foi: " + posicaoColuna);
		
		if(posicaoLinha == 0) {
			JOptionPane.showMessageDialog(null, "O problema é ilimitado", "Ilimitado ou Sem solução", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("Problema Ilimitado ou Sem solução");
		}
		
		System.out.println("A linha escolhida foi: " + posicaoLinha);
		System.out.println();
		System.out.println("Valores restritivos: " + Arrays.toString(valoresRestritivos));

		mostrarIteracao(Z, r1, r2, r3);

		return max3R(Z, r1, r2, r3);

	}

	/** Verifica se entrou em Loop */
	private void verificarLoopTableau(double[] Z, double[] r1, double[] r2, double[] r3, double[] guardarInicialR1,
			double[] guardarInicialR2, double[] guardarInicialR3, double[] guardarInicialZ) {
		
		if (cont > 0) {

			boolean testeR1 = testeLoop(guardarInicialR1, r1);
			boolean testeR2 = testeLoop(guardarInicialR2, r2);
			boolean testeR3 = testeLoop(guardarInicialR3, r3);
			boolean testeZ = testeLoop(guardarInicialZ, Z);

			if (testeR1 && testeR2 && testeR3 && testeZ) {
				JOptionPane.showMessageDialog(null, "O problema entrou em loop, não foi possível resolver",
						"Loop Tableau", JOptionPane.ERROR_MESSAGE);
				System.out.println();
				System.out.println("Os valores são iguais nas duas tabelas, por isso o Loop é gerado");
				System.out.println();
				mostrarQuadroInicialLoop(Z, r1, r2, r3);
				mostrarIteracao(Z, r1, r2, r3);
				throw new IllegalArgumentException("Loop Tableau");
			}
		}
		
	}

	/** Mostrar cada iteração */
	private void mostrarIteracao(double[] Z, double[] r1, double[] r2, double [] r3) {
		System.out.println();
		System.out.println("Tabela Resultante da " + cont + "ª Iteração:");
		double[] printR1 = formatarDecimaisUmaCasa(r1);
		double[] printR2 = formatarDecimaisUmaCasa(r2);
		double[] printR3 = formatarDecimaisUmaCasa(r3);
		double[] printZ = formatarDecimaisUmaCasa(Z);
		System.out.println(Arrays.toString(printR1));
		System.out.println(Arrays.toString(printR2));
		System.out.println(Arrays.toString(printR3));
		System.out.println(Arrays.toString(printZ));
	}
	
	/** Exibe tabela inicial quando há Loop */
	private void mostrarQuadroInicialLoop(double[] Z, double[] r1, double[] r2, double [] r3) {
		System.out.println("Tabela Inicial:");
		r1 = formatarDecimaisUmaCasa(r1);
		r2 = formatarDecimaisUmaCasa(r2);
		r3 = formatarDecimaisUmaCasa(r3);
		Z = formatarDecimaisUmaCasa(Z);
		System.out.println(Arrays.toString(r1));
		System.out.println(Arrays.toString(r2));
		System.out.println(Arrays.toString(r3));
		System.out.println(Arrays.toString(Z));
		System.out.println();
		
	}

	/** Testa se as tabelas são iguais */
	private boolean testeLoop(double[] inicial, double[] r) {
		double[] r1Formatado = formatarDecimaisUmaCasa(r);
		double[] inicialFormatodo = formatarDecimaisUmaCasa(inicial);
		int cont = 0;
		for (int i = 0; i < r1Formatado.length; i++) {
			if (inicialFormatodo[i] == r1Formatado[i]) {
				cont += 1;
			}
		}
		if (cont == r.length) {
			return true;
		} else {
			return false;
		}
	}

	/** Formatar para uma casa decimal */
	private double[] formatarDecimaisUmaCasa(double[] vetor) {
		double[] novo = new double[vetor.length];
		DecimalFormat umaCasa = new DecimalFormat("0.0");
		for (int i = 0; i < vetor.length; i++) {
			String v = umaCasa.format(vetor[i]);
			String[] part1 = v.split("[,]");
			String v1 = part1[0] + "." + part1[1];
			double valor1 = Double.parseDouble(v1);
			novo[i] = valor1;
		}
		return novo;
	}

	/** Verifica se a solução foi encontrada*/
	public boolean chegouNoOtimo(double[] max) {
		for (int i = 0; i < max.length; i++) {
			if (max[i] > 0) {
				return false;
			}
		}
		return true;
	}

	/** Exibe tabela inicial*/
	private void mostrarQuadroInicial(double[] Z, double[] r1, double[] r2, double[] r3) {
		if (cont == 0) {
			System.out.println("Tabela Inicial:");
			System.out.println(Arrays.toString(r1));
			System.out.println(Arrays.toString(r2));
			System.out.println(Arrays.toString(r3));
			System.out.println(Arrays.toString(Z));
		}
	}

	/** Escolhe a coluna da tabela*/
	public int colunaMaiorValorPositivo(double[] v) {
		double maior = Integer.MIN_VALUE;
		int coluna = 0;
		for (int i = 0; i < v.length; i++) {

			if (v[i] > maior && v[i] > 0) {
				maior = v[i];
				coluna = i + 1;
			}
		}

		return coluna;
	}

	/** Esolhe a linha da tabela*/
	public double linhaMaisRestrtitiva(double[] v, int i) {

		double valor = 0;

		if (v[(int) i] >= 0) {
			double s = v[v.length - 1];
			double r = v[(int) i];
			if (r == 0) {
				return -1;
			} else {
				valor = s / r;
				return valor;
			}
		} else {
			return -1;
		}
	}

	public double[] inserir(double valor1, double valor2, double valor3) {
		
		double vo1 = formatarDecimais(valor1);

		double vo2 = formatarDecimais(valor2);

		double vo3 = formatarDecimais(valor3);

		double[] v = new double[3];
		v[0] = vo1;
		v[1] = vo2;
		v[2] = vo3;

		return v;
	}
	
	private double formatarDecimais(double valor) {
		DecimalFormat umaCasa = new DecimalFormat("0.0000000000");
		String v = umaCasa.format(valor);
		String[] part1 = v.split("[,]");
		String v1 = part1[0] + "." + part1[1];
		double valor1 = Double.parseDouble(v1);
		return valor1;
	}


	public int linhaDoMenorValorRestritivoPositivo(double[] v) {
		double menor = Integer.MAX_VALUE;
		int linha = 0;
		for (int i = 0; i < v.length; i++) {

			if (v[i] < menor && v[i] >= 0) {
				menor = v[i];
				linha = i + 1;
			}
		}

		return linha;
	}

	public double[] linhaEscolhida(int linha, double[] r1, double[] r2, double[] r3) {
		if (linha == 1) {
			return r1;
		} else if (linha == 2) {
			return r2;
		} else {
			return r3;
		}
	}

	/** Definir valor para zerar coluna*/
	private double calcularValorEscalonamento(double interseccao, double d) {

		double vo1 = formatarDecimais(interseccao);

		double valor;

		valor = (d / vo1);
		valor = (valor * -1);

		double val1 = formatarDecimais(valor);

		return val1;
	}

	/** Realiza escalonamento para atualização da tabela*/
	public double[] zerarColuna(double[] vr, double[] vz, double valor) {
		double[] novoV = new double[vz.length];
		int cont = 0;

		for (int i = 0; i < vr.length; i++) {
			if (vr[i] == vz[i]) {
				cont += 1;
			}
		}

		atualizarValores(vr, vz, valor, novoV);

		int quantP = vr.length;

		if (cont == quantP) {
			return vr;
		} else {
			return novoV;
		}
	}

	private void atualizarValores(double[] vr, double[] vz, double valor, double[] novoV) {
		for (int i = 0; i < vr.length; i++) {

			double vIndice = ((vr[i] * valor) + vz[i]);
			
			double vo1 = formatarDecimais(vIndice);

			novoV[i] = vo1;
		}
	}

	/** Recebe dados da tabela*/
	public void entradaDados() {

		Maximizar3R problema = new Maximizar3R();
		String aux;
		int cont = 0;
		

		aux = JOptionPane.showInputDialog(null,
				"Quantas variáveis o problema apresenta? (Inclua também as variáveis de folga e o termo independente b)");
		int tamanho = Integer.parseInt(aux);

		double[] Z = valoresDeZ(tamanho, cont);
		double[] restricao1 = valoresR1(tamanho);
		double[] restricao2 = valoresR2(tamanho);
		double[] restricao3 = valoresR3(tamanho);

		double[] maxZ = problema.max3R(Z, restricao1, restricao2, restricao3);
		double solucaoOtima = maxZ[maxZ.length - 1] * -1;
		System.out.println();
		System.out.println("O valor da sólução ótima é: " + solucaoOtima);
	}

	/** Recebe valores para a primeira restrição*/
	private double[] valoresR1(int tamanho) {
		int restricao = 1;
		double[] restricao1 = adicionarVariaveis(tamanho, restricao);
		return restricao1;
	}

	/** Recebe valores para a segunda restrição*/
	private double[] valoresR2(int tamanho) {
		int restricao = 2;
		double[] restricao2 = adicionarVariaveis(tamanho, restricao);
		return restricao2;
	}
	
	/** Recebe valores para a terceira restrição*/
	private double[] valoresR3(int tamanho) {
		int restricao = 3;
		double[] restricao3 = adicionarVariaveis(tamanho, restricao);
		return restricao3;
	}

	/** Adiciona os valores*/
	private double[] adicionarVariaveis(int tamanho, int restri) {
		int posicao = 0;
		String aux;
		int cont = 0;
		double[] restricao = new double[tamanho];
		for (int i = 0; i < tamanho; i++) {
			cont += 1;
			if (cont == tamanho) {
				aux = JOptionPane.showInputDialog(null,"Para a restrição "+ restri + ", digite o valor do termo independente b"+restri);
				posicao = posicaoBarra(aux, posicao);
				verificarFracao(aux, posicao, restricao, i);
			} else {
				aux = JOptionPane.showInputDialog(null, "Para a restrição "+ restri+", digite o valor de X" + cont);
				posicao = posicaoBarra(aux, posicao);
				verificarFracao(aux, posicao, restricao, i);
			}
		}
		return restricao;
	}

	/** Recebe dados da função objetivo Z*/
	private double[] valoresDeZ(int tamanho, int cont) {
		String aux;
		int posicao = 0;
		double[] Z = new double[tamanho];
		for (int i = 0; i < tamanho; i++) {
			cont += 1;
			if (cont == tamanho) {
				aux = JOptionPane.showInputDialog(null, "Para a função Z, digite o valor do termo independente");
				posicao = posicaoBarra(aux, posicao);
				verificarFracao(aux, posicao, Z, i);
			} else {
				aux = JOptionPane.showInputDialog(null, "Para a função Z, digite o valor de X" + cont);
				posicao = posicaoBarra(aux, posicao);
				verificarFracao(aux, posicao, Z, i);
			}
		}
		return Z;
	}
	
	/** Trata caso com valor fracionário */
	private int posicaoBarra(String aux, int posicao) {
		posicao = aux.indexOf("/");
		return posicao;
	}

	/** Realiza operações com fração */
	private void verificarFracao(String aux, int posicao, double[] Z, int i) {
		if (posicao > 0) {
			double numerador = Double.parseDouble(aux.substring(0, posicao));
			double denominador = Double.parseDouble(aux.substring(posicao + 1, aux.length()));
			Z[i] = (numerador / denominador);
		} else {
			Z[i] = Double.parseDouble(aux);
		}
	}

}
