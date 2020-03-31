package estacaoAutoatendimento;

public class Mensagens {
	public static void menuInicial() {
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.println(
				" |                            BEM VINDO AO SISTEMA DE VENDAS!                             | ");
		System.out.println(
				" |              AQUI VOCÊ PODE COMPRAR COM A AJUDA DE NOSSO PRÓPRIO SISTEMA!              | ");
		System.out.println(
				" |                                                                                        | ");
		System.out.println(
				" |                                          MENU                                          | ");
		System.out.println(
				" |                                                                                        | ");
		System.out.println(
				" |  1 - COMPRAS                                                                           | ");
		System.out.println(
				" |  2 - ADMINISTRADOR                                                                     | ");
		System.out.println(
				" |  3 - CONSULTAR PEDIDO                                                                  | ");
		System.out.println(
				" |  0 - SAIR                                                                              | ");
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.print(" -> DIGITE A OPÇÃO DESEJADA: ");
	}

	public static void menuCompras() {
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.println(
				" |                                          MENU                                          | ");
		System.out.println(
				" |                                                                                        | ");
		System.out.println(
				" |  1 - LISTAR PRODUTOS                                                                   | ");
		System.out.println(
				" |  2 - ADICIONAR PRODUTO AO CARRINHO                                                     | ");
		System.out.println(
				" |  3 - VISUALIZAR CARRINHO DE COMPRAS                                                    | ");
		System.out.println(
				" |  4 - FINALIZAR COMPRA                                                                  | ");
		System.out.println(
				" |  0 - SAIR SEM COMPRAR                                                                  | ");
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.print(" -> DIGITE A OPÇÃO DESEJADA: ");
	}

	public static void menuAdm() {
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.println(
				" |                                          MENU                                          | ");
		System.out.println(
				" |                                                                                        | ");
		System.out.println(
				" |  1 - CADASTRAR PRODUTO                                                                 | ");
		System.out.println(
				" |  2 - LISTAR COMPRAS REALIZADAS                                                         | ");
		System.out.println(
				" |  3 - CONTROLAR ESTOQUE                                                                 | ");
		System.out.println(
				" |  0 - SAIR                                                                              | ");
		System.out.println(
				" -----------------------------------------------------------------------------------------  ");
		System.out.print(" -> DIGITE A OPÇÃO DESEJADA: ");
	}

	public static void numberException() {
		System.out.print("\n -> POR FAVOR INSIRA UM NÚMERO: \n");
	}
}
