import {
  FacebookAuthProvider,
  GoogleAuthProvider,
  onAuthStateChanged,
  signInWithEmailAndPassword,
  signInWithPopup,
  signOut as signOutFirebase,
} from 'firebase/auth';
import { useLayoutEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { createAccountWithSocial, getMe, register } from '~/apis/authApis';
import { authentication } from '~/config';
import { router } from '~/routes';
import { useUserStore } from '~/store';

export function useAuth() {
  const navigate = useNavigate();
  const { user, setUser } = useUserStore((state) => state);
  const [loading, setLoading] = useState(true);

  useLayoutEffect(() => {
    setLoading(true);

    const unregisterAuthObserver = onAuthStateChanged(authentication, async (credential) => {
      if (credential) {
        localStorage.setItem('access_token', credential.accessToken);
        getMe()
          .then((res) => {
            setUser(res.data);
            setLoading(false);
          })
          .catch((err) => {
            localStorage.removeItem('access_token');
            toast.error(err.message, { toastId: 'user_fetching' });
            setLoading(false);
          });
      } else {
        localStorage.removeItem('access_token');
        setLoading(false);
      }
    });

    return () => unregisterAuthObserver();
  }, [setUser]);

  const signInWithSocial = async (platform) => {
    setLoading(true);

    let provider = null;
    switch (platform) {
      case 'google':
        provider = new GoogleAuthProvider();
        break;
      case 'facebook':
        provider = new FacebookAuthProvider();
        break;
      default:
        break;
    }

    signInWithPopup(authentication, provider)
      .then(async ({ user: data }) => {
        localStorage.setItem('access_token', data.accessToken);
        getMe()
          .then((res) => setUser(res.data))
          .catch(
            async () =>
              await createAccountWithSocial({
                fullName: data.displayName,
                email: data.email,
                firebaseId: data.uid,
                password: data.uid,
              })
                .then(() => {
                  setLoading(false);
                  navigate(router.root);
                })
                .catch((err) => {
                  signOutFirebase(authentication);
                  localStorage.removeItem('access_token');
                  setLoading(false);
                  toast.error(err.message, { toastId: 'user_fetching' });
                })
          );
      })
      .catch((error) => {
        setLoading(false);
        toast.error(error.message, { toastId: 'social_login' });
      });
  };

  const signInWithEmailPassword = ({ email, password }) =>
    signInWithEmailAndPassword(authentication, email, password)
      .then(async ({ user }) => {
        localStorage.setItem('access_token', user.accessToken);
        await getMe()
          .then((res) => {
            setUser(res.data);
            setLoading(false);
          })
          .catch(() => {
            signOutFirebase(authentication);
            localStorage.removeItem('access_token');
            toast.error('Thông tin đăng nhập không chính xác', {
              toastId: 'user_fetching',
            });
          });
      })
      .catch(() => {
        setLoading(false);
        throw new Error('Thông tin đăng nhập không chính xác');
      });

  const signOut = () => {
    signOutFirebase(authentication)
      .then(() => {
        setUser(null);
        localStorage.removeItem('access_token');
        navigate(router.signIn);
      })
      .catch((error) => {
        toast.error(error.message, { toastId: 'firebase_sign_out' });
      });
  };

  const signUpAccount = async ({ fullName, email, password }) => {
    try {
      const res = await register({ fullName, email, password });
      localStorage.setItem('access_token', res.data);
    } catch (error) {
      setLoading(false);
      throw error;
    }
  };

  return {
    user,
    loading,
    signInWithEmailPassword,
    signInWithSocial,
    signUpAccount,
    signOut,
  };
}
