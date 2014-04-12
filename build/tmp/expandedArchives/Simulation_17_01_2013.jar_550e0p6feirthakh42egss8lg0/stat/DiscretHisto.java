/**
 * 
 */
package stat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import widgets.Diagram;

/**
 * ������� �������, ������� ����� ����� ������������� ��� ��������. �� ���.
 * �������� (value) - ����� ��������, ������� �������� � �����-���� ��������.
 * ��������, ����� �������.
 * ������� (frequency) - ������� ��� ���������� �������� � ��������, ��� �������
 * ������� ��� ���������� ����������. ��� ����� �������, ��������, ��������
 * ����� �����, �� ���������� �������� ����� ������� ���������� ����������.
 * ���� ��������� ������������� �������� � �������. ������� ����� ��������
 * ��������� ���������� ������ ������� addFrequencyForValue. ��������, ������ 
 * �� ��������. ��� ������ ����� ��������, ���������: �������� - ����������
 * �����, ������� - ������� ������� ������� ���������� ����������. �����
 * ���������� ������� ���������� ������, ����� ��������� ��������� �������    
 * showRelFrec, ������� ��������� ������������� ������� ��� ������� ��������,
 * �������� ��� ���� ���� ������� ����� ����� ������� ������� ������� ����
 * ����� �����. ���� ������� toString ������� � ��������� ���� �� �� ����������
 * ���� ������� �������� (������� ����� �������).
 * @author UlanYar
 */
public class DiscretHisto {
	private int count;
	
	/** ����� ����� ������������� ���� ��������, ���������� �� �������. */
	private double totalIntegral = 0;
	
	/** ����� ����� ������������� ���� ���� ������. */
	private double totalInterval = 0;
	
	/** ��������� �� ����������� �������� �� ��������� � ������� ��������� */
	private boolean trim = false;
	
	/** ������ ��������� */
	private int from;
	
	/** ����� ��������� */
	private int to;
	
	/** ������������ ���������� ������������� �������� */
	private int maxcnt = DEFAULT_MAX_CNT; 
	
	/** �������� �� ��������� ��� ������������� ���������� ������������� �������� */
	public static final int DEFAULT_MAX_CNT = 20;
	
	/** 
	 * SortedMap, � ������� �������� ������������ �� ���������� �������.
	 * ����� ����� ������������� ������� ��� ��������. 
	 */
	private SortedMap<Double, Double> map = new TreeMap<Double, Double>();
	
	/** @return ������ ���������� ������ ��� ��������, ����������� ����� */
	public double [] frequencies() {
		double [] frequencies = new double[map.size()];
		int i = 0;
		for (Double d: map.values())
			frequencies[i++] = d;
		return frequencies;
	}	
	
	/**
	 * ���������� ����������� ��������
	 * (�������� �������� �������� �������).
	 */
	private void normalize() {
		if (map.size() > maxcnt) {
			double [] values = values();
			double [] frequencies = frequencies();
			
			double minv = values[0];
			double maxv = values[maxcnt - 1];
			double maxfreq = 0;
			for (int i = 0; i < maxcnt; maxfreq += frequencies[i++]);
			double prevfreq = maxfreq;
			
			// �� ���� ��������� ������� �� �������.
			for (int i = 1; i <= map.size() - maxcnt; i++) {
				prevfreq -= frequencies[i - 1];
				prevfreq += frequencies[i + maxcnt - 1];
				if (prevfreq > maxfreq) {
					maxfreq = prevfreq;
					minv = values[i];
					maxv = values[i + maxcnt - 1];
				}
			}
			
			// �������, ��������������� ���������, ��������� �� ������� ���������,
			// ��������� � ������� ��������� ���������.
			double minfs = 0;
			double maxfs = 0;
			for (double value: map.keySet()) {
				if (value < minv) minfs += map.get(value);
				if (value > maxv) maxfs += map.get(value);
			}
			
			List<Double> todelete = new ArrayList<Double>();
			for (double value: map.keySet()) {
				if (value < minv) todelete.add(value);
				if (value > maxv) todelete.add(value);
			}
			for (Double d: todelete)
				map.remove(d);
			map.put(minv, map.get(minv) + minfs);
			map.put(maxv, map.get(maxv) + maxfs);
		}
	}

	/**
	 * ���������������� ����������� (�������� ����������� �����
	 * ��������, ������� ��������).
	 */
	public void init() {
		count=0;
		map.clear();
		trim = false;
		totalIntegral = 0;
		totalInterval = 0;
	}
	
	/**
	 * ���������������� ����������� (�������� ����������� �����
	 * ��������, ������� ��������).
	 * @param maxcnt ������������ ���������� ������������� ��������
	 */
	public void init(int maxcnt) {
		this.maxcnt = maxcnt;
		init();
	}
	
	/**
	 * ������������ �������� � ��������� �� b1 �� b2
	 * @param b1 ������ ���������
	 * @param b2 ����� ���������
	 */
	public void initFromTo(int b1, int b2) {
		map.clear();
		trim = true;
		from = b1;
		to = b2;
		
		// ��-�� ���������� ���� ���� ����� ���� ������.
		totalIntegral = 0;
		totalInterval = 0;
	}
	
	/** @return ������ ������������� ������ ��� ��������, ����������� ����� */
	public double [] relfrequencies() {
		normalize();
		double [] frequencies = frequencies();
		for (int i = 0, j = frequencies.length; i < j; i++)
			frequencies[i] /= totalInterval;
		return frequencies;
	}
	
	/** @return ������ ��������, ����������� ����� */
	public double [] values() {
		double [] values = new double[map.size()];
		int i = 0;
		for (Double d: map.keySet())
			values[i++] = d;
		return values;
	}
	public void add(double value){
		addFrequencyForValue(1.0,value);
	}
	/**
	 * �������� ������� ��� ��������.
	 * @param frequency �������
	 * @param value ��������
	 */
	public void addFrequencyForValue(double frequency, double value) {
		count++;
		totalIntegral += (value * frequency);
		totalInterval += frequency;
		if (trim) {
			if (value < from) value = from;
			if (value > to) value = to;
		}
		map.put(value, new Double((map.containsKey(value) ? map.get(value) : 0) + frequency));
	}
	
	/** @return ������� �������� ����������� �������� */
	public double getAverage() {
		return totalIntegral / totalInterval;
	}
	
	/**
	 * ���������� �� ��������� ���������� ��������� ������������� ������ (���������)
	 * ��� �������� (�����������).
	 * @param diagram ���������, �� ������� ��������
	 * @param color ���� ��������
	 * @param width ������ ���� � � ����� ������ ���������
	 * @param reset ��������� �� ��������� ��������� ���������
	 */
	public void showRelFrec(Diagram diagram, Color color, double width, boolean reset) {
		normalize();
		diagram.setPainterColor(color);
		diagram.drawNeedleDiagram(values(), relfrequencies(), width, reset);
	}

	/**
	 * ���������� �� ��������� ���������� ��������� ������������� ������ (���������)
	 * ��� �������� (�����������).
	 * @param diagram ���������, �� ������� ��������
	 */
	public void showRelFrec(widgets.Diagram diagram) {
		showRelFrec(diagram, Color.magenta, 0.2, true);
	}
	
	/** @return � ��������� ���� ���������� � ����������� ��������� � �������� */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("���������� �������:\n");
		buffer.append("ʳ������ ������� = " + count + "\n");
		buffer.append("������ �������� = " + getAverage() + "\n");
		buffer.append("��������  �������\n");
		for (Double value: map.keySet())
			buffer.append(StatTables.format(value, 8, 1) + 
					StatTables.format(map.get(value) / totalInterval, 9, 4) + "\n");
		return buffer.toString();
	}
}
