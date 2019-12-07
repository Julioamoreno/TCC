
package Telas;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controle.Comunicaçao;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Julio
 */
public class InserirImagem extends javax.swing.JFrame { 

    public InserirImagem() {
        initComponents();   
        this.setTitle("Inserir Imagem");
        try {
            preencherFotos();
            
        } catch (JSONException ex) {
            Logger.getLogger(InserirImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtImagensBucket = new java.awt.TextArea();
        lblSubTitulo = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        txtImagem = new javax.swing.JLabel();
        txtNomeDaImagem = new javax.swing.JTextField();
        lblPasta = new javax.swing.JLabel();
        txtNomeDaPasta = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        lblNomeDaFace = new javax.swing.JLabel();
        txtNomeDaFace = new javax.swing.JTextField();

        fileChooser.setDialogTitle("Inserir Imagem");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inserir imagem");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Inserir Imagem");

        txtImagensBucket.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        lblSubTitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSubTitulo.setText("Imagens Disponiveis na Bucket");

        lblInfo.setText("Visualize a tabela abaixo na hora de dizer o nome da imagem.");

        txtImagem.setText("Nome da Imagem:");

        lblPasta.setText("Nome da Pasta");

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        lblNomeDaFace.setText("Nome da Face");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtImagem)
                            .addComponent(lblNomeDaFace))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(lblInfo))
                                    .addComponent(txtNomeDaFace, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNomeDaImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPasta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNomeDaPasta))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(215, 215, 215)
                                .addComponent(lblTitulo))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(txtImagensBucket, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(lblSubTitulo)))
                        .addGap(0, 86, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(btnEnviar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImagem)
                    .addComponent(txtNomeDaImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPasta)
                    .addComponent(txtNomeDaPasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeDaFace)
                    .addComponent(txtNomeDaFace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(btnEnviar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubTitulo)
                .addGap(19, 19, 19)
                .addComponent(txtImagensBucket, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(601, 423));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        try {
            
        Comunicaçao conexao=new Comunicaçao();
                
         IndexFacesResult indexFacesResult = conexao.AdicionarFace(txtNomeDaPasta.getText(),txtNomeDaFace.getText(), txtNomeDaImagem.getText());
            System.out.println(txtNomeDaImagem + " adcionada no album "+txtNomeDaPasta);
            
            List < FaceRecord > faceRecords = indexFacesResult.getFaceRecords();
            for (FaceRecord faceRecord: faceRecords) {
                if (faceRecord.getFaceDetail().getConfidence() >= 90){
                    JOptionPane.showMessageDialog(null, "--- Foto Adicionada --  "+faceRecord.getFaceDetail().getConfidence()+"\n"+
                            "Rosto detectado;  Faceid: " +faceRecord.getFace().getFaceId()+" ImagemId: "+faceRecord.getFace().getImageId());
                    }
                }

        } catch (SdkClientException e) {
            System.out.println(""+e);
        }        
    }//GEN-LAST:event_btnEnviarActionPerformed
 
    public void preencherFotos() throws JSONException{ //funçao para preencher area de texto, com os itens do bucker.
           Comunicaçao conexao = new Comunicaçao();     
           JSONArray resposta = conexao.listarItens();   //chama a funçao listar itens, que retorna as fotos que estao na bucket.

           for (int i = 0; i < resposta.length(); i++) {
           //Escreve numa JtextArea, as imagens que estão na Bucket.
           txtImagensBucket.append(" Nome:  "+resposta.getJSONObject(i).getString("Nome")+"  ; Tamanho:"+resposta.getJSONObject(i).getInt("Tamanho")+"\n");
           }
        }
   
     //Handle open button action.
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblNomeDaFace;
    private javax.swing.JLabel lblPasta;
    private javax.swing.JLabel lblSubTitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel txtImagem;
    private java.awt.TextArea txtImagensBucket;
    private javax.swing.JTextField txtNomeDaFace;
    private javax.swing.JTextField txtNomeDaImagem;
    private javax.swing.JTextField txtNomeDaPasta;
    // End of variables declaration//GEN-END:variables

}
