/** @type {import('tailwindcss').Config} */
export default {
	content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
	theme: {
		extend: {
			fontFamily: {
				body: ["Inter", "sans-serif"],
				landing: ["Nunito", "sans-serif"],
			},
			colors: {
				primary: "#54b862",
				primary20: "#5cbd2c",
				primary40: "#64b36e",
				primary60: "#88c2a5",
				primary80: "#e8fff4",
				info: "#11c5db",
				strike: "#d1d2db",
				icon: "#3b3e66",
				icon2: "#7a7b97",
				error: "#EB5757",
				danger: "#f83245",
			},
			backgroundImage: {
				bg_gradient:
					"linear-gradient(81.68deg,#5ca139 18.74%,#bdcf99 83.58%,#d7e9a6 99.67%)",
				primary_gradient:
					"linear-gradient(118deg, #54b862, rgba(84, 184, 98, 0.7))",
				hero_banner: "url(./assets/images/bg_banner.png)",
			},
			boxShadow: {
				invalid: "0 0 0 0.2rem #eb575733",
				success: "0 0.25rem 0.55rem rgba(84,184,98,.35)",
				success_hover: "0 0 10px 1px rgba(84,184,98,.7)",
				sidebar: "0 0 15px 0 rgba(34,41,47,.05)",
				card: "rgb(145 158 171 / 24%) 0px 0px 2px 0px, rgb(145 158 171 / 24%) 0px 16px 32px -4px",
				dropdown:
					"0 0.313rem 0.8rem rgba(122,123,151,.5), 0 0.126rem 0.225rem rgba(122,123,151,.3)",
				dangers: "0 0.25rem 0.55rem rgba(248,50,69,.35)",
				dangers_hover:
					"0 0.22rem 0.525rem rgba(248,50,69,.4), 0 0.0625rem 0.385rem rgba(248,50,69,.54)",
			},
		},
	},
	plugins: [],
};
