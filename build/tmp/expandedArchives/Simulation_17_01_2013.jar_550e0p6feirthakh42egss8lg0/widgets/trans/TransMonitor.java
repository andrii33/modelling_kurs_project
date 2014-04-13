package widgets.trans;

import process.Actor;
import qusystem.IModelFactory;
import widgets.Diagram;

/**
 * ������� ����� ������ ������������� ��� �������� � ������� ����������
 * ���������� ����������� ���������� ������� � �������������� ������ �
 * ���������� ������ � ������ ������. ������� ������� ������ ��������
 * ITransProcesable, ��������� ������� �������, ����������� ���������
 * IModelFactory. ��������� ������ ������� ���� �� ������ �������
 * initForTrans() � �������� � ��������� ������ ���������� � ������� ������
 * componentsToStartList(). ���������� ����������� ���������� �������
 * �������������� ����� ���������� interval ���������� �������. � ������ �������
 * ���������� ���������� ���������� � ������ ���������������� � ������� ������
 * resetAccum(). � ����� ���������� ������� interval, monitor, � ������� ������
 * getTransResult(), ���������� ��� ����������� ���������� ������. ����������
 * ������ �����������, ���������� ������� painter ��� ��������� �� ��������� �
 * ������������ � ������� intervalAverageArray.
 * 
 * Creation date: (18.11.2007 17:26:58)
 * 
 * @author: biv
 */

public class TransMonitor extends Actor {

	public TransMonitor() {
		super();
	}

	private double interval; // �������� ���������� ���������� � ��������

	private int nIntervals; // ����� ���������� ����������

	private int nParallel; // ����� ����������� ���������� �������

	private Diagram diagram; // ������ ��� ��������� ����������� �����������

	// �� paint.Diagram //

	private IModelFactory factory = null; // �������, ��������� ������,

	// ����������� ���������
	// ITransProcesable

	private double[] intervalAverageArray; // ������ ����������� ��������

	// ����������� �����������

	// ������� �������� ��������

	public void rule() {
		// ����� ��� �������
		ITransProcesable[] modelArray = new ITransProcesable[nParallel];
		for (int i = 0; i < nParallel; i++) {
			// ��������� ����� � ������ �� �� ������
			modelArray[i] = (ITransProcesable) (getFactory()
					.createModel(getDispatcher()));
			modelArray[i].initForTrans(interval * nIntervals);
		}
		// �������� �� ���������� �������
		float average;
		intervalAverageArray = new double[nIntervals];
		// ���� �� ���������� ����������
		for (int i = 0; i < nIntervals; i++) {
			// ����������� ������������� ����������
			for (int j = 0; j < nParallel; j++)
				modelArray[j].resetTransAccum();
			// �������� �� ������� ���������
			this.holdForTime(interval);
			// ����������� ����������� �����
			average = 0;
			for (int j = 0; j < nParallel; j++)
				average += modelArray[j].getTransResult();
			average /= nParallel;
			// ����� �� ��������� ���������� �����������
			intervalAverageArray[i] = average;
			getDispatcher().printToProtocol(
					" " + getNameForProtocol() + ":������ �� ���������"
							+ average);
			if (diagram != null)
				diagram.getPainter().drawOvalAtXY(
						(float) (interval * (i + 0.5)), (float) average, 3, 3);
		}

	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public double getInterval() {
		return interval;
	}

	public int getNIntervals() {
		return nIntervals;
	}

	public int getNParallel() {
		return nParallel;
	}

	public void setIntervalAverageArray(double[] intervalAverageArray) {
		this.intervalAverageArray = intervalAverageArray;
	}

	public IModelFactory getFactory() {
		if (factory == null)
			System.out.println("�� ��������� ������� �������");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}

	public void setNIntervals(int intervals) {
		nIntervals = intervals;
	}

	public void setNParallel(int parallel) {
		nParallel = parallel;
	}

	public double[] getIntervalAverageArray() {
		return intervalAverageArray;
	}
}
