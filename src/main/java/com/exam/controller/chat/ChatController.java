package com.exam.controller.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

//    // Đối tượng để tương tác với LLM
//    private final ChatClient chatClient;
//
//    // đối tượng sử dụng để truy vấn thông tin
//    private final QdrantVectorStore qdrantVectorStore;
//
//    String context = "";
//
//    @GetMapping("chat-ai")
//    public String doChat(@RequestParam String message) {
//        List<Document> contexts = qdrantVectorStore.similaritySearch(SearchRequest.builder()
//                                                                                    .query(message)
//                                                                                    .topK(10).build());
//        for(Document document : contexts) {
//            context += document.getFormattedContent();
//        }
//        return chatClient.prompt().system(sp -> sp.param("context", context)).user(message).call().content();
//    }

//    @GetMapping("insert-from-url")
//    public String insertFromUrl(@RequestParam(value = "url") String url) {
//        org.Jsoup.nodes.Document jsoupDocument;
//        Document vectorDocument;
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try{
//            // kết nối và lấy nô dung từ url
//            jsoupDocument = Jsoup.connect(url).get();
//
//            // xây dựng nội dung
//            contentBuilder.append(jsoupDocument.body().text()).append(" Đy là web nguồn tham khảo của nội dung ").append(jsoupDocument.title()).append(". Trang web (").append(url).append(" )\n");
//
//            // Gán nội dung vào đối tượng Document để xử lý dữ liệu
//            vectorDocument = new Document(contentBuilder.toString());
//
//            // chia nhỏ nội dung thành các token và thêm vào VectorStore
//            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
//            qdrantVectorStore.add(tokenTextSplitter.split(vectorDocument));
//            System.out.println(" Lưu thành côg");
//
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }
}
