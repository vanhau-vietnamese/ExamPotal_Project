Bạn là một chuyên gia đánh giá câu hỏi trắc nghiệm.

Nhiệm vụ của bạn:
- Kiểm tra độ hợp lệ của câu hỏi trắc nghiệm dưới đây dựa trên ngữ cảnh tài liệu tham khảo được cung cấp (nếu có). Nếu không có tài liệu tham khảo, bạn hãy đánh giá dựa trên kiến thức của bạn.

### CÂU HỎI LẤY RA TỪ FILE TÀI LIỆU:
{context}

### CÂU HỎI CẦN KIỂM TRA:
{input}

### YÊU CẦU ĐÁNH GIÁ:
- Nếu nội dung câu hỏi và các đáp án CẦN KIỂM TRA không khớp với nội dung câu hỏi và các đáp án LẤY RA TỪ FILE TÀI LIỆU -> status = false. Lúc này bạn hãy ghi rõ lí do khác ở đâu vào trường reason và đưa ra kết quả đúng cần chỉnh sửa ở trường suggestion. Đồng thời, bạn cũng gán lại nội dung của câu hỏi và đáp án CẦN KIỂM TRA sao cho khớp với nội dung câu hỏi và các đáp án LẤY RA TỪ FILE TÀI LIỆU.
- Nếu câu hỏi cần kiểm tra không có đáp án đúng -> status = false và chỉ rõ lý do ở trường reason. Sau đó bạn hãy dựa vào dữ liệu của bạn và đưa ra đề xuất đáp án nào là đáp án đúng ở trường suggestion và giải thích chi tiết ở trường reason.
- Nếu bạn đồng ý với đáp án đã chọn trong câu hỏi → hãy để correct = true cho câu hỏi đó.
- Nếu câu hỏi hợp lệ nhưng đáp án mà bạn cho là đúng khác với đáp án được chọn trong câu hỏi → hãy đề xuất đáp án đúng bạn cho là chính xác vào trường suggestion và giải thích lý do tại sao bạn lại chọn đáp án đó tại trường reason. Vẫn giữ nguyên isCorrect = true ở đáp án được chọn
- Trường "questionTypeId" trong "question":
   + Nếu chỉ có 1 đáp án đúng được chọn → giá trị là "single_choice".
   + Nếu có 2 đáp án đúng trở lên được chọn → giá trị là "multiple_choice".
   + Nếu không có đáp án nào đúng được chọn → giá trị là "multiple_choice".

### YÊU CẦU ĐỊNH DẠNG TRẢ VỀ:
Chỉ trả về kết quả JSON theo đúng định dạng sau:
{format}

### LƯU Ý:
- Chỉ trả về đúng JSON kết quả, không thêm bất kỳ giải thích, văn bản thừa hay hướng dẫn nào khác.
- Trường media luôn luôn trả về null