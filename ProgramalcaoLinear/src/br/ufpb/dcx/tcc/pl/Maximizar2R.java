package br.ufpb.dcx.tcc.pl;

import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * 
 * Classe concreta para maximizar uma função obejetivo Z, com duas restrição  
 * 
 * @author Wellerson Alex
 *
 */

public class Maximizar2R extends ProgramacaoLinear {
	
	/** Propriedade de contagem*/
	private int cont;

	/**
	 * Encotra a solução ótima para a função Z de forma recursiva.
	 * 
	 *@return Tabela inicial, tabelas atualizadas e solução ótima.
	 */
	public double[] max2R(double[] Z, double[] r1, double[] r2) {

		if (chegouNoOtimo(Z)) {
			return Z;
		}
		
		if(cont > 100) {
			JOptionPane.showMessageDialog(null, "O problema entrou em loop, não foi possível resolver", "Loop Tableau", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("Loop Tableau");
		}

		mostrarQuadroInicial(Z, r1, r2);

		cont += 1;

		int posicaoColuna = colunaMaiorValorPositivo(Z);

		double valorRestritivo1 = linhaMaisRestrtitiva(r1, posicaoColuna - 1);
		double valorRestritivo2 = linhaMaisRestrtitiva(r2, posicaoColuna - 1);

		double[] valoresRestritivos = inserir(valorRestritivo1, valorRestritivo2);
		int posicaoLinha = linhaDoMenorValorRestritivoPositivo(valoresRestritivos);

		double[] linhaFixa = linhaEscolhida(posicaoLinha, r1, r2);

		double interseccao = linhaFixa[posicaoColuna - 1];

		double valorZerarColuna1 = calcularValorEscalonamento(interseccao, r1[posicaoColuna - 1]);
		double valorZerarColuna2 = calcularValorEscalonamento(interseccao, r2[posicaoColuna - 1]);
		double valorZerarColunaZ = calcularValorEscalonamento(interseccao, Z[posicaoColuna - 1]);

		r1 = zerarColuna(linhaFixa, r1, valorZerarColuna1);
		r2 = zerarColuna(linhaFixa, r2, valorZerarColuna2);
		Z = zerarColuna(linhaFixa, Z, valorZerarColunaZ);

		System.out.println();
		System.out.println("A coluna escolhida foi: " + posicaoColuna);

		if(posicaoLinha == 0) {
			JOptionPane.showMessageDialog(null, "O problema é ilimitado ou não tem solução", "Ilimitado ou Sem solução", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("Problema Ilimitado ou Sem solução");
		}
		
		System.out.println("A linha escolhida foi: " + posicaoLinha);
		System.out.println();
		System.out.println("Valores restritivos: " + Arrays.toString(valoresRestritivos));

		System.out.println();
		System.out.println("Tabela Resultante da " + cont + "ª Iteração:");
		System.out.println(Arrays.toString(r1));
		System.out.println(Arrays.toString(r2));
		System.out.println(Arrays.toString(Z));

		return max2R(Z, r1, r2);

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
	private void mostrarQuadroInicial(double[] Z, double[] r1, double[] r2) {
		if (cont == 0) {
			System.out.println("Tabela Inicial:");
			System.out.println(Arrays.toString(r1));
			System.out.println(Arrays.toString(r2));
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

	public double[] inserir(double valor1, double valor2) {

		double vo1 = formatarDecimais(valor1);

		double vo2 = formatarDecimais(valor2);

		double[] v = new double[2];
		v[0] = vo1;
		v[1] = vo2;

		return v;
	}
	
	private double formatarDecimais(double valor) {
		DecimalFormat umaCasa = new DecimalFormat("0.00");
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

	public double[] linhaEscolhida(int linha, double[] r1, double[] r2) {
		if (linha == 1) {
			return r1;
		} else {
			return r2;
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

		Maximizar2R problema = new Maximizar2R();
		int tamanho;
		String aux;
		int cont = 0;
		
	    aux = JOptionPane.showInputDialog(null, "Quantas variáveis o problema apresenta? (Inclua também as variáveis de folga e o termo independente b)");
	    tamanho = Integer.parseInt(aux);
	    
	    double[] Z = valoresDeZ(tamanho, cont);
	    double[] restricao1 = valoresR1(tamanho);
	    double[] restricao2 = valoresR2(tamanho);

		double[] maxZ = problema.max2R(Z, restricao1, restricao2);
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
	
	/** Adiciona os valores*/
	private double[] adicionarVariaveis(int tamanho, int restri) {
		String aux;
		int cont = 0;
		double[] restricao = new double[tamanho];
		for (int i = 0; i < tamanho; i++) {
			cont += 1;
			if (cont == tamanho) {
				aux = JOptionPane.showInputDialog(null,"Para a restrição " + restri + ", digite o valor do termo independente b"+restri);
				restricao[i] = Double.parseDouble(aux);
			} else {
				aux = JOptionPane.showInputDialog(null, "Para a restrição " + restri + ", digite o valor de X" + cont);
				restricao[i] = Double.parseDouble(aux);
			}
		}
		return restricao;
	}

	/** Recebe dados da função objetivo Z*/
	private double[] valoresDeZ(int tamanho, int cont) {
		String aux;
		double[] Z = new double[tamanho];
		for (int i = 0; i < tamanho; i++) {
			cont += 1;
			if (cont == tamanho) {
				aux = JOptionPane.showInputDialog(null, "Para a função Z, digite o valor do termo independente");
				Z[i] = Double.parseDouble(aux);
			} else {
				aux = JOptionPane.showInputDialog(null, "Para a função Z, digite o valor de X" + cont);
				Z[i] = Double.parseDouble(aux);

			}
		}
		return Z;
	}
}
