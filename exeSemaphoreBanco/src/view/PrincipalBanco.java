package view;

import java.util.Random;
import java.util.concurrent.Semaphore;

import controller.Transacao_SaqueDeposito;

public class PrincipalBanco {

	public static void main(String[] args) {

		int permissaoSaque = 1;
		int permissaoDeposito = 1;
		Random gerador = new Random();
		double saldo;
		double valorTransacao;
		int tipoTransacao;

		Semaphore Saque = new Semaphore(permissaoSaque);
		Semaphore Deposito = new Semaphore(permissaoDeposito);

		for (int Conta = 0; Conta < 20; Conta++) {

			tipoTransacao = (int) ((Math.random() * 2) + 1);

			saldo = ((gerador.nextDouble() * 5000) + 1000);

			valorTransacao = ((gerador.nextDouble() * 5000) + 1000);

			Thread transacao = new Transacao_SaqueDeposito(Conta, saldo, valorTransacao, tipoTransacao, Saque, Deposito);
			transacao.start();

		}
	}

}