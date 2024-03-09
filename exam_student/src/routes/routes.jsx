import { createBrowserRouter } from 'react-router-dom';
import { AuthLayout, DashBoardLayout } from '~/layouts';
import { SignInPage, SignUpPage } from '~/pages/auth';
import { AdminMain } from '~/pages/admin';
import { StudentMain } from '~/pages/student';
import NotFound from '~/pages/NotFound';

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
            element: <AdminMain />,
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
