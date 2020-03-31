package estacaoAutoatendimento;

@SuppressWarnings("serial")
public class QuantidadeMaiorException extends Exception {
	public QuantidadeMaiorException(int qtd) {
		super("\nEXISTE(M) " + qtd
				+ "ITEM(NS) QUE ESTÁ(ÃO) COM A QUANTIDADE MAIOR QUE 10. NOSSA LOJA PERMITE A COMPRA MÁXIMA DE 10 "
				+ "ITENS POR PRODUTO. VOLTE A OPÇÃO 3 DO MENU DE COMPRAS"
				+ " PARA VISUALIZAR O CARRINHO E ALTERAR A QUANTIDADE DO(S) PRODUTO(S).\n");
	}
}
