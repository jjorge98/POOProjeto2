package estacaoAutoatendimento;

public abstract class Produto implements Cloneable {
	protected int id;
	protected String nome;
	protected double valor;
	protected int qtd;
	protected int ano;
	protected String tipo;

	public Produto(String nome, double valor, int qtd, String tipo, int ano, int id) {
		this.nome = nome;
		this.valor = valor;
		this.qtd = qtd;
		this.tipo = tipo;
		this.ano = ano;
		this.id = id;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static void listarProdutos(LinkedList<Produto> arrayProdutos) {
		No<Produto> aux = arrayProdutos.getList();
		while (aux != null) {
			System.out.println(
					"------------------------------------------------------------------------------------------");
			System.out.println(aux.getObj().toString() + "\n");
			aux = aux.getNext();
		}
		System.out.println(
				"------------------------------------------------------------------------------------------\n\n");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (ano != other.ano)
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void cadastroProduto(LinkedList<Produto> atual) throws ProdutoCadastradoException {
		No<Produto> aux = atual.getList();
		while (aux != null) {
			if (aux.getObj().getNome().equals(this.nome)) {
				throw new ProdutoCadastradoException(aux.getObj());
			} else {
				aux = aux.getNext();
			}
		}

		SQLQuery a = new SQLQuery();

		a.insert(new ProductTable(), this);
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public abstract String getProprietario();

	public abstract String getEdiGra();

	public abstract String toString();
}
