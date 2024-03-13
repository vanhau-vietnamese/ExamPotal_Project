import { createBrowserRouter } from 'react-router-dom';
import { AuthLayout, DashBoardLayout } from '~/layouts';
import { SignInPage, SignUpPage } from '~/pages/auth';
import { AdminMain, Question } from '~/pages/admin';
import { StudentMain } from '~/pages/student';
import NotFound from '~/pages/NotFound';

import router from './const';
import { Navigate } from 'react-router-dom';

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
        ],
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
