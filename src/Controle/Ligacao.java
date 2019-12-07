package Controle;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ligacao {

    private AWSCredentials credentials;  
    
    public AmazonRekognition conectar() {                                           //função para obter autenticação, e retornar ela.
        try {
                credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (java.lang.IllegalArgumentException e) {
            //throw new AmazonClientException("Erro "+ e);
             final JPanel panel = new JPanel();
              JOptionPane.showMessageDialog(panel, "ERRO : "+e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder        //funçao que pede autenticação ao servidor.
                .standard()         
                .withRegion(Regions.US_WEST_2)                                      //informa qual a regiao do servidor Amazon que se faz uso.
                .withCredentials(new AWSStaticCredentialsProvider(credentials))     //pede uma nova autenticação.
                .build();
        return rekognitionClient;                                                   //retorna a autenticação.
    }
}