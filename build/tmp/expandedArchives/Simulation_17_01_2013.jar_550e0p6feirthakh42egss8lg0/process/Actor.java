package process;

public abstract class Actor implements Runnable, Cloneable {

	private String nameForProtocol = this.getClass().getName();

	private double activateTime;

	private IWaitCondition activateCondition;

	private Semaphore suspendIndicator = new Semaphore();

	private Thread thread = null;

	// private Dispatcher dispatcher = Dispatcher.getSingleton();

	private Dispatcher dispatcher;

	public Actor() {
		super();
	}

	/**
	 * ����� start() ���������� "�����������" ��� ��������� ������ startList. ��
	 * ��������� ����� ���������� ������ �������� "������", ��������� �
	 * ��������� � ������ rule, ������� ����� run ���������� Runnable. ��������
	 * ����� "������", ����� ������������ ������������ �����, �� ����
	 * "���������", �� ��� ���, ���� "�����" �� ����� �������������
	 */
	final void start() {
		// ������� � ��������� ����� ���������� ������ �������� ������
		thread = new Thread(this, getNameForProtocol());
		thread.start();
	}

	/**
	 * ����� ���������� ������� �������� ������. ������ ���� ������������� �
	 * ���������.
	 */
	abstract protected void rule();

	/**
	 * ����� ���������� Runnable. ������������ ���������� ������ �������� ������
	 * � ��������� ������
	 */
	public void run() {
		getDispatcher().printToProtocol(
				"  " + this.getNameForProtocol() + " ������");
		rule();	
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ������ ��������");

		// ������������� ��������� � ��������� "����� ����������"
		// ��� ����� ������� "����������", ��� ����� ����������
		// ������ �������� ������� "������" ��������,
		// ��� ���� ����������� "����������" ���������� ������
		suspendIndicator.setValue(true);
	}

	/**
	 * ����� ������������ �������� ���������� ������ �������� "������" �� �����
	 * holdTime (����� ����� �����������, ���������). ����������� ��� ��������
	 * �����-�� ������ "������", ��������� � ��������� �������.
	 */
	protected final void holdForTime(double holdTime) {
		if(holdTime<0){
			System.out.println("Negative holdTime! It is imposible. There was not made holdForTime.");
			return;
		}
		// �������� �� ����� ������, ���� ��������� �������� ������.
		if (!getDispatcher().getThread().isAlive()) {
			System.out.println("!getDispatcher().getThread().isAlive()");
			return;
		}
		// ��������� ����� ������������� ������ �������� "������"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ��������� �����
		getDispatcher().getTimingActorQueue().add(this);
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ��������� �� "
						+ Double.toString(activateTime));
		// ������������� ��������� � ��������� "����� �������������",
		// ��� ����� ����� ����������� �������� "����������".
		suspendIndicator.setValue(true);
		// ��������� ����� ���������� ������ �������� "������"
		// � ��������� ��������.
		// ����� ������ ����� ����������� ������ "������"
		// ��������� ���������� ��������� � ��������� false
		suspendIndicator.waitForValue(false);
		// ����� ����� ������������ ���������� ������ ��������
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ������������");
	}

	/**
	 * ����� ������������ �������� ���������� ������ �������� "������" ��
	 * ���������� �������, ������� ������� � ���� ������ testCondition(),
	 * ���������� WaitCondition. � ����� ������ �� �������� ������ ����� ������
	 * �, ������������ � ������ ����� � ���� ���������. ������� ����� � ����,
	 * ��� ������������� ������ �������� "������", ������� ���������� �������
	 * ����� ��������� � � ������, ����� "���������", ����� ����������� ������,
	 * "���������" ��� ������ ������. ������� ����� ������ �� ��������
	 * ���������� �������, ������������� � �������� �������� ��� �������
	 * ���������. ������������ ������� ��������������� � ���������� ��������
	 * �������������. ���������� ���������� ������ �������� ����� ������������.
	 * 
	 * @throws Exception
	 */
	protected final void waitForCondition(IWaitCondition c)
			throws DispatcherFinishException {
		// ���� ������� ����������� �� ������� �� �����
		if (c.testCondition())
			return;
		// ���� ��������� �������� ������, �������� �� ����� ������.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// ���������� ������, �������� �������.
		activateCondition = c;
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ���������� �������
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ���� '"
						+ activateCondition.toString() + "'");
		// ������������� ��������� � ��������� "����� �������������",
		// ��� ����� ����� ����������� �������� "����������".
		suspendIndicator.setValue(true);
		// ��������� ����� ���������� ������ �������� "������"
		// � ��������� ��������.
		// ����� ������� ����������,
		// ��������� ���������� ��������� � ��������� false
		suspendIndicator.waitForValue(false);
		if (activateCondition.testCondition())
			getDispatcher().printToProtocol(
					"  " + getNameForProtocol() + " ��������� '"
							+ activateCondition.toString() + "'");
		else
			throw new DispatcherFinishException();
	}

	/**
	 * ����� ������������ �������� ������ �������� "������" �� ����������
	 * �������, ������� ������� � ���� ������ testCondition(), ����������
	 * WaitCondition. �� ����������������� �������� �� ����� ���� ����� �����
	 * holdTime holdTime (����� ����� �����������, ���������).
	 */
	protected final void waitForConditionOrHoldForTime(IWaitCondition c,
			double holdTime)  {
		// �������� �� ����� ������, ���� ��������� �������� ������.
		if (!getDispatcher().getThread().isAlive()) {
			return;
		}
		// ���� ������� ����������� �� ������� �� �����
		if (c.testCondition())
			return;
		// ��������� ����� ������������� ������ �������� "������"
		activateTime = getDispatcher().getCurrentTime() + holdTime;
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ��������� �����
		getDispatcher().getTimingActorQueue().add(this);
		// ���������� ������, �������� �������.
		activateCondition = c;
		// �������� "������" "����������", � ������ �������,
		// ����������� �� ���������� �������
		getDispatcher().getWaitingActorQueue().add(this);
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ���� '"
						+ activateCondition.toString()
						+ "', ��� �� ����� �� �� "
						+ Double.toString(activateTime));
		// ������������� ��������� � ��������� "����� �������������",
		// ��� ����� ����� ����������� �������� "����������".
		suspendIndicator.setValue(true);
		// ��������� ����� ���������� ������ �������� "������"
		// � ��������� ��������.
		// ����� ������ ����� ����������� ������ "������"
		// ��������� ���������� ��������� � ��������� false
		suspendIndicator.waitForValue(false);
		// ����� ����� ������������ ���������� ������ ��������
		getDispatcher().printToProtocol(
				"  " + getNameForProtocol() + " ������������ ");
	}

	/**
	 * ������ "�������" ������ ���������� �������������, �� ��� ���� ���������
	 * ������ ���� � ������� ����.
	 */
	public Object clone() {
		try {
			Actor clon = (Actor) super.clone();
			clon.suspendIndicator = new Semaphore();
			return clon;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * ����� ������������ "�����������" ��� ������� ������ "��������", ������
	 * ���������� �������.
	 * 
	 * @return process.WaitCondition
	 */
	IWaitCondition getActivateCondition() {
		return activateCondition;
	}

	/**
	 * ����� ���������� �����, �� ������� ���� ������������� �������������
	 * ������ �������� ������� "������" *
	 * 
	 * @return double
	 */
	public double getActivateTime() {
		return activateTime;
	}

	/**
	 * ����� ������������ ������ � ����� "������". ���� ����� ����� ��� ������
	 * ���������� ���������� � ��������.
	 * 
	 * @return java.lang.String
	 */

	public String getNameForProtocol() {
		return nameForProtocol;
	}

	/**
	 * ����� ������������ ������ � ���������� ��������� ������ ���������� ������
	 * ��������. ��������� ������ ���� ��������, ���������������� �
	 * ��������������� ���������� ������ ������ �������� ��� "������" ��� �
	 * "����������". ������������ "�����������".
	 * 
	 * @return process.Semaphore
	 */

	Semaphore getSuspendIndicator() {
		return suspendIndicator;
	}

	/**
	 * ������������� ��������� ��������� ������ ���������� ������ ��������.
	 * ��������� ������ ���� ��������, ���������������� � ���������������
	 * ���������� ������ ������ �������� ��� "������" ��� � "����������".
	 * ������������ ��� ������������.
	 * 
	 * @param newBooleanIndicator
	 *            process.Semaphore
	 */
	// private void setSuspendIndicator(Semaphore newBooleanIndicator) {
	// suspendIndicator = newBooleanIndicator;
	// }
	/**
	 * ������� ����� ������, ������� ����� �������������� ��� ������ ���������
	 * ������ ������
	 * 
	 * @param newNameForProtocol
	 *            java.lang.String
	 */
	public void setNameForProtocol(java.lang.String newNameForProtocol) {
		nameForProtocol = newNameForProtocol;
	}

	/**
	 * ��� ������� ��������
	 */
	public String toString() {
		return getNameForProtocol();
	}

	public void setActivateCondition(IWaitCondition activateCondition) {
		this.activateCondition = activateCondition;
	}

	public void setActivateTime(double activateTime) {
		this.activateTime = activateTime;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	protected final void replaceActivateTimeBy(double newActivateTime)
			throws Exception {
		if (getDispatcher().getCurrentTime() > newActivateTime)
			throw new Exception("Time revers is impossible");
		if (!getDispatcher().getTimingActorQueue().contains(this))
			throw new Exception("Actor is not in  timingActorQueue");
		this.setActivateTime(newActivateTime);
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread t) {
		thread = null;

	}

	void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;

	}
}
