import { createBrowserRouter } from 'react-router-dom';

import { AuthLayout, DashBoardLayout } from '~/layouts';
import { SignInPage, SignUpPage } from '~/pages/auth';
import NotFound from '~/pages/NotFound';

import router from './const';
import { AdminMain } from '~/pages/admin';
import { StudentMain } from '~/pages/student';
import { QuestionList } from '~/pages';
import DetailExam from '~/pages/DetailExam';

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
            element: <AdminMain />,
          },

          {
            path: router.detailexam,
            element: <DetailExam />,
          },
        ],
      },
      {
        path: router.questionlist,
        element: <QuestionList />,
      },
      {
        path: router.student,
        children: [
          {
            index: true,
            element: <StudentMain />,
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
