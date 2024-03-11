import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Outlet } from 'react-router-dom';
import { Backdrop, Loading } from '~/components';
import { useAuth } from '~/hooks';
import { router } from '~/routes';

function AuthLayout() {
  const navigate = useNavigate();
  const { user, loading } = useAuth();

  useEffect(() => {
    if (!loading && user) {
      navigate(user.role === 'student' ? router.student : router.admin, { replace: true });
    }
  }, [loading, navigate, user]);

  return (
    <div className="min-h-screen app-wrapper">
      {loading && (
        <Backdrop opacity={0.15} className="backdrop-blur-[2px]">
          <div className="flex items-center justify-center w-full h-full">
            <Loading />
          </div>
        </Backdrop>
      )}
      <div className="app-main font-body">
        <div className="app-content">
          <div className="items-center app-content--inner">
            <div className="flex items-center flex-grow w-full">
              <div className="relative z-10 w-full">
                <div className="w-full px-[20px] mx-auto md:max-w-[1140px]">
                  <Outlet />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AuthLayout;
