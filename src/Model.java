import process.Dispatcher;
import process.QueueForTransactions;
import qusystem.MultiActor;
import stat.DiscretHisto;
import stat.Histo;
import widgets.experiments.IExperimentable;

public class Model implements IExperimentable {
	//Ссылка на диспетчера
	private Dispatcher dispatcher;
	//Ссылка на визуальную часть
	private VisualFrame gui;
	//Группа кассиров
	private MultiActor multiKassir;
	//Фиктивная очередь в торговом зале
	private QueueForTransactions queueTorgovyiZal; 
	//Очередь у касс
	private QueueForTransactions queueKassa;
	//Касир (оригинал для MultiActor)
	private Kassir kassir;
	//Генератор клиентов
	private GenKlientov genKlientov;
	//Гистограмма для длины фиктивной очереди в торговом зале 
	private DiscretHisto histoForQueueTorgovyiZal;
	//Гистограмма для времени пребывания в очереди к кассе
	private Histo histoForQueueTimeInQueueKassa;
	
	public Model(Dispatcher d, VisualFrame g) {
		if (d == null || g == null) {
			System.out.println("Не визначений диспетчер чи GUI для Model");
			System.out.println("Подальша робота неможлива");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		//Передаем актеров в стартовый список диспетчера
		componentsToStartList();
	}

	private void componentsToStartList() {
		//Передаем актеров диспетчеру
		dispatcher.addStartingActor(getMultiKassir());
		dispatcher.addStartingActor(getGenKlientov());
	}
	
	public GenKlientov getGenKlientov() {
		if (genKlientov == null)
			genKlientov = new GenKlientov("Генератор клієнтів", gui, this);
		return genKlientov;
	}
	
	public MultiActor getMultiKassir() {
		if (multiKassir == null) {
			multiKassir = new MultiActor();
			multiKassir.setNameForProtocol("MultiActor для групи касирів");
			multiKassir.setOriginal(getKassir());
			multiKassir.setNumberOfClones(gui.getChooseDataNKasir().getInt());
		}
		return multiKassir;
	}
	
	public Kassir getKassir() {
		if (kassir == null)
			kassir = new Kassir("Касир", gui, this);
		return kassir;
	}
	
	public QueueForTransactions getQueueTorgovyiZal() {
		if (queueTorgovyiZal == null) {
			queueTorgovyiZal = new QueueForTransactions();
			queueTorgovyiZal.setNameForProtocol("Фіктивна черга в торговому залі");
			queueTorgovyiZal.setDispatcher(dispatcher);
			queueTorgovyiZal.setDiscretHisto(getHistoForQueueTorgovyiZal());
		}
		return queueTorgovyiZal;
	}
	
	public QueueForTransactions getQueueKassa() {
		if (queueKassa == null) {
			queueKassa = new QueueForTransactions();
			queueKassa.setNameForProtocol("Черга біля кас");
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
