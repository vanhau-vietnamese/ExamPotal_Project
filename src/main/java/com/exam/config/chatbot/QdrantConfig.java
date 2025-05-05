//package com.exam.config.chatbot;
//
//import io.qdrant.client.QdrantClient;
//import io.qdrant.client.QdrantGrpcClient;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QdrantConfig {
//    @Bean
//    public QdrantClient qdrantClient() {
//        QdrantGrpcClient.Builder grpcClientBuilder =
//                QdrantGrpcClient.newBuilder("e247dfe6-f57c-4334-ad8a-43a4b39cc7b5.europe-west3-0.gcp.cloud.qdrant.io",6334, true);
//        grpcClientBuilder.withApiKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3MiOiJtIn0.aBLAIv-5Qv6O8RnTQ-U2cclYtsVZQOcLo3aYeux1WyA");
//
//        return new QdrantClient(grpcClientBuilder.build());
//    }
//
//    @Bean
//    public QdrantVectorStore vectorStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
//        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
//                .collectionName("custom-collection")     // Optional: defaults to "vector_store"
//                .initializeSchema(true)                  // Optional: defaults to false
//                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
//                .build();
//    }
//    // This can be any EmbeddingModel implementatio
//}
