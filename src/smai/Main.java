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
import smai.domain.SearchMethod;
import smai.common.utils.SearchMethods;
import smai.framework.hanoi.HanoiAnimatorDataSource;
import smai.framework.hanoi.HanoiState;
import smai.data.DepthSearchDataSource;
import smai.usecases.AnimateUseCase;
import smai.usecases.ResolveUseCase;


public class Main extends javax.swing.JFrame implements Callback<Answer> {

    private SearchLocalDataSource localDataSource;
    private final SearchMethodsRepository searchRepository;
    private final ResolveUseCase resolveUseCase;
    
    private AnimationDataSource animatorDataSource;
    private final AnimationRepository animationRepository;
    private final AnimateUseCase animateUseCase;
    
    private Answer answer = null;
    
    public Main() {
        this.localDataSource = new DepthSearchDataSource();
        this.searchRepository = new SearchMethodsRepository(localDataSource);
        this.resolveUseCase = new ResolveUseCase(searchRepository);   
                
        this.animatorDataSource = new HanoiAnimatorDataSource();
        this.animationRepository = new AnimationRepository(animatorDataSource);
        this.animateUseCase = new AnimateUseCase(animationRepository);
        
        initComponents();
        initAlgorithms();
    }
    
    private void initAlgorithms() {
        for (SearchMethod method : SearchMethods.methods) {
            this.cbSearchMethods.addItem(method);
        }
    }
    
    private void instanceSearchMethod(SearchMethod method) {
        this.localDataSource =  new DepthSearchDataSource();
        this.animatorDataSource = new HanoiAnimatorDataSource();
    }
    
    private Instance getInstance() {
        try {
            SearchMethod selectedMethods = (SearchMethod) this.cbSearchMethods.getSelectedItem();
            this.instanceSearchMethod(selectedMethods);
            
            int numberOfDisks = Integer.parseInt(this.cbNumberOfDisks.getSelectedItem().toString());
            String initialTower = this.cbInitialTower.getSelectedItem().toString();
            String targetTower = this.cbTargetTower.getSelectedItem().toString();
            
            HanoiState initialState = new HanoiState();
            HanoiState targetState = new HanoiState();
            
            if (initialState.instanceTower(initialTower, numberOfDisks) && initialState.instanceTower(targetTower, numberOfDisks)) {
                return new HanoiInstance(initialState, targetState, numberOfDisks);
            } else {
                return null;
            }
            
        } catch(Exception e) {
            return null;
        }
    }
    
    
    @Override
    public void onSuccess(Answer result) {
        this.answer = result;
        this.taConsole.append(result.toString());
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
        jButton2 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        cbSearchMethods = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbNumberOfDisks = new javax.swing.JComboBox();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        cbInitialTower = new javax.swing.JComboBox();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        cbTargetTower = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

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

        jButton2.setText("Play");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAnimationLayout = new javax.swing.GroupLayout(jpAnimation);
        jpAnimation.setLayout(jpAnimationLayout);
        jpAnimationLayout.setHorizontalGroup(
            jpAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAnimationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jpAnimationLayout.setVerticalGroup(
            jpAnimationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAnimationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setText("Run");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
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

        cbNumberOfDisks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "10", "11" }));
        jToolBar1.add(cbNumberOfDisks);
        jToolBar1.add(jSeparator5);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Initial Tower:");
        jToolBar1.add(jLabel3);
        jToolBar1.add(jSeparator6);

        cbInitialTower.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C" }));
        jToolBar1.add(cbInitialTower);
        jToolBar1.add(jSeparator7);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Target Tower:");
        jToolBar1.add(jLabel5);
        jToolBar1.add(jSeparator8);

        cbTargetTower.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "C" }));
        jToolBar1.add(cbTargetTower);

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

        jMenu4.setText("Window");

        jMenuItem1.setText("Close");
        jMenu4.add(jMenuItem1);

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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        HanoiInstance instance = (HanoiInstance) this.getInstance();
        
        if (instance != null) {
            resolveUseCase.invoke(instance, this);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.answer != null) {
            animateUseCase.invoke(this.answer, this.jpAnimation);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    
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
    private javax.swing.JComboBox cbInitialTower;
    private javax.swing.JComboBox cbNumberOfDisks;
    private javax.swing.JComboBox cbSearchMethods;
    private javax.swing.JComboBox cbTargetTower;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel jpAnimation;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTextArea taConsole;
    // End of variables declaration//GEN-END:variables

}
