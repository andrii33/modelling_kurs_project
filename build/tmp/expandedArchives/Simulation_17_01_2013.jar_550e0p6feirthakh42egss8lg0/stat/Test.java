package stat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import rnd.RandomGenerators;
import widgets.ChooseRandom;
import widgets.Diagram;

/**
 * ������ ����� ������������ ��� ������������� ����������� ������ ���������
 * ���������� ����� ������� � ������ ���������� ������������
 * 
 * @author Andrey
 * 
 */
public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JPanel jContentPane = null;
	private JButton ivjJButtonTest = null;
	private JTextArea ivjJTextArea = null;
	private ChooseRandom ivjChooseRandom = null;
	private Histo ivjHisto = null;
	private Diagram ivjDiagram = null;
	private Histo savedHisto = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Test thisClass = new Test();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * ����������� ��� ����������
	 * 
	 */
	public Test() {
		super();
		initialize();
	}

	/**
	 * ����������� � �����������
	 * 
	 * @param d -
	 *            ���������, �� ������� ����� ������� ����������
	 * @param h -
	 *            �����������, ������� ����������� �� �������� �������
	 */
	public Test(Diagram d, Histo h) {
		super();
		initialize();
		ivjHisto = h;
		ivjDiagram = d;
	}

	/**
	 * ��������� ������, �� ������� ����� ����������� ��� ����������.
	 * ����������� ��������� ��������� ����������� ����
	 */
	private void initialize() {
		// ������������� ��������� �������������� ����
		this.setLocation(500, 200);
		// ������������� ��������� ������ ����
		this.setSize(400, 300);
		// ������������� ����������� ������ ����
		this.setMinimumSize(new Dimension(300, 200));
		this.setAlwaysOnTop(true);
		this.setContentPane(getJContentPane());
		this.setTitle("������������ �� �������� �������");
	}

	/**
	 * ��������� ChooseRandom, ������ ��� ������� ����� � ��������� ��� ������
	 * ����������. � ����� ������������� ��������
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			// ��������� ������ ��� ������� ����� �� �������
			jContentPane.add(getJButtonTest(), BorderLayout.SOUTH);
			// ��������� ��������� ������� ��� ������ ����������� ������������
			jContentPane.add(getJScrollPane(getJTextArea()),
					BorderLayout.CENTER);
			// ��������� ChooseRandom ��� ������ ���������� �����
			jContentPane.add(getChooseRandom(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * ������� ������ �� ������� �� ������� ����� ����������� ���������� �����
	 * �� ������� ��� �������� ������ ������������ ������� ��������
	 */
	private javax.swing.JButton getJButtonTest() {
		if (ivjJButtonTest == null) {
			try {
				ivjJButtonTest = new javax.swing.JButton();
				ivjJButtonTest.setName("JButtonTest");
				ivjJButtonTest.setFont(new java.awt.Font("dialog", 0, 12));
				ivjJButtonTest.setText("��������� ����");
				ivjJButtonTest.setMargin(new java.awt.Insets(2, 4, 2, 4));
				ivjJButtonTest.setPreferredSize(new Dimension(100, 40));
				// ����������� ������� ������� �� ������
				ivjJButtonTest
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								try {
									// ��������� ���� �������
									pirsonTest();
									} catch (Exception e1) {
									// ���� ���� �� ����� �����������, ��
									// ������� ������� �������
									System.out
											.println("���� ������� �� ����� �����������: "
													+ e1.getMessage());
									e1.printStackTrace();
								}
							}
						});

			} catch (Throwable ivjExc) {

			}
		}
		return ivjJButtonTest;
	}

	/**
	 * ������� ��������� �������, ���� ����� ���������� ���������� ������������
	 * �� �������
	 * 
	 */
	private JTextArea getJTextArea() {
		if (ivjJTextArea == null) {
			try {
				ivjJTextArea = new javax.swing.JTextArea();
				ivjJTextArea.setName("JTextArea");
				ivjJTextArea.setLineWrap(false);
				ivjJTextArea.setWrapStyleWord(true);
				ivjJTextArea.setBounds(0, 0, 160, 120);
				ivjJTextArea.setFont(new java.awt.Font("Dialog",
						java.awt.Font.PLAIN, 14));
				ivjJTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));

			} catch (java.lang.Throwable ivjExc) {

			}
		}
		return ivjJTextArea;
	}

	/**
	 * ������ ����� ����� ��� ���������� ��������� ������
	 * 
	 * @param comp -
	 *            �������������� ���������
	 * @return jScrollPane
	 */
	private JScrollPane getJScrollPane(Component comp) {
		if (jScrollPane == null) {
			try {
				jScrollPane = new JScrollPane(comp);

			} catch (java.lang.Throwable ivjExc) {

			}
		}
		return jScrollPane;
	}

	/**
	 * ���������� �������� ChooseRandom
	 * 
	 * @return ivjChooseRandom - ���������
	 */
	private ChooseRandom getChooseRandom() {
		if (ivjChooseRandom == null) {
			try {
				ivjChooseRandom = new widgets.ChooseRandom();
				ivjChooseRandom.setName("ChooseRandom");

			} catch (java.lang.Throwable ivjExc) {

			}
		}
		return ivjChooseRandom;
	}

	/**
	 * �������� �����������, ����������� ������������� ���������
	 * 
	 * @return ivjHisto - ���������� ����������� � ������������ �������������
	 *         ���������
	 * @throws Exception -
	 *             ���� ����������� �� ��������
	 */
	private Histo getHisto() throws Exception {
		if (ivjHisto == null)
			throw new Exception("�� �������� Histo");
		return ivjHisto;
	}

	/**
	 * �������� ��������� �� ������� ����� ����������� ����������
	 * 
	 * @return ivjDiagram - ���������� ���������
	 * @throws Exception -
	 *             ���� ��������� �� ��������
	 */
	private Diagram getDiagram() throws Exception {
		if (ivjDiagram == null)
			throw new Exception("�� �������� Diagram");
		return ivjDiagram;
	}

	/**
	 * ���� �� �������� �������
	 * 
	 * @throws Exception -
	 *             ���� �� �������������������� ��������� ��� �����������. ���
	 *             ��� ������������� ����� ������ ��� ������������ �����������
	 */
	public void pirsonTest() throws Exception {
		// �������� ����� �������������
		RandomGenerators gen = getChooseRandom().getRandom();
		// ������� ����������� ��� �����???????
		if ((gen != null) && (getHisto() != null)) {

			/* ����� �� ����������� ������������ ������� */
			double[] a = getCopyHisto().absolutFrequency();
			double[] b = getCopyHisto().getBorders();
			/* ������� ������ ������������� ������ */
			double[] t = new double[a.length];
			t[0] = gen.probability(b[0]);
			t[t.length - 1] = 1 - gen.probability(b[b.length - 1]);
			for (int i = 1; i < t.length - 1; i++)
				t[i] = gen.probability(b[i]) - gen.probability(b[i - 1]);
			/* ���������� ����. ������� � ���������� */
			double[] ta = new double[t.length];
			for (int i = 0; i < ta.length; i++)
				ta[i] = t[i] * getCopyHisto().count();
			/* ������ ����������� ������������ � ������������� */
			getCopyHisto().showRelFrec(getDiagram(), java.awt.Color.magenta, 0.9,
					0.05, true);
			getDiagram().setPainterColor(java.awt.Color.orange);
			getDiagram().drawBarsDiagram(getCopyHisto().realBorders(), t, 0.6, 0.3,
					false);
			// �������� ���� � ������� ����������
			getJTextArea().setText("�������� � ������������\n");
			getJTextArea().append("���������� ������ �������������:\n");
			getJTextArea().append(gen.toString() + "\n");
			double[] res = stat.StatTables.pirsonResult(a, ta, getCopyHisto()
					.realBorders(), getJTextArea(), gen.getKParm());
			getJTextArea().append(
					"��-������� = " + stat.StatTables.format(res[0], 1, 1));
			getJTextArea().append(
					"\n����������� �������� = "
							+ stat.StatTables.format(res[1], 1, 1));
			if (res[0] < res[1])
				getJTextArea().append("\n�������� ����� �������");
			else
				getJTextArea().append("\n�������� ������� ����������");
			/* ���������� "���������� ������������ ����������" */
			savedHisto = null;
		}
	};

	/**
	 * ����� ���������� ����������� ����������� �������� �� ����� � ���
	 * 
	 */
	private Histo getCopyHisto() {
		if (savedHisto == null)
			try {
				savedHisto = getHisto().clone();
			} catch (Exception e) {
			}
		return savedHisto;
	};

}
