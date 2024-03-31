import Icons from '~/assets/icons';

const router = {
  root: '/',
  student: '/student',
  practice: '/student/practice',
  admin: '/admin',
  question: '/admin/question',
  exam: '/admin/exam',
  signIn: '/auth/sign-in',
  signUp: '/auth/sign-up',
  detailExam: '/admin/exam/detailExam',
  listQuestionChosse: '/admin/exam/listQuestionChosse',
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
  ['student']: router.admin,
};

export default router;
