import { z } from 'zod';

export const FormQuestionCreateSchema = z.object({
  questionType: z.string(),
  category: z.string(),
  content: z.string().refine((content) => content.length > 0, {
    message: 'Nội dung câu hỏi không được để trống!',
  }),
  answers: z.array(
    z.object({
      content: z.string().refine((content) => content.length > 0, {
        message: 'Nội dung câu hỏi không được để trống!',
      }),
      isCorrect: z.boolean().default(false),
    })
  ),
});
