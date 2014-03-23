import process.Dispatcher;
import process.QueueForTransactions;
import qusystem.MultiActor;
import stat.DiscretHisto;
import stat.Histo;
import widgets.experiments.IExperimentable;

public class Model implements IExperimentable {
	//������ �� ����������
	private Dispatcher dispatcher;
	//������ �� ���������� �����
	private VisualFrame gui;
	//������ ��������
	private MultiActor multiKassir;
	//��������� ������� � �������� ����
	private QueueForTransactions queueTorgovyiZal; 
	//������� � ����
	private QueueForTransactions queueKassa;
	//����� (�������� ��� MultiActor)
	private Kassir kassir;
	//��������� ��������
	private GenKlientov genKlientov;
	//����������� ��� ����� ��������� ������� � �������� ���� 
	private DiscretHisto histoForQueueTorgovyiZal;
	//����������� ��� ������� ���������� � ������� � �����
	private Histo histoForQueueTimeInQueueKassa;
	
	public Model(Dispatcher d, VisualFrame g) {
		if (d == null || g == null) {
			System.out.println("�� ���������� ��������� �� GUI ��� Model");
			System.out.println("�������� ������ ���������");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		//�������� ������� � ��������� ������ ����������
		componentsToStartList();
	}

	private void componentsToStartList() {
		//�������� ������� ����������
		dispatcher.addStartingActor(getMultiKassir());
		dispatcher.addStartingActor(getGenKlientov());
	}
	
	public GenKlientov getGenKlientov() {
		if (genKlientov == null)
			genKlientov = new GenKlientov("��������� �볺���", gui, this);
		return genKlientov;
	}
	
	public MultiActor getMultiKassir() {
		if (multiKassir == null) {
			multiKassir = new MultiActor();
			multiKassir.setNameForProtocol("MultiActor ��� ����� ������");
			multiKassir.setOriginal(getKassir());
			multiKassir.setNumberOfClones(gui.getChooseDataNKasir().getInt());
		}
		return multiKassir;
	}
	
	public Kassir getKassir() {
		if (kassir == null)
			kassir = new Kassir("�����", gui, this);
		return kassir;
	}
	
	public QueueForTransactions getQueueTorgovyiZal() {
		if (queueTorgovyiZal == null) {
			queueTorgovyiZal = new QueueForTransactions();
			queueTorgovyiZal.setNameForProtocol("Գ������ ����� � ��������� ���");
			queueTorgovyiZal.setDispatcher(dispatcher);
			queueTorgovyiZal.setDiscretHisto(getHistoForQueueTorgovyiZal());
		}
		return queueTorgovyiZal;
	}
	
	public QueueForTransactions getQueueKassa() {
		if (queueKassa == null) {
			queueKassa = new QueueForTransactions();
			queueKassa.setNameForProtocol("����� ��� ���");
			queueKassa.setDispatcher(dispatcher);
		}
		return queueKassa;
	}

	public void initForTest() {
		getQueueTorgovyiZal().setPainter(gui.getDiagramQueueCustomers().getPainter());
		getQueueKassa().setPainter(gui.getDiagramKassa().getPainter());
	}

	public DiscretHisto getHistoForQueueTorgovyiZal() {
		if (histoForQueueTorgovyiZal == null) {
			histoForQueueTorgovyiZal = new DiscretHisto();
		}
		return histoForQueueTorgovyiZal;
	}

	public Histo getHistoForQueueTimeInQueueKassa() {
		if (histoForQueueTimeInQueueKassa == null) {
			histoForQueueTimeInQueueKassa = new Histo();
		}
		return histoForQueueTimeInQueueKassa;
	}
	
	public double getResultOfExperiment() {
		return getHistoForQueueTimeInQueueKassa().getAverage();
	}
	
	public void initForExperiment(double factor) {
		getMultiKassir().setNumberOfClones((int) factor);
		gui.getChooseDataNKasir().setText(String.valueOf(factor));
	}
}
