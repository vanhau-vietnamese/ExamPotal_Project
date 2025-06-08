package com.exam.chatbot.controller;

import org.jsoup.Jsoup;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("")
public class TestController {
    private final QdrantVectorStore vectorStore;

    public TestController(QdrantVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/insert-from-url")
    public void insertFromUrl(@RequestParam("url") String url) {
        org.jsoup.nodes.Document jsoupDocument;
        Document vectorDocument;
        StringBuilder contentBuilder = new StringBuilder();

        try{
            jsoupDocument = Jsoup.connect(url).get();

            contentBuilder.append(jsoupDocument.body().text()).append(" Đây là web nguồn tham khảo của nội dung ").append(jsoupDocument.title()).append(". Trang web (").append(url).append(" )\n");

            // Gán nội dung vào document
            vectorDocument = new Document(contentBuilder.toString());

            // Chia nhỏ nội dung thành các token và thêm vao Vectostore
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            vectorStore.add(tokenTextSplitter.split(vectorDocument));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
