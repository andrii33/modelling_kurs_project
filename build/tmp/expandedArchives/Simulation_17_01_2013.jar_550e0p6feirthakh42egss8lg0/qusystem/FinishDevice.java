package qusystem;

import process.Actor;
import process.DispatcherFinishException;
import process.IWaitCondition;
import process.QueueForTransactions;
import rnd.Randomable;

public class FinishDevice extends Actor {
	protected Object transaction;

	protected QueueForTransactions inputQueue;

	protected double finishTime ;
	
	private Randomable random;

	protected Randomable getRandom() {
		if(random==null)
			System.out.println("�� ��������� random ��� FinishDevicer (GetPutDevice ).");
		return random;
	}

	public void setRandom(Randomable random) {
		this.random = random;
	}

	public FinishDevice() {
		super();
	}

	/**
	 * Insert the method's description here. Creation date: (04.02.2006
	 * 18:43:02)
	 * 
	 * @return double
	 */
	public double getFinishTime() {
		return finishTime;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:21:15)
	 * 
	 * @return qusystem.QueueForTransactions
	 */
	public QueueForTransactions getInputQueue() {
		if(inputQueue==null)
			System.out.println("�� ��������� inputQueue ��� FinishDevice (GetPutDevice ).");

		return inputQueue;
	}

	public Object getTransaction() {
		return transaction;
	}

	public void rule() {
		while (getDispatcher().getCurrentTime()<=finishTime) {
			try {
				waitForCondition(new IWaitCondition() {
					public String toString() {
						return "���� � " + inputQueue.getNameForProtocol()
								+ " �'������� ����������";
					}
					public boolean testCondition() {
						return inputQueue.size() > 0;
					}				
				});
			} catch (DispatcherFinishException e) {
				return;
			}
			if (getDispatcher().getCurrentTime()>finishTime) break;
			transaction = inputQueue.removeFirst();
			beforeHold();
			holdForTime(getRandom().next());
			afterHold();
		}
	}
	/**
	 * ����� ���������� ����� ��������� ������ �� ��������� �����. ����� ����
	 * ����������� � ����������. � ��� ����� ��������� ������� ��������� �����
	 * ������������� ������
	 */
	protected void beforeHold() {
	}

	/**
	 * ����� ���������� ����� �������� ������ �� ��������� �����. � ��� �����
	 * ��������� ������� ��������� ������ ����� ������������� �������� ����
	 * "������"
	 */
	protected void afterHold() {
	}

	
	/**
	 * Insert the method's description here. Creation date: (04.02.2006
	 * 18:43:02)
	 * 
	 * @param newFinishTime
	 *            double
	 */
	public void setFinishTime(double newFinishTime) {
		finishTime = newFinishTime;
	}

	/**
	 * Insert the method's description here. Creation date: (13.05.2005
	 * 21:21:15)
	 * 
	 * @param newInputQueue
	 *            qusystem.QueueForTransactions
	 */
	public void setInputQueue(QueueForTransactions newInputQueue) {
		inputQueue = newInputQueue;
	}
	

}
