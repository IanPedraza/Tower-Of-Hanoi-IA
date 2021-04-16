package smai;

import javax.swing.JOptionPane;
import smai.common.utils.Callback;
import smai.data.AnimationDataSource;
import smai.data.AnimationRepository;
import smai.data.SearchLocalDataSource;
import smai.data.SearchMethodsRepository;
import smai.domain.Answer;
import smai.domain.Instance;
import smai.framework.hanoi.HanoiInstance;
import smai.common.utils.SearchMethodItem;
import smai.common.utils.SearchMethods;
import smai.data.BreadthSearchDataSource;
import smai.framework.hanoi.HanoiAnimatorDataSource;
import smai.data.DepthSearchDataSource;
import smai.usecases.AnimateUseCase;
import smai.usecases.ResolveUseCase;

public class Main extends javax.swing.JFrame implements Callback<Answer> {

    private SearchMethodsRepository searchRepository;
    private ResolveUseCase resolveUseCase;

    private final AnimationDataSource animatorDataSource;
    private final AnimationRepository animationRepository;
    private final AnimateUseCase animateUseCase;

    private Answer answer = null;

    public Main() {
        this.animatorDataSource = new HanoiAnimatorDataSource();
        this.animationRepository = new AnimationRepository(animatorDataSource);
        this.animateUseCase = new AnimateUseCase(animationRepository);

        initComponents();
        initAlgorithms();
    }
    
    private void setMethod(SearchLocalDataSource localDataSource) {
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
                this.setMethod(new DepthSearchDataSource());
                break;

            case SearchMethods.BREADTH:
                this.setMethod( new BreadthSearchDataSource());
                break;

            default:
                this.setMethod(new DepthSearchDataSource());
        }
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

    private void cleanConsole() {
        this.taConsole.setText(null);
    }
    
    private void run() {        
        HanoiInstance instance = (HanoiInstance) this.getInstance();

        if (instance != null) {            
            setDisableLoadingControls(true);
            resolveUseCase.invoke(instance, this);
        } else {
            this.taConsole.append("Coul not create instance...\n");
        }
        
    }
    
    private void closeWindow() {
        this.dispose();
    }
    
    private void playAnimation() {
        if (this.answer != null) {
            animateUseCase.invoke(this.answer, this.jpAnimation);
        }
    }
    
    private void setDisableLoadingControls(boolean isLoading) {
        boolean isEnabled = !isLoading;
        
        this.btnRun.setEnabled(isEnabled);
        this.miRun.setEnabled(isEnabled);
        this.btnPlay.setEnabled(isEnabled);
    }

    @Override
    public void onSuccess(Answer result) {
        setDisableLoadingControls(false);
        
        if (result == null) {
            this.taConsole.append("Something went grown, please try again.");
            return;
        }

        this.answer = result;
        this.taConsole.append(answer.toString());
    }

    @Override
    public void onFailed(Exception error) {
        JOptionPane.showMessageDialog(this, "Error");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        taConsole = new javax.swing.JTextArea();
        jpAnimation = new javax.swing.JPanel();
        btnPlay = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnRun = new javax.swing.JButton();
        btnCleanConsole = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        cbSearchMethods = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbNumberOfDisks = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mRun = new javax.swing.JMenu();
        miRun = new javax.swing.JMenuItem();
        miCleanConsole = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        miExit = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tower Of Hanoi - AI");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        taConsole.setEditable(false);
        taConsole.setBackground(new java.awt.Color(255, 255, 255));
        taConsole.setColumns(20);
        taConsole.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        taConsole.setRows(5);
        jScrollPane1.setViewportView(taConsole);

        jpAnimation.setBackground(new java.awt.Color(255, 255, 255));
        jpAnimation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAnimationLayout = new javax.swing.GroupLayout(jpAnimation);
        jpAnimation.setLayout(jpAnimationLayout);
        jpAnimationLayout.setHorizontalGroup(
            jpAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAnimationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPlay)
                .addContainerGap())
        );
        jpAnimationLayout.setVerticalGroup(
            jpAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAnimationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnRun.setText("Run");
        btnRun.setFocusable(false);
        btnRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRun);

        btnCleanConsole.setText("Clean Console");
        btnCleanConsole.setFocusable(false);
        btnCleanConsole.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCleanConsole.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanConsoleActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCleanConsole);
        jToolBar1.add(jSeparator1);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Search Method:");
        jToolBar1.add(jLabel1);
        jToolBar1.add(jSeparator4);

        jToolBar1.add(cbSearchMethods);
        jToolBar1.add(jSeparator2);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Number of disks:");
        jToolBar1.add(jLabel2);
        jToolBar1.add(jSeparator3);

        cbNumberOfDisks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "6", "10", "11" }));
        cbNumberOfDisks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNumberOfDisksActionPerformed(evt);
            }
        });
        jToolBar1.add(cbNumberOfDisks);

        jLabel4.setText("Data");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jMenuBar2.setBackground(new java.awt.Color(255, 255, 255));

        mRun.setText("Run");

        miRun.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        miRun.setText("Run");
        miRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRunActionPerformed(evt);
            }
        });
        mRun.add(miRun);

        miCleanConsole.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        miCleanConsole.setText("Clean Console");
        miCleanConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCleanConsoleActionPerformed(evt);
            }
        });
        mRun.add(miCleanConsole);

        jMenuBar2.add(mRun);

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
                        .addComponent(jpAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jpAnimation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
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
       this.playAnimation();
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
    private javax.swing.JButton btnRun;
    private javax.swing.JComboBox cbNumberOfDisks;
    private javax.swing.JComboBox cbSearchMethods;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel jpAnimation;
    private javax.swing.JMenu mRun;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem miCleanConsole;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miRun;
    private javax.swing.JTextArea taConsole;
    // End of variables declaration//GEN-END:variables

}
