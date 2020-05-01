package br.ufpb.dcx.tcc.pl;

/**
 * 
 * Classe concreta Programação Linear, define o tipo de problema 
   que será maximizado
 * 
 * @author Wellerson Alex
 *
 */
public class ProgramacaoLinear {

	/** Problema com uma restrição*/
	public TipoProblemaPL maximizar1R() {
		return TipoProblemaPL.Maximizar1R;
		
	}

	/** Problema com duas restrições*/
	public TipoProblemaPL maximizar2R() {
		return TipoProblemaPL.Maximizar2R;
		
	}

	/** Problema com três restrições*/
	public TipoProblemaPL maximizar3R() {
		return TipoProblemaPL.Maximizar3R;
		
	}

	/** Problema com quatro restrições*/
	public TipoProblemaPL maximizar4R() {
		return TipoProblemaPL.Maximizar4R;
		
	}

	/** Problema com cinco restrições*/
	public TipoProblemaPL maximizar5R() {
		return TipoProblemaPL.Maximizar5R;
		
	}
	
	/** Cada classe implementa seu prórpio método desse tipo*/
	public void entradaDados() {
	}

}
