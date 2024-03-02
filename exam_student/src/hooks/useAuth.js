import { GoogleAuthProvider, onAuthStateChanged, signInWithPopup } from 'firebase/auth';
import { useLayoutEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authentication } from '~/config';
import { router } from '~/routes';
import { useUserStore } from '~/store';

export function useAuth() {
  const navigate = useNavigate();
  const { setUser } = useUserStore((state) => state);
  const [loading, setLoading] = useState(true);

  useLayoutEffect(() => {
    setLoading(true);

    const unregisterAuthObserver = onAuthStateChanged(authentication, (credential) => {
      if (credential) {
        localStorage.clear();
        const data = credential.providerData[0];
        const firebaseId = data.uid;
        localStorage.setItem('accessToken', credential.accessToken);
        setUser({ ...data, firebaseId });
        setLoading(false);
      } else {
        setLoading(false);
      }
    });

    return () => unregisterAuthObserver();
  }, [setUser]);

  const signInWithGoogle = async () => {
    setLoading(true);
    const provider = new GoogleAuthProvider();
    signInWithPopup(authentication, provider)
      .then(({ user: userData, _tokenResponse }) => {
        setUser(userData);
        localStorage.setItem('token', _tokenResponse);
        navigate(router.root);
      })
      .catch((error) => {
        setLoading(false);
        console.error(error.message);
      });
  };

  return {
    loading,
    signInWithGoogle,
  };
}
