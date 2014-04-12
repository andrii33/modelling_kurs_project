package main.java;

import process.Actor;
import process.DispatcherFinishException;
import process.IWaitCondition;
import process.QueueForTransactions;
import rnd.Randomable;

public class Kassir extends Actor {
	//����������������� ������ �������
	private double finishTime;
	//������� � ����
	private QueueForTransactions queueKassa;
	//������� ������������� ������
	private Klient klient;
	//����� ������������ ������� ��������
	public Randomable rndVremiaObslKl;
	
	public Kassir(String name, VisualFrame gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataMTime().getDouble();
		queueKassa = model.getQueueKassa();
		rndVremiaObslKl = gui.getRndVremiaObslKl();
	}
	
	//������� �������� ��� �������
	protected void rule() {
		//��������: �� ������ �� ������� �� �����?
		IWaitCondition notIsEmpty = new IWaitCondition() {
			public boolean testCondition() {
				return queueKassa.size() > 0;
			}
		};
		//���� �������� ��� �������
		do {
			try {
				//���� ������� �� ����� ������, �� ����� ��������
				waitForCondition(notIsEmpty);
			} catch (DispatcherFinishException e) {
				return;
			}
			//����� �� ������� ������� �������
			klient = (Klient) queueKassa.peekFirst();
			klient.setObsluzhen(true);
			//������������ ������� ��������
			holdForTime(rndVremiaObslKl.next());
		} while (getDispatcher().getCurrentTime() <= finishTime);
	}

}
