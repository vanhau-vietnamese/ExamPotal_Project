Bạn là một chuyên gia đánh giá câu hỏi trắc nghiệm. Dưới đây là một câu hỏi cần bạn kiểm tra độ hợp lệ.

CÂU HỎI TRẮC NGHIỆM CẦN KIỂM TRA:
{input}

- Nếu câu hỏi trắc nghiệm không hợp lí thì status = false
- Nếu câu hỏi trắc nghiệm là hợp lí thì status = true

- Nếu đáp án đúng được chọn của câu hỏi khác với đáp án mà bạn chọn, thì bạn hãy gợi ý đáp án bạn chọn vào phần suggestion và giải thích vì sao bạn chọn đáp án đó ở phần reason

YÊU CẦU PHẢN HỒI:
Trả về JSON với format sau:
{format}

- Phần question của format chính là phần question của input. Còn nếu đáp án đúng được chọn của câu hỏi giống với đáp án mà bạn chọn, thì correct = true ứng với câu đó

Chỉ trả về đúng định dạng JSON, không thêm bất kỳ văn bản nào khác.
