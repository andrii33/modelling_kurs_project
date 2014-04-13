package test.java;
import main.java.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import process.Dispatcher;
import process.QueueForTransactions;
import rnd.Randomable;
import stat.Histo;

public class TestClient {
	
	Klient klient;
	//Количество покупок клиента 
	public Randomable rndKolPokupokKl;
	//Время выбора товара
	public Randomable rndVremiaVyboraTov;
	//Фиктивная очередь в торговом зале
	private QueueForTransactions queueTorgovyiZal;
	//Очередь у касс
	private QueueForTransactions queueKassa;
	//Гистограмма для времени пребывания в очереди к кассе
	private Histo histoForQueueTimeInQueueKassa;

	@Before
	public void setUp() throws Exception {
		VisualFrame visualFrame = new VisualFrame();
		Model model = new Model(new Dispatcher(),visualFrame );
		klient = new Klient("Test", visualFrame,model);
	    rndKolPokupokKl = visualFrame.getRndKolPokupokKl();
	    rndVremiaVyboraTov = visualFrame.getRndVremiaVyboraTov();
	    queueTorgovyiZal = model.getQueueTorgovyiZal();
	    queueKassa = model.getQueueKassa();
	    histoForQueueTimeInQueueKassa = model.getHistoForQueueTimeInQueueKassa();
	}
	
	@Test
	public void testObsluzhen() {
		klient.setObsluzhen(true);
		assertTrue(klient.isObsluzhen());
	}
	
	@Test
	public void testTimeToQueueKassa() {
		klient.setTimeAddToQueueKassa(25.5);
		assertEquals(25.5, klient.getTimeAddToQueueKassa(),0);
	}
	
	@Test
	public void testRndKolPokupokKl() {
		assertEquals(klient.rndKolPokupokKl, rndKolPokupokKl);
	}
	
	@Test
	public void testRndVremiaVyboraTov() {
		assertEquals(klient.rndVremiaVyboraTov, rndVremiaVyboraTov);
	}
	
	@Test
	public void testQueueTorgovyiZal() {
		assertEquals(klient.getQueueTorgovyiZal(), queueTorgovyiZal);
	}
	
	@Test
	public void testQueueKassa() {
		assertEquals(klient.getQueueKassa(), queueKassa);
	}
	
	@Test
	public void testHistoForQueueTimeInQueueKassa() {
		assertEquals(klient.getHistoForQueueTimeInQueueKassa(), histoForQueueTimeInQueueKassa);
	}
	
	

}
