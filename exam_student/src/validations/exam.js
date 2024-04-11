import { z } from 'zod';

export const FormExamCreateSchema = z.object({
    category: z.union([z.string(), z.number()]),
  examName: z.string().refine((examName) => examName.length > 0),
});
