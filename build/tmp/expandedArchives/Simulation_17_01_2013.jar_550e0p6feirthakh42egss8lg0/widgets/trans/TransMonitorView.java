package widgets.trans;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import process.Dispatcher;
import qusystem.IModelFactory;
import widgets.Diagram;
import widgets.regres.IRegresable;

/**
 * ������� ����� ������ ������������� ��� ������� ���������� �����������,
 * �������� � ������� ���������� ���������� ����������� ���������� ������� �
 * �������������� ������ � ���������� ������ � ������ ������. �������� �������,
 * �� ������ � ���������� �������������� �������� monitor, ������� ��������� �
 * ������� ����������� ����������� ������, ������������ ����� Actor. ������
 * monitor ��������� ������ � ����������� ��� ������� �� ������ "�����". ������
 * monitor ���������� ����������, ����� ���� ��������� �����������. �����
 * ������� ���������� �������� ����������� ������� �������� ��������. �������
 * ������� ������ �������� ITransProcesable, ��������� ������ model ���� �� ����, 
 * ������� ��� ����� createAndInitTransModel(Dispatcherdispatcher,double finishTime),
 * ������� ������ ���������� �������� � ���������� ������ � ������. 
 * �������� ������ ������ � ��� �� �����������, ��� � �������. 
 * ��������� ������ ������� �������� � ��������� ������ ����������
 * � ������� ������ componentsToStartList().
 * ���������� ����������� ���������� �������
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
public class TransMonitorView extends javax.swing.JPanel implements IRegresable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double interval; // �������� ���������� ���������� � ��������

	private int nIntervals; // ����� ���������� ����������

	private int nParallel; // ����� ����������� ���������� �������

	private Diagram diagram; // ������ ��� ��������� ����������� �����������  //  @jve:decl-index=0:visual-constraint="111,246"
								// �� paint.Diagram //
								// @jve:decl-index=0:visual-constraint="21,235"

	private IModelFactory factory = null; // ������� ������,
											// ����������� ���������
											// ITransProcesable
	 				
	private TransMonitor monitor = null;  //  @jve:decl-index=0:visual-constraint="42,294"
	
	private Dispatcher dispatcher =null;  //  @jve:decl-index=0:

	private JButton jButtonStart = null;

	private JPanel jPanelIntrval = null;

	private JTextField jTextFieldInterval = null;

	private JPanel jPanelNintervals = null;

	private JTextField jTextFieldNintervals = null;

	private JPanel jPanelNparallel = null;

	private JTextField jTextFieldNParallel = null;

	/**
	 * This is the default constructor
	 */
	public TransMonitorView() {
		super();
		initialize();
	}

	/**
	 * Initialize the class.
	 */
	private void initialize() {
		try {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.ipadx = 0;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.weighty = 1.0D;
			gridBagConstraints3.gridy = 3;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.ipadx = -2;
			gridBagConstraints2.ipady = 8;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.ipadx = -2;
			gridBagConstraints1.ipady = 8;
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = -2;
			gridBagConstraints.ipady = 8;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			this.setLayout(new GridBagLayout());
			setName("TransMonitorView");
			setBorder(javax.swing.BorderFactory
					.createCompoundBorder(
							javax.swing.BorderFactory
									.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
							javax.swing.BorderFactory
									.createTitledBorder(
											javax.swing.BorderFactory
													.createBevelBorder(javax.swing.border.BevelBorder.LOWERED),
											"��������� ��������",
											javax.swing.border.TitledBorder.CENTER,
											javax.swing.border.TitledBorder.DEFAULT_POSITION,
											new java.awt.Font("Dialog",
													java.awt.Font.BOLD, 12),
											new java.awt.Color(51, 51, 51))));
			setSize(162, 219);
			this.set�����������_�������("200");
			this.set����������_����������("5");
			this.set��������_����������("15");
			this.add(getJPanelIntrval(), gridBagConstraints);
			this.add(getJPanelNintervals(), gridBagConstraints1);
			this.add(getJPanelNparallel(), gridBagConstraints2);
			this.add(getJButtonStart(), gridBagConstraints3);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	/**
	 * This method initializes jButtonStart
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("�����");
			jButtonStart.setMinimumSize(new Dimension(66, 22));
			jButtonStart.setHorizontalTextPosition(SwingConstants.CENTER);
			jButtonStart.setMaximumSize(new Dimension(11111, 2222222));
			jButtonStart.setPreferredSize(new Dimension(196, 560));
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startMonitoring();
				}
			});
		}
		return jButtonStart;
	}

	// ������ ������� ������������
	private void startMonitoring() {
		getJButtonStart().setEnabled(false);
		diagram.setHorizontalMaxText(String.valueOf((int) getFinishTime()));
		diagram.setGridByX(getNIntervals());
		diagram.clear();
		
		getMonitor().setNParallel(getNParallel());
		getMonitor().setNIntervals(getNIntervals());
		getMonitor().setInterval(getInterval());
		getMonitor().setFactory(getFactory());
		getMonitor().setDiagram(diagram);
		
		getDispatcher().addStartingActor(getMonitor());
		getDispatcher().start();
		new Thread(){
			public void run(){
				try {
					dispatcher.getThread().join();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				getJButtonStart().setEnabled(true);
			};
			
		}.start();

		
	}

	/*
	 * ���������� ����� �������������
	 */
	public double getFinishTime() {
		return getInterval() * getNIntervals();
	}

	// ��������� ������� ������� � ��������� ����������
	// ������� �������� ��� ����������� �����������
	public void setParmFinder(ParmFinderView o) {
	}

	/**
	 * This method initializes jPanelIntrval
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelIntrval() {
		if (jPanelIntrval == null) {
			jPanelIntrval = new JPanel();
			jPanelIntrval.setLayout(new CardLayout());
			jPanelIntrval
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"�������� �����������",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelIntrval.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelIntrval.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelIntrval.add(getJTextFieldInterval(), getJTextFieldInterval()
					.getName());
		}
		return jPanelIntrval;
	}

	/**
	 * This method initializes jTextFieldInterval
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldInterval() {
		if (jTextFieldInterval == null) {
			jTextFieldInterval = new JTextField();
			jTextFieldInterval.setName("jTextFieldInterval");
			jTextFieldInterval
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldInterval.setText("15");
		}
		return jTextFieldInterval;
	}

	/**
	 * This method initializes jPanelNintervals
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNintervals() {
		if (jPanelNintervals == null) {
			jPanelNintervals = new JPanel();
			jPanelNintervals.setLayout(new CardLayout());
			jPanelNintervals
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"ʳ������ ���������",
									javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelNintervals.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelNintervals.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelNintervals.add(getJTextFieldNintervals(),
					getJTextFieldNintervals().getName());
		}
		return jPanelNintervals;
	}

	/**
	 * This method initializes jTextFieldNintervals
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNintervals() {
		if (jTextFieldNintervals == null) {
			jTextFieldNintervals = new JTextField();
			jTextFieldNintervals.setName("jTextFieldNintervals");
			jTextFieldNintervals
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldNintervals.setText("5");
		}
		return jTextFieldNintervals;
	}

	/**
	 * This method initializes jPanelParallel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelNparallel() {
		if (jPanelNparallel == null) {
			jPanelNparallel = new JPanel();
			jPanelNparallel.setLayout(new CardLayout());
			jPanelNparallel
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"���������� �������",
									javax.swing.border.TitledBorder.CENTER,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jPanelNparallel.setPreferredSize(new java.awt.Dimension(148, 47));
			jPanelNparallel.setMinimumSize(new java.awt.Dimension(148, 46));
			jPanelNparallel.add(getJTextFieldNparallel(),
					getJTextFieldNparallel().getName());
		}
		return jPanelNparallel;
	}

	/**
	 * This method initializes jTextFieldParallel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNparallel() {
		if (jTextFieldNParallel == null) {
			jTextFieldNParallel = new JTextField();
			jTextFieldNParallel.setName("jTextFieldNParallel");
			jTextFieldNParallel
					.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jTextFieldNParallel.setText("200");
		}
		return jTextFieldNParallel;
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNparallelText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String get�����������_�������() {
		return getJTextFieldNparallel().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldIntervalText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String get��������_����������() {
		return getJTextFieldInterval().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNintervalsText
	 * attribute.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String get����������_����������() {
		return getJTextFieldNintervals().getText();
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNparallelText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void set�����������_�������(java.lang.String arg1) {
		getJTextFieldNparallel().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the JTextFieldIntervalText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void set��������_����������(java.lang.String arg1) {
		getJTextFieldInterval().setText(arg1);
	}

	/**
	 * Method generated to support the promotion of the JTextFieldNintervalsText
	 * attribute.
	 * 
	 * @param arg1
	 *            java.lang.String
	 */
	public void set����������_����������(java.lang.String arg1) {
		getJTextFieldNintervals().setText(arg1);
	}


	/**
	 * Method generated to support the promotion of the monitorPainter
	 * attribute.
	 * 
	 * @param arg1
	 *            paint.Painter
	 */
	public void setDiagram(widgets.Diagram arg1) {
		diagram = arg1;
	}

	/**
	 * Called whenever the part throws an exception.
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* Uncomment the following lines to print uncaught exceptions to stdout */
		System.out.println("�� ������ ������������� ���������� ����� �������");
		exception.printStackTrace(System.out);
	}


	public double getInterval() {
		return interval = Integer.parseInt(getJTextFieldInterval().getText());
	}

	public int getNIntervals() {
		return nIntervals = Integer.parseInt(getJTextFieldNintervals()
				.getText());
	}

	public int getNParallel() {
		nParallel = Integer.parseInt(getJTextFieldNparallel().getText());
		return nParallel;
	}

	private IModelFactory getFactory() {
		if(factory==null)
			System.out.println("�� �������� factory ��� TransMonitorView");
		return factory;
	}

	public void setFactory(IModelFactory factory) {
		this.factory = factory;
	}


	public TransMonitor getMonitor() {
		if(monitor==null)
		monitor = new TransMonitor();
		monitor.setNameForProtocol("Monitor");
		return monitor;
	}

	public Dispatcher getDispatcher() {
		if (dispatcher==null) {
			dispatcher=new Dispatcher();
			dispatcher.setProtocolFileName("");
		}
		return dispatcher;
	}

	public double[] getFactorsArray() {
		double[] a = new double[nIntervals];
		for (int i = 0; i < nIntervals; i++) {
			a[i] = (i + 0.5) * interval;
		}
		return a;	
	}

	public double[][] getResultMatrix() {
		double[][] a = new double[1][nIntervals];
		for (int i = 0; i < nIntervals; i++) {
			a[0][i] = getMonitor().getIntervalAverageArray()[i];
		}
		return a;
	}

}  //  @jve:decl-index=0:visual-constraint="52,6"
