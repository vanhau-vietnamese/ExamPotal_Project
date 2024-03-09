/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        body: ['Inter', 'sans-serif'],
      },
      colors: {
        primary: '#54b862',
        strike: '#d1d2de',
        icon: '#3b3e66',
        error: '#EB5757',
        danger: '#f83245',
      },
      boxShadow: {
        invalid: '0 0 0 0.2rem #eb575733',
        success: '0 0.25rem 0.55rem rgba(84,184,98,.35)',
        success_hover: '0 0 10px 1px rgba(84,184,98,.7)',
        card: 'rgb(145 158 171 / 24%) 0px 0px 2px 0px, rgb(145 158 171 / 24%) 0px 16px 32px -4px',
        dangers: '0 0.25rem 0.55rem rgba(248,50,69,.35)',
        dangers_hover:
          '0 0.22rem 0.525rem rgba(248,50,69,.4), 0 0.0625rem 0.385rem rgba(248,50,69,.54)',
      },
    },
  },
  plugins: [],
};
