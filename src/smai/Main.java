package smai;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.text.DefaultCaret;
import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;
import smai.common.Assets;
import smai.common.utils.Callback;
import smai.data.datasources.AnimationDataSource;
import smai.data.repositories.AnimationRepository;
import smai.data.datasources.SearchLocalDataSource;
import smai.data.repositories.SearchMethodsRepository;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.framework.hanoi.HanoiInstance;
import smai.domain.SearchMethodItem;
import smai.common.utils.SearchMethods;
import smai.common.StatusMessages;
import smai.data.datasources.AnimationListener;
import smai.data.searches.BreadthSearchDataSource;
import smai.framework.hanoi.datasources.HanoiAnimatorDataSource;
import smai.data.searches.DepthSearchDataSource;
import smai.usecases.PauseAnimationUseCase;
import smai.usecases.PlayAnimationUseCase;
import smai.usecases.ResolveUseCase;

public class Main extends javax.swing.JFrame implements Callback<Answer>, AnimationListener {

    private SearchMethodsRepository searchRepository;
    private ResolveUseCase resolveUseCase;

    private final AnimationDataSource animatorDataSource;
    private final AnimationRepository animationRepository;
    private final PlayAnimationUseCase playAnimationUseCase;
    private final PauseAnimationUseCase pauseAnimationUseCase;

    private Answer answer;
    private boolean autoPlay;
    private boolean isPlaying;
    private boolean isAnimatingResponse;

    public Main() {
        this.animatorDataSource = new HanoiAnimatorDataSource(this);
        this.animationRepository = new AnimationRepository(animatorDataSource);
        this.playAnimationUseCase = new PlayAnimationUseCase(animationRepository);
        this.pauseAnimationUseCase = new PauseAnimationUseCase(animationRepository);

        this.answer = null;
        this.autoPlay = true;
        this.isPlaying = false;
        this.isAnimatingResponse = false;

        initComponents();
        initUI();
        initAlgorithms();
    }

    private void initUI() {
        // this.getContentPane().setBackground(Color.WHITE);
        
        ImageIcon icon = new ImageIcon(Assets.ICON);
        setIconImage(icon.getImage());

        DefaultCaret caret = (DefaultCaret) this.taConsole.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);
        
        enableControls(true);
        enabledMediaControls(false);
        enableLoader(false, StatusMessages.STAND_BY);
    }

    private void setSearchMethod(SearchLocalDataSource localDataSource) {
        this.searchRepository = new SearchMethodsRepository(localDataSource);
        this.resolveUseCase = new ResolveUseCase(searchRepository);
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
                this.setSearchMethod(new DepthSearchDataSource());
                break;

            case SearchMethods.BREADTH:
                this.setSearchMethod(new BreadthSearchDataSource());
                break;

            default:
                this.setSearchMethod(new DepthSearchDataSource());
        }
    }

    private void enabledMediaControls(boolean enabled) {
        this.btnPlay.setEnabled(enabled);
        this.miPlay.setEnabled(enabled);
        this.btnResetAnimation.setEnabled(enabled && !isPlaying);
        this.miReplay.setEnabled(enabled && !isPlaying);
    }

    private void enableControls(boolean enabled) {
        this.btnRun.setEnabled(enabled);
        this.miRun.setEnabled(enabled);
        this.cbSearchMethods.setEnabled(enabled);
        this.cbNumberOfDisks.setEnabled(enabled);
    }
        
    private void enableLoader(boolean enabled, String status) {
        this.progressBar.setIndeterminate(enabled);
        this.progressBar.setVisible(enabled);
        this.lStatus.setText(status);
    }

    private void animateResponse() {  
        if (this.answer == null) return;
        
        playAnimationUseCase.invoke(this.answer, this.pAnimation);
        
        this.isPlaying = true;
        this.isAnimatingResponse = true;
        
        enabledMediaControls(true);
        enableControls(false);
        enableLoader(true, StatusMessages.PLAYING_ANIMATION);
        
        toggleMediaIcons();
    }
    
    private void play() {
        this.isPlaying = true;
            
        enabledMediaControls(true);
        enableControls(false);
        enableLoader(true, StatusMessages.PLAYING_ANIMATION);
        
        playAnimationUseCase.invoke();
        toggleMediaIcons();
    }
    
    private void pause() {
        this.isPlaying = false;
            
        enabledMediaControls(true);
        enableControls(true);
        enableLoader(false, StatusMessages.ANIMATION_PAUSED);
        
        pauseAnimationUseCase.invoke();
        toggleMediaIcons();
    }
    
    private Instance getInstance() {
        try {
            SearchMethodItem selectedMethods = (SearchMethodItem) this.cbSearchMethods.getSelectedItem();
            this.instanceSearchMethod(selectedMethods);
            int numberOfDisks = Integer.parseInt(this.cbNumberOfDisks.getSelectedItem().toString());
            return new HanoiInstance(numberOfDisks);
        } catch (Exception e) {
            return null;
        }
    }

    private void run() {
        HanoiInstance instance = (HanoiInstance) this.getInstance();

        if (instance != null) {
            this.answer = null;
            this.isAnimatingResponse = false;
            
            enableControls(false);
            enabledMediaControls(false);
            enableLoader(true, StatusMessages.RUNNING);
            
            resolveUseCase.invoke(instance, this);
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
        if (this.answer == null) {
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
    public void onSuccess(Answer result) {
        enableControls(true);
        enabledMediaControls(true);
        enableLoader(false, StatusMessages.STAND_BY);
        
        if (result == null) {
            this.log("Something went grown, please try again.");
            return;
        }

        this.answer = result;
        this.log(answer.toString());

        if (this.autoPlay) {
            this.animateResponse();
        }
    }

    @Override
    public void onFailed(Exception error) {
        enableControls(true);
        enabledMediaControls(false);
        enableLoader(false, StatusMessages.STAND_BY);
        
        this.loge(error.toString());
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
        enableLoader(false, StatusMessages.STAND_BY);
        
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
        pAnimation = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnRun = new javax.swing.JButton();
        btnCleanConsole = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbSearchMethods = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cbNumberOfDisks = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        lStatus = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        btnPlay = new javax.swing.JButton();
        btnResetAnimation = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        mRun = new javax.swing.JMenu();
        miRun = new javax.swing.JMenuItem();
        miCleanConsole = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        miPlay = new javax.swing.JMenuItem();
        miReplay = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
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

        pAnimation.setBackground(new java.awt.Color(255, 255, 255));
        pAnimation.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        javax.swing.GroupLayout pAnimationLayout = new javax.swing.GroupLayout(pAnimation);
        pAnimation.setLayout(pAnimationLayout);
        pAnimationLayout.setHorizontalGroup(
            pAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 684, Short.MAX_VALUE)
        );
        pAnimationLayout.setVerticalGroup(
            pAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play.png"))); // NOI18N
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRun);

        btnCleanConsole.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/clean.png"))); // NOI18N
        btnCleanConsole.setFocusable(false);
        btnCleanConsole.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCleanConsole.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanConsoleActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCleanConsole);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("  Search Method:  ");
        jToolBar1.add(jLabel1);

        cbSearchMethods.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jToolBar1.add(cbSearchMethods);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("  Number of disks:  ");
        jToolBar1.add(jLabel2);

        cbNumberOfDisks.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbNumberOfDisks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "7", "10", "11" }));
        cbNumberOfDisks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNumberOfDisksActionPerformed(evt);
            }
        });
        jToolBar1.add(cbNumberOfDisks);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lStatus))
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/play.png"))); // NOI18N
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnResetAnimation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smai/src/replay.png"))); // NOI18N
        btnResetAnimation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetAnimationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnResetAnimation)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetAnimation)
                    .addComponent(btnPlay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void cbNumberOfDisksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNumberOfDisksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNumberOfDisksActionPerformed

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
    private javax.swing.JButton btnCleanConsole;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnResetAnimation;
    private javax.swing.JButton btnRun;
    private javax.swing.JCheckBoxMenuItem cbAutoPlay;
    private javax.swing.JComboBox cbNumberOfDisks;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lStatus;
    private javax.swing.JMenu mRun;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miCleanConsole;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miPlay;
    private javax.swing.JMenuItem miReplay;
    private javax.swing.JMenuItem miRun;
    private javax.swing.JPanel pAnimation;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea taConsole;
    // End of variables declaration//GEN-END:variables

}
