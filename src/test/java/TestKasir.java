package test.java;
import main.java.*;


import org.junit.Before;
import org.junit.Test;

import process.Dispatcher;
import process.QueueForTransactions;
import rnd.Randomable;

import static org.junit.Assert.*;

public class TestKasir {
	
	Kassir kasir;
	
	//����������������� ������ �������
	private double finishTime;
	//������� � ����
	private QueueForTransactions queueKassa;
	//������� ������������� ������
	private Klient klient;
	
	private Dispatcher dispacher;
	//����� ������������ ������� ��������
	public Randomable rndVremiaObslKl;

	@Before
	public void setUp() throws Exception {
		VisualFrame gui = new VisualFrame();
		dispacher = new Dispatcher();
	 	Model model = new Model(dispacher, gui);
		kasir = new Kassir("Test", gui, model);
		finishTime = gui.getChooseDataMTime().getDouble();
		queueKassa = model.getQueueKassa();
		rndVremiaObslKl = gui.getRndVremiaObslKl();
	}
	
	@Test
	public void testFinishTime(){
		assertEquals(finishTime, kasir.getFinishTime(),0);
	}
	
	@Test
	public void testQueueKassa() {
		assertEquals(queueKassa, kasir.getQueueKassa());
	}
	
	@Test
	public void testRndVremiaObslKl() {
		assertEquals(rndVremiaObslKl, kasir.rndVremiaObslKl);
	}
	
	
	

}
