package br.ufpb.dcx.tcc.mainpl;

import javax.swing.JOptionPane;

import br.ufpb.dcx.tcc.pl.FachadaPL;
import br.ufpb.dcx.tcc.pl.TipoProblemaPL;

/**
 * 
 * Classe concreta que permite executar a aplicação de Programação Linear.
 * 
 * @author Wellerson Alex
 *
 */
public class AppPL {
	

	/** Executa toda a aplicação recebendo os dados de entrada*/
	public static void main(String[] args) {

		/** Propriedade fachada*/
		FachadaPL fachada;
		
		/** Proprieade valor incorreto para evitar erros de entrada*/
		boolean valorIncorreto = true;
		
		/** Propriedade tipo de problema*/
		int tipoProblema;
		
		/** Recebe o tipo de problema*/
		String aux = JOptionPane.showInputDialog(null, "Informe o número de restrições do problema?",
				"Valores possíveis: 1, 2, 3, 4, 5", JOptionPane.QUESTION_MESSAGE);
		tipoProblema = Integer.parseInt(aux);
		
		/** Corrige os dados de entrada caso venha incorreto*/
		while(valorIncorreto) {
			
			if(tipoProblema > 0 && tipoProblema < 6) {
				valorIncorreto = false;
			}else {
				JOptionPane.showMessageDialog(null, "Apenas probelmas com o número de restrições entre 1 e 5", 
						"O programa não funciona para "+aux+" rstrições", JOptionPane.ERROR_MESSAGE);
				aux = JOptionPane.showInputDialog(null, "Informe o número de restrições do problema?",
						"Valores possíveis: 1, 2, 3, 4, 5", JOptionPane.QUESTION_MESSAGE);
				tipoProblema = Integer.parseInt(aux);
			}
		}
		
		/** Direciona o tipo de problema para sua determinada classe*/
		if (tipoProblema == 1) {
			fachada = new FachadaPL(TipoProblemaPL.Maximizar1R);
		} else if (tipoProblema == 2) {
			fachada = new FachadaPL(TipoProblemaPL.Maximizar2R);
		} else if (tipoProblema == 3) {
			fachada = new FachadaPL(TipoProblemaPL.Maximizar3R);
		} else if (tipoProblema == 4) {
			fachada = new FachadaPL(TipoProblemaPL.Maximizar4R);
		} else{
			fachada = new FachadaPL(TipoProblemaPL.Maximizar5R);	
		}
		
		fachada.entradaDados();
	}

}
