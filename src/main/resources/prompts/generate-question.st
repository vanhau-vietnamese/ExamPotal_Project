Bạn là một chuyên gia giáo dục. Dựa vào tài liệu sau, hãy tạo ra {number} câu hỏi trắc nghiệm phù hợp.

Tài liệu tham khảo:
{context}

Yêu cầu quan trọng:
1. Tạo {number} câu hỏi trắc nghiệm dựa trên tài liệu trên.
2. {message}
3. Mỗi câu hỏi phải có từ 3 đến 5 đáp án.
4. Mỗi câu hỏi bắt buộc phải có ít nhất một đáp án đúng. Các đáp án đúng phải có trường "isCorrect" có giá trị true (kiểu dữ liệu Boolean). Các đáp án sai phải có trường "isCorrect" có giá trị false.
5. Trường "questionTypeId":
   - Nếu câu hỏi chỉ có 1 đáp án đúng → giá trị là "single_choice".
   - Nếu câu hỏi có từ 2 đáp án đúng trở lên → giá trị là "multiple_choice".
6. Trường "marksOfQuestion" của mỗi câu hỏi luôn bằng 1.
7. Trường "content" trong "question" là nội dung của câu hỏi.
8. Trường "content" trong "answerRequestList" là nội dung của đáp án.
9. Khi bạn tạo câu hỏi, bạn bắt buộc phải chọn đáp án đúng cho câu hỏi (ít nhất 1 đáp án đúng và gắn isCorrect = true cho đán án đúng đó)
10. Dựa vào trường "reason", BẮT BUỘC gắn giá trị isCorrect = true hoặc isCorrect = false cho các đáp án sao cho phù hợp
11. Trường "reason" là lời giải thích chi tiết vì sao đáp án đúng được chọn. Trường này bắt buộc phải có cho mỗi câu hỏi.
12. Trường "status":
   - Có giá trị true nếu trong câu hỏi có ít nhất một đáp án đúng được chọn.
   - Có giá trị false nếu không có đáp án đúng nào (trường hợp này không được khuyến khích xảy ra).

Yêu cầu về kết quả:
- Chỉ trả về JSON dữ liệu theo đúng format sau:
{format}
- Không được trả về JSON Schema.
- Không được trả về bất kỳ giải thích, hướng dẫn, văn bản mô tả, hoặc kết luận nào ngoài JSON dữ liệu.
- Đảm bảo JSON trả về là danh sách các câu hỏi trắc nghiệm hoàn chỉnh và chính xác, để có thể gửi trực tiếp cho frontend.

Vui lòng đảm bảo dữ liệu đúng 100% với yêu cầu trên.
