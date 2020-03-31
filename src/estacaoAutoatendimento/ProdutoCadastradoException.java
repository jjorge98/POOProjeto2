package estacaoAutoatendimento;

@SuppressWarnings("serial")
public class ProdutoCadastradoException extends Exception {
	public ProdutoCadastradoException(Produto p) {
		super("\nO PRODUTO " + p.getNome() + " J� EST� CADASTRADO COM O ID: " + p.getId() + ".\n");
	}
}
