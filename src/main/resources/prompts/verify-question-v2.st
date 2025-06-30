Bạn là một chuyên gia trích xuất dữ liệu từ tài liệu trắc nghiệm. Dưới đây là nội dung của một file trắc nghiệm đã được người dùng cung cấp. Nhiệm vụ của bạn là trích xuất lại chính xác tất cả các câu hỏi trắc nghiệm có trong tài liệu này.

Tài liệu tham khảo:
{context1}

Yêu cầu quan trọng:
1. Trích xuất đầy đủ tất cả các câu hỏi trắc nghiệm có trong tài liệu, giữ nguyên nội dung gốc.
2. Nếu tài liệu có chỉ rõ đáp án đúng, bạn phải gán cho đáp án đó trường "isCorrect" với giá trị true (kiểu dữ liệu Boolean). Các đáp án còn lại gán "isCorrect" là false.
3. Nếu tài liệu không ghi đáp án đúng, bạn gán "isCorrect" là false cho tất cả các đáp án.
4. Trường "questionTypeId" trong "question":
   - Nếu chỉ có 1 đáp án đúng được chọn → giá trị là "single_choice".
   - Nếu có 2 đáp án đúng trở lên được chọn → giá trị là "multiple_choice".
   - Nếu không có đáp án đúng được chọn → giá trị là "multiple_choice".
5. Trường "marksOfQuestion" trong "question" luôn bằng 1.
6. Trường "reason" giải thích tại sao chọn đáp án đúng.
7. Trường "status":
   - Có giá trị true nếu trong "question" có ít nhất một đáp án đúng được chỉ định trong tài liệu.
   - Có giá trị false nếu trong "question" không có đáp án đúng được chỉ định trong (câu hỏi không có đáp án đúng cũng là hợp lệ và phải giữ lại).
8. Trường "content" trong "question" là nội dung của câu hỏi.
9. Trường "content" trong "answerRequestList" là nội dung của đáp án.

Kết quả yêu cầu:
- Trả về đúng định dạng JSON sau:
{format}
- Tuyệt đối không được trả về JSON Schema, không được trả về bất kỳ giải thích, văn bản mô tả, ví dụ hay hướng dẫn nào ngoài JSON kết quả.

⚠️ Lưu ý: Phải giữ nguyên nội dung câu hỏi và đáp án như trong tài liệu. Không được tự tạo mới hoặc thêm bớt nội dung.
