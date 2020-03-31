package estacaoAutoatendimento;

public class Livro extends Produto {
	private String autor = "null";
	private String editora = "null";

	public Livro(int id, String nome, int qtd, String tipo, int ano, String editora, String autor, double valor) {
		super(nome, valor, qtd, tipo, ano, id);
		this.autor = autor;
		this.editora = editora;
	}

	public String getProprietario() {
		return this.autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEdiGra() {
		return this.editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	@Override
	public String toString() {
		return "\n - " + this.tipo.toUpperCase() + "\n" + "\n - Id: " + this.id + "\n - Nome: " + this.nome
				+ "\n - Autor(a): " + this.autor + "\n - Editora: " + this.editora + "\n - Ano: " + this.ano
				+ "\n - Valor: R$" + this.valor + "\n - Quantidade: " + this.qtd;
	}
}
