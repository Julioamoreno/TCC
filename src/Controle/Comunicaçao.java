package Controle;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.AccessDeniedException;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.amazonaws.services.rekognition.model.DeleteCollectionRequest;
import com.amazonaws.services.rekognition.model.DeleteCollectionResult;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.InvalidParameterException;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;
import com.amazonaws.services.rekognition.model.SearchFacesRequest;
import com.amazonaws.services.rekognition.model.SearchFacesResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.json.*;

public class Comunicaçao {

    public static final String S3_BUCKET = "nuvemreconhecimento"; //Nuvem para guardar imagens.

    public CreateCollectionResult criarPasta(String nome) throws JSONException { //Função para criar uma pasta, passando o nome como referencia.
        try {
            
            Ligacao auth = new Ligacao();
            AmazonRekognition rekognitionClient = auth.conectar();  //Obtendo autenticação.
            CreateCollectionRequest request = new CreateCollectionRequest()     //Chama a funçao que cria uma pasta.
                    .withCollectionId(nome);                        //Atribui um nome a pasta/Collection.

            return rekognitionClient.createCollection(request);

        } catch (InvalidParameterException | AccessDeniedException e) {
            System.out.println("" + e);
        }
        return null;
    }

    public DeleteCollectionResult excluirPasta(String nome) {       //Função para exclusão de uma pasta, passando como parametro o nome.
        Ligacao auth = new Ligacao();
        AmazonRekognition rekognitionClient = auth.conectar();      //chama a funçao para obter autenticaçao.

        try {
            DeleteCollectionRequest request = new DeleteCollectionRequest()     //Chama a funçao de deletar um pasta.
                    .withCollectionId(nome);                                    //informa o nome da pasta a ser excluida.
            return rekognitionClient.deleteCollection(request);
        } catch (InvalidParameterException | AccessDeniedException e) {
            System.out.println("" + e);
        }
        return null;
    }

    public JSONArray listarPasta() {         //Função para listar os itens de uma pasta.
        Ligacao auth = new Ligacao();
        AmazonRekognition rekognitionClient = auth.conectar();  //Chama a função para obter autenticação.
        try {

            int limit = 1;
            ListCollectionsResult listaDeCollectionsResultado = null;
            String paginationToken = null;
            //String[] pastas = null;
            JSONArray jSONArray = new JSONArray();
            do {
                if (listaDeCollectionsResultado != null) {
                    paginationToken = listaDeCollectionsResultado.getNextToken(); //atribui a uma variavel, o proximo resultado.
                }
                listaDeCollectionsResultado = verListaDeColections(paginationToken, limit, rekognitionClient);//Chama a função para ver a lista de pastas, e guarda em uma variavel.
                List< String> colecaoIds = listaDeCollectionsResultado.getCollectionIds(); // cria uma lista de String chamada colecaoIDS, e atribui todos IDS.
                for (String resultId : colecaoIds) {
                    JSONObject meu = new JSONObject();
                    meu.put("Pasta", resultId);
                    jSONArray.put(meu);
                    
                }
            } while (listaDeCollectionsResultado != null && listaDeCollectionsResultado.getNextToken()!= null); //Testa se a lista de resultados foi terminada.
            return jSONArray;
        } catch (Exception e) {
            System.out.println("" + e);
        }
            return null;
    }

    public PutObjectResult uploadDeImagem(String caminho) {    //envia imagem do pc para o Amazon Bucket.
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());   //Se autenticando com o servidor AWS.
        try {
            File file = new File(caminho);
            String[] ultimo = caminho.split("\\\\"); //Splitando caminho, para receber o nome do arquivo enviado.
            String nomeDoArquivo = ultimo[ultimo.length - 1]; //Separando a ultima parte do caminho, que é o nome do propio arquivo.
            PutObjectResult putObjectResultado = s3client.putObject(new PutObjectRequest(S3_BUCKET, nomeDoArquivo, file)); //Funçao que envia uma imagem ao Bucket, passando o parametro o nome do bucket, nome do arquivo e o arquivo a ser enviado.
                     
            return putObjectResultado;
        } catch (AmazonServiceException ase) {
            System.out.println("" + ase);
        }
        return null;
    }

    public JSONArray listarItens() throws JSONException, IllegalArgumentException {   //Listar itens da Bucket.
       
        try {
             AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
            final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(S3_BUCKET).withMaxKeys(2);
            ListObjectsV2Result resultado;
            //instancia um novo JSONObject

            JSONArray jSONArray = new JSONArray();
            do {
                resultado = s3client.listObjectsV2(req);

                for (S3ObjectSummary objectSummary : resultado.getObjectSummaries()) {
                    JSONObject meu = new JSONObject();
                    meu.put("Nome", objectSummary.getKey());
                    meu.put("Tamanho", objectSummary.getSize());
                    jSONArray.put(meu);

                }
                req.setContinuationToken(resultado.getNextContinuationToken());

            } while (resultado.isTruncated() == true);
            return jSONArray;
        } catch (AmazonServiceException ase) {
            JOptionPane.showMessageDialog(null, "ERRO : "+ase);
            //System.out.println(" " + ase);
        }catch(java.lang.IllegalArgumentException ex){
             JOptionPane.showMessageDialog(null, "ERRO : "+ex);
        }
        return null;
    }

    public JSONArray listarFaces(String nomeCollection) throws JsonProcessingException {
        try {

            Ligacao auth = new Ligacao();
            AmazonRekognition rekognitionClient = auth.conectar();
            ObjectMapper objectMapper = new ObjectMapper();
            ListFacesResult listFacesResult = null;
            System.out.println("Rostos cadastrados na pasta " + nomeCollection);
            String paginationToken = null;
            JSONArray jSONArray = new JSONArray();
            do {
                if (listFacesResult != null) {
                    paginationToken = listFacesResult.getNextToken();
                }
                listFacesResult = verListaDeFaces(nomeCollection, 1, paginationToken,
                        rekognitionClient);
                List< Face> faces = listFacesResult.getFaces();
                for (Face face : faces) {
                    JSONObject meu = new JSONObject();
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(face));     //Escrevendo no terminal.
                    meu.put("Face", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
                    jSONArray.put(meu);
                    
                }
            } while (listFacesResult != null && listFacesResult.getNextToken() != null);
            return jSONArray;
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return null;
    }
    
    public SearchFacesResult procurarFaces(String collectionId, String idDaImagem) { //Reconhece uma imagem, passando como parametro uma pasta para ser feito a comparaçao, o id da imagem que é o nome da imagem.
        Float taxaDeIgualdade = 70F;
        int maxFaces = 2;
        Ligacao auth = new Ligacao();
        AmazonRekognition rekognitionClient = auth.conectar();

        IndexFacesResult indexFacesResult = AdicionarFace(collectionId, idDaImagem);     //Chama a funçar Adicionar Face ,que é passado como parametro uma imagem ser adcionada na pasta. E ser retornado o FaceID.
        List< FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
        String faceId = null;
        for (FaceRecord faceRecord : faceRecords) {
            faceId = faceRecord.getFace().getFaceId();      //faceId da imagem é colocada em uma variavel.
        }

        SearchFacesRequest searchFacesRequest = new SearchFacesRequest()
                .withCollectionId(collectionId)             //atribui um nome a pasta.
                .withFaceId(faceId)                         //Informa o ID da imagem a ser analisada.
                .withFaceMatchThreshold(taxaDeIgualdade)    //Atribui um Porcentagem de taxa de acerto.
                .withMaxFaces(maxFaces);                    
        return rekognitionClient.searchFaces(searchFacesRequest);
    }

    public IndexFacesResult AdicionarFace(String collectionId, String externalImageId, String idImagem) { //Enviar do bucket para alguma Collection.
        Ligacao auth = new Ligacao();
        AmazonRekognition rekognitionClient = auth.conectar();
        Image imagem = tornarImagem(S3_BUCKET, idImagem);

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(imagem)
                .withCollectionId(collectionId)
                .withExternalImageId(externalImageId);

        return rekognitionClient.indexFaces(indexFacesRequest);
    }

    public IndexFacesResult AdicionarFace(String collectionId, String idImagem) {   //Enviar do bucket para alguma Collection, com assinatura de metodo diferente pois essa funçao sera usada na funçao de reconhecimento.
        Ligacao auth = new Ligacao();
        AmazonRekognition rekognitionClient = auth.conectar();
        Image imagem = tornarImagem(S3_BUCKET, idImagem);

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(imagem)
                .withCollectionId(collectionId);

        return rekognitionClient.indexFaces(indexFacesRequest);
    }

    private static ListCollectionsResult verListaDeColections(String paginationToken, int limit, AmazonRekognition amazonRekognition) {
        ListCollectionsRequest listCollectionsRequest = new ListCollectionsRequest()
                .withMaxResults(limit)
                .withNextToken(paginationToken);
        return amazonRekognition.listCollections(listCollectionsRequest);
    }

    private static ListFacesResult verListaDeFaces(String collectionId, int limit, String paginationToken, AmazonRekognition amazonRekognition) {
        ListFacesRequest listFacesRequest = new ListFacesRequest()
                .withCollectionId(collectionId)
                .withMaxResults(limit)
                .withNextToken(paginationToken);
        return amazonRekognition.listFaces(listFacesRequest);
    }

    private static Image tornarImagem(String bucket, String key) {
        return new Image()
                .withS3Object(new S3Object()
                        .withBucket(bucket)
                        .withName(key));
    }
}
