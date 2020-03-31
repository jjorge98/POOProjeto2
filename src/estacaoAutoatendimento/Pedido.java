package estacaoAutoatendimento;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pedido implements Cloneable {
	private int numPedido;
	private String cpf;
	private String entrega;
	private double valor;
	LinkedList<Produto> carrinho;

	public Pedido() {
		numPedido = 0;
		cpf = "";
		entrega = "";
		valor = 0;
		carrinho = new LinkedList<Produto>();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void finalizaCompra(String cpf) throws QuantidadeMaiorException {
		int count = 0;

		No<Produto> aux = carrinho.getList();
		while (aux != null) {
			if (aux.getObj().getQtd() > 10) {
				count++;
			}
			aux = aux.getNext();
		}

		if (count != 0) {
			throw new QuantidadeMaiorException(count);
		} else {
			this.geraPedido();
			this.geraPrazo();
			this.cpf = cpf;
		}
	}

	public void addCarrinho(Produto add, int qtdIni, int qtdAdd) throws AcimaEstoqueException {
		if (qtdIni >= qtdAdd) {
			add.setQtd(qtdAdd);
			carrinho.add(add);
		} else {
			throw new AcimaEstoqueException(add, qtdAdd);
		}
	}

	public void alterQtd(Produto alter, int qtdIni, int qtdAdd) throws AcimaEstoqueException {
		if (qtdIni >= qtdAdd) {
			alter.setQtd(qtdAdd);
		} else {
			throw new AcimaEstoqueException(alter, qtdIni);
		}

	}

	@SuppressWarnings("rawtypes")
	public void geraPedido() {
		OrderTable o = new OrderTable();
		@SuppressWarnings("unchecked")
		LinkedList<Pedido> atuais = o.readBD();
		No<Pedido> aux = atuais.getList();
		if (aux.getObj() == null) {
			this.setNumPedido(1);
			System.out.println();
		} else {

			while (aux.getNext() != null) {
				aux = aux.getNext();
			}
			this.setNumPedido(aux.getObj().getNumPedido() + 1);
		}

	}

	public void geraPrazo() {
		String formato = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);

		String date = simpleDateFormat.format(new Date());
		String date2[] = date.split("-");

		int dia = Integer.parseInt(date2[0]);
		int mes = Integer.parseInt(date2[1]);
		int ano = Integer.parseInt(date2[2]);

		dia += 15;
		if (dia > 31) {
			dia -= 30;
			mes += 1;
			if (mes > 12) {
				mes -= 12;
				ano += 1;
			}
		}

		this.setEntrega(ano + "-" + mes + "-" + dia);
	}

	public void listarCarrinho() {
		Produto.listarProdutos(carrinho);
	}

	@Override
	public String toString() {
		return "NumPedido: " + numPedido + "\nCpf: " + cpf + "\nEntrega:" + entrega + "\nValor: " + valor
				+ "\nCarrinho: ";
	}

	public int getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(int numPedido) {
		this.numPedido = numPedido;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
