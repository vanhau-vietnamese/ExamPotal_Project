import { createBrowserRouter } from 'react-router-dom';
import { AuthLayout, DashBoardLayout, StudentLayout } from '~/layouts';
import { NotFound, SignInPage, Main, SignUpPage } from '~/pages';
import router from './const';

const routes = createBrowserRouter([
  {
    path: router.root,
    element: <Main />,
  },
  {
    path: router.dashboard,
    element: <DashBoardLayout />,
  },
  {
    path: router.student,
    element: <StudentLayout />,
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
