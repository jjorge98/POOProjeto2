package estacaoAutoatendimento;

@SuppressWarnings("serial")
public class AcimaEstoqueException extends Exception {
	public AcimaEstoqueException(Produto p, int qtd) {
		super("\nA QUANTIDADE " + qtd + " PARA O PRODUTO " + p.getNome()
				+ " EST� ACIMA DO ESTOQUE DISPON�VEL. ESTOQUE DISPON�VEL " + "PARA ESSE PRODUTO �: " + p.getQtd()
				+ ".\n");
	}
}
