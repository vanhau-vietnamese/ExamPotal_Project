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
};

export const SidebarNavLinks = [
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
  {
    path: router.exam,
    name: 'Danh sách bài tập',
    icon: <Icons.Academic />,
  },
];

export default router;
