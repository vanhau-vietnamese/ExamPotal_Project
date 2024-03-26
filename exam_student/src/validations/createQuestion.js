import { z } from 'zod';

  export const FormCreateQuestionInput = z.object({
    answer: z.string().min(1, 'Vui lòng nhập nội dung câu hỏi!'),
  });
