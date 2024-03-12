import { useNavigate } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import { Backdrop, Loading, TypeLanguges } from '~/components';
import { useAuth } from '~/hooks';
import { router } from '~/routes';
import { Header, SideBar } from './components';
import { useEffect } from 'react';

function DashBoardLayout() {
  const navigate = useNavigate();
  const { user, loading } = useAuth();

  useEffect(() => {
    if (!loading) {
      if (!user) {
        navigate(router.signIn, { replace: true });
      } else {
        navigate(user.role === 'student' ? router.student : router.admin, { replace: true });
      }
    }
  }, [loading, navigate, user]);

  return (
    <div>
      {loading ? (
        <Backdrop opacity={0}>
          <div className="flex items-center justify-center w-full h-full">
            <Loading />
          </div>
        </Backdrop>
      ) : (
        <>
          <Header />
          <main className="flex">
            <SideBar />
            <section className="bg-slate-300 m-3 rounded-md h-full max-w-fit ">
              <TypeLanguges></TypeLanguges>
              <Outlet />
            </section>
          </main>
        </>
      )}
    </div>
  );
}

export default DashBoardLayout;