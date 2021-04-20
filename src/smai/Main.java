package smai;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;
import smai.common.Assets;
import smai.data.Callback;
import smai.data.datasources.AnimationDataSource;
import smai.data.repositories.AnimationRepository;
import smai.data.datasources.UninformedSearchLocalDataSource;
import smai.data.repositories.UninformedSearchesRepository;
import smai.domain.Response;
import smai.domain.Instance;
import smai.framework.hanoi.HanoiInstance;
import smai.domain.SearchMethodItem;
import smai.common.SearchMethods;
import smai.common.StatusMessages;
import smai.data.AnimationListener;
import smai.data.datasources.InformedSearchLocalDataSource;
import smai.data.animations.StepAnimation;
import smai.data.repositories.InformedSearchesRepository;
import smai.data.searches.AStarSearchDataSource;
import smai.data.searches.BestFirstSearchDataSource;
import smai.data.searches.BreadthSearchDataSource;
import smai.data.searches.DepthSearchDataSource;
import smai.data.searches.HillClimbingSearchDataSource;
import smai.data.searches.OptimalSearchDataSource;
import smai.domain.Heuristic;
import smai.common.SearchType;
import smai.framework.hanoi.HanoiHeuristic;
import smai.usecases.AnimationControlUseCase;
import smai.usecases.ResolveInformedUseCase;
import smai.usecases.ResolveUninformedUseCase;

public class Main extends javax.swing.JFrame implements Callback<Response>, AnimationListener {

    private final UninformedSearchesRepository uninformedSearchesRepository;
    private final ResolveUninformedUseCase resolveUninformedUseCase;

    private final DepthSearchDataSource depthSearchDataSource;
    private final BreadthSearchDataSource breadthSearchDataSource;

    private final InformedSearchesRepository informedSearchesRepository;
    private final ResolveInformedUseCase resolveInformedUseCase;

    private final AStarSearchDataSource aStarSearchDataSource;
    private final BestFirstSearchDataSource bestFirstSearchDataSource;
    private final HillClimbingSearchDataSource hillClimbingSearchDataSource;
    private final OptimalSearchDataSource optimalSearchDataSource;

    private final AnimationDataSource animatorDataSource;
    private final AnimationRepository animationRepository;
    private final AnimationControlUseCase animationControlUseCase;
    private final Heuristic heuristic;

    private Response response;
    private boolean autoPlay;
    private boolean isPlaying;
    private boolean isAnimatingResponse;

    public Main() {
        heuristic = new HanoiHeuristic();

        depthSearchDataSource = new DepthSearchDataSource();
        breadthSearchDataSource = new BreadthSearchDataSource();

        uninformedSearchesRepository = new UninformedSearchesRepository(depthSearchDataSource);
        resolveUninformedUseCase = new ResolveUninformedUseCase(uninformedSearchesRepository);

        aStarSearchDataSource = new AStarSearchDataSource();
        bestFirstSearchDataSource = new BestFirstSearchDataSource();
        hillClimbingSearchDataSource = new HillClimbingSearchDataSource();
        optimalSearchDataSource = new OptimalSearchDataSource();

        informedSearchesRepository = new InformedSearchesRepository(aStarSearchDataSource);
        resolveInformedUseCase = new ResolveInformedUseCase(informedSearchesRepository);

        animatorDataSource = new StepAnimation(this);
        animationRepository = new AnimationRepository(animatorDataSource);
        animationControlUseCase = new AnimationControlUseCase(animationRepository);

        response = null;
        autoPlay = true;
        isPlaying = false;
        isAnimatingResponse = false;

        initComponents();
        initUI();
        initAlgorithms();
    }

    private void initUI() {
        ImageIcon icon = new ImageIcon(Assets.ICON);
        setIconImage(icon.getImage());

        DefaultCaret caret = (DefaultCaret) this.taConsole.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);

        enableControls(true);
        enabledMediaControls(false);
        enableLoader(false, StatusMessages.ON_STAND_BY);

        setColor(Color.white);
    }

    private void setColor(Color color) {
        this.getContentPane().setBackground(color);
        this.tbMenu.setBackground(color);
        this.pMediaControls.setBackground(color);
        this.pStatusBar.setBackground(color);
        this.btnPlay.setBackground(color);
        this.btnRun.setBackground(color);
        this.btnCleanConsole.setBackground(color);
        this.btnResetAnimation.setBackground(color);
    }

    private void setUninformedSearch(UninformedSearchLocalDataSource localDataSource) {
        uninformedSearchesRepository.setLocalDataSource(localDataSource);
    }

    private void setInformedSearch(InformedSearchLocalDataSource localDataSource) {
        informedSearchesRepository.setLocalDataSource(localDataSource);
    }

    private void initAlgorithms() {
        for (SearchMethodItem method : SearchMethods.METHODS) {
            this.cbSearchMethods.addItem(method);
        }

        instanceSearchMethod(SearchMethods.METHODS[0]);
    }

    private void instanceSearchMethod(SearchMethodItem method) {
        switch (method.getKey()) {
            case SearchMethods.DEPTH:
                setUninformedSearch(depthSearchDataSource);
                break;

            case SearchMethods.BREADTH:
                setUninformedSearch(breadthSearchDataSource);
                break;

            case SearchMethods.A_STAR:
                setInformedSearch(aStarSearchDataSource);
                break;

            case SearchMethods.BEST_FIRST:
                setInformedSearch(bestFirstSearchDataSource);
                break;

            case SearchMethods.HILL_CLIMBING:
                setInformedSearch(hillClimbingSearchDataSource);
                break;
                
            case SearchMethods.OPTIMAL:
                setInformedSearch(optimalSearchDataSource);
                break;
        }
    }

    private void enabledMediaControls(boolean enabled) {
        this.btnPlay.setEnabled(enabled);
        this.miPlay.setEnabled(enabled);
        this.btnResetAnimation.setEnabled(enabled && !isPlaying);
        this.miReplay.setEnabled(enabled && !isPlaying);
    }

    private void enableControls(boolean enabled) {
        btnRun.setEnabled(enabled);
        miRun.setEnabled(enabled);
        cbSearchMethods.setEnabled(enabled);
        sNumberOfDisks.setEnabled(enabled);
    }

    private void enableLoader(boolean enabled, String status) {
        progressBar.setIndeterminate(enabled);
        progressBar.setVisible(enabled);
        lStatus.setText(status);
    }
    
    private void setFPS(int fps) {
        animatorDataSource.setFps(fps);
    }
    
    private void changeFPS() {
        String inputFPS = JOptionPane.showInputDialog("Frames per second:", animatorDataSource.getFps());
        
        if (inputFPS != null) {
            try {
                int fps = Integer.parseInt(inputFPS);
                setFPS(fps);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "The amount of FPS should be a number", "Invalid input", JOptionPane.WARNING_MESSAGE);
            }
        }
        
    }

    private void animateResponse() {
        if (response == null) {
            return;
        }

        animationControlUseCase.play(this.response, this.animationPanel);

        this.isPlaying = true;
        this.isAnimatingResponse = true;

        enabledMediaControls(true);
        enableControls(false);
        enableLoader(true, StatusMessages.ON_PLAYING_ANIMATION);

        toggleMediaIcons();
    }

    private void play() {
        this.isPlaying = true;

        enabledMediaControls(true);
        enableControls(false);
        enableLoader(true, StatusMessages.ON_PLAYING_ANIMATION);

        animationControlUseCase.play();
        toggleMediaIcons();
    }

    private void pause() {
        this.isPlaying = false;

        enabledMediaControls(true);
        enableControls(true);
        enableLoader(false, StatusMessages.ON_ANIMATION_PAUSED);

        animationControlUseCase.pause();
        toggleMediaIcons();
    }

    private Instance getInstance() {
        try {
            int numberOfDisks = Integer.parseInt(this.sNumberOfDisks.getValue().toString());
            return new HanoiInstance(numberOfDisks);
        } catch (Exception e) {
            return null;
        }
    }

    private SearchMethodItem getSelectedMethod() {
        try {
            return (SearchMethodItem) this.cbSearchMethods.getSelectedItem();
        } catch (Exception e) {
            return null;
        }
    }

    private void runUninformed(Instance instance) {
        resolveUninformedUseCase.invoke(instance, this);
    }

    private void runInformed(Instance instance) {
        resolveInformedUseCase.invoke(instance, this.heuristic, this);
    }

    private void run(Instance instance, SearchType searchType) {
        switch (searchType) {
            case INFORMED:
                runInformed(instance);
                break;

            case UNINFORMED:
                runUninformed(instance);
                break;
        }
    }

    private void run() {
        HanoiInstance instance = (HanoiInstance) getInstance();
        SearchMethodItem selectedMethod = getSelectedMethod();

        if (instance != null && selectedMethod != null) {
            instanceSearchMethod(selectedMethod);

            response = null;
            isAnimatingResponse = false;

            enableControls(false);
            enabledMediaControls(false);
            enableLoader(true, StatusMessages.ON_RUNNING);

            run(instance, selectedMethod.getType());
        } else {
            this.loge("Could not create instance");
        }
    }

    private void toggleMediaIcons() {
        if (this.isPlaying) {
            this.btnPlay.setIcon(new ImageIcon(Assets.IC_PAUSE));
            this.miPlay.setIcon(new ImageIcon(Assets.IC_PAUSE_16));
            this.miPlay.setText("Pause");
        } else {
            this.btnPlay.setIcon(new ImageIcon(Assets.IC_PLAY));
            this.miPlay.setIcon(new ImageIcon(Assets.IC_PLAY_16));
            this.miPlay.setText("Play");
        }
    }

    private void toggleAnimation() {
        if (this.response == null) {
            return;
        }

        if (!isAnimatingResponse) {
            this.animateResponse();
        } else {
            if (!isPlaying) {
                this.play();
            } else {
                this.pause();
            }
        }

    }

    private void closeWindow() {
        this.dispose();
    }

    private void log(String message) {
        this.taConsole.append(message + "\n");
    }

    private void loge(String error) {
        this.taConsole.append(error + "\n");
    }

    private void cleanConsole() {
        this.taConsole.setText(null);
    }

    private void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    @Override
    public void onSuccess(Response result) {
        enableControls(true);
        enabledMediaControls(true);
        enableLoader(false, StatusMessages.ON_STAND_BY);

        if (result == null) {
            log("Something went grown, please try again.");
            return;
        }
        
        if (!result.hasSolution()) {
            this.log("No solution found.");
            return;
        }

        response = result;
        log(response.toString());

        if (autoPlay) {
            animateResponse();
        }
    }

    @Override
    public void onFailed(Exception e) {
        enableControls(true);
        enabledMediaControls(false);
        enableLoader(false, StatusMessages.ON_STAND_BY);

        this.loge(e.toString());
    }

    @Override
    public void onAnimationComplete() {
        this.pause();
        this.btnPlay.setEnabled(false);
        this.miPlay.setEnabled(false);
    }

    @Override
    public void onAnimationError(Exception e) {
        enableControls(true);
        enabledMediaControls(true);
        enableLoader(false, StatusMessages.ON_STAND_BY);

        this.loge(e.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        taConsole = new javax.swing.JTextArea();
        tbMenu = new javax.swing.JToolBar();
        btnRun = new javax.swing.JButton();
        btnCleanConsole = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        cbSearchMethods = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        sNumberOfDisks = new javax.swing.JSpinner();
        pStatusBar = new javax.swing.JPanel();
        lStatus = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        pMediaControls = new javax.swing.JPanel();
        btnPlay = new javax.swing.JButton();
        btnResetAnimation = new javax.swing.JButton();
        animationPanel = new smai.framework.hanoi.HanoiAnimationPanel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mRun = new javax.swing.JMenu();
        miRun = new javax.swing.JMenuItem();
        miCleanConsole = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        miPlay = new javax.swing.JMenuItem();
        miReplay = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        cbAutoPlay = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        miExit = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search Methods AI");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        taConsole.setEditable(false);
        taConsole.setBackground(new java.awt.Color(255, 255, 255));
        taConsole.setColumns(20);
        taConsole.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        taConsole.setRows(5);
        jScrollPane1.setViewportView(taConsole);

        tbMenu.setFloatable(false);
        tbMenu.setRollover(true);

        btnRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play.png"))); // NOI18N
        btnRun.setToolTipText("Run");
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        tbMenu.add(btnRun);

        btnCleanConsole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/clean.png"))); // NOI18N
        btnCleanConsole.setToolTipText("Clean Console");
        btnCleanConsole.setFocusable(false);
        btnCleanConsole.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCleanConsole.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanConsoleActionPerformed(evt);
            }
        });
        tbMenu.add(btnCleanConsole);
        tbMenu.add(jSeparator1);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("  Search Method:  ");
        tbMenu.add(jLabel1);

        cbSearchMethods.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbSearchMethods.setPreferredSize(new java.awt.Dimension(300, 27));
        tbMenu.add(cbSearchMethods);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("  Number of disks:  ");
        tbMenu.add(jLabel2);

        sNumberOfDisks.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        sNumberOfDisks.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(3), Integer.valueOf(3), null, Integer.valueOf(1)));
        sNumberOfDisks.setMaximumSize(new java.awt.Dimension(60, 28));
        sNumberOfDisks.setPreferredSize(new java.awt.Dimension(60, 28));
        tbMenu.add(sNumberOfDisks);

        javax.swing.GroupLayout pStatusBarLayout = new javax.swing.GroupLayout(pStatusBar);
        pStatusBar.setLayout(pStatusBarLayout);
        pStatusBarLayout.setHorizontalGroup(
            pStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pStatusBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pStatusBarLayout.setVerticalGroup(
            pStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pStatusBarLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(pStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lStatus))
                .addContainerGap())
        );

        pMediaControls.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play.png"))); // NOI18N
        btnPlay.setToolTipText("Play/Pause Animation");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnResetAnimation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/replay.png"))); // NOI18N
        btnResetAnimation.setToolTipText("Restart animation");
        btnResetAnimation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetAnimationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pMediaControlsLayout = new javax.swing.GroupLayout(pMediaControls);
        pMediaControls.setLayout(pMediaControlsLayout);
        pMediaControlsLayout.setHorizontalGroup(
            pMediaControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMediaControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnResetAnimation)
                .addContainerGap())
        );
        pMediaControlsLayout.setVerticalGroup(
            pMediaControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMediaControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMediaControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetAnimation)
                    .addComponent(btnPlay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        animationPanel.setBackground(new java.awt.Color(255, 255, 255));
        animationPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        javax.swing.GroupLayout animationPanelLayout = new javax.swing.GroupLayout(animationPanel);
        animationPanel.setLayout(animationPanelLayout);
        animationPanelLayout.setHorizontalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        animationPanelLayout.setVerticalGroup(
            animationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenuBar2.setBackground(new java.awt.Color(255, 255, 255));

        mRun.setText("Run");

        miRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        miRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play-16.png"))); // NOI18N
        miRun.setText("Run");
        miRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRunActionPerformed(evt);
            }
        });
        mRun.add(miRun);

        miCleanConsole.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        miCleanConsole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/clean-16.png"))); // NOI18N
        miCleanConsole.setText("Clean Console");
        miCleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCleanConsoleActionPerformed(evt);
            }
        });
        mRun.add(miCleanConsole);

        jMenuBar2.add(mRun);

        jMenu3.setText("Animation");

        miPlay.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        miPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play-16.png"))); // NOI18N
        miPlay.setText("Play");
        miPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPlayActionPerformed(evt);
            }
        });
        jMenu3.add(miPlay);

        miReplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/replay-16.png"))); // NOI18N
        miReplay.setText("Replay");
        miReplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miReplayActionPerformed(evt);
            }
        });
        jMenu3.add(miReplay);
        jMenu3.add(jSeparator5);

        jMenuItem3.setText("FPS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        cbAutoPlay.setSelected(true);
        cbAutoPlay.setText("Auto Play Animation");
        cbAutoPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAutoPlayActionPerformed(evt);
            }
        });
        jMenu3.add(cbAutoPlay);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Window");

        miExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        jMenu4.add(miExit);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pMediaControls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(animationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pMediaControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        this.run();
    }//GEN-LAST:event_btnRunActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        this.toggleAnimation();
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnCleanConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanConsoleActionPerformed
        this.cleanConsole();
    }//GEN-LAST:event_btnCleanConsoleActionPerformed

    private void miCleanConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCleanConsoleActionPerformed
        this.cleanConsole();
    }//GEN-LAST:event_miCleanConsoleActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        this.closeWindow();
    }//GEN-LAST:event_miExitActionPerformed

    private void miRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRunActionPerformed
        this.run();
    }//GEN-LAST:event_miRunActionPerformed

    private void miPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPlayActionPerformed
        this.toggleAnimation();
    }//GEN-LAST:event_miPlayActionPerformed

    private void cbAutoPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutoPlayActionPerformed
        this.setAutoPlay(cbAutoPlay.isSelected());
    }//GEN-LAST:event_cbAutoPlayActionPerformed

    private void btnResetAnimationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetAnimationActionPerformed
        animateResponse();
    }//GEN-LAST:event_btnResetAnimationActionPerformed

    private void miReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miReplayActionPerformed
        animateResponse();
    }//GEN-LAST:event_miReplayActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        changeFPS();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private smai.framework.hanoi.HanoiAnimationPanel animationPanel;
    private javax.swing.JButton btnCleanConsole;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnResetAnimation;
    private javax.swing.JButton btnRun;
    private javax.swing.JCheckBoxMenuItem cbAutoPlay;
    private javax.swing.JComboBox cbSearchMethods;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lStatus;
    private javax.swing.JMenu mRun;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miCleanConsole;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miPlay;
    private javax.swing.JMenuItem miReplay;
    private javax.swing.JMenuItem miRun;
    private javax.swing.JPanel pMediaControls;
    private javax.swing.JPanel pStatusBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSpinner sNumberOfDisks;
    private javax.swing.JTextArea taConsole;
    private javax.swing.JToolBar tbMenu;
    // End of variables declaration//GEN-END:variables

}
