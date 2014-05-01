package test.java;

import static org.junit.Assert.assertEquals;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.Factory;
import main.java.GenKlientov;
import main.java.Kassir;
import main.java.Model;
import main.java.VisualFrame;

import org.junit.Before;
import org.junit.Test;

import process.Dispatcher;
import process.QueueForTransactions;
import qusystem.MultiActor;
import stat.DiscretHisto;
import stat.Histo;


public class TestModel {
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

	
	
	private Model model;
	
	@Before
	public void setUp() throws Exception {
	    gui = new VisualFrame();
		dispatcher = new Dispatcher();
	 	model = new Model(dispatcher, gui);
	 	multiKassir = model.getMultiKassir();
		queueKassa = model.getQueueKassa();
		queueTorgovyiZal = model.getQueueTorgovyiZal();
		
		
	}
	
	@Test
	public void testQueueKassa() {
		assertEquals(queueKassa, model.getQueueKassa());
	}
	
	@Test
	public void testMultiKassir() {
		assertEquals(multiKassir, model.getMultiKassir());
	}
	
	@Test
	public void testQueueTorgovyiZal() {
		assertEquals(queueTorgovyiZal, model.getQueueTorgovyiZal());
	}

}
