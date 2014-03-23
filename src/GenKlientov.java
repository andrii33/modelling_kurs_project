import process.Actor;
import rnd.Randomable;

public class GenKlientov extends Actor {
	//Продолжительность работы генератора клиентов
	private double finishTime;
	//Интервал прихода клиентов в супермаркет
	private Randomable rndIntervalPrihodaKl;
	//Ссылка на визуальную часть
	private VisualFrame gui;
	//Ссылка на модель
	private Model model;
	
	public GenKlientov(String name, VisualFrame gui, Model model) {
		setNameForProtocol(name);
		this.gui = gui;
		this.model = model;
		finishTime = gui.getChooseDataMTime().getDouble();
		rndIntervalPrihodaKl = gui.getRndIntervalPrihodaKl();
	}
	
	//Правила действия для генератора клиентов
	protected void rule() {
		//Цикл действий для генератора клиентов
		do {
			//Время когда клиентов нету
			holdForTime(rndIntervalPrihodaKl.next());
			//Приход клиента
			Klient klient = new Klient("Клієнт", gui, model);
			getDispatcher().addStartingActor(klient);
		} while (getDispatcher().getCurrentTime() <= finishTime);
	}
}
