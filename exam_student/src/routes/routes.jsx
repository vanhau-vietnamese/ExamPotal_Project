import { createBrowserRouter } from 'react-router-dom';
import { AuthLayout, DashBoardLayout } from '~/layouts';
import { SignInPage, SignUpPage } from '~/pages/auth';
import { AdminMain, Question } from '~/pages/admin';
import { StartPractice, StudentMain } from '~/pages/student';
import NotFound from '~/pages/NotFound';

import router from './const';
import { Navigate } from 'react-router-dom';
import { DetailExam, ExamList } from '~/pages/admin/Exam';

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
            element: <Question />,
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
            element: <StudentMain />,
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
