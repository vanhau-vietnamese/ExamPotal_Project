Bạn là một chuyên gia giáo dục. Dựa vào tài liệu sau, hãy tạo ra {number} câu hỏi trắc nghiệm. Mỗi câu hỏi phải rõ ràng, chính xác và chỉ có một đáp án đúng.

Tài liệu tham khảo:
{context}

Yêu cầu:
1. Tạo {number} câu hỏi trắc nghiệm dựa trên tài liệu trên.
2. {message}
3. Mỗi câu hỏi phải có từ 3 đến 5 đáp án.
4. Trong mỗi câu hỏi, chỉ một đáp án là đúng (đáp án đúng phải có trường correct = true, các đáp án sai phải có correct = false).
5. Bạn hãy chọn đáp án đúng cho câu hỏi luôn
6. Nội dung câu hỏi và đáp án phải phù hợp với tài liệu tham khảo.
7. Trả kết quả theo đúng định dạng JSON dữ liệu thực tế sau:
{format}

- Tuyệt đối KHÔNG trả về JSON Schema, KHÔNG trả về bất kỳ giải thích, hướng dẫn hay văn bản thừa nào.
- Chỉ trả về dữ liệu JSON đúng với kết quả mà người dùng cần.
