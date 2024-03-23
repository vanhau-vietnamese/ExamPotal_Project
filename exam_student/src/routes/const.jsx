import Icons from '~/assets/icons';

const router = {
  root: '/',
  student: '/student',
  admin: '/admin',
  question: '/admin/question',
  signIn: '/auth/sign-in',
  signUp: '/auth/sign-up',
};

export const AdminNavLinks = [
  {
    path: `${router.admin}/overview`,
    name: 'Dashboard',
    icon: <Icons.ChartPie />,
  },
  {
    path: router.question,
    name: 'Danh sách câu hỏi',
    icon: <Icons.DocumentText />,
  },
];

export const StudentNavLinks = [
  {
    path: `${router.student}/excises`,
    name: 'Luyệnt tập',
    icon: <Icons.BookOpen />,
  },
];

export default router;
