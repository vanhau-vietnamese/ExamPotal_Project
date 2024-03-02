import { Navigate } from 'react-router-dom';
import { Backdrop, Loading } from '~/components';
import { useAuth } from '~/hooks';
import { router } from '~/routes';
import { useUserStore } from '~/store';

export default function Main() {
  const user = useUserStore((state) => state.user);
  const { loading } = useAuth();

  if (loading) {
    return (
      <Backdrop opacity={0}>
        <div className="flex items-center justify-center w-full h-full">
          <Loading />
        </div>
      </Backdrop>
    );
  }

  return user ? (
    <Navigate to={user.role === 'student' ? router.student : router.dashboard} />
  ) : (
    <Navigate to={router.signIn} />
  );
}
