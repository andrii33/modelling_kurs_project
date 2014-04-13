package main.java;

import process.Actor;
import process.DispatcherFinishException;
import process.IWaitCondition;
import process.QueueForTransactions;
import rnd.Randomable;
import stat.Histo;

public class Klient extends Actor {
	//Количество покупок клиента 
	public Randomable rndKolPokupokKl;
	//Время выбора товара
	public Randomable rndVremiaVyboraTov;
	//Фиктивная очередь в торговом зале
	private QueueForTransactions queueTorgovyiZal;
	//Очередь у касс
	private QueueForTransactions queueKassa;
	//Признак того, что клиент обслужен
	private boolean obsluzhen;
	//Время, когда клиент стал в очередь к кассе
	private double timeAddToQueueKassa;
	//Гистограмма для времени пребывания в очереди к кассе
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
	
	//Правила действия для клиента
	protected void rule() {
		IWaitCondition isObsluzhen = new IWaitCondition() {
			public boolean testCondition() {
				return obsluzhen;
			}
		};
		//Добавление в фиктивную очередь (клиент вошел в торговый зал)
		queueTorgovyiZal.add(this);
		//Выбор товара клиентом
		holdForTime(rndKolPokupokKl.next() * rndVremiaVyboraTov.next());
		//Переход клиента из торгового зала в очередь к кассе
		queueTorgovyiZal.remove(this);
		queueKassa.add(this);
		//Сохранение значения времени, когда клиент стал в очередь к кассе
		setTimeAddToQueueKassa(getDispatcher().getCurrentTime());
		try {
			//Ждать, когда обслужит кассир
			waitForCondition(isObsluzhen);
		} catch (DispatcherFinishException e) {
			return;
		}
		//Удаление клиента из очереди на кассе, когда он будет обслужен кассиром
		queueKassa.remove(this);
		//Передача значения времени гистограмме 
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
