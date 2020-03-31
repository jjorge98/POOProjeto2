package estacaoAutoatendimento;

@SuppressWarnings("serial")
public class AcimaEstoqueException extends Exception {
	public AcimaEstoqueException(Produto p, int qtd) {
		super("\nA QUANTIDADE " + qtd + " PARA O PRODUTO " + p.getNome()
				+ " ESTÁ ACIMA DO ESTOQUE DISPONÍVEL. ESTOQUE DISPONÍVEL " + "PARA ESSE PRODUTO É: " + p.getQtd()
				+ ".\n");
	}
}
