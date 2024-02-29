import { z } from 'zod';

const uppercaseRegex = /[A-Z]/;
const symbolRegex = /[^a-zA-Z0-9]/;

export const FormSignInInput = z.object({
  email: z.string().email('Địa chỉ email không hợp lệ!'),
  password: z
    .string()
    .min(8, 'Mật khẩu phải có ít nhất 8 ký tự!')
    .refine(
      (password) => {
        const uppercaseRegex = /[A-Z]/;
        const symbolRegex = /[^a-zA-Z0-9]/;
        return uppercaseRegex.test(password) && symbolRegex.test(password);
      },
      {
        message: 'Mật khẩu phải có ít nhất 1 ký tự viết hoa và 1 ký tự đặc biệt!',
      }
    ),
});

export const FormSignUpInput = z
  .object({
    fullName: z.string().min(3, 'Họ và tên phải có ít nhất 3 ký tự!'),
    email: z.string().email('Địa chỉ email không hợp lệ!'),
    password: z
      .string()
      .min(8, 'Mật khẩu phải có ít nhất 8 ký tự!')
      .refine((password) => uppercaseRegex.test(password) && symbolRegex.test(password), {
        message: 'Password must be at least 1 uppercase letters and 1 symbols!',
      }),
    confirmPassword: z.string().min(8, 'Mật khẩu phải có ít nhất 8 ký tự!'),
  })
  .refine((values) => values.password === values.confirmPassword, {
    message: 'Passwords must match!',
    path: ['confirmPassword'],
  });
