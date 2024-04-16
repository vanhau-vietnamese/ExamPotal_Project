import Icons from '~/assets/icons';

const router = {
  root: '/',
  student: '/student',
  practice: '/student/practice',
  admin: '/admin',
  question: '/admin/question',
  exam: '/admin/exam',
  category: '/admin/category',
  signIn: '/auth/sign-in',
  signUp: '/auth/sign-up',
};

export const AdminNavLinks = [
  {
    path: `${router.admin}/overview`,
    name: 'Tổng quan',
    icon: <Icons.ChartPie />,
  },
  {
    path: router.question,
    name: 'Danh sách câu hỏi',
    icon: <Icons.DocumentText />,
  },
  {
    path: router.exam,
    name: 'Danh sách bài tập',
    icon: <Icons.Academic />,
  },
  // {
  //   path: router.category,
  //   name: 'Danh mục',
  //   icon: <Icons.Menu />,
  // },
];

export const StudentNavLinks = [
  {
    path: `${router.student}/excises`,
    name: 'Luyện tập',
    icon: <Icons.BookOpen />,
  },
];

export const RoleRootRoute = {
  ['admin']: router.admin,
  ['student']: router.student,
};

export default router;
