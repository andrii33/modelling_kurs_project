import process.Actor;
import rnd.Randomable;

public class GenKlientov extends Actor {
	//����������������� ������ ���������� ��������
	private double finishTime;
	//�������� ������� �������� � �����������
	private Randomable rndIntervalPrihodaKl;
	//������ �� ���������� �����
	private VisualFrame gui;
	//������ �� ������
	private Model model;
	
	public GenKlientov(String name, VisualFrame gui, Model model) {
		setNameForProtocol(name);
		this.gui = gui;
		this.model = model;
		finishTime = gui.getChooseDataMTime().getDouble();
		rndIntervalPrihodaKl = gui.getRndIntervalPrihodaKl();
	}
	
	//������� �������� ��� ���������� ��������
	protected void rule() {
		//���� �������� ��� ���������� ��������
		do {
			//����� ����� �������� ����
			holdForTime(rndIntervalPrihodaKl.next());
			//������ �������
			Klient klient = new Klient("�볺��", gui, model);
			getDispatcher().addStartingActor(klient);
		} while (getDispatcher().getCurrentTime() <= finishTime);
	}
}
