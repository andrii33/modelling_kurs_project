import javax.swing.JPanel;
import javax.swing.JFrame;
import widgets.ChooseRandom;
import java.awt.Rectangle;
import java.awt.GridLayout;
import widgets.ChooseData;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import process.Dispatcher;

import javax.swing.JTabbedPane;
import widgets.Diagram;
import rnd.Triangular;
import rnd.Erlang;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import widgets.experiments.ExperimentControl;
import widgets.regres.RegresAnaliser;

public class VisualFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	public ChooseRandom rndIntervalPrihodaKl = null;
	public ChooseRandom rndVremiaObslKl = null;
	public ChooseRandom rndKolPokupokKl = null;
	public ChooseRandom rndVremiaVyboraTov = null;
	private JPanel jPanel = null;
	public ChooseData chooseDataNKasir = null;
	public ChooseData chooseDataMTime = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelTest = null;
	private JPanel jPanelStatistic = null;
	public Diagram diagramQueueCustomers = null;
	public Diagram diagramKassa = null;
	public Diagram diagramQueueTorgovyiZal = null;
	private JScrollPane jScrollPaneQueueTorgovyiZal = null;
	private JTextArea jTextAreaQueueTorgovyiZal = null;
	public Diagram diagramTimeInQueueKassa = null;
	private JScrollPane jScrollPaneTimeInQueueKassa = null;
	private JTextArea jTextAreaTimeInQueueKassa = null;
	private JButton jButtonStart = null;
	private JPanel jPanelAnalyse = null;
	private ExperimentControl experimentControl = null;
	private RegresAnaliser regresAnaliser = null;
	private Diagram diagramAnalyse = null;
	/**
	 * This is the default constructor
	 */
	public VisualFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(811, 495);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Расчетно-графическая работа - Супермаркет");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setName("");
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(getJButtonStart(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes chooseRandom
	 * 
	 * @return widgets.ChooseRandom
	 */
	public ChooseRandom getRndIntervalPrihodaKl() {
		if (rndIntervalPrihodaKl == null) {
			rndIntervalPrihodaKl = new ChooseRandom();
			rndIntervalPrihodaKl
					.setTitle("Интервал времени между приходом клиентов в супермаркет");
		}
		return rndIntervalPrihodaKl;
	}

	/**
	 * This method initializes chooseRandom1
	 * 
	 * @return widgets.ChooseRandom
	 */
	public ChooseRandom getRndVremiaObslKl() {
		if (rndVremiaObslKl == null) {
			rndVremiaObslKl = new ChooseRandom();
			rndVremiaObslKl.setTitle("Время обслуживания клиента кассиром");
			rndVremiaObslKl.setRandom(new Triangular(0.5, 1, 2));
		}
		return rndVremiaObslKl;
	}

	/**
	 * This method initializes chooseRandom2
	 * 
	 * @return widgets.ChooseRandom
	 */
	public ChooseRandom getRndKolPokupokKl() {
		if (rndKolPokupokKl == null) {
			rndKolPokupokKl = new ChooseRandom();
			rndKolPokupokKl.setTitle("Количество покупок клиента");
			rndKolPokupokKl.setRandom(new Erlang(4, 3, true));
		}
		return rndKolPokupokKl;
	}

	/**
	 * This method initializes chooseRandom3
	 * 
	 * @return widgets.ChooseRandom
	 */
	public ChooseRandom getRndVremiaVyboraTov() {
		if (rndVremiaVyboraTov == null) {
			rndVremiaVyboraTov = new ChooseRandom();
			rndVremiaVyboraTov.setTitle("Время выбора товара");
			rndVremiaVyboraTov.setRandom(new Triangular(1, 2, 4));
		}
		return rndVremiaVyboraTov;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(6);
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(6);
			jPanel = new JPanel();
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Параметры системы", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.setBounds(new Rectangle(0, 0, 366, 467));
			jPanel.setLayout(gridLayout);
			jPanel.add(getRndIntervalPrihodaKl(), null);
			jPanel.add(getRndVremiaObslKl(), null);
			jPanel.add(getRndKolPokupokKl(), null);
			jPanel.add(getRndVremiaVyboraTov(), null);
			jPanel.add(getChooseDataNKasir(), null);
			jPanel.add(getChooseDataMTime(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes chooseData
	 * 
	 * @return widgets.ChooseData
	 */
	public ChooseData getChooseDataNKasir() {
		if (chooseDataNKasir == null) {
			chooseDataNKasir = new ChooseData();
			chooseDataNKasir.setTitle("Количество кассиров");
			chooseDataNKasir.setText("2");
		}
		return chooseDataNKasir;
	}

	/**
	 * This method initializes chooseData1
	 * 
	 * @return widgets.ChooseData
	 */
	public ChooseData getChooseDataMTime() {
		if (chooseDataMTime == null) {
			chooseDataMTime = new ChooseData();
			chooseDataMTime.setTitle("Время моделирования");
			chooseDataMTime.setText("100");
			chooseDataMTime
					.addCaretListener(new javax.swing.event.CaretListener() {
						public void caretUpdate(javax.swing.event.CaretEvent e) {
							if (jPanelTest.isShowing())
								setTestModelingTime();
							if (jPanelStatistic.isShowing())
								setStatisticModelingTime();
						}
					});
		}
		return chooseDataMTime;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(366, 0, 438, 440));
			jTabbedPane.addTab("Тест", null, getJPanelTest(), null);
			jTabbedPane.addTab("Статистика", null, getJPanelStatistic(), null);
			jTabbedPane.addTab("Анализ", null, getJPanelAnalyse(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */

	private void setTestModelingTime() {
		String s = getChooseDataMTime().getText();
		getDiagramKassa().setHorizontalMaxText(s);
		getDiagramQueueCustomers().setHorizontalMaxText(s);
	}
	
	private void setStatisticModelingTime() {
		String s = getChooseDataMTime().getText();
		getDiagramQueueTorgovyiZal().setHorizontalMaxText(s);
		getDiagramTimeInQueueKassa().setHorizontalMaxText(s);
	}

	private JPanel getJPanelTest() {
		if (jPanelTest == null) {
			GridLayout gridLayout3 = new GridLayout();
			gridLayout3.setRows(2);
			gridLayout3.setColumns(1);
			jPanelTest = new JPanel();
			jPanelTest.setLayout(gridLayout3);
			jPanelTest.add(getDiagramKassa(), null);
			jPanelTest.add(getDiagramQueueCustomers(), null);
			jPanelTest
					.addComponentListener(new java.awt.event.ComponentAdapter() {
						public void componentShown(
								java.awt.event.ComponentEvent e) {
							setTestModelingTime();
							getDiagramKassa().clear();
							getDiagramQueueCustomers().clear();
						}
					});
		}
		return jPanelTest;
	}

	/**
	 * This method initializes jPanelStatistic
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelStatistic() {
		if (jPanelStatistic == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(4);
			gridLayout2.setColumns(1);
			jPanelStatistic = new JPanel();
			jPanelStatistic.setLayout(gridLayout2);
			jPanelStatistic.add(getDiagramQueueTorgovyiZal(), null);
			jPanelStatistic.add(getJScrollPaneQueueTorgovyiZal(), null);
			jPanelStatistic.add(getDiagramTimeInQueueKassa(), null);
			jPanelStatistic.add(getJScrollPaneTimeInQueueKassa(), null);
			jPanelStatistic
			.addComponentListener(new java.awt.event.ComponentAdapter() {
				public void componentShown(
						java.awt.event.ComponentEvent e) {
					setStatisticModelingTime();
					getDiagramQueueTorgovyiZal().clear();
					getJTextAreaQueueTorgovyiZal().setText("");
					getJTextAreaTimeInQueueKassa().setText("");
				}
			});
		}
		return jPanelStatistic;
	}

	/**
	 * This method initializes diagram
	 * 
	 * @return widgets.Diagram
	 */
	public Diagram getDiagramQueueCustomers() {
		if (diagramQueueCustomers == null) {
			diagramQueueCustomers = new Diagram();
			diagramQueueCustomers.setTitleText("Количество покупателей");
			diagramQueueCustomers.setVerticalMaxText("20");
			diagramQueueCustomers.setHorizontalMaxText("100");
			diagramQueueCustomers.setPainterColor(Color.RED);
		}
		return diagramQueueCustomers;
	}

	/**
	 * This method initializes diagram1
	 * 
	 * @return widgets.Diagram
	 */
	public Diagram getDiagramKassa() {
		if (diagramKassa == null) {
			diagramKassa = new Diagram();
			diagramKassa.setTitleText("Очередь на кассе");
			diagramKassa.setVerticalMaxText("10");
			diagramKassa.setHorizontalMaxText("100");
			diagramKassa.setPainterColor(Color.RED);
		}
		return diagramKassa;
	}

	private void startTest() {
		getDiagramKassa().clear();
		getDiagramQueueCustomers().clear();
		final Dispatcher dispatcher = new Dispatcher();
		Factory factory = new Factory(this);
		Model model = (Model) factory.createModel(dispatcher);
		model.initForTest();
		getJButtonStart().setEnabled(false);
		dispatcher.start();
		new Thread() {
			public void run() {
				try {
					dispatcher.getThread().join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getJButtonStart().setEnabled(true);
			};
		}.start();
	}
	
	private void startStat() {
		getDiagramQueueTorgovyiZal().clear();
		getJTextAreaQueueTorgovyiZal().setText("");
		getDiagramTimeInQueueKassa().clear();
		getJTextAreaTimeInQueueKassa().setText("");
		final Dispatcher dispatcher = new Dispatcher();
		Factory factory = new Factory(this);
		final Model model = (Model) factory.createModel(dispatcher);
		getJButtonStart().setEnabled(false);
		dispatcher.start();
		new Thread() {
			public void run() {
				try {
					dispatcher.getThread().join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getJButtonStart().setEnabled(true);
				model.getHistoForQueueTorgovyiZal().showRelFrec(getDiagramQueueTorgovyiZal());
				getJTextAreaQueueTorgovyiZal().setText(model.getHistoForQueueTorgovyiZal().toString());
				model.getHistoForQueueTimeInQueueKassa().showRelFrec(getDiagramTimeInQueueKassa());
				getJTextAreaTimeInQueueKassa().setText(model.getHistoForQueueTimeInQueueKassa().toString());
			};
		}.start();
	}
	
	/**
	 * This method initializes diagramQueueTorgovyiZal	
	 * 	
	 * @return widgets.Diagram	
	 */
	public Diagram getDiagramQueueTorgovyiZal() {
		if (diagramQueueTorgovyiZal == null) {
			diagramQueueTorgovyiZal = new Diagram();
			diagramQueueTorgovyiZal.setTitleText("Очередь в зале");
			diagramQueueTorgovyiZal.setHorizontalMaxText("100");
			diagramQueueTorgovyiZal.setVerticalMaxText("1");
			diagramQueueTorgovyiZal.setPainterColor(Color.RED);
		}
		return diagramQueueTorgovyiZal;
	}

	/**
	 * This method initializes jScrollPaneQueueTorgovyiZal	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneQueueTorgovyiZal() {
		if (jScrollPaneQueueTorgovyiZal == null) {
			jScrollPaneQueueTorgovyiZal = new JScrollPane();
			jScrollPaneQueueTorgovyiZal.setViewportView(getJTextAreaQueueTorgovyiZal());
		}
		return jScrollPaneQueueTorgovyiZal;
	}

	/**
	 * This method initializes jTextAreaQueueTorgovyiZal	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaQueueTorgovyiZal() {
		if (jTextAreaQueueTorgovyiZal == null) {
			jTextAreaQueueTorgovyiZal = new JTextArea();
			jTextAreaQueueTorgovyiZal.setEditable(false);
		}
		return jTextAreaQueueTorgovyiZal;
	}

	/**
	 * This method initializes diagramTimeInQueueKassa	
	 * 	
	 * @return widgets.Diagram	
	 */
	public Diagram getDiagramTimeInQueueKassa() {
		if (diagramTimeInQueueKassa == null) {
			diagramTimeInQueueKassa = new Diagram();
			diagramTimeInQueueKassa.setTitleText("Время пребывания в очереди к кассе");
			diagramTimeInQueueKassa.setHorizontalMaxText("100");
			diagramTimeInQueueKassa.setVerticalMaxText("1");
			diagramTimeInQueueKassa.setPainterColor(Color.RED);
		}
		return diagramTimeInQueueKassa;
	}

	/**
	 * This method initializes jScrollPaneTimeInQueueKassa	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneTimeInQueueKassa() {
		if (jScrollPaneTimeInQueueKassa == null) {
			jScrollPaneTimeInQueueKassa = new JScrollPane();
			jScrollPaneTimeInQueueKassa.setViewportView(getJTextAreaTimeInQueueKassa());
		}
		return jScrollPaneTimeInQueueKassa;
	}

	/**
	 * This method initializes jTextAreaTimeInQueueKassa	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaTimeInQueueKassa() {
		if (jTextAreaTimeInQueueKassa == null) {
			jTextAreaTimeInQueueKassa = new JTextArea();
			jTextAreaTimeInQueueKassa.setEditable(false);
		}
		return jTextAreaTimeInQueueKassa;
	}

	/**
	 * This method initializes jButtonStart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonStart() {
		if (jButtonStart == null) {
			jButtonStart = new JButton();
			jButtonStart.setText("Старт");
			jButtonStart.setBounds(new Rectangle(366, 441, 438, 26));
			jButtonStart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jPanelTest.isShowing())
						startTest();
					if (jPanelStatistic.isShowing())
						startStat();
				}
			});
		}
		return jButtonStart;
	}

	/**
	 * This method initializes jPanelAnalyse	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelAnalyse() {
		if (jPanelAnalyse == null) {
			jPanelAnalyse = new JPanel();
			jPanelAnalyse.setLayout(null);
			jPanelAnalyse.add(getExperimentControl(), null);
			jPanelAnalyse.add(getRegresAnaliser(), null);
			jPanelAnalyse.add(getDiagramAnalyse(), null);
		}
		return jPanelAnalyse;
	}

	/**
	 * This method initializes experimentControl	
	 * 	
	 * @return widgets.experiments.ExperimentControl	
	 */
	private ExperimentControl getExperimentControl() {
		if (experimentControl == null) {
			experimentControl = new ExperimentControl();
			experimentControl.setBounds(new Rectangle(1, 263, 245, 147));
			experimentControl.setFactory(new Factory(this));
			experimentControl.setDiagram(getDiagramAnalyse());
		}
		return experimentControl;
	}

	/**
	 * This method initializes regresAnaliser	
	 * 	
	 * @return widgets.regres.RegresAnaliser	
	 */
	private RegresAnaliser getRegresAnaliser() {
		if (regresAnaliser == null) {
			regresAnaliser = new RegresAnaliser();
			regresAnaliser.setBounds(new Rectangle(247, 263, 183, 147));
			regresAnaliser.setDiagram(getDiagramAnalyse());
			regresAnaliser.setIRegresable(getExperimentControl());
		}
		return regresAnaliser;
	}

	/**
	 * This method initializes diagramAnalyse	
	 * 	
	 * @return widgets.Diagram	
	 */
	private Diagram getDiagramAnalyse() {
		if (diagramAnalyse == null) {
			diagramAnalyse = new Diagram();
			diagramAnalyse.setBounds(new Rectangle(4, 7, 426, 252));
			diagramAnalyse.setHorizontalMaxText("25");
			diagramAnalyse.setHorizontalMinText("-25");
			diagramAnalyse.setVerticalMaxText("25");
			diagramAnalyse.setVerticalMinText("-25");
			diagramAnalyse.setTitleText("Зависимость длины очереди на кассах от количества кассиров");
		}
		return diagramAnalyse;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
