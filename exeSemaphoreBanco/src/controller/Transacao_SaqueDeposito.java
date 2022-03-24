package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class Transacao_SaqueDeposito extends Thread {
	private int Conta;
	private double saldo;
	private double valorTransacao;
	private int tipoTransacao;
	private Semaphore saque;
	private Semaphore deposito;

	public Transacao_SaqueDeposito(int conta, double saldo, double valorTransacao, int tipoTransacao, Semaphore saque,
			Semaphore deposito) {
		this.Conta = conta;
		this.saldo = saldo;
		this.tipoTransacao = tipoTransacao;
		this.valorTransacao = valorTransacao;
		this.saque = saque;
		this.deposito = deposito;
	}

	@Override
	public void run() {

		if (tipoTransacao == 1) {
			try {
				saque.acquire();
				Saque();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				saque.release();
			}

		} else {
			try {
				deposito.acquire();
				Deposito();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				deposito.release();
			}
		}

	}

	private void Saque() {
		if (saldo < valorTransacao) {
			System.out.println("Conta #" + Conta + " não há saldo suficiente para realizar o saque");
		} else {

			saldo -= valorTransacao;
			DecimalFormat df = new DecimalFormat("#,###.00");

			System.out.printf("Conta #" + Conta + " realizou saque de R$" + df.format(valorTransacao)
					+ ", Saldo atual: R$" + df.format(saldo) + "\n");
			Tempo();

		}
	}

	private void Deposito() {
		saldo += valorTransacao;
		DecimalFormat df = new DecimalFormat("#,###.00");

		System.out.printf("Conta #" + Conta + " realizou deposito de R$" + df.format(valorTransacao)
				+ ", Saldo atual: R$" + df.format(saldo) + "\n");
		Tempo();
	}

	private void Tempo() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}