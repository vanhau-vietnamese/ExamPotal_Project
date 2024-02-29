import { createBrowserRouter } from 'react-router-dom';
import { AuthLayout } from '~/layouts';
import { NotFound, SignInPage, HomePage, SignUpPage } from '~/pages';

const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
  },
  {
    path: '/auth',
    element: <AuthLayout />,
    children: [
      {
        path: 'sign-in',
        element: <SignInPage />,
      },
      {
        path: 'sign-up',
        element: <SignUpPage />,
      },
    ],
  },
  {
    path: '*',
    element: <NotFound />,
  },
]);

export default router;
