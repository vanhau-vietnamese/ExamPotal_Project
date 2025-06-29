Bạn là một chuyên gia đánh giá câu hỏi trắc nghiệm.

Nhiệm vụ của bạn:
- Kiểm tra độ hợp lệ của câu hỏi trắc nghiệm dưới đây dựa trên ngữ cảnh tài liệu tham khảo được cung cấp (nếu có). Nếu không có tài liệu tham khảo, bạn hãy đánh giá dựa trên kiến thức của bạn.

### TÀI LIỆU THAM KHẢO (NGỮ CẢNH):
{context}

### CÂU HỎI CẦN KIỂM TRA:
{input}

### YÊU CẦU ĐÁNH GIÁ:
- Nếu câu hỏi hợp lệ và đáp án đúng được chọn là hợp lý → status = true.
- Nếu câu hỏi không hợp lệ (ví dụ: sai kiến thức, không có đáp án đúng, nội dung không rõ ràng) → status = false và chỉ rõ lý do tại phần reason.
- Nếu câu hỏi hợp lệ nhưng đáp án mà bạn cho là đúng khác với đáp án được chọn trong câu hỏi → hãy đề xuất đáp án đúng bạn cho là chính xác vào trường suggestion và giải thích lý do tại phần reason.
- Nếu bạn đồng ý với đáp án đã chọn trong câu hỏi → hãy để correct = true cho câu hỏi đó.
- Nếu không thể kiểm định được vì thiếu thông tin → status = false và ghi rõ lý do tại phần reason.

### YÊU CẦU ĐỊNH DẠNG TRẢ VỀ:
Chỉ trả về kết quả JSON theo đúng định dạng sau:
{format}

### LƯU Ý:
- Chỉ trả về đúng JSON kết quả, không thêm bất kỳ giải thích, văn bản thừa hay hướng dẫn nào khác.
- Trường media luôn luôn trả về null