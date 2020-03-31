package estacaoAutoatendimento;

public class DVD extends Produto {
	private String bandaCantor = "null";
	private String gravadora = "null";

	public DVD(int id, String nome, int qtd, String tipo, int ano, String gravadora, String banda, double valor) {
		super(nome, valor, qtd, tipo, ano, id);
		this.bandaCantor = banda;
		this.gravadora = gravadora;
	}

	public String getProprietario() {
		return bandaCantor;
	}

	public void setBandaCantor(String bandaCantor) {
		this.bandaCantor = bandaCantor;
	}

	public String getEdiGra() {
		return gravadora;
	}

	public void setGravadora(String gravadora) {
		this.gravadora = gravadora;
	}

	@Override
	public String toString() {
		return "\n - " + tipo.toUpperCase() + "\n" + "\n - Id: " + this.id + "\n - Nome: " + this.nome
				+ "\n - Banda/cantor(a): " + this.bandaCantor + "\n - Gravadora: " + this.gravadora + "\n - Ano: "
				+ this.ano + "\n - Valor: R$" + this.valor + "\n - Quantidade: " + this.qtd;
	}
}
