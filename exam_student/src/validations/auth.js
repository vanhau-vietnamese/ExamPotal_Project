import { z } from "zod";

export const FormSignInInput = z.object({
	email: z.string().email("Email address is not valid!"),
	password: z
		.string()
		.min(8, "Password must be at least 8 characters!")
		.refine(
			(password) => {
				const uppercaseRegex = /[A-Z]/;
				const symbolRegex = /[^a-zA-Z0-9]/;
				return uppercaseRegex.test(password) && symbolRegex.test(password);
			},
			{
				message: "Password must be at least 1 uppercase letters and 1 symbols!",
			}
		),
});
