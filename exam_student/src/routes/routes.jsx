import { Navigate, createBrowserRouter } from 'react-router-dom';
import { AuthLayout, DashBoardLayout } from '~/layouts';
import NotFound from '~/pages/NotFound';
import { AdminMain } from '~/pages/admin';
import { DetailExam, ExamList } from '~/pages/admin/Exam';
import { QuestionList } from '~/pages/admin/Queston';
import { SignInPage, SignUpPage } from '~/pages/auth';
import { StartPractice, StudentExcises } from '~/pages/student';
import router from './const';

const routes = createBrowserRouter([
  {
    path: router.root,
    element: <DashBoardLayout />,
    children: [
      {
        path: router.admin,
        children: [
          {
            index: true,
            element: <Navigate to={`${router.admin}/overview`} />,
          },
          {
            path: 'overview',
            element: <AdminMain />,
          },

          {
            path: 'question',
            element: <QuestionList />,
          },
          {
            path: 'exam',
            children: [
              {
                index: true,
                element: <ExamList />,
              },
              {
                path: 'detailExam',
                element: <DetailExam />,
              },
            ],
          },
        ],
      },
      {
        path: router.student,
        children: [
          {
            index: true,
            element: <Navigate to={`${router.student}/excises`} />,
          },
          {
            path: 'excises',
            element: <StudentExcises />,
          },
          {
            path: 'practice',
            element: <StartPractice />,
          },
        ],
      },
    ],
  },

  // Auth Route
  {
    path: '/auth',
    element: <AuthLayout />,
    children: [
      {
        path: router.signIn,
        element: <SignInPage />,
      },
      {
        path: router.signUp,
        element: <SignUpPage />,
      },
    ],
  },
  {
    path: '*',
    element: <NotFound />,
  },
]);

export default routes;
