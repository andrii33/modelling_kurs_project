package process;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Dispatcher implements Runnable {
	/**
	 * ������� ����������� ����������
	 */
	private boolean starting=false;

	
	/**
	 * ������ �� ����� ���������� ������ �������� ����������
	 */
	private Thread thread;

	/**
	 * �������� �������� ���������� �������
	 * ���� -1, �� �� �� ���������
	 */
	private double currentTime ;

	/**
	 * ������ "�������", �������� ��������� ���� ������� ��������
	 */
	private List<Actor> startList = new ArrayList<Actor>();

	/**
	 * ������ "�������", ������� ������������� ���� ������� �������� ��
	 * ���������� ������� �������
	 */
	private List<Actor> timingActorQueue = new ArrayList<Actor>();

	/**
	 * ������ "�������", ������� ������������� ���� ������� �������� ��
	 * ���������� ���������� �������
	 */
	private List<Actor> waitingActorQueue = new ArrayList<Actor>();


	/**
	 * ��� ����� ��� ������ ����������� ��������� ������ ������. ��� "Console"
	 * ������������ ����� ��������� �� �������
	 */
	private String protocolFileName = "Console";

	/**
	 * ������ ��� ���������� ������� dispatcherStart
	 */
	private Vector<DispatcherStartListener> dispatcherStartListeners;

	/**
	 * ������ ��� ���������� ������� changeTime
	 */
	private Vector<ChangeTimeListener> changeTimeListeners;

	/**
	 * ������ ��� ���������� ������� dispatcherFinish
	 */
	private Vector<DispatcherFinishListener> dispatcherFinishListeners;

	public Dispatcher() {
		super();
	}

	/**
	 * ����� ��� ���������� "������" � ������ ���������� �������. ���
	 * ������������ ������ ��� "������" ������ ���������� ����� ������ ��������.
	 * ����� ������������ "������" ������� �� "����������"
	 * 
	 * @param a
	 */
	public void addStartingActor(Actor a) {
		a.setDispatcher(this);
		startList.add(a);
	}

	/**
	 * ������������� "����������" � ������ ������ ���������� ��� ������ ��������
	 * 
	 */
	public void start() {
		if(starting){
			System.out.println("������ �������� ��������� ��������� ������������");
			return;
		}
		starting=true;
		currentTime = 0;
		waitingActorQueue.clear();
		timingActorQueue.clear();
		if (protocolFileName != null && protocolFileName != "Console"
				&& protocolFileName.trim() != "") {
			FileWriter file;
			try {
				file = new FileWriter(protocolFileName, false);
				file.close();
			} catch (java.io.IOException e) {
				System.out.println("�� ������� ������� ���� ���������");
			}
		}
		Thread thread = new Thread(this, "Dispatcher");
		this.thread = thread;
		thread.start();
	}

	/**
	 * ����� ���������� Runnable. ������� �������� "����������".
	 * 
	 */
	public void run() {
		Actor readyActor;
		printToProtocol("��������� ��� " + Double.toString(currentTime));
		// ������������ ������� DispatcherStart
		fireDispatcherStartEvent();
		// �������� ���� ���������� ������ ��������
		while (true) {
			// ��������� ������ ���������� �������
			runStartList();
			// ��������� ������ �������, ������ ���������� �������
			// readyActor - ��� �����, ��� �������� ����������� �������
			// ���� readyActor == null, �� ������� �� �����������
			// �� ��� ������ �� �������
			readyActor = testWaitingQueue();
			if (readyActor == null) {
				// ���� ���������� ������ �������� "����������" �������������,
				// ���� ������� �� ����������� �� ��� ������ �� �������
				// � ���� ������ ������� ����������� �� ��������� ����� ����
				if (timingActorQueue.isEmpty()) {
					break;
				}
				// ���� ������� �� ����������� �� ��� ������ �� �������
				// �������� �� ������ ������� ����������� �� ��������� �����,
				// ������ � ���������� �������� ���������
				readyActor = removeActorWithMinActivTime();
				// �������� readyActor ��� � ���� ���� ��
				waitingActorQueue.remove(readyActor);
				// ������ ������� ����� � ������������ �� �������� ���������
				// ������
				setCurrentTime(readyActor.getActivateTime());
			}
			// ������ ��������� ���������� ������� readyActor
			// ��� ����� ����� ����������� ��� ������ ���������� ������
			readyActor.getSuspendIndicator().setValue(false);
			// � ������� ���� �� ������ ���������� ������������� �����
			// "����������"
			readyActor.getSuspendIndicator().waitForValue(true);
		}
		// �������� ��������� ������
		if (waitingActorQueue.size() > 0) {
			// ������������� ������, ������ ���������� �������
			releaseWaitingQueue();
		}
		printToProtocol("  ��������� ������ ��������");
		// ������������ ������� DispatcherFinish
		fireDispatcherFinishEvent();
		starting=false;
	}

	/**
	 * ��������� ������ ���������� ������� ����� ��������� ������ ����������
	 * ������ �������� �������. ����� ������ ������� "������" ���������� �����
	 * ������ ������������������, � �������������� ������ ����� ����, ��� �����
	 * ������������� "�����a" ��������������.
	 */
	private void runStartList() {
		while (!startList.isEmpty()) {
			Actor a = startList.remove(0);
			a.getSuspendIndicator().setValue(false);
			a.start();
			a.getSuspendIndicator().waitForValue(true);
		}
	}

	/**
	 * ��������� ������ �������, ������ ���������� �������
	 * 
	 * @return process.Actor ���� ��� ������� ���������
	 * @return null ���� ������� �� ��� ���� �� ���������
	 */
	private Actor testWaitingQueue() {
		Iterator<Actor> i = waitingActorQueue.iterator();
		while (i.hasNext()) {
			Actor a = i.next();
			if (a.getActivateCondition().testCondition()) {
				waitingActorQueue.remove(a);
				timingActorQueue.remove(a);
				return a;
			}
		}
		return null;
	}
	/**
	 * ����� ������ � ����������� �������� �����������
	 */
	private Actor removeActorWithMinActivTime(){
		Actor a = timingActorQueue.get(0);
		for (Actor element : timingActorQueue) {
			if(element.getActivateTime()<a.getActivateTime())
				a=element;
		}
		timingActorQueue.remove(a);
		return a;
	}

	/**
	 * ��������� ������ �������� �������� ������� � ������������ �������
	 * ChangeTime
	 * 
	 * @param newCurrentTime
	 */
	private void setCurrentTime(double newCurrentTime) {
		currentTime = newCurrentTime;
		fireChangeTimeEvent();
		printToProtocol("��������� ��� " + Double.toString(currentTime));
	}

	/**
	 * �����, ������������� ������ ������� ������ ���������� �������
	 */
	private void releaseWaitingQueue() {
		printToProtocol("  ��������� ������� ������, �� ������ ��������� �����");
		while (waitingActorQueue.size() > 0) {
			Actor a = waitingActorQueue.remove(0);
			printToProtocol("  " + a.getNameForProtocol()
					+ " �� ���������, �� ��������� ������� ������");
			a.getSuspendIndicator().setValue(false);
			a.getSuspendIndicator().waitForValue(true);
		}
	}

	/**
	 * ������� ����� ����� ��� ������ ����������� ��������� ������ ������. ���
	 * "Console" ������������ ����� ��������� �� �������
	 * 
	 * @param newProtocolFileName
	 */
	public void setProtocolFileName(java.lang.String newProtocolFileName) {
		protocolFileName = newProtocolFileName;
	}

	/**
	 * ����� ������ � �������� ������ ���������� ����������
	 * 
	 * @param newString
	 */
	public void printToProtocol(String newString) {
		if (protocolFileName == null || protocolFileName.trim().length() == 0) {
			return;
		}
		if (protocolFileName == "Console") {
			System.out.println(newString);
			return;
		}
		FileWriter file;
		try {
			file = new java.io.FileWriter(protocolFileName, true);
			file.write(newString + "\n");
			file.close();
		} catch (java.io.IOException e) {
		}
	}

	// ////////////////////////////////////////////////////////////////
	// ������, �������������� ������ � ����� ����������

	/**
	 * ����� ������� � �������� ���������� �������
	 * 
	 * @return double
	 */
	public double getCurrentTime() {
		return currentTime;
	}


	/**
	 * ������ � ������ ���������� ������ �������� ����������
	 * 
	 * @return boolean
	 */
	public final Thread getThread() {
		return thread;
	}

	/**
	 * ������ � ������������� ������� �������, ���������������� �� �����
	 * 
	 * @return PriorityQueue
	 */
	public final List<Actor> getTimingActorQueue() {
		return timingActorQueue;
	}

	/**
	 * ������ � ������� �������, ������ ���������� �������
	 * 
	 * @return Queue
	 */
	public final List<Actor> getWaitingActorQueue() {
		return waitingActorQueue;
	}

	// ////////////////////////////////////////////////////////////////
	// ������, �������������� ������������ ������� ����������
	/**
	 * Add a ChangeTimeListener to the listener list. The listener is registered
	 * for all properties.
	 * 
	 * @param listener
	 *            TheChangeTimeListener to be added
	 */

	public synchronized void addChangeTimeListener(ChangeTimeListener listener) {
		if (changeTimeListeners == null) {
			changeTimeListeners = new java.util.Vector<ChangeTimeListener>();
		}
		changeTimeListeners.addElement(listener);
	}

	/**
	 * Add a DispatcherFinishListener to the listener list. The listener is
	 * registered for all properties.
	 * 
	 * @param listener
	 *            The DispatcherFinishListener to be added
	 */

	public synchronized void addDispatcherFinishListener(
			DispatcherFinishListener listener) {
		if (dispatcherFinishListeners == null) {
			dispatcherFinishListeners = new java.util.Vector<DispatcherFinishListener>();
		}
		dispatcherFinishListeners.addElement(listener);
	}

	/**
	 * Add a DispatcherStartListener to the listener list. The listener is
	 * registered for all properties.
	 * 
	 * @param listener
	 *            The DispatcherStartListener to be added
	 */

	public synchronized void addDispatcherStartListener(
			DispatcherStartListener listener) {
		if (dispatcherStartListeners == null) {
			dispatcherStartListeners = new java.util.Vector<DispatcherStartListener>();
		}
		dispatcherStartListeners.addElement(listener);
	}

	/**
	 * Fire an existing ChangeTimeEvent to any registered leafDeletedListeners.
	 * 
	 * @param evt
	 *            The ChangeTimeEvent object.
	 */
	private void fireChangeTimeEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (changeTimeListeners != null) {
				targets = (java.util.Vector) changeTimeListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				ChangeTimeListener target = (ChangeTimeListener) targets
						.elementAt(i);
				target.onChangeTime(new EventObject(Dispatcher.this));
			}
		}

	}

	/**
	 * Fire an existing DispatcherFinishEvent to any registered
	 * dispatcherFinishListeners.
	 * 
	 * @param evt
	 *            The DispatcherFinishEvent object.
	 */
	private void fireDispatcherFinishEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (dispatcherFinishListeners != null) {
				targets = (java.util.Vector) dispatcherFinishListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				DispatcherFinishListener target = (DispatcherFinishListener) targets
						.elementAt(i);
				target.onDispatcherFinish();
			}
		}

	}

	/**
	 * Fire an existing DispatcherStartEvent to any registered
	 * dispatcherStartListeners.
	 * 
	 * @param evt
	 *            The DispatcherStartEvent object.
	 */
	private void fireDispatcherStartEvent() {
		java.util.Vector targets = null;
		synchronized (this) {
			if (dispatcherStartListeners != null) {
				targets = (java.util.Vector) dispatcherStartListeners.clone();
			}
		}
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				DispatcherStartListener target = (DispatcherStartListener) targets
						.elementAt(i);
				target.onDispatcherStart();
			}
		}

	}

	/**
	 * Remove a ChangeTimeListener from the listener list. This removes a
	 * ChangeTimeListener that was registered for all properties.
	 * 
	 * @param listener
	 *            The ChangeTimeListener to be removed
	 */

	public synchronized void removeChangeTimeListener(
			ChangeTimeListener listener) {
		if (changeTimeListeners == null) {
			return;
		}
		changeTimeListeners.removeElement(listener);
	}

	/**
	 * Remove a DispatcherFinishListener from the listener list. This removes a
	 * DispatcherFinishListener that was registered.
	 * 
	 * @param listener
	 *            The DispatcherFinishListener to be removed
	 */

	public synchronized void removeDispatcherFinishListener(
			DispatcherFinishListener listener) {
		if (dispatcherFinishListeners == null) {
			return;
		}
		dispatcherFinishListeners.removeElement(listener);
	}

	/**
	 * Remove a DispatcherStartListener from the listener list. This removes a
	 * DispatcherStartListener that was registered.
	 * 
	 * @param listener
	 *            The DispatcherStartListener to be removed
	 */

	public synchronized void removeDispatcherStartListener(
			DispatcherStartListener listener) {
		if (dispatcherStartListeners == null) {
			return;
		}
		dispatcherStartListeners.removeElement(listener);
	}

}
