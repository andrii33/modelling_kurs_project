package main.java;

import process.Actor;
import process.DispatcherFinishException;
import process.IWaitCondition;
import process.QueueForTransactions;
import rnd.Randomable;
import stat.Histo;

public class Klient extends Actor {
	//���������� ������� ������� 
	public Randomable rndKolPokupokKl;
	//����� ������ ������
	public Randomable rndVremiaVyboraTov;
	//��������� ������� � �������� ����
	private QueueForTransactions queueTorgovyiZal;
	//������� � ����
	private QueueForTransactions queueKassa;
	//������� ����, ��� ������ ��������
	private boolean obsluzhen;
	//�����, ����� ������ ���� � ������� � �����
	private double timeAddToQueueKassa;
	//����������� ��� ������� ���������� � ������� � �����
	private Histo histoForQueueTimeInQueueKassa;
	
	public Klient(String name, VisualFrame gui, Model model) {
		setNameForProtocol(name);
		obsluzhen = false;
		rndKolPokupokKl = gui.getRndKolPokupokKl();
		rndVremiaVyboraTov = gui.getRndVremiaVyboraTov();
		queueTorgovyiZal = model.getQueueTorgovyiZal();
		queueKassa = model.getQueueKassa();
		histoForQueueTimeInQueueKassa = model.getHistoForQueueTimeInQueueKassa();
	}
	
	public boolean isObsluzhen() {
		return obsluzhen;
	}

	public void setObsluzhen(boolean obsluzhen) {
		this.obsluzhen = obsluzhen;
	}
	
	//������� �������� ��� �������
	protected void rule() {
		IWaitCondition isObsluzhen = new IWaitCondition() {
			public boolean testCondition() {
				return obsluzhen;
			}
		};
		//���������� � ��������� ������� (������ ����� � �������� ���)
		queueTorgovyiZal.add(this);
		//����� ������ ��������
		holdForTime(rndKolPokupokKl.next() * rndVremiaVyboraTov.next());
		//������� ������� �� ��������� ���� � ������� � �����
		queueTorgovyiZal.remove(this);
		queueKassa.add(this);
		//���������� �������� �������, ����� ������ ���� � ������� � �����
		setTimeAddToQueueKassa(getDispatcher().getCurrentTime());
		try {
			//�����, ����� �������� ������
			waitForCondition(isObsluzhen);
		} catch (DispatcherFinishException e) {
			return;
		}
		//�������� ������� �� ������� �� �����, ����� �� ����� �������� ��������
		queueKassa.remove(this);
		//�������� �������� ������� ����������� 
		histoForQueueTimeInQueueKassa.add(getDispatcher().getCurrentTime() - getTimeAddToQueueKassa());
	}

	public double getTimeAddToQueueKassa() {
		return timeAddToQueueKassa;
	}

	public void setTimeAddToQueueKassa(double timeAddToQueueKassa) {
		this.timeAddToQueueKassa = timeAddToQueueKassa;
	}
	
	public QueueForTransactions getQueueTorgovyiZal() {
		return queueTorgovyiZal;
	}

	public void setQueueTorgovyiZal(QueueForTransactions queueTorgovyiZal) {
		this.queueTorgovyiZal = queueTorgovyiZal;
	}

	public QueueForTransactions getQueueKassa() {
		return queueKassa;
	}

	public void setQueueKassa(QueueForTransactions queueKassa) {
		this.queueKassa = queueKassa;
	}

	public Histo getHistoForQueueTimeInQueueKassa() {
		return histoForQueueTimeInQueueKassa;
	}

	public void setHistoForQueueTimeInQueueKassa(Histo histoForQueueTimeInQueueKassa) {
		this.histoForQueueTimeInQueueKassa = histoForQueueTimeInQueueKassa;
	}

}
