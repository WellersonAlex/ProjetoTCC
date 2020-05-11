package br.ufpb.dcx.tcc.pl;

/**
 * 
 * Classe concreta que apenas chama outras classes, é uma fachada.
 * 
 * @author Wellerson Alex
 *
 */
public class FachadaPL {
	
	/** Propriedade probelma */
	private ProgramacaoLinear problema = new ProgramacaoLinear();
	
	/** Escolhe o tipo de maximização de acordo com o tipo de problema */
	public FachadaPL(TipoProblemaPL tipoProblema) {
		
		/** Probelmas com até uma restrição */
		if(tipoProblema.equals(problema.maximizar1R())) {
			problema = new Maximizar1R();
		}
		/** Probelmas com até duas restrições */
		else if(tipoProblema.equals(problema.maximizar2R())) {
			problema = new Maximizar2R();
		}
		/** Probelmas com até três restrições */
		else if(tipoProblema.equals(problema.maximizar3R())) {
			problema = new Maximizar3R();
		}
		/** Probelmas com até quatro restrições */
		else if(tipoProblema.equals(problema.maximizar4R())) {
			problema = new Maximizar4R();
		}
		/** Probelmas com até 5 restrições */
		else {
			problema = new Maximizar5R();
		}
	}
	
	/** 
	 * 
	 * Recebe todos os dados de entrada: função objetivo, restrições, variáveis,
	   variáveis de folga e termos independentes.  
	 * 
	 * @return Solução ótima para Z, todas as iterações feitas na tabela e
	   os valores mais restritivos para cada escolha de linha em cada iteração. 
	 * 
	 */
	public void entradaDados() {
		problema.entradaDados();
	}
}
