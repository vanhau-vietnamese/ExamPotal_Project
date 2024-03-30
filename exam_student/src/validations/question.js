import { z } from 'zod';

export const FormQuestionCreateSchema = z.object({
  questionType: z.string(),
  content: z.string().refine((content) => content.length > 0, {
    message: 'Nội dung câu hỏi không được để trống!',
  }),
  // media: z.array(z.string()),
  answers: z.object({
    content: z.string().refine((content) => content.length > 0, {
      message: 'Nội dung câu hỏi không được để trống!',
    }),
    isCorrect: z.boolean().default(false),
    // media: z.array(z.string()),
  }),
});
