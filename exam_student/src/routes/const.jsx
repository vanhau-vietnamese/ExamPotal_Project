import Icons from '~/assets/icons';

const router = {
  root: '/',
  student: '/student',
  practice: '/student/practice',
  admin: '/admin',
  studentList: '/admin/students',
  question: '/admin/question',
  configuration: '/admin/configurations',
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
    icon: <Icons.Dashboard />,
  },
  {
    path: router.exam,
    name: 'Kho bài tập',
    icon: <Icons.Paper />,
  },
  {
    path: router.question,
    name: 'Kho câu hỏi',
    icon: <Icons.DocumentText />,
  },
  {
    path: router.studentList,
    name: 'Học viên',
    icon: <Icons.Academic />,
  },
  {
    path: router.configuration,
    name: 'Cấu hình',
    icon: <Icons.Configuration />,
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
  ['student']: router.student,
};

export default router;
