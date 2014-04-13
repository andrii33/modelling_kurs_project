package main.java;

import process.Actor;
import process.DispatcherFinishException;
import process.IWaitCondition;
import process.QueueForTransactions;
import rnd.Randomable;

public class Kassir extends Actor {
	
	//Продолжительность работы кассира
	private double finishTime;
	//Очередь у касс
	private QueueForTransactions queueKassa;
	//Текущий обслуживаемый клиент
	private Klient klient;
	//Время обслуживания клиента кассиром
	public Randomable rndVremiaObslKl;
	
	public Kassir(String name, VisualFrame gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataMTime().getDouble();
		queueKassa = model.getQueueKassa();
		rndVremiaObslKl = gui.getRndVremiaObslKl();
	}
	
	//Правила действия для кассира
	protected void rule() {
		//Проверка: не пустая ли очередь на кассе?
		IWaitCondition notIsEmpty = new IWaitCondition() {
			public boolean testCondition() {
				return queueKassa.size() > 0;
			}
		};
		//Цикл действий для кассира
		do {
			try {
				//Если очередь на кассе пустая, то ждать клиентов
				waitForCondition(notIsEmpty);
			} catch (DispatcherFinishException e) {
				return;
			}
			//Взять из очереди первого клиента
			klient = (Klient) queueKassa.peekFirst();
			klient.setObsluzhen(true);
			//Обслуживание клиента кассиром
			holdForTime(rndVremiaObslKl.next());
		} while (getDispatcher().getCurrentTime() <= finishTime);
	}
	
	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	public QueueForTransactions getQueueKassa() {
		return queueKassa;
	}

	public void setQueueKassa(QueueForTransactions queueKassa) {
		this.queueKassa = queueKassa;
	}

	public Klient getKlient() {
		return klient;
	}

	public void setKlient(Klient klient) {
		this.klient = klient;
	}


}
