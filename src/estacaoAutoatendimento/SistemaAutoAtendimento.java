package estacaoAutoatendimento;

import java.util.Scanner;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SistemaAutoAtendimento<T> {
	static Pedido user = new Pedido();
	static Scanner input = new Scanner(System.in);
	static LinkedList<Produto> produtosLoja;
	static SQLQuery a = new SQLQuery();

	public static void main(String[] args) {
		int op = 0;
		int opCompras = 0;
		int opAdm = 0;

		do {
			op = -1;

			Mensagens.menuInicial();
			do {
				try {
					op = Integer.parseInt(input.nextLine());
				} catch (NumberFormatException e) {
					Mensagens.numberException();
				}
			} while (op == -1);
			produtosLoja = a.readBD(new ProductTable());

			switch (op) {
			case 0:
				System.out.println("\n\n - >SAINDO! OBRIGADO POR UTILIZAR NOSSO SISTEMA!\n");
				break;
			case 1:
				user = new Pedido();

				do {
					opCompras = -1;
					Mensagens.menuCompras();;
					do {
						try {
							opCompras = Integer.parseInt(input.nextLine());
						} catch (NumberFormatException e) {
							Mensagens.numberException();
						}
					} while (opCompras == -1);

					switch (opCompras) {
					case 0:
						System.out.println("\nSAINDO DO MENU DE COMPRAS! VOLTE NOVAMENTE!\n");
						break;
					case 1:
						Produto.listarProdutos(produtosLoja);
						break;
					case 2:
						addCarrinho();
						break;
					case 3:
						visualizarCarrinho();
						break;
					case 4:
						int ret = finalizaCompra();

						if (ret == 1) {
							System.out.printf(
									"\nPEDIDO FINALIZADO! VALOR FINAL DO PEDIDO: %.2f"
											+ "\nANOTE O NÚMERO DO SEU PEDIDO: %d"
											+ "\nO PRAZO DE ENTREGA É DE ATÉ 15 DIAS. DATA PREVISTA: %s\n",
									user.getValor(), user.getNumPedido(), user.getEntrega());

							opCompras = 0;
						} else if (ret == 2) {
							System.out.println(
									"\nCARRINHO VAZIO, PORTANTO COMPRA NÃO FINALIZADA! RETORNANDO AO MENU PRINCIPAL!\n\n");

							opCompras = 0;
						}

						break;

					default:
						System.out.println("\nOPÇÃO INVÁLIDA, TENTE NOVAMENTE!\n");
						break;
					}
				} while (opCompras != 0);
				break;

			case 2:
				int senha = 123;
				int log = -1;
				int verificacao = 0;

				System.out.println("\n -> POR FAVOR, DIGITE A SENHA: ");
				while (log == -1) {
					try {
						log = Integer.parseInt(input.nextLine());
						if (log == 0) {
							System.out.println("\nVOLTANDO AO MENU INICIAL!\n");
						} else if (senha != log) {
							System.out.print("\n -> SENHA INVÁLIDA! TENTE NOVAMENTE OU DIGITE 0 PARA SAIR: ");
							log = -1;
						} else {
							verificacao = 1;
						}
					} catch (NumberFormatException e) {
						Mensagens.numberException();
					}
				}

				if (verificacao == 1) {
					do {
						Mensagens.menuAdm();

						opAdm = -1;
						do {
							try {
								opAdm = Integer.parseInt(input.nextLine());
							} catch (NumberFormatException e) {
								Mensagens.numberException();
							}
						} while (opAdm == -1);
						switch (opAdm) {
						case 0:
							System.out.println("\nSAINDO DO MENU ADMINISTRADOR!\n");
							break;
						case 1:
							cadastraProduto();
							break;
						case 2:
							LinkedList<Pedido> allShopping = a.readShopping();

							No<Pedido> show = allShopping.getList();
							while (show != null) {
								System.out.println(show.getObj().toString());
								show.getObj().listarCarrinho();

								show = show.getNext();
							}
							break;
						case 3:
							controlaEstoque();
							break;
						default:
							System.out.println("\nOPÇÃO INVÁLIDA, TENTE NOVAMENTE!\n");
							break;
						}
					} while (opAdm != 0);
				}
				break;
			case 3:
				pesquisaPedido();
				break;
			default:
				System.out.println("\nOPÇÃO INVÁLIDA, TENTE NOVAMENTE!\n");
				break;
			}
		} while (op != 0);

		input.close();
	}

	public static void addCarrinho() {
		int cod = -1;
		int qtd = -1;

		Produto.listarProdutos(produtosLoja);

		while (cod == -1) {
			System.out.println(" -> DIGITE 0 CASO QUEIRA CANCELAR OPERAÇÃO.");
			System.out.print(" -> DIGITE O ID DO PRODUTO QUE DESEJA ADICIONAR AO CARRINHO: ");
			while (cod == -1) {
				try {
					cod = Integer.parseInt(input.nextLine());
				} catch (NumberFormatException e) {
					Mensagens.numberException();
				}
			}

			if (cod == 0) {
				System.out.println("\n -> VOLTANDO AO MENU DE COMPRAS!");
			} else {
				No<Produto> aux = produtosLoja.getList();
				while (aux.getNext() != null && aux.getObj().getId() != cod) {
					aux = aux.getNext();
				}
				if (aux.getObj().getId() == cod) {
					if (aux.getObj().getQtd() == 0) {
						System.out.println("\nPRODUTO EM FALTA!\n");
					} else {
						No<Produto> aux2 = user.carrinho.getList();
						while (aux2.getNext() != null && aux2.getObj().getId() != cod) {
							aux2 = aux2.getNext();
						}

						if (aux2.getObj() != null && aux2.getObj().getId() == cod) {
							System.out.println(
									"\n -> PRODUTO JÁ ESTÁ INSERIDO NO CARRINHO. CASO QUEIRA ALTERAR A QUANTIDADE DELE, DIGITE "
											+ "A OPÇÃO 3, DEPOIS A OPÇÃO 1 E POR FIM A OPÇÃO 2!\n");
						} else {
							Produto auxClone = null;
							System.out.print(" -> DIGITE A QUANTIDADE DESEJA COMPRAR: ");
							do {
								try {
									qtd = Integer.parseInt(input.nextLine());
									if (qtd < 0) {
										System.out.println(
												"\nQUANTIDADE INFORMADA É MENOR QUE 0, PORTANTO ESTÁ INVÁLIDA!TENTE NOVAMENTE: \n");
										qtd = -1;
									}
								} catch (NumberFormatException e) {
									Mensagens.numberException();
								}
							} while (qtd == -1);

							if (qtd == 0) {
								System.out.println(
										"\n -> QUANTIDADE INFORMADA É IGUAL A 0! VOLTANDO AO MENU DE COMPRAS!");
							} else {
								try {
									auxClone = (Produto) aux.getObj().clone();
								} catch (CloneNotSupportedException e) {
									System.out.println("Clonagem não permitida");
								}

								try {
									user.addCarrinho(auxClone, auxClone.getQtd(), qtd);
									aux.getObj().setQtd(aux.getObj().getQtd() - qtd);
									user.setValor(user.getValor() + (aux.getObj().getValor() * qtd));
									System.out.println("\nPRODUTO INSERIDO COM SUCESSO!\n");
									break;
								} catch (AcimaEstoqueException e) {
									System.out.println(e.getMessage());
									qtd = -1;
								}
							}
						}
					}
				} else {
					System.out.println("\n -> ID DO PRODUTO INVÁLIDO!\n");
					cod = -1;
				}
			}
		}
	}

	public static void visualizarCarrinho() {
		int opAlter;

		if (user.carrinho.getList().getObj() == null) {
			System.out.println("\n -> CARRINHO VAZIO!\n");
		} else {

			user.listarCarrinho();
			System.out.printf(" -> VALOR TOTAL DO CARRINHO: %.2f\n", (user.getValor()));
			System.out.println("\n -> DESEJA ALTERAR/EXCLUIR ALGUM PRODUTO?");
			System.out.print(" -> 0 PARA NÃO E 1 PARA SIM: ");

			opAlter = -1;
			while (opAlter == -1) {
				try {
					opAlter = Integer.parseInt(input.nextLine());
					if (opAlter != 0 && opAlter != 1) {
						System.out.println(opAlter);
						System.out.print("\nOPÇÃO INVÁLIDA! TENTE NOVAMENTE: ");
						opAlter = -1;
					}
				} catch (NumberFormatException e) {
					Mensagens.numberException();
				}
			}

			if (opAlter == 0) {
				System.out.println("\n -> VOLTANDO AO MENU DE COMPRAS!\n");
			} else if (opAlter == 1) {
				alterCarrinho();
			}
		}

	}

	public static void alterCarrinho() {
		int opAdm;
		int cod;
		int qtd;

		do {
			opAdm = -1;

			System.out.println("\n 0 - SAIR");
			System.out.println(" 1 - EXCLUIR PRODUTO DO CARRINHO");
			System.out.println(" 2 - ALTERAR QUANTIDADE DO PRODUTO");

			do {
				try {
					opAdm = Integer.parseInt(input.nextLine());
				} catch (NumberFormatException e) {
					Mensagens.numberException();
				}
			} while (opAdm == -1);

			if (opAdm == 0) {
				System.out.println("\n -> VOLTANDO AO MENU DE COMPRAS!\n");
			} else if (opAdm == 1) {
				cod = -1;
				while (cod == -1) {
					System.out.println(" -> DIGITE 0 CASO QUEIRA CANCELAR OPERAÇÃO.");
					System.out.print(" -> DIGITE O ID DO PRODUTO QUE DESEJA REMOVER DO CARRINHO: ");
					while (cod == -1) {
						try {
							cod = Integer.parseInt(input.nextLine());
						} catch (NumberFormatException e) {
							Mensagens.numberException();
						}
					}

					if (cod == 0) {
						System.out.println("\n -> VOLTANDO AO MENU DE COMPRAS!");
					} else {
						No<Produto> auxCarrinho = user.carrinho.getList();
						while (auxCarrinho.getNext() != null && auxCarrinho.getObj().getId() != cod) {
							auxCarrinho = auxCarrinho.getNext();
						}

						No<Produto> auxEstoque = produtosLoja.getList();
						while (auxEstoque.getNext() != null && auxEstoque.getObj().getId() != cod) {
							auxEstoque = auxEstoque.getNext();
						}

						if (auxCarrinho.getObj().getId() == cod) {
							auxEstoque.getObj().setQtd(auxEstoque.getObj().getQtd() + auxCarrinho.getObj().getQtd());
							user.setValor(
									user.getValor() - auxCarrinho.getObj().getValor() * auxCarrinho.getObj().getQtd());

							int ret = user.carrinho.remove(auxCarrinho.getObj());

							if (ret == 1) {
								System.out.println("\n -> PRODUTO REMOVIDO COM SUCESSO!\n");
							} else {
								System.out.println("\n -> NÃO FOI POSSÍVEL REMOVER O PRODUTO!\n");
								user.setValor(user.getValor() + auxCarrinho.getObj().getValor());
							}
						} else {
							System.out.println("\n -> ID DO PRODUTO INVÁLIDO!\n");
							cod = -1;
						}
					}
				}
			} else if (opAdm == 2) {
				cod = -1;
				while (cod == -1) {
					System.out.println(" -> DIGITE 0 CASO QUEIRA CANCELAR OPERAÇÃO.");
					System.out.print(" -> DIGITE O ID DO PRODUTO QUE DESEJA ALTERAR A QUANTIDADE: ");
					while (cod == -1) {
						try {
							cod = Integer.parseInt(input.nextLine());
						} catch (NumberFormatException e) {
							Mensagens.numberException();
						}
					}

					if (cod == 0) {
						System.out.println("\n -> VOLTANDO AO MENU DE COMPRAS!");
					} else {
						No<Produto> auxCarrinho = user.carrinho.getList();
						while (auxCarrinho.getNext() != null && auxCarrinho.getObj().getId() != cod) {
							auxCarrinho = auxCarrinho.getNext();
						}

						No<Produto> auxEstoque = produtosLoja.getList();
						while (auxEstoque.getNext() != null && auxEstoque.getObj().getId() != cod) {
							auxEstoque = auxEstoque.getNext();
						}

						if (auxCarrinho.getObj().getId() == cod) {
							user.setValor(user.getValor()
									- (auxCarrinho.getObj().getValor() * auxCarrinho.getObj().getQtd()));
							auxEstoque.getObj().setQtd(auxEstoque.getObj().getQtd() + auxCarrinho.getObj().getQtd());
							auxCarrinho.getObj().setQtd(0);
							qtd = -1;
							while (qtd == -1) {
								System.out.print("\n -> QUANTIDADE DISPONÍVEL: " + auxEstoque.getObj().getQtd());
								System.out.print(
										"\n -> CASO A QUANTIDADE INFORMADA FOR IGUAL A 0, O PRODUTO SERÁ REMOVIDO DO ESTOQUE ");
								System.out.print("\n -> DIGITE A QUANTIDADE DESEJA COMPRAR: ");
								try {
									qtd = Integer.parseInt(input.nextLine());
									if (qtd == 0) {
										int ret = user.carrinho.remove(auxCarrinho.getObj());

										if (ret == 1) {
											System.out.println("\n -> PRODUTO REMOVIDO COM SUCESSO!\n");
										} else {
											System.out.println("\n -> NÃO FOI POSSÍVEL REMOVER O PRODUTO!\n");
											user.setValor(user.getValor() + auxCarrinho.getObj().getValor());
										}
									} else {
										try {
											user.alterQtd(auxCarrinho.getObj(), auxEstoque.getObj().getQtd(), qtd);
											auxEstoque.getObj().setQtd(auxEstoque.getObj().getQtd() - qtd);
											user.setValor(user.getValor() + (auxCarrinho.getObj().getValor() * qtd));
											System.out.println("\nPRODUTO INSERIDO COM SUCESSO!\n");
											break;
										} catch (AcimaEstoqueException e) {
											System.out.println(e.getMessage());
											qtd = -1;
										}
									}
								} catch (NumberFormatException e) {
									Mensagens.numberException();
								}
							}
						} else {
							System.out.println("\n -> ID DO PRODUTO INVÁLIDO!\n");
							cod = -1;
						}
					}
				}
			} else {
				System.out.println("\nOPÇÃO INVÁLIDA, TENTE NOVAMENTE!\n");
			}

			if (user.carrinho.getList().getObj() == null) {
				System.out.println("\n -> CARRINHO VAZIO! VOLTANDO AO MENU DE COMPRAS\n");
				opAdm = 0;
			} else {
				System.out.println("\n -> CARRINHO ATUALIZADO: ");
				user.listarCarrinho();
				System.out.printf(" -> VALOR TOTAL DO CARRINHO: %.2f\n", (user.getValor()));
			}
		} while (opAdm != 0);
	}

	public static int finalizaCompra() {
		String cpf = new String();
		if (user.carrinho.getQtd() == 0) {
			System.out.println();
			return 2;
		} else {
			System.out.println("\nPOR FAVOR, DIGITE SEU CPF SOMENTE COM NÚMEROS: ");
			do {
				cpf = input.nextLine();
				if (cpf.length() != 11) {
					System.out.println("HÁ ALGO ERRADO COM SEU CPF. POR FAVOR REVISE E INSIRA NOVAMENTE: ");
				}
			} while (cpf.length() != 11);

			try {

				user.finalizaCompra(cpf);

				a.insert(new OrderTable(), user);

				No<Produto> aux = user.carrinho.getList();
				while (aux != null) {
					a.insertVenda(user.getNumPedido(), aux.getObj().getId(), aux.getObj().getQtd());
					aux = aux.getNext();
				}

				No<Produto> auxProduto = produtosLoja.getList();
				while (auxProduto != null) {
					a.update(new ProductTable(), auxProduto.getObj());
					auxProduto = auxProduto.getNext();
				}

				return 1;
			} catch (QuantidadeMaiorException e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
	}

	public static void cadastraProduto() {
		Produto novo = null;
		String inputNome = null;
		String inputTipo = null;
		String inputGraEdi = null;
		String inputAutBand = null;
		double inputValor = -1;
		int inputQtd = -1;
		int inputAno = -1;
		int inputId = -1;

		No<Produto> aux = produtosLoja.getList();
		while (aux.getNext() != null) {
			aux = aux.getNext();
		}
		inputId = aux.getObj().getId() + 1;

		System.out.println(" -> DIGITE 0 A QUALQUER MOMENTO SE QUISER VOLTAR AO MENU ADMINISTRADOR. ");
		System.out.println(" -> DIGITE O NOME DO NOVO PRODUTO: ");
		inputNome = input.nextLine().toUpperCase();
		if (inputNome == "0") {
			return;
		} else {
			System.out.println(" -> DIGITE O VALOR DO NOVO PRODUTO: ");
			do {
				try {
					inputValor = Double.parseDouble(input.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("NÃO FOI DIGITADO UM VALOR. TENTE INSERIR O VALOR NOVAMENTE: ");
				}
			} while (inputValor == -1);

			if (inputValor == 0) {
				return;
			} else {
				System.out.println(
						" -> DIGITE A QUANTIDADE DO NOVO PRODUTO. PS: SE O NÚMERO INFORMADO FOR 0, VOCÊ RETORNARÁ AO "
								+ "MENU ADMINISTRADOR: ");
				do {
					try {
						inputQtd = Integer.parseInt(input.nextLine());
					} catch (NumberFormatException e) {
						System.out.println("NÃO FOI DIGITADO UM NÚMERO. TENTE INSERIR O VALOR NOVAMENTE!");
					}
				} while (inputQtd == -1);

				if (inputQtd == 0) {
					return;
				} else {
					System.out.println(" -> DIGITE O ANO DO NOVO PRODUTO: ");

					do {
						try {
							inputAno = Integer.parseInt(input.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("NÃO FOI DIGITADO UM NÚMERO. TENTE INSERIR O VALOR NOVAMENTE!");
						}
					} while (inputAno == -1);

					if (inputAno == 0) {
						return;
					} else {
						System.out.println(" -> DIGITE O TIPO DO NOVO PRODUTO (CD, DVD ou LIVRO): ");
						inputTipo = input.nextLine().toUpperCase();
						if (inputTipo == "0") {
							return;
						} else {
							if (inputTipo.equals("LIVRO")) {
								System.out.println(" -> DIGITE A EDITORA DO NOVO LIVRO: ");
								inputGraEdi = input.nextLine().toUpperCase();
								if (inputGraEdi.equals("0")) {
									return;
								} else {
									System.out.println(" -> DIGITE O(A) AUTOR(A) DO NOVO LIVRO: ");
									inputAutBand = input.nextLine().toUpperCase();
									if (inputAutBand.equals("0")) {
										return;
									}
								}
							} else if (inputTipo.equals("DVD") || inputTipo.equals("CD")) {
								System.out.println(" -> DIGITE A GRAVADOR DO NOVO CD/DVD: ");
								inputGraEdi = input.nextLine().toUpperCase();
								if (inputGraEdi.equals("0")) {
									return;
								} else {
									System.out.println(" -> DIGITE A BANDO OU O(A) CANTOR(A) DO NOVO CD/DVD: ");
									inputAutBand = input.nextLine().toUpperCase();
									if (inputAutBand.equals("0")) {
										return;
									}
								}
							} else {
								System.out.println("\nNÃO FOI DIGITADO UM TIPO DE PRODUTO VÁLIDO! "
										+ "VOLTANDO AO MENU ADMINISTRADOR!\n");
								return;
							}
						}
					}
				}
			}
		}

		if (inputTipo == "LIVRO") {
			novo = new Livro(inputId, inputNome, inputQtd, inputTipo, inputAno, inputGraEdi, inputAutBand, inputValor);
		} else if (inputTipo == "DVD") {
			novo = new DVD(inputId, inputNome, inputQtd, inputTipo, inputAno, inputGraEdi, inputAutBand, inputValor);
		} else {
			novo = new CD(inputId, inputNome, inputQtd, inputTipo, inputAno, inputGraEdi, inputAutBand, inputValor);
		}

		try {
			novo.cadastroProduto(produtosLoja);
			produtosLoja = a.readBD(new ProductTable());
		} catch (ProdutoCadastradoException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void controlaEstoque() {
		int opAlter = -1;
		int cod;
		int qtd;

		do {
			Produto.listarProdutos(produtosLoja);
			System.out.print("\nDIGITE O ID DO PRODUTO QUE DESEJA ALTERAR O ESTOQUE OU DIGITE 0 PARA SAIR: ");

			cod = -1;
			while (cod == -1) {
				try {
					cod = Integer.parseInt(input.nextLine());
				} catch (NumberFormatException e) {
					Mensagens.numberException();
				}
			}

			if (cod == 0) {
				break;
			} else {
				No<Produto> aux1 = produtosLoja.getList();
				while (aux1.getNext() != null && aux1.getObj().getId() != cod) {
					aux1 = aux1.getNext();
				}

				if (aux1.getObj().getId() != cod) {
					System.out.println("\nPRODUTO NÃO ENCONTRADO!\n");
				} else {
					int opControl = -1;
					System.out.println("\n 0 - SAIR");
					System.out.println(" 1 - ALTERAR QUANTIDADE");
					System.out.println(" 2 - ALTERAR VALOR");
					System.out.println(
							" 3 - EXCLUIR PRODUTO (OBS.: EXCLUIR UM PRODUTO É APENAS EXCLUIR TODOS OS ITENS DE ESTOQUE!)");
					System.out.println("\n -> DIGITE A OPÇÃO DESEJADA: ");

					while (opControl == -1) {
						try {
							opControl = Integer.parseInt(input.nextLine());
						} catch (NumberFormatException e) {
							Mensagens.numberException();
						}

						if (opControl > 3) {
							System.out.println("\nOPÇÃO INVÁLIDA, TENTE NOVAMENTE: ");
							opControl = -1;
						}
					}

					switch (opControl) {
					case 0:
						System.out.println("\nSAINDO!\n");
						break;
					case 1:
						System.out.println("\nDIGITE A NOVA QUANTIDADE DO PRODUTO OU 0 PARA SAIR: \n");

						qtd = -1;
						while (qtd == -1) {
							try {
								qtd = Integer.parseInt(input.nextLine());
							} catch (NumberFormatException e) {
								Mensagens.numberException();
							}
						}

						if (qtd == 0) {
							break;
						} else {
							aux1.getObj().setQtd(qtd);
							a.update(new ProductTable(), aux1.getObj());
						}
						break;
					case 2:
						System.out.println("\nDIGITE O NOVO VALOR DO PRODUTO OU 0 PARA SAIR: \n");

						double valor = -1;

						while (valor == -1) {
							try {
								valor = Double.parseDouble(input.nextLine());
							} catch (NumberFormatException e) {
								Mensagens.numberException();
							}
						}

						if (valor == 0) {
							break;
						} else {
							aux1.getObj().setValor(valor);
							a.update(new ProductTable(), aux1.getObj());
						}
						break;
					case 3:
						aux1.getObj().setQtd(0);
						a.update(new ProductTable(), aux1.getObj());
						break;
					}
				}
			}
		} while (opAlter != 0);
	}

	public static void pesquisaPedido() {
		LinkedList<Pedido> allShopping = a.readShopping();
		int pedido = -1;

		System.out.println("DIGITE O NUMERO DO PEDIDO: ");
		do {
			try {
				pedido = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException e) {
				Mensagens.numberException();
			}
		} while (pedido == -1);

		No<Pedido> aux = allShopping.getList();
		while (aux.getNext() != null && aux.getObj().getNumPedido() != pedido) {
			aux = aux.getNext();
		}

		if (aux.getObj().getNumPedido() == pedido) {
			System.out.println(aux.getObj().toString());
			aux.getObj().listarCarrinho();
		} else {
			System.out.println("\nPEDIDO NÃO ENCONTRADO! VERIFIQUE E TENTE NOVAMENTE\n");
		}

	}
}
