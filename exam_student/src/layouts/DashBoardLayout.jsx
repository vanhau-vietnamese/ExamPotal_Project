import { useNavigate } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import { Backdrop, Loading } from '~/components';
import { useAuth } from '~/hooks';
import { router } from '~/routes';
import { Header, SideBar } from './components';
import { useLayoutEffect } from 'react';

function DashBoardLayout() {
  const navigate = useNavigate();
  const { user, loading } = useAuth();

  useLayoutEffect(() => {
    if (!loading) {
      if (!user) {
        navigate(router.signIn, { replace: true });
      } else {
        navigate(user.role === 'student' ? router.student : router.admin, { replace: true });
      }
    }
  }, [loading, navigate, user]);

  return (
    <div className="app-wrapper bg-[#f8f9f9] min-h-screen">
      {loading ? (
        <Backdrop opacity={0}>
          <div className="flex flex-col items-center justify-center w-full h-full">
            <Loading />
            <h4 className="font-semibold text-center text-icon mt-4">
              Hệ thống đang xử lý,{' '}
              <span className="font-semibold text-icon">Xin vui lòng chờ trong giây lát!</span>
            </h4>
          </div>
        </Backdrop>
      ) : (
        <>
          <div>
            <SideBar />
          </div>
          <div className="relative app-main">
            <Header />
            <div className="app-content !pl-[280px] pt-16">
              <div className="app-content--inner">
                <Outlet />
              </div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default DashBoardLayout;
